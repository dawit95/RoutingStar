<template>
    <v-card class="overflow-hidden">
    <v-app-bar
      app
      color="#101423"
      dark
      style="width=100%; justify-content=flex-end;"
      pa-0
    >
      <v-btn
      @click="goBack"
      icon>
        <v-icon x-large>mdi-chevron-left</v-icon>
      </v-btn>
      <v-spacer></v-spacer>

      <v-btn @click="handleClick2" color="#101423" right dark>선택완료</v-btn>
    </v-app-bar>
  </v-card>
</template>

<script>
export default {
  name: 'ReviseHeader',
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    handleClick2() {
      console.log('hello2')
    },
    
  }
}
</script>

<style>
.v-toolbar__content, .v-toolbar__extension {
  padding-right: 0px;
  padding-left: 8px;
}
</style>