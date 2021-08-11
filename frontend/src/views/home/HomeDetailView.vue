<template>
  <div class="white">
    {{ feed }}
    <v-card class="mx-auto" color="#2A355D" dark max-width="400">
      <v-card-title>
        <v-list-item-avatar color="grey darken-3" @click="$router.push('/mypage')">
          <!-- https://m.blog.naver.com/lizziechung/221793761299 -->
          <!-- {{ feed.user.profileImg }} -->
          <v-img @click="onClickUser(feed)" class="elevation-6" alt="" :src=feed.user.profileImg></v-img>
        </v-list-item-avatar>
          <!-- <v-list-item-title class="pa-2">Fromecha</v-list-item-title> -->
          <span @click="onClickUser(feed)">{{ feed.user.name }}</span>
      </v-card-title>    
        <HomeDetailMap />
          
    </v-card>
    <!-- <div>

    </div> -->
  </div>


</template>

<script>
import { mapGetters, } from 'vuex'
import HomeDetailMap from '@/components/common/HomeDetailMap.vue'

export default {
  name: 'HomeDetailView',
  components: {
    HomeDetailMap,
  },
  data() {
    return {
      map: null,
    }
  },
  computed: {
    ...mapGetters(['places', 'polyLine', 'imgList','feeds', 'feed', ])
  },
  created() {
    console.log(this.$route.params)
    console.log(this.$route.params.feedId)
    this.feeds[this.feeds.length] = this.$route.params.feedId
    this.$store.dispatch('selectedFeed', this.feeds)
    // console.log('created마지막2', this.feed.places.length)
    // console.log('created마지막3', this.feed.places[0].lat, this.feed.places[0].lng)
    // console.log('created마지막3', this.feed.places[1].lat, this.feed.places[1].lng)
    // console.log('created마지막3', this.feed.places[2].lat, this.feed.places[2].lng)

  },
  methods: {
 
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
