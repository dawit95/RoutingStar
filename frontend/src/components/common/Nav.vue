<template>
<v-card>
    <v-bottom-navigation
    absolute
    :value="activeBtn"
    grow
    color="teal"
  >
    <v-btn>
      <span>Search</span>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn>
      <span>Post</span>
      <v-icon>mdi-star-circle-outline</v-icon>
    </v-btn>

    <v-btn>
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
      bottomNav: 3,
    }
  },
}
</script>

<style>

</style>