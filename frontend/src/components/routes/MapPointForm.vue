<template>
  <v-flex xs12>
    <v-list
      outlined
      v-for="pointItem in pointedItems"
      :key="pointItem.id"
    > 
      <v-list-item outlined ma-0 pa-0>
        <v-list-item-content>
          <!-- <v-file-input :change="pointItem.image" label="첨부파일"></v-file-input> -->
          <!-- <input @change="upload" multiple accept="image/*" type="file" id="file" class="inputfile" /> -->
          <!-- <button @click="onUpload(pointItem)">확인용</button> -->
          <!-- <input @change="onFileSelected(pointItem)" accept="image/*" type="file"> -->
          <input @change="onFileSelected2(pointItem)" accept="image/*" type="file">


          <v-textarea v-model="pointItem.content" @click="activePoint(pointItem)" @mouseout="stopPoint(pointItem)" label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"></v-textarea>
          <v-btn>썸네일 활용유무(선택시 이미지 썸네일로 적용)</v-btn>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <button @click="check">확인용 버튼</button>
  </v-flex>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'MapPointForm',
  data () {
    return {
      isActive: false,
      selectedFile: null,
    }
  },
  methods: {
    activePoint(item) {
      item.marker.setAnimation(window.google.maps.Animation.BOUNCE);
    },
    stopPoint(item) {
      item.marker.setAnimation(null);
    },
    // upload(e){
    //   var file = e.target.files;
    //   let url = URL.createObjectURL(file[0]);
    //   this.image = url
    // },

    // onFileSelected(pointItem) {
    //   // 이미지 파일업로드되면 FormData로 만들기
    //   this.selectedFile = event.target.files[0]
    //   const fd = new FormData()
    //   fd.append('image', this.selectedFile, this.selectedFile.name)
    //   const idx = this.$store.getters.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
    //   this.$store.getters.imgList[idx] = fd
    // },
    onFileSelected2(pointItem) {
      this.selectedFile = event.target.files[0]
      const idx = this.$store.getters.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
      this.$store.getters.imgList[idx] = this.selectedFile
      console.log( this.$store.getters.imgList)
    },
    check() {
      const ins = this.$store.getters.imgList.length
      const fd = new FormData()
      for (var x = 0; x < ins; x++) {
        const image =  this.$store.getters.imgList[x]
        fd.append('image[' + x + ']', image)
      }
    },
  },
  computed: {
    ...mapGetters(['pointedItems', 'imgList'])
  }
}
</script>

<style>

</style>