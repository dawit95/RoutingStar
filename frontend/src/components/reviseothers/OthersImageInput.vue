<template>
  <div>
    <!-- slot for parent component to activate the file changer -->
    <!-- 상위컴포넌트에서 하위컴포넌트를 수정해서 사용하고 싶을 때 slot 사용하는 것으로
         상위컴포넌트의 내용이 하위컴포넌트로 가게 됨
         동그라미 클릭하면(PARENT) 안에서 이미지 첨부하는(CHILD) 구조 
         https://medium.com/free-code-camp/how-to-build-a-flexible-image-uploader-component-using-vue-js-2-0-5ee7fc77516
     -->
    <div @click="launchFilePicker()">
      <slot name="activator"></slot>
    </div>
    <!-- <input type="file" ref="file" :name="uploadFieldName" @change="onFileChange($event.target.name, $event.target.files)" style="display:none"> -->
    <input accept=".heic, .png, .jpg, .gif" type="file" ref="file" :name="uploadFieldName" @change="onFileSelected(place, $event.target.name, $event.target.files)" style="display:none">
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import AWS from 'aws-sdk'

export default {
  name: 'OthersImageInput',
  data: ()=> ({
    uploadFieldName: 'file',
    maxSize: 1024,
    selectedFile: null,
    albumBucketName: 'routingstar-photo-album',
    bucketRegion: 'ap-northeast-2',
    IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
    tmpImg:''
  }),
  props: {
    place: {
      type: Object
    }
  },
  computed: {
    ...mapGetters(['imgList'])
  },
  methods: {
    launchFilePicker(){
      this.$refs.file.click();
    },
    onFileSelected(place, fieldName, file) {
      console.log(place.isThumbnail)
      // const { maxSize } = this
      // 파일 갱신되었는데 이미 썸네일인 경우도 고려해야함.
      let imageFile = file[0]
      if (file.length>0) {
        // let size = imageFile.size / maxSize / maxSize
        this.imgList[place.createdOrder] = imageFile
        
        place.imageUpload = true
        // console.log(size)

        // Append file into FormData and turn file into image URL
        let formData = new FormData()
        let imageURL = URL.createObjectURL(imageFile)

        formData.append(fieldName, imageFile)
        place.placeImg = imageURL
        // Emit the FormData and image URL to the parent component
        this.$emit('input', { formData, imageURL })
        // 썸네일로 지정되어있던 상태에서 첨부파일이 갱신된다면
        
        if (place.isThumbnail) {
          this.tmpImg = imageURL
          this.$store.state.images.thumbnailImage = imageURL
        }
      }
    },
    thumbnailSendToS3 (image) {
      // console.log('ㅇㅇㅇ')
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
        console.log('바꾼다잉')
        this.tmpImg = data.Location
        // this.$emit('update-tumbnail-image', data.Location)
      })
    },
    // emitImage(imageURL) {
    //   console.log(imageURL)
    //   this.$emit('update-tumbnail-image', imageURL)
    // }
  },
  watch: {
    tmpImg: function (imageURL) {
      this.$emit('update-tumbnail-image', imageURL)
      // this.emitImage(imageURL)
    }
  }
}
</script>