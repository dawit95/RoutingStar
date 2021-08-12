<template>
  <div class="white">
    {{ feed }}
    <div class="mx-auto" color="#2A355D" dark max-width="400">
      <div>
        <v-list-item-avatar color="grey darken-3" @click="$router.push('/mypage')">
          <!-- https://m.blog.naver.com/lizziechung/221793761299 -->
          <!-- {{ feed.user.profileImg }} -->
          <v-img @click="onClickUser(feed)" class="elevation-6" alt="" :src=feed.user.profileImg></v-img>
        </v-list-item-avatar>
          <!-- <v-list-item-title class="pa-2">Fromecha</v-list-item-title> -->
          <span @click="onClickUser(feed)">{{ feed.user.name }}</span>
        <v-btn v-if="feed.user.id === this.jwt[2] || feed.isStored" @click="moveToRevisePage(feed.id)" icon>
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </div>
          
    </div>
    <!-- <div>

    </div> -->
  </div>


</template>

<script>
import { mapGetters, } from 'vuex'


export default {
  name: 'DetailViewButton',
  components: {

  },
  data() {
    return {
      map: null,
    }
  },
  computed: {
    ...mapGetters(['places', 'polyLine', 'imgList', 'feeds', 'feed', 'jwt'])
  },
  created() {
    console.log(this.$route.params)
    console.log(this.$route.params.feedId)
    this.feeds[this.feeds.length] = this.$route.params.feedId
    this.$store.dispatch('selectedFeed', this.feeds)
  },
  methods: {
    moveToRevisePage(routeId) {
      // feed를 작성한 userId와 현재 로그인한 userID와 같다면 => reviseroute/routeId
      if (this.feed.user.id === this.jwt[2]) {
        this.$router.push({ name: 'ReviseRouteView', params: { routeId: `${routeId}`}})
        // 작성자와 로그인유저가 다르고, 로그인 유저가 저장한 feed라면 => reviseothers/routeId
      } else if (this.feed.user.id !== this.jwt[2] && this.feed.isStored) {
        this.$router.push({ name: 'ReviseOthersRouteView', params: { routeId: `${routeId}`}})
      }
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
