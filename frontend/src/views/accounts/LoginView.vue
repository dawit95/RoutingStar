<template>
  <div>
    <!-- <h1 style="color: white">안녕~~~</h1> -->
    <FallingImg/>
    <v-row justify="center">
      <v-img class="logoTopM rotate-center" max-height="300" max-width="300" :src="require('../../assets/LOGO7.png')" />  
    <!-- <v-row justify="center"> -->
    <!-- <p class="logoFont"> -->
      <h1 class="logoFont">ROUTING STAR</h1>

    </v-row>
    <!-- width: 30px
    height: 150px /> -->
    <Google />
    <!-- <Kakao /> -->
  </div>
</template>

<script>
import Google from '@/components/accounts/Google.vue'
// import Kakao from '@/components/accounts/Kakao.vue'
import FallingImg from '@/components/common/FallingImg.vue'

export default {
  name: 'LoginView',
  components: {
    Google,
    // Kakao,
    FallingImg
  },
}
</script>

<style>
.logoFont {
  font-family: 'Montserrat', sans-serif;
  font-weight: bold;
  color: white;
  font-size: 44px;
  position: absolute;
  top: 206px;
  /* left: 30%; */
}

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