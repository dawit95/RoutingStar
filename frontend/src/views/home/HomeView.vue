<template>
  <v-container pa-0>
    <Header />
    
    <div class="white">
      {{ feeds }}
    </div>
   
   <v-list
    v-for="(feed, idx) in feeds" 
    :key="idx"
    :feed="feed"
    >
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
     

      <v-card-text class="text-h5 font-weight-bold">
        <div class="container">
          <div class="box">

            <!-- <v-for="(place, idx) in feed.places" 
            :key="idx"
            :place="place">
            fw
            </v-for> -->
        <div v-for="(place, idx) in feed.places" :key="idx">
          <span v-if="place.isThumbnail===true">
             <span class="thumbnail" @click="$router.push({name: 'HomeDetailView', params: { feedId: 123 }})"><img :src=place.placeImg alt=""></span>
            <span class="routeImg" @click="$router.push({name: 'HomeDetailView', params: { feedId: 123 }})"><img :src=feed.routeImg alt=""></span>
          </span>
        </div>
          </div>
        </div>


        <!-- "Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well." -->
      </v-card-text>

    <!-- 기타 버튼 등이 들어가는 v-card-actions -->
    <v-card-actions>
      <v-list-item class="grow">
        <v-row align="center" justify="end">
          
          <div v-if="feed.isLiked">
            <v-icon @click="requestLike(feed.id, idx)" class="mr-1">mdi-heart</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestLike(feed.id, idx)" class="mr-1">mdi-heart-outline</v-icon>
          </div> 
          <div class="subheading mr-2">{{ feed.likeCnt }}</div>


          <div v-if="feed.isStored">
            <v-icon @click="requestStore(feed.id, idx)" class="mr-1">mdi-bookmark</v-icon>
          </div>
          <div v-else>
            <v-icon @click="requestStore(feed.id, idx)" class="mr-1">mdi-bookmark-outline</v-icon>
          </div> 
          <div class="subheading">{{ feed.storageCnt }}</div>
        </v-row>
      </v-list-item>
    </v-card-actions>
  </v-card>
  <v-card class="mx-auto mt-3" color="#2A355D" dark max-width="400">
         <v-card-text class="text-h5 font-weight-bold">
        {{ feed.routeDescription }}
      </v-card-text>
  </v-card>
   </v-list>
        <!-- Hacker News item loop -->
    <!-- {{item}} -->
    <!-- </div> -->
<!-- infiniteHandler method 실행 -->
<!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->

    <Nav />
  </v-container>
</template>

<script>
// import Header from '@/components/common/Header.vue'
// infinite scroll: 참조사이트: https://peachscript.github.io/vue-infinite-loading/guide/#installation
// import InfiniteLoading from 'vue-infinite-loading'
import Nav from '@/components/common/Nav.vue'
import Header from '@/components/common/Header.vue'
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
  components: {
    Nav,
    // InfiniteLoading,
    Header,
    // HomeDetailView,
  },
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
  // mounted () {
  //   if (this.name === 'HomeView') {
  //     console.log('home')
  //     // this.$router.push({name: 'HomeView'})
  //     this.value = 2
  //   }
  // },
  computed: {
    ...mapGetters(['jwt', 'feeds']),

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
    ...mapActions(['fetchUserInfo']),

    requestLike( id, idx ) {
      this.jwt[3] = id
      if (this.feeds[idx].isLiked) {
        this.feeds[idx].likeCnt -= 1
      } else {
        this.feeds[idx].likeCnt += 1     
      }
      this.feeds[idx].isLiked = !this.feeds[idx].isLiked 
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