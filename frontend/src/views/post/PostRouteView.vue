<template>
  <v-container class="pa-0">
    <ReviseOthersHeader :tempThumbnail="tempThumbnail" @freeze-map="freezeMap" @recover-freeze-map="recoverFreezeMap"/>
    <Map :isFreeze="isFreeze"/>
    <v-divider></v-divider>
    <MapPointFormS3 
    :isthumbail="isthumbail" 
    @update-tumbnail-image="updateThumbnailImage"
    @change-isthumbail="changeIsthumbail"/>

  </v-container>

</template>

<script>
// components
import Map from '@/components/routes/Map.vue'
import MapPointFormS3 from '@/components/routes/MapPointFormS3.vue'
import ReviseOthersHeader from '@/components/reviseothers/ReviseOthersHeader.vue'
import { mapActions } from 'vuex'
import Swal from 'sweetalert2'
export default {
 name: 'PostRouteView',
 data() {
   return {
     isFreeze: false,
     tempThumbnail: '',
     isthumbail: false,
   }
 },
 components: { 
    Map, 
    MapPointFormS3,
    ReviseOthersHeader,
  },
  methods: {
    ...mapActions(['changeMethodType']),
    // 모달이 열리면 map freeze하기
    freezeMap() {
      this.isFreeze = true
    },
    // 모달이 닫히면 map freeze 풀기=
    recoverFreezeMap() {
      this.isFreeze = false
      console.log(this.isFreeze)
    },
    updateThumbnailImage(image) {
      this.tempThumbnail = image
    },
    changeIsthumbail() {
      this.isthumbail = !this.isthumbail
    },
  },
  created() {
    this.changeMethodType('post')
    Swal.fire({
      title: '반갑습니다!',
      text: '지도에서 원하는 곳을 찍어보세요!',
      imageUrl: 'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EB%A3%A8%ED%8A%B8+%EB%93%B1%EB%A1%9D%ED%8E%98%EC%9D%B4%EC%A7%80.gif',
      imageWidth: 380,
      imageHeight: 550,
      imageAlt: 'Custom image',
    })
  }
}
</script>

<style scoped>

</style>