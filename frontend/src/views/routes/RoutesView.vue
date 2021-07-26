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
import Map from '@/components/routes/Map.vue'
import MapPointForm from '@/components/routes/MapPointForm.vue'

export default {
 name: 'RoutesView',
 components: { Map, MapPointForm },
}
</script>

<style>

</style>