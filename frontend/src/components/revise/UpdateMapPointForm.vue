<template>
  <v-flex xs12 class="big-box rounded-lg">
    <draggable class="card-box" @update="onUpdated">
      <v-list
        outlined
        v-for="(place, idx) in places"
        :key="place.createdOrder"
        class="rounded-lg"
      >

        <v-card flat class="d-flex">
          <v-icon drak large right style="cursor: pointer;">mdi-drag-horizontal-variant</v-icon>
          {{ idx }}
          <v-spacer></v-spacer>
          <v-icon left style="cursor: pointer;" @click="removePoint(place.marker, idx)">mdi-close</v-icon>
        </v-card>

        <v-list-item outlined>
          <v-list-item-content class="py-0">
              <input @change="onFileSelected(place)" id="uploadFile" accept="image/*" type="file">
              <!-- <img style="width:50px;" id="preview-image" :src="fileList[place.createdOrder]" alt=""> -->
            <v-textarea v-model="place.content" @click="activePoint(place)" @mouseout="stopPoint(place)" label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"></v-textarea>
          </v-list-item-content>
        </v-list-item>
          <v-switch
          class="switch-prop"
          id="thumbnail_switch"
          @click="refreshThumbnailBtn(place)"
          :label="place.isThumbnail ? thumbnailLabel : '썸네일로 설정하기!'" 
          :disabled="(!place.isThumbnail && isthumbail) || !place.imageUpload"
          :v-model="place.isThumbnail"
          inset color="indigo darken-3"></v-switch>
      </v-list>
    </draggable>
    <!-- <v-btn @click="postPointImages">확인용 버튼</v-btn> -->
  </v-flex>
</template>

<script>
// import axios from 'axios'
import { mapActions, mapGetters, mapMutations, } from 'vuex'
import draggable from 'vuedraggable'
import { dragscroll } from 'vue-dragscroll'
import AWS from 'aws-sdk'

export default {
  name: 'UpdateMapPointForm',
  components: {
    draggable,
  },
  directives: {
    dragscroll
  },
  data () {
    return {
      // 현재 썸네일이 골라진 상태인지 유무
      isthumbail: false,
      selectedFile: null,
      thumbnailLabel: '',
      albumBucketName: 'routingstar-photo-album',
      bucketRegion: 'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
    }
  },
  computed: {
    ...mapGetters(['places', 'imgList', 'polyLine', 'thumbnailImage']),
  },
  methods: {
    // 일단 데이터의 조작만 있고, 모든 데이터의 input이 완료된 이후에 백앤드와 통신하니
    // 일단은 mutations함수를 사용하였습니다.
    ...mapMutations(['UPDATE_DRAGGERBLE_ITEMS','REFRESH_PLACES']),
    ...mapActions(['updateThumbnailImage']),
    onUpdated(event) {
      this.UPDATE_DRAGGERBLE_ITEMS(event);
      this.refreshPolyline();
    },
    activePoint(place) {
      place.marker.location.setAnimation(window.google.maps.Animation.BOUNCE);
    },
    stopPoint(place) {
      place.marker.location.setAnimation(null);
    },
    // 첨부파일 업로드되면 imgList에 지속 업로드
    // 핀이 등록되면 해당 핀에의해 만들어진 리스트 인덱스를 가져와서 해당 인덱스를 활용하여 imgList에 이미지 넣기
    // 이렇게해야만 사용자가 draggable로 리스트 이동후 파일 첨부를 해도 imgList의 올바른 자리에 사진이 갱신됩니다.
    onFileSelected(place) {
      this.selectedFile = event.target.files[0]
      this.imgList[place.createdOrder] = this.selectedFile
      place.imageUpload = true
    },
    // deleteItem 함수는 삭제하고 각각 component에서 조건에 맞게 포인트를 삭제한 후에
    // 삭제된 places를 변수로 설정 후 mutation 함수로 state에 반영하는 것으로 바꿈
    removePoint(marker, idx) {
      if ( idx != -1 ) {
        marker.location.setMap(null);
        const oldItems = this.places
        const newplaces = oldItems.filter((place) => {
          return place.createdOrder !== marker.createdOrder
        })
        this.REFRESH_PLACES(newplaces)
        this.refreshPolyline();
      }
    },
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const places = this.places
      console.log(places)
      path.clear();
      for( const point of places ) {
        path.push( new window.google.maps.LatLng( point.lat, point.lng));
      }
    },
    forcheck(place) {
      console.log(`${place.createdOrder}번째로 생성된 마커의 pk`)
    },
    // 썸네일 설정, 취소
    refreshThumbnailBtn(place) {
      // 썸네일인 경우 => 썸네일 취소
      if (this.isthumbail) {
        this.isthumbail = !this.isthumbail
        place.isThumbnail = false
        this.thumbnailLabel = ''
      // 썸네일이 아닌경우 => 첨부파일이 등록되어있다면 => 썸네일로 지정
      } else if (this.isthumbail === false) {
        this.isthumbail = !this.isthumbail
        place.isThumbnail = true
        this.thumbnailLabel = '썸네일 이미지가 등록되었습니다.'

        const image = this.imgList[place.createdOrder]
        const date = new Date().getTime();
        AWS.config.update({
          region: this.bucketRegion,
          credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
          })
        });
        // 썸네일 지정시 프론트에서 바로 업로드(리팩토링 필요)
        var s3 = new AWS.S3({
          apiVersion: "2006-03-01",
          params: { Bucket: this.albumBucketName }
        });
        s3.upload({
          Key: `thumbnail/${date + image.name}`,
          Body: image,
          ContentType: image.type,
          ACL: 'public-read'
        }, (err, data) => {
          if (err) {
            console.log(err)
            return alert("There was an error uploading your photo: ", err.message);
          }
          this.$store.state.images.thumbnailImage = data.Location
          this.$emit('update-tumbnail-image', data.Location)
      })
      }
    },
  },
}
</script>

<style scoped>
.big-box {
  width: 300px;
  height: 300px;
  overflow: scroll;
  margin: 0 20px 0 15px;
  background-color: #2A355D;
  overflow-x: hidden;
}
.big-box::-webkit-scrollbar {
  width: 10px;
}
.big-box::-webkit-scrollbar-thumb {
  background-color: #2A355D;
  background-clip: padding-box;
  border-radius: 10px;
  border: 2px solid transparent;
}
.big-box::-webkit-scrollbar-track {
  background-color: grey;
  border-radius: 10px;
  box-shadow: inset 0px 0px 5px white;
}
.card-box {
  margin: 10px 10px 0 10px;
}
input[type=file]::file-selector-button {
  border: 2px solid #2A355D;
  padding: .2em .4em;
  border-radius: .2em;
  background-color: #B4DFE5;
  transition: 1s;
}

input[type=file]::file-selector-button:hover {
  background-color: #FBE8A6;
}

.switch-prop {
  margin: 0 0 0 10px;
}
</style>