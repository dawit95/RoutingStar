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
    <input type="file" ref="file" :name="uploadFieldName" @change="onFileSelected(place, $event.target.name, $event.target.files)" style="display:none">
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'ImageInput',
  data: ()=> ({
    uploadFieldName: 'file',
    maxSize: 1024,
    selectedFile: null,
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
      }
    },
  }
}
</script>