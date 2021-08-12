<template>
  <div class="white" height="10000">
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
        <!-- <v-card-text> -->
        <HomeDetailMap />
                <!-- </v-card-text> -->
          
        <!-- <v-row align="center" justify="end"> -->
          <!-- {{ feed.isLiked}}
          {{ feed.isLiked}}
          {{ feed.id }} -->
        
             <!-- {{ feed.whatTag.length }}
             {{ feed.whatTag[0] }}
             {{ feed.whatTag[0].title }}
             {{ feed.withTag }} -->
        <!-- <li v-for="item in feed.withTag">{{ item }}</li> -->
        <!-- <li v-for="tag in feed.whatTag" ></li> -->


         <div v-for="n in feed.whatTag.length" v-bind:key="n">
           {{ feed.whatTag[n-1].title }}
         </div>
 
         <div v-for="n in feed.withTag.length" v-bind:key="n">
           {{ feed.withTag[n-1].title }}
         </div>

        <div>  
          <div v-if="feed.isLiked">
            <v-icon @click="requestLike(feed.id)" class="mr-1">mdi-heart</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestLike(feed.id)" class="mr-1">mdi-heart-outline</v-icon>
          </div> 
          <div class="subheading mr-2">{{ feed.likeCnt }}</div>

        <div v-if="feed.isStored">
            <v-icon @click="requestStore(feed.id)" class="mr-1">mdi-bookmark</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestStore(feed.id)" class="mr-1">mdi-bookmark-outline</v-icon>
          </div> 
          <div class="subheading">{{ feed.storageCnt }}</div>


        <div v-for="(place, idx) in feed.places" :key="idx">
          <span v-if="place.isThumbnail===true">
             <span class="thumbnail"><img :src=place.placeImg alt=""></span>
            <span class="routeImg"><img :src=feed.routeImg alt=""></span>
          </span>
        </div>

        {{ feed.routeDescription }}

          <div>---</div>
              <div>---</div>
                  <div>---</div>
                      <div>---</div>
        <!-- </v-row> -->
        </div>
    </div>
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
    ...mapGetters(['places', 'polyLine', 'imgList','feeds', 'feed', 'jwt', ])
  },
  created() {
    console.log(this.$route.params)
    console.log(this.$route.params.feedId)
    this.feeds[this.feeds.length] = this.$route.params.feedId
    this.$store.dispatch('selectedFeed', this.feeds)


  },
  methods: {
    requestLike( id ) {
      this.jwt[3] = id
      if (this.feed.isLiked) {
        this.feed.likeCnt -= 1
      } else {
        this.feed.likeCnt += 1     
      }
      this.feed.isLiked = !this.feed.isLiked 
      this.$store.dispatch('fetchLike', this.jwt)
    },
    requestStore( id, idx) {
      this.jwt[3] = id
      if (this.feeds[idx].isStored) {
        this.feeds[idx].storageCnt -= 1
      } else {
        this.feeds[idx].storageCnt += 1     
      }
      this.feeds[idx].isStored = !this.feeds[idx].isStored 
      this.$store.dispatch('fetchStore', this.jwt)
    },
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
