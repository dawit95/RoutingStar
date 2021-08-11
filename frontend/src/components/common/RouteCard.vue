<template>
  <div>
    <v-card class="mx-auto" color="#2A355D" dark max-width="400">
      <v-card-title>
        <v-list-item-avatar color="grey darken-3">
          <v-img @click="onClickUser(feed)" class="elevation-6" alt="" :src=feed.user.profileImg></v-img>
        </v-list-item-avatar>
          <span @click="onClickUser(feed)">{{ feed.user.name }}</span>
      </v-card-title>
    
      <v-card-text class="text-h5 font-weight-bold">
        <div class="container">
          <div v-for="(place, idx) in feed.places" :key="idx">
            <span v-if="place.isThumbnail===true">
              <span class="thumbnail" @click="$router.push({name: 'HomeDetailView', params: { feedId: 123 }})"><img :src=place.placeImg alt=""></span>
              <span class="routeImg" @click="$router.push({name: 'HomeDetailView', params: { feedId: 123 }})"><img :src=feed.routeImg alt=""></span>
            </span>
          </div>
        </div>
      </v-card-text>

      <!-- 기타 버튼 등이 들어가는 v-card-actions -->
      <v-card-actions>
        <v-list-item class="grow">
          <v-row align="center" justify="end">
            
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
          </v-row>
        </v-list-item>
      </v-card-actions>
    </v-card>
    
    <v-card class="mx-auto mt-3" color="#2A355D" dark max-width="400">
        <v-card-text v-if="feed.routeDescription" class="text-h5 font-weight-bold">
          {{ feed.routeDescription }}
        </v-card-text>
        <v-card-text v-else class="text-h5 font-weight-bold">
          루트에 대한 설명이 없습니다.
        </v-card-text>
    </v-card>
  </div>
</template>


<script>
import { mapGetters } from 'vuex'

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
    ...mapGetters(['jwt']),
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
    requestStore( id ) {
      this.jwt[3] = id
      if (this.feed.isStored) {
        this.feed.storageCnt -= 1
      } else {
        this.feed.storageCnt += 1     
      }
      this.feed.isStored = !this.feed.isStored 
      this.$store.dispatch('fetchStore', this.jwt)
    },

    // 닉네임, 사진 누르면 프로필로 간다
    onClickUser(feed) {
      // 피드에 올린 글쓴이가 로그인한 유저이면 mypage로
      if (this.jwt[2] === feed.user.id) {
        this.$router.push({ name: 'MyPageView' })
      } else {
        // 아니면 글쓴이의 profilepage로 간다
        this.$router.push({ name: 'OtherUserPageView' })
      }
    }
  }
}
</script>

<style scoped>
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