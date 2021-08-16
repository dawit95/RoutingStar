<template>
  <v-flex xs12 class="big-box rounded-lg">
    <!-- <draggable class="card-box" @update="onUpdated"> -->
    <div>
      <v-list
        outlined
        v-for="(place, idx) in places"
        :key="place.createdOrder"
        class="rounded-lg mb-2"
      >
        <v-card flat class="d-flex">
          <v-card flat>
            <v-icon
              large
              color="indigo"
              right
              style="cursor: pointer;"
              class=" ma-0 pa-0 drag-position"
              >mdi-drag-horizontal-variant</v-icon
            >
            <post-image-input
              v-model="avatar"
              :place="place"
              @update-tumbnail-image="updateThumbnailImage"
            >
              <div slot="activator">
                <v-avatar
                  size="140px"
                  rounded
                  v-ripple
                  v-if="!place.placeImg"
                  class="grey lighten-3 ml-1"
                >
                  <v-icon color="indigo" dark x-large>
                    <!-- 2A355D / 303c6c -->
                    mdi-plus
                  </v-icon>
                </v-avatar>
                <v-avatar size="140px" rounded v-ripple v-else class="lighten ml-1">
                  <img :src="place.placeImg" alt="Image" />
                </v-avatar>
              </div>
            </post-image-input>
          </v-card>
          <v-card flat class="d-flex flex-column">
            <v-text-field
              v-model="place.content"
              @click="activePoint(place)"
              @mouseout="stopPoint(place)"
              label="간략한 설명"
              color="indigo"
              class="text-field-font"
              rows="2"
            >
              <v-icon right slot="prepend" color="grey">mdi-comment</v-icon>
            </v-text-field>
            <v-switch
              class="switch-prop pa-0 text-field-font"
              id="thumbnail_switch"
              @click="refreshThumbnailBtn(place)"
              :label="place.isThumbnail ? thumbnailLabel : '썸네일 등록하기!'"
              :disabled="(!place.isThumbnail && isthumbail) || !place.imageUpload"
              :v-model="place.isThumbnail"
              inset
              color="indigo darken-3"
            ></v-switch>
          </v-card>

          <v-card flat class="d-flex flex-column">
            <v-icon
              left
              style="cursor: pointer;"
              class="pa-0 ma-0 justify-end align-start"
              @click="removePoint(place.marker, idx)"
              >mdi-close</v-icon
            >
            <v-spacer></v-spacer>
            <!-- <v-icon drak right style="cursor: pointer;" class=" ma-0 pa-0">mdi-drag-horizontal-variant</v-icon> -->
            <!-- <v-icon class="d-flex col-9 justify-end">mdi-plus</v-icon> -->
          </v-card>
        </v-card>
      </v-list>
    </div>
    <!-- </draggable> -->
    <!-- <v-btn @click="postPointImages">확인용 버튼</v-btn> -->
  </v-flex>
</template>

<script>
// import axios from 'axios'
import { mapGetters, mapMutations } from 'vuex';
// import draggable from 'vuedraggable'
import { dragscroll } from 'vue-dragscroll';
import AWS from 'aws-sdk';
import PostImageInput from '@/components/routes/PostImageInput.vue';

