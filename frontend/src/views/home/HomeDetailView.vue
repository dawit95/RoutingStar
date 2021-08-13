<template>
  <div class="white" height="10000">
    <div class="mx-auto" color="#2A355D" dark max-width="400">
      <div>
        <v-list-item-avatar color="grey darken-3">
          <v-img @click="onClickUser(routeInfoWithComment)" class="elevation-6" alt="" :src="routeInfoWithComment.user.profileImg"></v-img>
        </v-list-item-avatar>
          <!-- <v-list-item-title class="pa-2">Fromecha</v-list-item-title> -->
          <span @click="onClickUser(routeInfoWithComment)">{{ routeInfoWithComment.user.name }}</span>
          <v-btn v-if="routeInfoWithComment.user.id === this.jwt[2] || routeInfoWithComment.isStored" @click="moveToRevisePage(routeInfoWithComment.id)" icon>
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
      </div>    
        <HomeDetailMap />

        <div v-for="(whatTag, idx) in routeInfoWithComment.whatTag" v-bind:key="idx +'i'">
          {{ whatTag.title }}
        </div>

        <div v-for="(withTag, idx) in routeInfoWithComment.withTag" v-bind:key="idx+ 'j'">
          {{ withTag.title }}
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


          <div v-for="(place, idx) in routeInfoWithComment.places" :key="idx+'k'">
            <span v-if="place.isThumbnail===true">
              <span class="thumbnail"><img :src=place.placeImg alt=""></span>
              <span class="routeImg"><img :src=routeInfoWithComment.routeImg alt=""></span>
            </span>
          </div>

          {{ routeInfoWithComment.routeDescription }}

        </div>
  
        <div>
          <CommentBox/>
        </div>
    
    </div>
  </div>


</template>

<script>
import { mapGetters, mapActions } from 'vuex'
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
    ...mapGetters(['places', 'polyLine', 'imgList', 'routeInfoWithComment', 'jwt', 'isLiked', 'isSaved'])
  },
  created() {
    this.fetchRouteInfoWithComment(this.$route.params.feedId)
  },
  methods: {
    ...mapActions(['fetchRouteInfoWithComment', 'enterUserprofile']),

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
      if (this.ferouteInfoWithCommented.user.id === this.jwt[2]) {
        this.$router.push({ name: 'ReviseRouteView', params: { routeId: `${routeId}`}})
        // 작성자와 로그인유저가 다르고, 로그인 유저가 저장한 feed라면 => reviseothers/routeId
      } else if (this.routeInfoWithComment.user.id !== this.jwt[2] && this.routeInfoWithComment.isStored) {
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
    }
  },
  watch: {
    isLiked: function() {
      console.log('불려야돼')
      this.fetchRouteInfoWithComment(this.$route.params.feedId)
    },
    isSaved: function() {
      console.log('얘도 불려야돼')
      this.fetchRouteInfoWithComment(this.$route.params.feedId)
    }

  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
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
</style>
