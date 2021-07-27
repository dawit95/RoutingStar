<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>
      <Map/>
      <v-divider></v-divider>
      <MapPointForm/>
    </v-layout>
    <v-layout>
      <PostRouteDetailModal/>
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
 components: { 
    Map, 
    MapPointForm,
    PostRouteDetailModal,
  },
}
</script>

<style>

</style>