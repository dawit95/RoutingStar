<template>
<v-card>
    <v-bottom-navigation
    absolute
    :value="activeBtn"
    grow
    color="teal"
  >
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

    <v-sheet
      id="scroll-area-2"
      class="overflow-y-auto"
      max-height="605"
    >
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

    }
  },
  methods: {
    moveToSearch () {
      this.$router.push({ name: 'SearchView' })
    },
    moveToPost () {
      this.$router.push({ name: 'PostView' })
    },
    moveToHome () {
      this.$router.push({ name: 'HomeView' })
    },
  }
}
</script>

<style>

</style>