<template>
  <v-flex xs12 class="big-box">
    <draggable @update="onUpdated">
      <v-list
        outlined
        v-for="(pointItem, idx) in pointedItems"
        :key="pointItem.pk"
      >
        <v-icon drak large right style="cursor: pointer;">mdi-drag-horizontal-variant</v-icon>

        <v-icon right large style="cursor: pointer;" @click="removePoint(pointItem.marker, idx)">mdi-alpha-x-circle-outline</v-icon>
        {{pointItem.pk}}
        <v-list-item outlined ma-0 pa-0 @click="forcheck(pointItem)">
          <v-list-item-content>
            <input @change="onFileSelected(pointItem)" accept="image/*" type="file">
            <v-textarea v-model="pointItem.content" @click="activePoint(pointItem)" @mouseout="stopPoint(pointItem)" label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"></v-textarea>
          </v-list-item-content>
          <v-switch
          id="thumbnail_switch"
          @click="refreshThumbnailBtn(pointItem)"
          :label="pointItem.thumbnail ? thumbnailTxt : ''" 
          :disabled="(!pointItem.thumbnail && isthumbail) || !pointItem.imageUpload"
          :v-model="pointItem.thumbnail"
          inset color="indigo darken-3"></v-switch>
        </v-list-item>
      </v-list>
    </draggable>
    <v-btn @click="postPointImages">확인용 버튼</v-btn>
  </v-flex>
</template>

<script>
// import axios from 'axios'
import { mapActions, mapGetters, mapMutations, } from 'vuex'
import draggable from 'vuedraggable'
import { dragscroll } from 'vue-dragscroll'
import AWS from 'aws-sdk'

