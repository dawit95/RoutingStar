<template>
  <v-container>
    <v-layout>
      <v-row class="text-center" justify="center">
        <v-col cols="12">
          <v-avatar size="250px" v-ripple class="lighten-3 ml-1">
            <img src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/162873181471879-796003_hd-desktop-wallpapers-free-download-background-desktop-amazing.jpg" alt="Image">
          </v-avatar>
          <h1 class="h1-color">Routing Badge</h1>
          <h3 class="h2-color">많은 루트를 작성하여 뱃지를 획득해보세요.</h3>
        </v-col>
      </v-row>
    </v-layout>


    <!-- <h1 class="h-color">Routing Badge</h1>
    <v-card md-2 color="#azure">많은 루트를 작성하여 뱃지를 획득해보세요.</v-card> -->
    <!-- <v-row class="text-center" justify="center">
      <v-col cols="12">
        <div
          class="pa-10 secondary rounded-circle d-inline-block"
        >
            <v-img
            max-width="150px"
            max-height="150px"
            src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/16287312702476b862f723ab9db7e69f5b92601c4ae02.png"
          ></v-img>
        </div>
      </v-col>
    </v-row> -->

    <!-- <v-layout row>
      <v-flex xs12>
        <img src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/16287312702476b862f723ab9db7e69f5b92601c4ae02.png" alt="">

      </v-flex>
      <br>
    </v-layout> -->
  </v-container>
</template>

<script>
export default {
  name: 'BadegView',
  data() {
    return {
      routeCount: 0,
      avatar: null,
    }
  }
}
</script>

<style>
.h1-color {
  color:azure;
}
.h2-color {
  color:khaki;
}
</style>