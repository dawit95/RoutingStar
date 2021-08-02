<template>
  <v-app>
    <!-- <Header /> -->
    <!-- state를 통해 정보를 가져왔다 가정하고 card 제작 -->
      <v-card
    class="mx-auto"
    color="#26c6da"
    dark
    max-width="400"
  >
    <v-card-title>
      <v-icon
        large
        left
      >
        mdi-twitter
      </v-icon>
      <span class="text-h6 font-weight-light">Twitter</span>
    </v-card-title>

    <v-card-text class="text-h5 font-weight-bold">
      "Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well."
    </v-card-text>

    <v-card-actions>
      <v-list-item class="grow">
        <v-list-item-avatar color="grey darken-3">
          <v-img
            class="elevation-6"
            alt=""
            src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>Evan You</v-list-item-title>
        </v-list-item-content>

        <v-row
          align="center"
          justify="end"
        >
          <v-icon class="mr-1">
            mdi-heart
          </v-icon>
          <span class="subheading mr-2">256</span>
          <span class="mr-1">·</span>
          <v-icon class="mr-1">
            mdi-share-variant
          </v-icon>
          <span class="subheading">45</span>
        </v-row>
      </v-list-item>
    </v-card-actions>
  </v-card>
    <!-- <v-container>
      <v-row justify="space-around">
        <v-card>
          <v-img height="200px" src="https://cdn.pixabay.com/photo/2020/07/12/07/47/bee-5396362_1280.jpg">
          <v-toolbar-title class="mt-3 ml-3">
            <v-avatar size="56">
              <img
                alt="user"
                src="https://cdn.pixabay.com/photo/2020/06/24/19/12/cabbage-5337431_1280.jpg"
              >
            </v-avatar>
              <span class="white--text">

              John Doe
              </span>

          </v-toolbar-title>

          
        </v-img>
        
        </v-card>
                <v-row
          align="center"
          justify="end"
        >
          <v-icon class="mr-1">
            mdi-heart
          </v-icon>
          <span class="subheading mr-2">256</span>
          <span class="mr-1">·</span>
          <v-icon class="mr-1">
            mdi-share-variant
          </v-icon>
          <span class="subheading">45</span>
        </v-row>
      </v-row>
    </v-container> -->

    <Nav />
  </v-app>
</template>

<script>
// import Header from '@/components/common/Header.vue'
import { getFeedList } from '@/api/home.js'

import Nav from '@/components/common/Nav.vue'
// import axios from 'axios'

// 1. created 되는 순간에 axios get 요청으로 데이터 받아오기
// 2. mapGetters에서 필터링 해주기

export default {
  name: 'HomeView',
  components: {
    Nav,
    // Header,
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

    }
  },
  mounted () {
    if (this.name === 'Home') {
      console.log('home')
      this.value = 2
    }
  },
  created () {
    // main page 들어오자마자 피드 정보들 받아오기
    // 참조: https://jasonwatmore.com/post/2020/07/23/vue-axios-http-get-request-examples
    // 현재 내가 로그인되있어서 user pk가 가지고 있다면
    // likes, routes_storage, routes에 요청을 보내야 필요한 정보를 모두 얻을 수 있음
    // store/home.js 생성 후 state 에 정보 저장
    getFeedList(
      (res) => {console.log(res)},
      (err) => {console.log(err)}
    )  
  },
}
</script>

<style>

</style>