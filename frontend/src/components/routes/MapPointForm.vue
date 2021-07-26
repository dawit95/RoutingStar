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
          <input @change="onFileSelected(pointItem)" accept="image/*" type="file">
          <!-- <button @click="onUpload(pointItem)">확인용</button> -->


          <v-textarea v-model="pointItem.content" @click="activePoint(pointItem)" @mouseout="stopPoint(pointItem)" label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"></v-textarea>
          <v-btn>썸네일 활용유무(선택시 이미지 썸네일로 적용)</v-btn>
          <!-- {{ pointItem.lat }} // {{ pointItem.lng }} // {{ pointItem.pk }} -->
          {{pointItem.id}}
        </v-list-item-content>
      </v-list-item>
    </v-list>
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
      // imgLst: imgLst,
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
    onFileSelected(pointItem) {
      // 이미지 파일업로드되면 FormData로 만들기
      this.selectedFile = event.target.files[0]
      const fd = new FormData()
      fd.append('image', this.selectedFile, this.selectedFile.name)
      const idx = this.$store.getters.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
      this.$store.getters.imgList[idx] = fd
    },
    // onUpload(pointItem) {
    //   const fd = new FormData()
    //   fd.append('image', this.selectedFile, this.selectedFile.name)
    //   // axios.post('')
    //   const idx = this.$store.getters.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
    //   // console.log(idx)
    //   // console.log(this.$store.getters.pointedItems)
    //   this.$store.getters.imgList[idx] = fd
    //   // this.$store.dispatch('updateImage', fd)
    //   console.log(this.$store.getters.imgList)
    // }
  },
  computed: {
    ...mapGetters(['pointedItems', 'imgList'])
  }
}
</script>

<style>

</style>