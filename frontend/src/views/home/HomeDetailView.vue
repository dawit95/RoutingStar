<template>
  <div class="white" height="10000">
    <!-- {{ routeInfoWithComment }} -->
    <div class="mx-auto" color="#2A355D" dark max-width="400">
      <div>
        
        <div>-------</div>
        <div>-------</div>
        <div>-------</div>
        <v-list-item-avatar color="grey darken-3" @click="$router.push('/mypage')">
          <v-img @click="onClickUser(routeInfoWithComment)" class="elevation-6" alt="" :src=routeInfoWithComment.user.profileImg></v-img>
        </v-list-item-avatar>
          <span @click="onClickUser(routeInfoWithComment)">{{ routeInfoWithComment.user.name }}</span>
          <v-btn v-if="routeInfoWithComment.user.id === this.jwt[2] || routeInfoWithComment.isStored" @click="moveToRevisePage(routeInfoWithComment.id)" icon>
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
      </div>    
 
        <HomeDetailMap />
          
 

         <div v-for="n in routeInfoWithComment.whatTag.length" v-bind:key="n">
           {{ routeInfoWithComment.whatTag[n-1].title }}
         </div>
         <div v-for="n in routeInfoWithComment.withTag.length" v-bind:key="n">
           {{ routeInfoWithComment.withTag[n-1].title }}
         </div>

        
        <div>  
          <div v-if="routeInfoWithComment.isLiked">
            <v-icon @click="requestLike(routeInfoWithComment.id)" class="mr-1">mdi-heart</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestLike(routeInfoWithComment.id)" class="mr-1">mdi-heart-outline</v-icon>
          </div> 
          <div class="subheading mr-2">{{ routeInfoWithComment.likeCnt }}</div>

          <div v-if="routeInfoWithComment.isStored">
              <v-icon @click="requestStore(routeInfoWithComment.id)" class="mr-1">mdi-bookmark</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestStore(routeInfoWithComment.id)" class="mr-1">mdi-bookmark-outline</v-icon>
          </div> 
          <div class="subheading">{{ routeInfoWithComment.storageCnt }}</div>


          <div v-for="(place, idx) in routeInfoWithComment.places" :key="idx">
            <span v-if="place.isThumbnail===true">
              {{ place }}
              <span class="thumbnail"><img :src=place.placeImg alt="" height="100px" width="100px"></span>
              <span class="routeImg"><img :src=routeInfoWithComment.routeImg alt="" height="100px" width="100px"></span>
            </span>
          </div>
        </div>
          {{ routeInfoWithComment.routeDescription }} 

            <div>---</div>
                <div>---</div>
                    <div>---</div>
                        <div>---</div>
          <!-- </v-row> -->
        <!-- </div> -->
  
        <div>
          <CommentBox/>
        </div>
    
    </div>
  </div>


</template>

<script>
import { mapGetters, } from 'vuex'
import HomeDetailMap from '@/components/common/HomeDetailMap.vue'
import CommentBox from '@/components/routeDetail/CommentBox.vue'

export default {
  name: 'HomeDetailView',
  components: {
    HomeDetailMap,
    CommentBox
  },
  data() {
    return {
      map: null,
    }
  },
  computed: {
    ...mapGetters(['places', 'polyLine', 'imgList','feeds', 'feed', 'jwt', 'routeInfoWithComment'])
  },
  created() {
    console.log(this.$route.params)
    console.log(this.$route.params.feedId)
    // this.feeds[this.feeds.length] = this.$route.params.feedId
    this.$store.dispatch('fetchRouteInfoWithComment', this.$route.params.feedId)


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