export default {
  name: 'MapPointFormS3',
  components: {
    // draggable,
    PostImageInput: PostImageInput,
  },
  props: {
    isthumbail: {
      type: Boolean,
    },
  },
  directives: {
    dragscroll,
  },
  data() {
    return {
      // 현재 썸네일이 골라진 상태인지 유무
      // isthumbail: false,
      selectedFile: null,
      thumbnailLabel: '',
      avatar: null,
      albumBucketName: 'routingstar-photo-album',
      bucketRegion: 'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
    };
  },
  computed: {
    ...mapGetters(['places', 'imgList', 'polyLine', 'thumbnailImage']),
  },
  // created() {
  //   this.getFiles()
  // },
  methods: {
    // 일단 데이터의 조작만 있고, 모든 데이터의 input이 완료된 이후에 백앤드와 통신하니
    // 일단은 mutations함수를 사용하였습니다.
    ...mapMutations(['UPDATE_DRAGGERBLE_ITEMS', 'REFRESH_PLACES']),
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

    // deleteItem 함수는 삭제하고 각각 component에서 조건에 맞게 포인트를 삭제한 후에
    // 삭제된 places를 변수로 설정 후 mutation 함수로 state에 반영하는 것으로 바꿈
    removePoint(marker, idx) {
      if (idx != -1) {
        marker.location.setMap(null);
        const oldItems = this.places;
        const newplaces = oldItems.filter((place) => {
          if (place.createdOrder === marker.createdOrder && place.isThumbnail) {
            this.$emit('change-isthumbail');
            place.isThumbnail = false;
            this.thumbnailLabel = '';
            this.$store.state.images.thumbnailChecked = false;
          }
          return place.createdOrder !== marker.createdOrder;
        });
        this.REFRESH_PLACES(newplaces);
        this.refreshPolyline();
      }
    },
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const places = this.places;
      console.log(places);
      path.clear();
      for (const point of places) {
        path.push(new window.google.maps.LatLng(point.lat, point.lng));
      }
    },
    // forcheck(place) {
    //   console.log(`${place.createdOrder}번째로 생성된 마커의 pk`)
    // },
    // 썸네일 설정, 취소
    refreshThumbnailBtn(place) {
      // 썸네일인 경우 => 썸네일 취소
      if (this.isthumbail) {
        this.$emit('change-isthumbail');
        // this.isthumbail = !this.isthumbail
        place.isThumbnail = false;
        this.thumbnailLabel = '썸네일 등록하기!';
        this.$store.state.images.thumbnailChecked = false;
        // 썸네일이 아닌경우 => 첨부파일이 등록되어있다면 => 썸네일로 지정
      } else if (this.isthumbail === false) {
        this.$emit('change-isthumbail');
        // this.isthumbail = !this.isthumbail
        place.isThumbnail = true;
        this.thumbnailLabel = '썸네일 등록완료!';

        const image = this.imgList[place.createdOrder];
        const date = new Date().getTime();
        AWS.config.update({
          region: this.bucketRegion,
          credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: this.IdentityPoolId,
          }),
        });
        // 썸네일 지정시 프론트에서 바로 업로드(리팩토링 필요)
        var s3 = new AWS.S3({
          apiVersion: '2006-03-01',
          params: { Bucket: this.albumBucketName },
        });
        s3.upload(
          {
            Key: `thumbnail/${date + image.name}`,
            Body: image,
            ContentType: image.type,
            ACL: 'public-read',
          },
          (err, data) => {
            if (err) {
              console.log(err);
              return alert('There was an error uploading your photo: ', err.message);
            }
            this.$store.state.images.thumbnailImage = data.Location;
            this.$emit('update-tumbnail-image', data.Location);
          }
        );
      }
    },
    updateThumbnailImage(imgUrl) {
      this.$emit('update-tumbnail-image', imgUrl);
    },
  },
};
</script>

<style scoped>
.big-box {
  /* width: 300px; */
  height: 330px;
  overflow: scroll;
  margin: 5px 15px 0 15px;
  background-color: #2a355d;
  overflow-x: hidden;
}
.big-box::-webkit-scrollbar {
  width: 15px;
}
.big-box::-webkit-scrollbar-thumb {
  background-color: #2a355d;
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
  margin: 10px 10px 10px 10px;
}
/* .card-list {

} */
input[type='file']::file-selector-button {
  border: 2px solid #2a355d;
  padding: 0.2em 0.4em;
  border-radius: 0.2em;
  background-color: #b4dfe5;
  transition: 1s;
}

input[type='file']::file-selector-button:hover {
  background-color: #fbe8a6;
}

.switch-prop {
  margin: 0 0 0 10px;
}
.drag-position {
  position: absolute;
  background-color: none;
  z-index: 6;
  /* width: 200px; height: 200px; */
  /* object-position: top; */
  left: 5px;
  /* top: 50%; */
}
.text-field-font {
  font-size: 14px;
}
</style>
