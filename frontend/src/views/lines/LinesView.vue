<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>
      <Map/>
      <v-divider></v-divider>
      <MapPointForm />
    </v-layout>
  </v-container>
</template>

<script>
// components
import Map from '@/components/lines/Map.vue'
import MapPointForm from '@/components/lines/MapPointForm.vue'

export default {
 name: 'LinesView',
 components: { Map, MapPointForm },
}
</script>

<style>

</style>