<template>
  <div></div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'LoginProcessView',
  computed: {
    ...mapGetters(['jwt'])
  },
  mounted() {
    console.log('여기 로그인 프로세스 마운트')
    // if (this.jwt[0].legth) {
    //   this.$router.push( {name: 'LoginProcessView'} )
    // }
    this.$router.push( {name: 'HomeView'} )
  },
  // wahtch: {
  //   jwt: function(jwt) {
  //     console.log(jwt)
  //     if (jwt[0]) {
  //       this.$router.push( {name: 'LoginProcessView'} )
  //     }
  //   }
  // },
}
</script>

<style>

</style>