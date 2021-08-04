<template>
  <v-layout row swap>
    <Header />
    <Map :isFreeze="isFreeze"/>
    <v-divider></v-divider>
    <MapPointFormS3/>

    <PostRouteDetailModal @freeze-map="freezeMap" @recover-freeze-map="recoverFreezeMap"/>
  </v-layout>

</template>

<script>
// components
import Map from '@/components/routes/Map.vue'
import MapPointFormS3 from '@/components/routes/MapPointFormS3.vue'
import PostRouteDetailModal from '@/components/routes/PostRouteDetailModal.vue'
import Header from '@/components/common/Header.vue'

export default {
 name: 'PostRouteView',
 data() {
   return {
     isFreeze: false
   }
 },
 components: { 
    Map, 
    MapPointFormS3,
    PostRouteDetailModal,
    Header,
  },
  methods: {
    // 모달이 열리면 map freeze하기
    freezeMap() {
      this.isFreeze = true
    },
    // 모달이 닫히면 map freeze 풀기=
    recoverFreezeMap() {
      this.isFreeze = false
      console.log(this.isFreeze)
    }
  }
}
</script>

<style>

</style>