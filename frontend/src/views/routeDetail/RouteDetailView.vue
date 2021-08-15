<template>
    <div class="mx-auto tmpBackgroud" max-width="400">
      <div v-if="routeInfo.user">
        <v-list-item-avatar color="grey darken-3">
          <v-img v-if="routeInfo.user" @click="onClickUser(routeInfo)" class="elevation-6" alt="" :src="routeInfo.user.profileImg"></v-img>
        </v-list-item-avatar>
          <!-- <v-list-item-title class="pa-2">Fromecha</v-list-item-title> -->
          <span @click="onClickUser(routeInfo)">{{ routeInfo.user.name }}</span>
          <v-btn v-if="routeInfo.user.id === this.jwt[2] || routeInfo.isStored" @click="moveToRevisePage(routeInfo.id)" icon>
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
      </div>    
        <RouteDetailMap />

        <div v-for="(whatTag, idx) in routeInfo.whatTag" v-bind:key="idx +'i'">
          {{ whatTag.title }}
        </div>

        <div v-for="(withTag, idx) in routeInfo.withTag" v-bind:key="idx+ 'j'">
          {{ withTag.title }}
        </div>

        <div>  
          <div v-if="routeInfo.isLiked">
            <v-icon @click="requestLike(routeInfo.id)" class="mr-1">mdi-heart</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestLike(routeInfo.id)" class="mr-1">mdi-heart-outline</v-icon>
          </div> 
          <div class="subheading mr-2">{{ routeInfo.likeCnt }}</div>

          <div v-if="routeInfo.isStored">
              <v-icon @click="requestStore(routeInfo.id)" class="mr-1">mdi-bookmark</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestStore(routeInfo.id)" class="mr-1">mdi-bookmark-outline</v-icon>
          </div> 
          <div class="subheading">{{ routeInfo.storageCnt }}</div>


          <div v-for="(place, idx) in routeInfo.places" :key="idx+'k'">
            <span v-if="place.isThumbnail===true">
              <span class="thumbnail"><img :src=place.placeImg alt=""></span>
              <span class="routeImg"><img :src=routeInfo.routeImg alt=""></span>
            </span>
          </div>
          {{ routeInfo.routeDescription }}

        </div>
  
        <div>
          <CommentBox/>
        </div>
    
    </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import RouteDetailMap from '@/components/routeDetail/RouteDetailMap.vue'
import CommentBox from '@/components/routeDetail/CommentBox.vue'

export default {
  name: 'RouteDetailView',
  components: {
    RouteDetailMap,
    CommentBox
  },
  data() {
    return {
      map: null,
    }
  },
  computed: {
    ...mapGetters(['routeInfo', 'jwt', 'isLiked', 'isSaved'])
  },
  methods: {
    ...mapActions(['enterUserprofile', 'fetchRouteInfo', 'clearRouteInfo']),

    requestLike(id) {
      this.jwt[3] = id
      this.$store.dispatch('fetchLike', this.jwt)
    },
    requestStore(id) {
      this.jwt[3] = id
      this.$store.dispatch('fetchStore', this.jwt)
    },
    moveToRevisePage(routeId) {
      // feed를 작성한 userId와 현재 로그인한 userID와 같다면 => reviseroute/routeId
      if (this.routeInfo.user.id === this.jwt[2]) {
        this.$router.push({ name: 'ReviseRouteView', params: { routeId: `${routeId}`}})
        // 작성자와 로그인유저가 다르고, 로그인 유저가 저장한 feed라면 => reviseothers/routeId
      } else if (this.routeInfo.user.id !== this.jwt[2] && this.routeInfo.isStored) {
        this.$router.push({ name: 'ReviseOthersRouteView', params: { routeId: `${routeId}`}})
      }
    },
    // 닉네임, 사진 누르면 프로필로 간다
    onClickUser(feed) {
      this.enterUserprofile({
        userId: feed.user.id,
        access_token: this.jwt[0],
        jwtId: this.jwt[2]
      })
    }, 
  },
  created() {
    this.fetchRouteInfo({
      userId: this.jwt[2],
      routeId: this.$route.params.feedId,
      access_token: this.jwt[0]
    })
  },
  watch: {
    isLiked: function() {
      console.log('불려야돼')
        this.fetchRouteInfo({
        userId: this.jwt[2],
        routeId: this.$route.params.feedId,
        access_token: this.jwt[0]
        })
    },
    isSaved: function() {
      console.log('얘도 불려야돼')
      this.fetchRouteInfo({
        userId: this.jwt[2],
        routeId: this.$route.params.feedId,
        access_token: this.jwt[0]
        })
    },
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* 수정필요 전역 ㄴㄴ */
.container {
  margin: 0px;
  padding: 0px;
}
img {
  width: 150px; height: 150px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
}
.box {
  position: relative;
}
.thumbnail {
  top: 0;
  left: 0;
  position: relative;
}
.routeImg {
  position: absolute;
  top: -10px;
  left: 110px;
  /* transform: translate( 10%, 10% ); */
}
.tmpBackgroud{
  background-color: aliceblue;
}
</style>
