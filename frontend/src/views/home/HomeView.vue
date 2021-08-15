<template>
  <v-container>
    <v-flex xs12 class="big-box rounded-lg">

      <v-list
      v-for="(feed, idx) in feeds" 
      :key="idx"
      :feed="feed"
      class="pt-0 pr-1 pb-4"
      color="#101423"
      >
      <v-card class="mb-4" color="#2A355D" dark>
        <v-card-title class="pa-2 pl-0 pb-0">
          <v-list-item-avatar size="45px">
            <!-- https://m.blog.naver.com/lizziechung/221793761299 -->
            <v-img @click="onClickUser(feed)" class="elevation-6" :src="feed.user.profileImg" alt="profileImg" ></v-img>
          </v-list-item-avatar>
            <span @click="onClickUser(feed)">{{ feed.user.name }}</span>
        </v-card-title>
      

        <v-card-text class="text-h5 pb-0">
          <div class="container">
            <div class="box">
              <div v-for="place of feed.places" :key="place.createdOrder" class="d-flex justify-center">
                <span v-if="place.isThumbnail===true">
                  <!-- <v-avatar size="50px" v-ripple class="thumbnail ml-1"
                  @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})">
                    <img :src="place.placeImg" alt="Image">
                  </v-avatar> -->
                  <span @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})"><img class="main-image" :src=place.placeImg alt=""></span>
                  <span @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}` }})"><img class="mid-image" src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/mid-image.png" alt=""></span>
                  <span class="routeImg2" @click="$router.push({name: 'RouteDetailView', params: { feedId: `${feed.id}`}})"><img class="main-image" :src=feed.routeImg alt=""></span>
                </span>
                
              </div>
              <v-row class="pb-1 pt-2" align="center" justify="end">
              
                <div v-if="feed.isLiked">
                  <v-icon @click="requestLike(feed.id)" color="red" class="mr-1">mdi-heart</v-icon>
                </div>
                <div v-else>
                  <v-icon @click="requestLike(feed.id)" class="mr-1">mdi-heart-outline</v-icon>
                </div> 
                <div class="subheading mr-2">{{ feed.likeCnt }}</div>


                <div v-if="feed.isStored">
                  <v-icon @click="requestStore(feed.id)" color="#B4DFE5" class="mr-1">mdi-bookmark</v-icon>
                </div>
                <div v-else>
                  <v-icon @click="requestStore(feed.id)" class="mr-1">mdi-bookmark-outline</v-icon>
                </div> 
                <div class="subheading">{{ feed.storageCnt }}</div>
              </v-row>
            </div>
          </div>
          <!-- "Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well." -->
        </v-card-text>

        <!-- 기타 버튼 등이 들어가는 v-card-actions -->
        <!-- <v-card-actions>
          <v-list-item >
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
        </v-card-actions> -->
    </v-card>
    <v-card class="mx-auto mt-3" color="#2A355D" dark max-width="400">
          <v-card-text class="text-center text-h5 font-weight-bold">
          {{ feed.routeDescription }}
        </v-card-text>
    </v-card>
    </v-list>
    </v-flex>
        <!-- Hacker News item loop -->
    <!-- {{item}} -->
    <!-- </div> -->
<!-- infiniteHandler method 실행 -->
<!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->

  </v-container>

</template>

<script>
// import Header from '@/components/common/Header.vue'
// infinite scroll: 참조사이트: https://peachscript.github.io/vue-infinite-loading/guide/#installation
// import InfiniteLoading from 'vue-infinite-loading'

// import { login } from '@/api/user.js'
import axios from 'axios'
import { mapGetters, mapActions } from 'vuex'
// import HomeDetailView from '@/views/home/HomeDetailView.vue'

// 1. created 되는 순간에 axios get 요청으로 데이터 받아오기
// 2. mapGetters에서 필터링 해주기
// infinte scroll 실험
const api = 'https://hn.algolia.com/api/v1/search_by_date?tags=story'

export default {
  name: 'HomeView',
  data() {
    return {
      // profile과 관련된 이미지는 jwt에 있다면 안받아와도 될 것 같습니다
      // 아마 서버에서 아래의 정보들을 함께 모아놓은 객체로 받아올 것 같습니다?
      // profileAvaterImg: '',
      // profileNickname: '',
      // thumbnail: '',
      // like: False,
      // save: False,
      // description: '',
      page: 1,
      list: [],
    }
  },
  computed: {
    ...mapGetters(['jwt', 'feeds', 'getterbrowserToken', 'isLiked', 'isSaved']),

    // readUser
  },
  created () {
    // 로그인 되 있는 사용자인지 / 처음으로 로그인 된 사용자인지 / 로그인 안된 사용자인지
  if (this.jwt[0]) {
    console.log('token이 이미 저장이 되어있음')
    this.$store.dispatch('fetchLoginedToken', this.jwt)
    this.$store.dispatch('fetchLoginedFeeds', this.jwt)
   }
  },
  // else if (this.$route.query.access && this.$route.query.refresh){
    //   console.log('token을 지금 처음 저장함')
  //   const token = 
  //     {
    //       access: this.$route.query.access,
  //       refresh: this.$route.query.refresh
  //     }
  //     this.$store.dispatch('createUser', token)
  //     this.$store.dispatch('createHome', token)
  //   } 
  // else {
    //     console.log('token이 없음')
  //     // axios.get('http://i5a309.p.ssafy.io:8000/api/v1/routes')
  //     axios.get('http://i5a309.p.ssafy.io:8000/api/guest/routes')
  //     .then((res) => {console.log(res)})
  //     .catch((err) => {console.log(err)})
  // }
  //   // main page 들어오자마자 피드 정보들 받아오기
  //   // 참조: https://jasonwatmore.com/post/2020/07/23/vue-axios-http-get-request-examples
  //   // 현재 내가 로그인되있어서 user pk가 가지고 있다면
  //   // likes, routes_storage, routes에 요청을 보내야 필요한 정보를 모두 얻을 수 있음
  //   // store/home.js 생성 후 state 에 정보 저장

  // },
  methods: {
    ...mapActions(['enterUserprofile', 'sendBrowerToken']),

    // requestLike( id, idx ) {
    //   this.jwt[3] = id
    //   if (this.feeds[idx].isLiked) {
    //     this.feeds[idx].likeCnt -= 1
    //   } else {
    //     this.feeds[idx].likeCnt += 1     
    //   }
    //   this.feeds[idx].isLiked = !this.feeds[idx].isLiked 
    //   this.$store.dispatch('fetchLike', this.jwt)
    // },
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

    infiniteHandler($state) {
      axios.get(api, {
        params: {
          page: this.page,
        },
      }).then(({ data }) => {
        console.log(data)
        if (data.hits.length) {
          this.page += 1;
          this.list.push(...data.hits);
          $state.loaded();
        } else {
          $state.complete();
        }
      });
    },
    renew() {
      // console.log(this.jwt),
    console.log(this.jwt.refresh)
    console.log(this.jwt.access)
    const config = {
      headers: {
        'access_token': this.jwt.access,
        'refresh_token': this.jwt.refresh,
      }
    }
    
    axios.get(`http://i5a309.p.ssafy.io:8000/token/refresh`, config)
    // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
    .then(res => {console.log(res)})  
    .catch((fail) => console.log(fail))  
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
  watch: {
    getterbrowserToken: function() {
      const user_id = this.jwt[2]
      this.sendBrowerToken(user_id)
    },
    isLiked: function() {
      this.$store.dispatch('fetchLoginedFeeds', this.jwt)
    },
    isSaved: function() {
      this.$store.dispatch('fetchLoginedFeeds', this.jwt)
    }

  },
  
}
</script>

<style scoped>
.big-box {
  /* width: 95%; */
  margin-left: 10px;
  margin-right: 10px;
}
/* 전역으로 사용되고 있음 안됨 */
.main-image {
  width: 200px; height: 200px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  /* filter: grayscale(50%); */
}
.mid-image {
  width: 200px; height: 200px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  left: 65px;
  opacity: 25%;
}
.container {
  margin: 0px;
  padding: 0px;
}
.box {
  position: relative;
}
/* .thumbnail { */
  /* top: 0;
  left: 0;
  position: relative; */
/* } */
.routeImg2 {
  position: absolute;
  /* top: -10px; */
  left: 65px;
  /* transform: translate( 10%, 10% ); */
}

</style>
