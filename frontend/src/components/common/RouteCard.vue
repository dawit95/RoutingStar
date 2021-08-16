<template>
  <v-flex xs12 class="big-box rounded-lg">
    <div class="list-prop">
      <!-- 프로필 이미지, 이름 -->
      <div>
        <v-list-item-avatar size="45px">
          <v-img @click="onClickUser(feed)" class="elevation-6" alt="" :src="feed.user.profileImg"></v-img>
        </v-list-item-avatar>
        <span class="profile-font" @click="onClickUser(feed)">{{ feed.user.name }}</span>
      </div>
      
      <div class="box">
        <!-- 썸네일 이미지 -->
        <div v-for="(place, idx) in feed.places" :key="idx" class="d-flex justify-center container">
          <span v-if="place.isThumbnail" class="d-flex justify-center">
            <img class="main-image" @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})" :src=place.placeImg alt="">
            <img class="mid-image" @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})" src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/mid-image-black.png" alt="">
            <img class="logo-image" @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})" src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/LOGO1.png" alt="">
            <img class="route-image" @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})" :src=feed.routeImg alt="">
          </span>
        </div>
        <!-- 좋아요, 저장 -->
        <v-row class="pr-6 pt-1" align="center" justify="end">

          <div v-if="feed.isLiked">
            <v-icon @click="requestLike(feed.id)" color="red" class="mr-1">mdi-heart</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestLike(feed.id)" color="red" class="mr-1">mdi-heart-outline</v-icon>
          </div> 
          <div class="subheading mr-2 name-font">{{ feed.likeCnt }}</div>
          
          <div v-if="feed.isStored">
            <v-icon @click="requestStore(feed.id)" color="#B4DFE5" class="mr-1">mdi-bookmark</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestStore(feed.id)" color="#B4DFE5" class="mr-1">mdi-bookmark-outline</v-icon>
          </div> 
          <div class="subheading name-font">{{ feed.storageCnt }}</div>

        </v-row>
        <!-- 루트설명 -->
        <v-row align="center" justify="center">
          <v-card-text v-if="feed.routeDescription" class="text-center route-description2 text-h7 font-weight-bold route-description-font">
            {{ feed.routeDescription }}
          </v-card-text>
          <v-card-text v-else class="text-center route-description2 text-h7 font-weight-bold route-description-font">
            루트에 대한 설명이 없습니다.
          </v-card-text>
        </v-row>
      </div>
    </div>
  </v-flex>
</template>


<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'RouteCard',
  data() {
    return {
    }
  },
  props: {
    feed: Object,
  },
  computed: {
    ...mapGetters(['jwt', 'feeds', 'searchedNonFollowRoutes', 'isLiked', 'isSaved']),
  },

  methods: {
    ...mapActions(['enterUserprofile']),

    requestLike(id) {
      this.jwt[3] = id
      this.$store.dispatch('fetchLike', this.jwt)
    },
    requestStore(id) {
      this.jwt[3] = id
      this.$store.dispatch('fetchStore', this.jwt)
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
}
</script>

<style scoped>
.big-box {
  /* width: 95%; */
  margin-left: 25px;
  margin-right: 25px;
}
/* 얘도 */
.container {
  margin: 0px;
  padding: 0px;
}
/* img {
  width: 150px; height: 150px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
} */
.box {
  position: relative;
  margin-bottom: 40px;
}
/* .thumbnail {
  top: 0;
  left: 0;
  position: relative;
} */
/* .routeImg {
  position: absolute;
  top: -10px;
  left: 110px; */
  /* transform: translate( 10%, 10% ); */
/* } */
.list-prop {
  background-color: #101423;
  color:white;
  border-radius: 30px;
  box-shadow:  10px 10px 5px #06080e,
              -10px -10px 5px #1a2038;
}
.profile-font {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 20px;
  margin-left: -5px;
}
.main-image {
  width: 200px; height: 200px;
  object-fit: cover;
  border-radius: 50%;
}
.route-image {
  width: 200px; height: 200px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
}
.mid-image {
  width: 200px; height: 200px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  justify-content: center;
  opacity: 25%;
}
.logo-image {
  width: 190px; height: 190px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  top: 2%;
}
.name-font {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 20px;
}
.route-description2 {
  width: 90%;
  border-radius: 10px;
  text-align: center;
  background-color:#2a355d;
  padding: 10px 10px 10px 10px;
  margin: 10px 10px 10px 10px;
  background: #c1c8e4;
  box-shadow: inset 5px 5px 10px #7a7e90,
              inset -5px -5px 10px #ffffff;
}
.route-description-font {
  font-family: 'Nanum Gothic Coding', monospace;
}
</style>