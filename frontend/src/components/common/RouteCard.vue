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
              <span class="thumbnail" @click="$router.push({name: 'HomeDetailView', params: { feedId: `${feed.id}` }})"><img :src=place.placeImg alt=""></span>
              <span class="routeImg" @click="$router.push({name: 'HomeDetailView', params: { feedId: `${feed.id}` }})"><img :src=feed.routeImg alt=""></span>
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
import { mapGetters, mapActions } from 'vuex'
// import axios from 'axios'

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
  created() {
    console.log('이것슨피드', this.feed)
    const data = {
      userId : this.jwt[2],
      access_token: this.jwt[0],
      param: {
        whatTag: [this.feed.whatTag[0].id],
        withTag: [this.feed.withTag[0].id],
        }
      }
    console.log('이거슨데이터', data)
    this.$store.dispatch('fetchSearchedRoutes', data)
  },

  methods: {
    ...mapActions(['enterUserprofile']),

    requestLike(id) {
      this.jwt[3] = id
      // if (this.feeds[idx].isLiked) {
      //   this.feeds[idx].likeCnt -= 1
      // } else {
      //   this.feeds[idx].likeCnt += 1     
      // }
      // this.feeds[idx].isLiked = !this.feeds[idx].isLiked 
      this.$store.dispatch('fetchLike', this.jwt)
    },
    requestStore(id) {
      this.jwt[3] = id
      // if (this.feeds[idx].isStored) {
      //   this.feeds[idx].storageCnt -= 1
      // } else {
      //   this.feeds[idx].storageCnt += 1     
      // }
      // this.feeds[idx].isStored = !this.feeds[idx].isStored 
      this.$store.dispatch('fetchStore', this.jwt)
    },
      // console.log(feed)
      // console.log(this.searchedNonFollowRoutes)
      // console.log(this.searchedNonFollowRoutes)
      // this.jwt[3] = nonFollowRoute.id
      // for ( const nonFollowRoute of this.searchedNonFollowRoutes) {
      //   if ( nonFollowRoute.id == feed.id) {
      //     console.log('일치', nonFollowRoute.id, feed.id, nonFollowRoute)
          // if (nonFollowRoute.isLiked) {
          //   nonFollowRoute.likeCnt -= 1
          // } else {
          //   nonFollowRoute.likeCnt += 1     
          // }
          // nonFollowRoute.isLiked = !nonFollowRoute.isLiked 
          // this.$store.dispatch('fetchLike', this.jwt)
        // }
      // }
    // },
      
      // if (this.feed.isLiked) {
      //   this.feed.likeCnt -= 1
      // } else {
      //   this.feed.likeCnt += 1     
      // }
      // this.feed.isLiked = !this.feed.isLiked 
      // this.$store.dispatch('fetchLike', this.jwt)

    //   const config = {
    //     headers: {
    //       'access_token': this.jwt[0],
    //     }
    //   }
     
    //   axios.get(`http://i5a309.p.ssafy.io:8000/api/v1/reaction/like/${this.jwt[2]}/${this.jwt[3]}`, config)
    //     .then(res => console.log('응답받은데이터', res.data.success))
    //     .catch((err) => console.log(err))
    //      if (this.jwt[4] == "좋아요 취소"){
    //        feed.likeCnt -= 1
    //        feed.isLiked = !feed.isLiked
    //       //  console.log(1, this.jwt)
    //      }
    //      else {
    //         feed.isLiked = !feed.isLiked
    //         feed.likeCnt += 1
    //         // console.log(2, this.jwt)
    //      }
    //     //  console.log('jwt', this.jwt)
    //     //  console.log('feed', feed)
    // },

    // requestStore( id ) {
    //   this.jwt[3] = id
    //   if (this.feed.isStored) {
    //     this.feed.storageCnt -= 1
    //   } else {
    //     this.feed.storageCnt += 1     
    //   }
    //   this.feed.isStored = !this.feed.isStored 
    //   this.$store.dispatch('fetchStore', this.jwt)
    // },

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
      const data = {
        userId : this.jwt[2],
        access_token: this.jwt[0],
        param: {
          whatTag: [this.feed.whatTag[0].id],
          withTag: [this.feed.withTag[0].id],
          }
      }
      this.$store.dispatch('fetchSearchedRoutes', data)
    },
    isSaved: function() {
      console.log('얘도 불려야돼')
      const data = {
        userId : this.jwt[2],
        access_token: this.jwt[0],
        param: {
          whatTag: [this.feed.whatTag[0].id],
          withTag: [this.feed.withTag[0].id],
          }
      }
      this.$store.dispatch('fetchSearchedRoutes', data)
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