export default {
  name: 'MapPointForm',
  components: {
    draggable,
  },
  directives: {
    dragscroll
  },
  data () {
    return {
      // 썸네일이 골라졌는지 유무
      isthumbail: false,
      selectedFile: null,
      thumbnailTxt: '',
      albumBucketName: 'routingstar-photo-album',
      bucketRegion: 'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
    }
  },
  computed: {
    ...mapGetters(['pointedItems', 'imgList', 'polyLine']),
  },
  // created() {
  //   this.getFiles()
  // },
  methods: {
    // 일단 데이터의 조작만 있고, 모든 데이터의 input이 완료된 이후에 백앤드와 통신하니
    // 일단은 mutations함수를 사용하였습니다.
    ...mapMutations(['UPDATE_DRAGGERBLE_ITEMS','REFRESH_POINTED_ITEMS']),
    ...mapActions(['complete', 'updateThumbnailImage']),
    onUpdated(event) {
      this.UPDATE_DRAGGERBLE_ITEMS(event);
      this.refreshPolyline();
    },
    activePoint(item) {
      item.marker.location.setAnimation(window.google.maps.Animation.BOUNCE);
    },
    stopPoint(item) {
      item.marker.location.setAnimation(null);
    },
    // 첨부파일 업로드되면 imgList에 지속 업로드
    // 핀이 등록되면 해당 핀에의해 만들어진 리스트 인덱스를 가져와서 해당 인덱스를 활용하여 imgList에 이미지 넣기
    // 이렇게해야만 사용자가 draggable로 리스트 이동후 파일 첨부를 해도 imgList의 올바른 자리에 사진이 갱신됩니다.
    onFileSelected(pointItem) {
      this.selectedFile = event.target.files[0]
      this.imgList[pointItem.pk] = this.selectedFile
      pointItem.imageUpload = true
    },
    postPointImages() {
      console.log(this.imgList)
      const ins = this.pointedItems.length
      for (var x = 0; x < ins; x++) {
        console.log(x + '번째 인덱스의 pk')
        const pointItemPk = this.pointedItems[x].pk
        console.log(pointItemPk)
        // 첨부파일이 없다면 pass
        if (this.imgList[pointItemPk] === '') {
          continue
          // 첨부파일이 있다면 S3로 업로드 진행 및 응답 데이터를 서버로 보내는 이미지 리스트에 저장
        } else {
          console.log('오키오키요')
          const image = this.imgList[pointItemPk]
          // S3로 업로드 후 반환값(이미지URL)을 저장
          this.upload(image, x)
        }
      }
      setTimeout(()=>{console.log(this.pointedItems)}, 5000)
      
    },
    upload(image, x) {
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        })
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.albumBucketName }
      });

      s3.upload({
        Key: image.name,
        Body: image,
        ContentType: 'image/jpeg',
        ACL: 'public-read'
      }, (err, data) => {
        if (err) {
          return alert("There was an error uploading your photo: ", err.message);
        }
        // alert("Successfully uploaded photo.");
        // const idx = this.pointedItems.findIndex((e) => e.pk === pointItemPk)
        // console.log(idx, pointItemPk)
        this.pointedItems[x].image = data.Location
        // this.getFiles()
      })
    },
    // getFiles() {
    //   AWS.config.update({
    //     region: this.bucketRegion,
    //     credentials: new AWS.CognitoIdentityCredentials({
    //       IdentityPoolId: this.IdentityPoolId,
    //     })
    //   });

    //   var s3 = new AWS.S3({
    //     apiVersion: "2006-03-01",
    //     params: { Bucket: this.albumBucketName }
    //   });

    //   s3.listObjects({ Delimiter: "/" }, function(err, data) {
    //     if (err) {
    //       return alert("There was an error listing your albums: " + err.message); 
    //     } else {
    //       var href = this.request.httpRequest.endpoint
    //       var Url = href.protocol + '//' + href.host + '/' + data.Contents[0].Key
    //       console.log(Url)
    //       console.log('----')
    //       console.log(data)
    //     }
    //   });
    // },
    // deleteFile (key) {
    //   AWS.config.update({
    //     region: this.bucketRegion,
    //     credentials: new AWS.CognitoIdentityCredentials({
    //       IdentityPoolId: this.IdentityPoolId,
    //     })
    //   });

    //   var s3 = new AWS.S3({
    //     apiVersion: "2006-03-01",
    //     params: { Bucket: this.albumBucketName }
    //   });
      
    //   s3.deleteObject({ Key: key }, (err, data) => {
    //     if (err) {
    //       return alert("There was an error deleting your photo: ", err.message);
    //     }
    //     alert("Successfully deleted photo.");
    //     console.log(data)
    //     this.getFiles()
    //   });
    // },
    // deleteItem 함수는 삭제하고 각각 component에서 조건에 맞게 포인트를 삭제한 후에
    // 삭제된 pointedItems를 변수로 설정 후 mutation 함수로 state에 반영하는 것으로 바꿈
    removePoint(marker, idx) {
      if ( idx != -1 ) {
        marker.location.setMap(null);
        const oldItems = this.pointedItems
        const newPointedItems = oldItems.filter((point) => {
          return point.pk !== marker.pk
        })
        this.REFRESH_POINTED_ITEMS(newPointedItems)
        this.refreshPolyline();
      }
    },
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const pointedItems = this.pointedItems
      console.log(pointedItems)
      path.clear();
      for( const point of pointedItems ) {
        path.push( new window.google.maps.LatLng( point.lat, point.lng));
      }
    },
    // 완료버튼이 눌러진다면
    // 임시로 여기다 두고, api 파일 소화 후 이동 예정 이동 후 PostRouteDetail과 연동

    // postPointImages() {
    //   const ins = this.pointedItems.length
    //   for (var x = 0; x < ins; x++) {
    //     const files = new FormData()
    //     const pk = this.pointedItems[x].pk
    //     // console.log(pk)
    //     if (this.imgList[pk] === null) {
    //       continue
    //     } else {
    //       const image =  this.imgList[pk]
    //       files.append('files', image)
    //       axios({
    //         method: 'post',
    //         url:'http://192.168.1.214:8000/api/v1/img/place',
    //         data: files,
    //         headers: {'Content-Type': 'multipart/form-data'}
    //       })
    //       .then(res => {
    //         console.log('보내짐')
    //         const responseData = res.data.success.image
    //         console.log(responseData)
    //         this.pointedItems[pk].image = responseData
    //       })
    //       .catch(err => {
    //         console.log('안보내짐')
    //         console.log(err)
    //       })
    //     }
    //   }
    //   console.log(this.pointedItems)
    // },
    forcheck(item) {
      console.log(`${item.pk}번째로 생성된 마커의 pk`)
    },
    refreshThumbnailBtn(pointItem) {
      // 썸네일 설정, 취소
      // 썸네일인 경우 => 썸네일 취소
      if (this.isthumbail) {
        this.isthumbail = !this.isthumbail
        pointItem.thumbnail = false
        this.thumbnailTxt = ''
      // 썸네일이 아닌경우 => 첨부파일이 등록되어있다면 => 썸네일로 지정
      } else if (this.isthumbail === false) {
        const idx = this.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
        if (this.imgList[idx] !== '') {
          this.isthumbail = !this.isthumbail
          pointItem.thumbnail = true
          this.thumbnailTxt = '썸네일 이미지가 등록되었습니다.'
          this.updateThumbnailImage(this.imgList[idx])
        } else if (this.imgList[idx] === '') {
          alert('장소에대한 이미지를 추가해주세요!')
          pointItem.thumbnail = false
        }
      }
    }
  },
}
</script>

<style>
.big-box {
  width: 300px;
  height: 300px;
  overflow: scroll;
}
</style>