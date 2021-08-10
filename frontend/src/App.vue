<template>
  <v-app>
    <v-main class="background-color">
      <!-- Header -->
      <div v-if="$route.name !== 'LoginView' && $route.name !== 'PostRouteView' && $route.name !== 'ReviseRouteView' ">
        <Header/>
      </div>

      <!-- 라우터 뷰 -->
      <div class="mt-1">
        <router-view/>
      </div>  
    
      <!-- Footer -->
      <div v-if="$route.name !== 'LoginView'">
        <Nav/>
      </div>

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
  background-color: #101423;;
}
</style>