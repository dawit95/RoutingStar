<template>
<v-card>
  <v-bottom-navigation 
  absolute grow color="teal"
  :value="value">
    <v-btn @click="moveToSearch">
      <span>Search</span>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn @click="moveToPost">
      <span>Post</span>
      <v-icon>mdi-star-circle-outline</v-icon>
    </v-btn>

    <v-btn @click="moveToHome">
      <span>Home</span>
      <v-icon>mdi-home</v-icon>
    </v-btn>
  </v-bottom-navigation>

  <v-sheet align-self-end>
    <v-container style="height: 800px;">

    </v-container>
  </v-sheet>
</v-card>
</template>

<script>
export default {
  name: 'Nav',
  data () {
    return {
      value: 2,
    }
  },
  methods: {
    moveToSearch () {
      this.$router.push({ name: 'SearchView' })
    },
    moveToPost () {
      this.$router.push({ name: 'PostRouteView' })
    },
    moveToHome () {
      this.$router.push({ name: 'HomeView' })
    },
  },
}
</script>

<style>

</style>