<template>
  <v-app>
    <h1>Home Page</h1>
    <Nav />
  </v-app>
</template>

<script>
import Nav from '@/components/common/Nav'

export default {
  name: 'HomeView',
  components: {
    Nav
  },
  mounted () {
    if (this.name === 'Home') {
      console.log('home')
      this.value = 2
    }
  }
}
</script>

<style>

</style>