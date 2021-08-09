<template>
  <v-container pa-0>
    <div class="white">
      <span>abc</span>
      {{ feeds }}
    </div>
    <!-- <Header /> -->
   
   <v-list
    v-for="(feed, idx) in feeds" 
    :key="idx"
    :feed="feed"
    >
      <!-- <router-link :to="mypage">
abafwef    </router-link> -->
    <v-card class="mx-auto" color="#2A355D" dark max-width="400">
      <v-card-title>
        <!-- {{ feed. }} -->
        <v-list-item-avatar color="grey darken-3" @click="$router.push('/mypage')">
          <!-- feed.places 썸네일로 교체 -->
          <v-img class="elevation-6" alt="" src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"></v-img>
        </v-list-item-avatar>
          <!-- <v-list-item-title class="pa-2">Fromecha</v-list-item-title> -->
          <span @click="$router.push('/mypage')">Fromecha</span>
      </v-card-title>
     

      <v-card-text class="text-h5 font-weight-bold">
        "Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well."
      </v-card-text>

    <!-- 기타 버튼 등이 들어가는 v-card-actions -->
    <v-card-actions>
      <v-list-item class="grow">
        <v-row align="center" justify="end">
          <v-icon class="mr-1">mdi-heart</v-icon>
          <span class="subheading mr-2">256</span>
          <span class="mr-1">·</span>
          <v-icon class="mr-1">mdi-share-variant</v-icon>
          <span class="subheading">45</span>
        </v-row>
        <!-- <v-row>
          {짧은 글 소개 }
        </v-row> -->
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

    <Header />
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
import { mapGetters, } from 'vuex'
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
  mounted () {
    if (this.name === 'HomeView') {
      console.log('home')
      // this.$router.push({name: 'HomeView'})
      this.value = 2
    }
  },
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
  else if (this.$route.query.access && this.$route.query.refresh){
    console.log('token을 지금 처음 저장함')
    const token = 
      {
        access: this.$route.query.access,
        refresh: this.$route.query.refresh
      }
      this.$store.dispatch('createUser', token)
      this.$store.dispatch('createHome', token)
    } 
  else {
      console.log('token이 없음')
      // axios.get('http://i5a309.p.ssafy.io:8000/api/v1/routes')
      axios.get('http://i5a309.p.ssafy.io:8000/api/guest/routes')
      .then((res) => {console.log(res)})
      .catch((err) => {console.log(err)})
  }
    // main page 들어오자마자 피드 정보들 받아오기
    // 참조: https://jasonwatmore.com/post/2020/07/23/vue-axios-http-get-request-examples
    // 현재 내가 로그인되있어서 user pk가 가지고 있다면
    // likes, routes_storage, routes에 요청을 보내야 필요한 정보를 모두 얻을 수 있음
    // store/home.js 생성 후 state 에 정보 저장

  },
  methods: {
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
    }
  }
}
</script>

<style scoped>

</style>