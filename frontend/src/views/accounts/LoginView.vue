<template>
  <div>
    <!-- <v-row justify="center"> -->
    <v-img class="mx-auto logoTopM rotate-center" max-height="400" max-width="400" :src="require('../../assets/logo.png')" />  
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
.logoTopM {
  margin-top: 100px;
}
.rotate-center {
  animation: rotate-center 10s linear infinite both
}
@keyframes rotate-center {
  0% {
    transform: rotate(0);
  }
  100% {
    transform: rotate(360deg);
  }
}

</style>