<template>
  <v-container>
    <v-layout>
      <v-row class="text-center" justify="center">
        <v-col cols="12">
          <v-avatar size="200px" v-ripple class="lighten-3 ml-1">
            <img :src="badges[pk].imageUrl" alt="Image">
          </v-avatar>
          <h1 class="h1-color">Routing Badge</h1>
          <h3 class="h2-color">많은 루트를 작성하여 뱃지를 획득해보세요.</h3>
        </v-col>
      </v-row>
    </v-layout>
    <v-layout>
      <v-card color="#101423" class="mt-3">
        <v-avatar size="170px" v-ripple class="lighten-3 ml-3 mt-2">
          <img :src="badges[0].imageUrl" alt="Image">
        </v-avatar>
        
        <v-avatar size="170px" v-ripple class="lighten-3 ml-5 mt-2">
          <img :src="badges[1].imageUrl" alt="Image">
        </v-avatar>

        <v-avatar size="170px" v-ripple class="lighten-3 ml-3 mt-2">
          <img :src="badges[2].imageUrl" alt="Image">
        </v-avatar>

        <v-avatar size="170px" v-ripple class="lighten-3 ml-5 mt-2">
          <img :src="badges[3].imageUrl" alt="Image">
        </v-avatar>

        <v-avatar size="170px" v-ripple class="lighten-3 ml-3 mt-2">
          <img :src="badges[4].imageUrl" alt="Image">
        </v-avatar>

        <v-avatar size="170px" v-ripple class="lighten-3 ml-5 mt-2">
          <img :src="badges[5].imageUrl" alt="Image">
        </v-avatar>

      </v-card>
    </v-layout>

  </v-container>
</template>

<script>
export default {
  name: 'BadegView',
  data() {
    return {
      routeCount: 0,
      avatar: null,
      badges: [
        {pk:1, imageUrl:'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EA%B7%B8%EB%A6%BC1.png'},
        {pk:2, imageUrl:'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EA%B7%B8%EB%A6%BC2.png'},
        {pk:3, imageUrl:'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EA%B7%B8%EB%A6%BC3.png'},
        {pk:4, imageUrl:'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EA%B7%B8%EB%A6%BC4.png'},
        {pk:5, imageUrl:'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EA%B7%B8%EB%A6%BC5.png'},
        {pk:6, imageUrl:'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/%EA%B7%B8%EB%A6%BC6.png'},
      ],
      length: 1,
      pk: 0
    }
  },
  method: {
    checkLevel(length) {
      if (length <= 5) {
        this.pk = 0
      } else if (length <= 10) {
        this.pk = 1
      } else if (length <= 15) {
        this.pk = 2
      } else if (length <= 20) {
        this.pk = 3
      } else if (length <= 25) {
        this.pk = 4
      } else {
        this.pk = 5
      }
    }
  },
  created() {
    // const length = this.$route.params.length
    const length = 30
    if (length <= 5) {
      this.pk = 0
    } else if (length <= 10) {
      this.pk = 1
    } else if (length <= 15) {
      this.pk = 2
    } else if (length <= 20) {
      this.pk = 3
    } else if (length <= 25) {
      this.pk = 4
    } else {
      this.pk = 5
    }
  }
}
</script>

<style scoped>
.h1-color {
  color:azure;
}
.h2-color {
  color:khaki;
}
.layout {
    display: flex;
    flex-direction: column;
    flex: 1 1 auto;
    flex-wrap: nowrap;
    min-width: 0;
}
</style>