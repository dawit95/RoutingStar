<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>
      <Map :isFreeze="isFreeze"/>
      <v-divider></v-divider>
      <MapPointForm/>
    </v-layout>
    <v-layout>
      <PostRouteDetailModal @freeze-map="freezeMap"/>
    </v-layout>
  </v-container>
</template>

<script>
// components
import Map from '@/components/routes/Map.vue'
import MapPointForm from '@/components/routes/MapPointForm.vue'
import PostRouteDetailModal from '@/components/routes/PostRouteDetailModal.vue'

export default {
 name: 'PostRouteView',
 data() {
   return {
     isFreeze: false
   }
 },
 components: { 
    Map, 
    MapPointForm,
    PostRouteDetailModal,
  },
  methods: {
    // 모달이 열리면 map freeze하기
    freezeMap() {
      this.isFreeze = true
      console.log(this.isFreeze)
    },
  }
}
</script>

<style>

</style>