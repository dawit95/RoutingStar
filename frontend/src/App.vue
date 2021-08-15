<template>
  <v-app>
    <v-main class="background-color">
      <!-- Header -->
      <Header v-if="$route.name !== 'LoginView' && $route.name !== 'PostRouteView' && $route.name !== 'ReviseRouteView' "/>


      <!-- 라우터 뷰 -->
      <router-view/>

    
      <!-- Footer -->
      <Nav v-if="$route.name !== 'LoginView'"/>

    </v-main>
  </v-app>
</template>

<script>
import Header from '@/components/common/Header.vue'
import Nav from '@/components/common/Nav.vue'

export default {
  name: 'App',
  data: () => ({
    
  }),
  components: {
    Header,
    Nav
  }
};
</script>
<style>
.background-color {
  background-color: #101423;
  /* background-image: url("./assets/background-color.jpg"); */
}

/* .chooseButton까지 TagForm에서 사용되는 CSS */
.button {
  width: 70px;
  height: 25px;
  background: #C1C8E4;
  border-radius: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3x ease-in-out;
  filter: drop-shadow(0px 6px 6px rgba(0, 0, 0, 0.25));
  margin-top: 6px;
}
.button span {
  line-height: 10px;
  font-size: 13px;
  font-weight: bold;
  color: #303C6C;
}
.chooseButton {
  width: 70px;
  height: 25px;
  background: #B4DFE5;
  border-radius: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3x ease-in-out;
  box-shadow: inset 0px 6px 6px rgba(0, 0, 0, 0.25);
}


</style>