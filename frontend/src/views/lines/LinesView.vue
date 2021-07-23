<template>
  <div>
    <Map/>
    <PointList />
  </div>
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