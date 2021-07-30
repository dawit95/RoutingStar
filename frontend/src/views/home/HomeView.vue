<template>
  <v-app>
    <Header />
    <h1>Home Page</h1>
    <Nav />
  </v-app>
</template>

<script>
import Header from '@/components/common/Header.vue'
import Nav from '@/components/common/Nav.vue'

export default {
  name: 'HomeView',
  components: {
    Nav,
    Header,
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