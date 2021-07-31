<template>
  <v-app>
    <Header />
    <h1>Home Page</h1>
    <Nav />
  </v-app>
</template>

<script>
import Header from '@/components/common/Header.vue'
import { getFeedList } from '@/api/home.js'

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
  },
  created () {
    getFeedList (
      (res) => {console.log(res)},
      (err) => {console.log(err)}
    )
  },
}
</script>

<style>

</style>