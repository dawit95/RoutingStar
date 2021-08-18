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
  }
}
</script>

<style scoped>

</style>