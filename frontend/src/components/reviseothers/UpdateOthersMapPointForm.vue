<template>
  <v-flex xs12 class="big-box rounded-lg">
    <draggable class="card-box" @update="onUpdated">
      <v-list outlined v-for="(place, idx) in places" :key="place.createdOrder" class="rounded-lg mb-2">

        <v-card flat class="d-flex">
          <v-card flat>
            <v-icon large color="indigo" right style="cursor: pointer;" class=" ma-0 pa-0 drag-position">mdi-drag-horizontal-variant</v-icon>
            <others-image-input v-model="avatar" :place="place" @update-tumbnail-image="updateThumbnailImage">
              <div slot="activator">
                <v-avatar size="140px" rounded v-ripple v-if="!place.placeImg" class="grey lighten-3 ml-1">
                  <v-icon color="indigo" dark x-large>
                    <!-- 2A355D / 303c6c -->
                    mdi-plus
                  </v-icon>
                </v-avatar>
                <v-avatar size="140px" rounded v-ripple v-else class="lighten ml-1">
                  <img :src="place.placeImg" alt="Image">
                </v-avatar>
              </div>
            </others-image-input>
          </v-card>
          <v-card flat class="d-flex flex-column">
            <v-text-field v-model="place.content" @click="activePoint(place)" @mouseout="stopPoint(place)" 
              label="간략한 설명" color="indigo" class="text-field-font" rows="1">
                <v-icon right slot="prepend" color="grey">mdi-comment</v-icon>
              </v-text-field>
              <v-switch
              class="switch-prop"
              id="thumbnail_switch"
              @click="refreshThumbnailBtn(place)"
              :label="place.isThumbnail ? thumbnailLabel : '썸네일 등록하기!'" 
              :disabled="(!place.isThumbnail && isthumbail) || !place.imageUpload" 
              v-model="place.isThumbnail"
              inset color="indigo darken-3"></v-switch>
          </v-card>
          <v-card flat class="d-flex flex-column">
            <v-icon left style="cursor: pointer;" @click="removePoint(place.marker, idx)">mdi-close</v-icon>
            <v-spacer></v-spacer>
          </v-card>

        </v-card>
      </v-list>
    </draggable>
  </v-flex>
</template>

<script>

import { mapGetters, mapMutations, } from 'vuex'
import draggable from 'vuedraggable'
import { dragscroll } from 'vue-dragscroll'
import AWS from 'aws-sdk'
import OthersImageInput from '@/components/reviseothers/OthersImageInput.vue'

export default {
  name: 'UpdateOthersMapPointForm',
  components: {
    draggable,
    OthersImageInput: OthersImageInput,
  },
  props: {
    isthumbail: {
      type: Boolean
    },
    listLength: {
      type: Number
    }
  },
  directives: {
    dragscroll
  },
  data () {
    return {
      // 현재 썸네일이 골라진 상태인지 유무
      // thumbnailChecked: this.isthumbail,
      // isthumbail: false,
      thumbnailLabel: '썸네일 이미지가 등록되었습니다.',
      albumBucketName: 'routingstar-photo-album',
      bucketRegion: 'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
      avatar: null,
    }
  },
  computed: {
    ...mapGetters(['places', 'imgList', 'polyLine', 'thumbnailImage']),
  },
  methods: {
    // 일단 데이터의 조작만 있고, 모든 데이터의 input이 완료된 이후에 백앤드와 통신하니
    // 일단은 mutations함수를 사용하였습니다.
    ...mapMutations(['UPDATE_DRAGGERBLE_ITEMS','REFRESH_PLACES']),
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
    removePoint(marker, idx) {
      if ( idx != -1 ) {
        marker.location.setMap(null);
        const oldItems = this.places
        const newplaces = oldItems.filter((place) => {
          if (place.createdOrder === marker.createdOrder && place.isThumbnail) {
            this.$emit('change-isthumbail')
            // this.isthumbail = !this.isthumbail
            place.isThumbnail = false
            this.thumbnailLabel = ''
            this.$store.state.images.thumbnailChecked = false
          }
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
    // refreshThumbnailBtn(place) {
    //   // 썸네일인 경우 => 썸네일 취소
    //   if (this.isthumbail) {
    //     this.isthumbail = !this.isthumbail
    //     place.isThumbnail = false
    //     this.thumbnailLabel = ''
    //   // 썸네일이 아닌경우 => 첨부파일이 등록되어있다면 => 썸네일로 지정
    //   } else if (this.isthumbail === false) {
    //     this.isthumbail = !this.isthumbail
    //     place.isThumbnail = true
    //     this.thumbnailLabel = '썸네일 이미지가 등록되었습니다.'

    //     const image = this.imgList[place.createdOrder]
    //     const date = new Date().getTime();
    //     AWS.config.update({
    //       region: this.bucketRegion,
    //       credentials: new AWS.CognitoIdentityCredentials({
    //       IdentityPoolId: this.IdentityPoolId,
    //       })
    //     });
    //     // 썸네일 지정시 프론트에서 바로 업로드(리팩토링 필요)
    //     var s3 = new AWS.S3({
    //       apiVersion: "2006-03-01",
    //       params: { Bucket: this.albumBucketName }
    //     });
    //     s3.upload({
    //       Key: `thumbnail/${date + image.name}`,
    //       Body: image,
    //       ContentType: image.type,
    //       ACL: 'public-read'
    //     }, (err, data) => {
    //       if (err) {
    //         console.log(err)
    //         return alert("There was an error uploading your photo: ", err.message);
    //       }
    //       this.$store.state.images.thumbnailImage = data.Location
    //       this.$emit('update-tumbnail-image', data.Location)
    //   })
    //   }
    // },
    // 썸네일 설정, 취소
    refreshThumbnailBtn(place) {
      // 썸네일인 경우 => 썸네일 취소
      if (this.isthumbail) {
        this.$emit('change-isthumbail')
        // this.isthumbail = !this.isthumbail
        place.isThumbnail = false
        this.thumbnailLabel = '썸네일 등록하기!'
        this.$store.state.images.thumbnailChecked = false
      // 썸네일이 아닌경우 => 첨부파일이 등록되어있다면 => 썸네일로 지정
      } else if (this.isthumbail === false) {
        this.$emit('change-isthumbail')
        // this.isthumbail = !this.isthumbail
        place.isThumbnail = true
        this.thumbnailLabel = '썸네일 등록완료!'

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
    updateThumbnailImage(imgUrl) {
      this.$emit('update-tumbnail-image', imgUrl)
    }
  },
}
</script>

<style scoped>
.big-box {
  /* width: 300px; */
  height: 330px;
  overflow: scroll;
  margin: 5px 15px 0 15px;
  background-color: #2A355D;
  overflow-x: hidden;
}
.big-box::-webkit-scrollbar {
  width: 15px;
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
  margin: 10px 10px 10px 10px;
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