<template>
  <v-app>
    <v-main class="background-color">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  data: () => ({
    //
  }),
};
</script>
<style>
.background-color {
  background-color: #101423;;
}
</style>