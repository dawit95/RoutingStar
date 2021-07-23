<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>
      <Map/>
      <v-divider></v-divider>
      <PointList />
    </v-layout>
  </v-container>
</template>

<script>
// components
import Map from '@/components/lines/Map.vue'
import PointList from '@/components/lines/MapPointList.vue'

export default {
 name: 'LinesView',
 components: { Map, PointList },
}
</script>

<style>

</style>