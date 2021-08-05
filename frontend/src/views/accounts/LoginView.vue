<template>
  <div>
    <!-- <v-row justify="center"> -->
    <v-img class="mx-auto logoTopM" max-height="400" max-width="400" :src="require('../../assets/LOGO.png')" />  
    <!-- </v-row> -->
    <!-- width: 30px
    height: 150px /> -->
    <Google />
    <!-- <Kakao /> -->
  </div>
</template>

<script>
import Google from '@/components/accounts/Google.vue'
// import Kakao from '@/components/accounts/Kakao.vue'

export default {
  name: 'LoginView',
  components: {
    Google,
    // Kakao,
  },
}
</script>

<style>
.logoTopM {margin-top: 100px;}
</style>