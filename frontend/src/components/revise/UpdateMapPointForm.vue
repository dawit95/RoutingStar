<template>
  <v-flex xs12 class="big-box rounded-lg">
    <draggable class="card-box" @update="onUpdated">
      <v-list
        outlined
        v-for="(place, idx) in places"
        :key="place.createdOrder"
        class="rounded-lg"
      >

        <v-card flat class="d-flex justify-end">


          <image-input v-model="avatar" :place="place" @update-tumbnail-image="updateThumbnailImage">
            <div slot="activator">
              <v-avatar size="50px" v-ripple v-if="!place.placeImg" class="grey lighten-3 ml-1">
                <v-icon dark x-large>
                  mdi-plus
                </v-icon>
              </v-avatar>
              <v-avatar size="50px" v-ripple v-else class="ml-1">
                <img :src="place.placeImg" alt="Image">
              </v-avatar>
            </div>
          </image-input>



          {{place.createdOrder}}
          <v-spacer></v-spacer>
          <v-icon drak large right style="cursor: pointer;">mdi-drag-horizontal-variant</v-icon>
          <!-- <v-spacer></v-spacer> -->
          <v-icon left style="cursor: pointer;" @click="removePoint(place.marker, idx)">mdi-close</v-icon>
        </v-card>
        <v-list-item outlined>
          <v-list-item-content class="py-0">
            <v-textarea v-model="place.content" @click="activePoint(place)" @mouseout="stopPoint(place)" label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"></v-textarea>
          </v-list-item-content>
        </v-list-item>
          <v-switch
          class="switch-prop"
          id="thumbnail_switch"
          @click="refreshThumbnailBtn(place)"
          :label="place.isThumbnail ? thumbnailLabel : '썸네일로 설정하기!'" 
          :disabled="(!place.isThumbnail && isthumbail) || !place.imageUpload" 
          v-model="place.isThumbnail"
          inset color="indigo darken-3"></v-switch>
      </v-list>
    </draggable>
  </v-flex>
</template>

<script>

import { mapGetters, mapMutations, } from 'vuex'
import draggable from 'vuedraggable'
import { dragscroll } from 'vue-dragscroll'
import AWS from 'aws-sdk'
import ImageInput from '@/components/revise/ImageInput.vue'

export default {
  name: 'UpdateMapPointForm',
  components: {
    draggable,
    ImageInput: ImageInput,
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
      selectedFile: null,
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
    // 썸네일 설정, 취소
    refreshThumbnailBtn(place) {
      // 썸네일인 경우 => 썸네일 취소
      if (this.isthumbail) {
        this.$emit('change-isthumbail')
        // this.isthumbail = !this.isthumbail
        place.isThumbnail = false
        this.thumbnailLabel = '썸네일로 설정하기!'
        this.$store.state.images.thumbnailChecked = false
      // 썸네일이 아닌경우 => 첨부파일이 등록되어있다면 => 썸네일로 지정
      } else {
        // 새롭게 등록 된 이미지 파일의 경우
        if (place.createdOrder >= this.listLength) {
          console.log('위로들어와서 s3에 업로드 & 저장')
          this.$emit('change-isthumbail')
          // this.isthumbail = !this.isthumbail
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
          var s2 = new AWS.S3({
            apiVersion: "2006-03-01",
            params: { Bucket: this.albumBucketName }
          });
          s2.upload({
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
          // 기존에 있던 파일의 경우
        } else {
          // console.log(typeof(this.imgList[place.createdOrder]))
          // 새로등록해서 파일인 경우 S3로 보내서 업로드 후 저장
          if (typeof(this.imgList[place.createdOrder]) == 'object') {
            console.log('밑으로 들어와서 S3 변환 후 해당 URL저장')
            this.thumbnailSendToS3(this.imgList[place.createdOrder])
            // 기존 URL인 경우 그대로 저장
          } else {
            console.log('밑으로 들어와서 해당 URL바로 저장')
            this.$store.state.images.thumbnailImage = this.imgList[place.createdOrder]
            this.$emit('update-tumbnail-image', this.imgList[place.createdOrder])
            this.$emit('change-isthumbail')
            // this.isthumbail = !this.isthumbail
            place.isThumbnail = true
            this.thumbnailLabel = '썸네일 이미지가 등록되었습니다.'
          }
        }
      }
        // if (typeof(this.imgList[place.createdOrder]) !== String || typeof(this.imgList[place.createdOrder]) === Object) {
        //   // console.log(typeof(this.imgList[place.createdOrder]))
        //   // console.log('ssasdfasdfadfsdsfsdfsdfsdf')
        //   this.$emit('change-isthumbail')
        //   // this.isthumbail = !this.isthumbail
        //   place.isThumbnail = true
        //   this.thumbnailLabel = '썸네일 이미지가 등록되었습니다.'
  
        //   const image = this.imgList[place.createdOrder]
        //   const date = new Date().getTime();
        //   AWS.config.update({
        //     region: this.bucketRegion,
        //     credentials: new AWS.CognitoIdentityCredentials({
        //     IdentityPoolId: this.IdentityPoolId,
        //     })
        //   });
        //   // 썸네일 지정시 프론트에서 바로 업로드(리팩토링 필요)
        //   var s2 = new AWS.S3({
        //     apiVersion: "2006-03-01",
        //     params: { Bucket: this.albumBucketName }
        //   });
        //   s2.upload({
        //     Key: `thumbnail/${date + image.name}`,
        //     Body: image,
        //     ContentType: image.type,
        //     ACL: 'public-read'
        //   }, (err, data) => {
        //     if (err) {
        //       console.log(err)
        //       return alert("There was an error uploading your photo: ", err.message);
        //     }
        //     this.$store.state.images.thumbnailImage = data.Location
        //     this.$emit('update-tumbnail-image', data.Location)
        //   })
        // } else if (this.imgList[place.createdOrder].indexOf('https') != -1) {
        //   console.log('밑으로 들어옴')
        //   this.$store.state.images.thumbnailImage = this.imgList[place.createdOrder]
        //   this.$emit('update-tumbnail-image', this.imgList[place.createdOrder])
        //   this.$emit('change-isthumbail')
        //   // this.isthumbail = !this.isthumbail
        //   place.isThumbnail = true
        //   this.thumbnailLabel = '썸네일 이미지가 등록되었습니다.'
        // }
      // }
    },
    thumbnailSendToS3 (image) {
      const date = new Date().getTime();
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
        IdentityPoolId: this.IdentityPoolId,
        })
      });
      // 썸네일 지정시 프론트에서 바로 업로드(리팩토링 필요)
      var s2 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.albumBucketName }
      });
      s2.upload({
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
    },
    updateThumbnailImage(imgUrl) {
      this.$emit('update-tumbnail-image', imgUrl)
    }
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
  width: 20px;
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