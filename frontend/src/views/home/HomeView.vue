<template>
  <v-container>
    <div v-if="!this.feeds.length">
      <EmptyHome />
    </div>
    <v-flex xs12 class="big-box rounded-lg">
      <div v-for="(feed, idx) in feeds" :key="idx" :feed="feed">
        <div class="list-prop">
          <!-- 프로필 이미지, 이름 -->
          <div>
            <v-list-item-avatar size="45px">
              <!-- https://m.blog.naver.com/lizziechung/221793761299 -->
              <v-img
                @click="onClickUser(feed)"
                class="elevation-6"
                :src="feed.user.profileImg"
                alt="profileImg"
              ></v-img>
            </v-list-item-avatar>
            <span class="profile-font" @click="onClickUser(feed)">{{ feed.user.name }}</span>
          </div>
          <!-- 본문내용 -->
          <div>
            <div>
              <div class="box">
                <!-- 썸네일 이미지 -->
                <div
                  v-for="place of feed.places"
                  :key="place.createdOrder"
                  class="d-flex justify-center container"
                >
                  <span v-if="place.isThumbnail" class="d-flex justify-center">
                    <img
                      @click="
                        $router.push({ name: 'RouteDetailView', params: { feedId: `${feed.id}` } })
                      "
                      class="main-image"
                      :src="place.placeImg"
                      alt=""
                    />
                    <img
                      @click="
                        $router.push({ name: 'RouteDetailView', params: { feedId: `${feed.id}` } })
                      "
                      class="mid-image "
                      src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/mid-image-black.png"
                      alt=""
                    />
                    <img
                      @click="
                        $router.push({ name: 'RouteDetailView', params: { feedId: `${feed.id}` } })
                      "
                      class="logo-image"
                      src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/LOGO1.png"
                      alt=""
                    />
                    <img
                      @click="
                        $router.push({ name: 'RouteDetailView', params: { feedId: `${feed.id}` } })
                      "
                      class="route-image"
                      :src="feed.routeImg"
                      alt=""
                    />
                  </span>
                </div>
                <!-- 좋아요, 저장 -->
                <v-row class="pr-6 pt-1" align="center" justify="end">
                  <div v-if="feed.isLiked">
                    <v-icon @click="requestLike(feed.id)" color="red" class="mr-1"
                      >mdi-heart</v-icon
                    >
                  </div>
                  <div v-else>
                    <v-icon @click="requestLike(feed.id)" color="red" class="mr-1"
                      >mdi-heart-outline</v-icon
                    >
                  </div>
                  <div class="subheading mr-2 name-font">{{ feed.likeCnt }}</div>

                  <div v-if="feed.isStored">
                    <v-icon @click="requestStore(feed.id)" color="#B4DFE5" class="mr-1"
                      >mdi-bookmark</v-icon
                    >
                  </div>
                  <div v-else>
                    <v-icon @click="requestStore(feed.id)" color="#B4DFE5" class="mr-1"
                      >mdi-bookmark-outline</v-icon
                    >
                  </div>
                  <div class="subheading name-font">{{ feed.storageCnt }}</div>
                </v-row>
                <!-- 루트설명 -->
                <v-row align="center" justify="center">
                  <v-card-text
                    class="text-center route-description2 text-h7 font-weight-bold route-description-font"
                  >
                    {{ feed.routeDescription }}
                  </v-card-text>
                </v-row>
              </div>
            </div>
          </div>
        </div>
      </div>
    </v-flex>
    <!-- Hacker News item loop -->
    <!-- {{item}} -->
    <!-- </div> -->
    <!-- infiniteHandler method 실행 -->
    <!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->
  </v-container>
</template>

<script>
// infinite scroll: 참조사이트: https://peachscript.github.io/vue-infinite-loading/guide/#installation
// import InfiniteLoading from 'vue-infinite-loading'

import axios from 'axios';
import { mapGetters, mapActions } from 'vuex';
import EmptyHome from '../../components/common/EmptyHome.vue';

// 1. created 되는 순간에 axios get 요청으로 데이터 받아오기
// 2. mapGetters에서 필터링 해주기
// infinte scroll 실험
const api = 'https://hn.algolia.com/api/v1/search_by_date?tags=story';
export default {
  components: {
    EmptyHome,
  },
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
    };
  },
  computed: {
    ...mapGetters(['jwt', 'feeds', 'getterbrowserToken', 'isLiked', 'isSaved']),

    // readUser
  },
  created() {
    // 로그인 되 있는 사용자인지 / 처음으로 로그인 된 사용자인지 / 로그인 안된 사용자인지
    if (this.jwt[0]) {
      console.log('token이 이미 저장이 되어있음');
      this.$store.dispatch('fetchLoginedToken', this.jwt);
      this.$store.dispatch('fetchLoginedFeeds', this.jwt);
    }
    axios
      .get(`https://i5a309.p.ssafy.io/push/${this.jwt[2]}`, {
        headers: {
          browser_token: this.getterbrowserToken,
        },
      })
      .then(console.log)
      .catch(console.log);
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
      this.jwt[3] = id;
      // if (this.feeds[idx].isLiked) {
      //   this.feeds[idx].likeCnt -= 1
      // } else {
      //   this.feeds[idx].likeCnt += 1
      // }
      // this.feeds[idx].isLiked = !this.feeds[idx].isLiked
      this.$store.dispatch('fetchLike', this.jwt);
    },
    requestStore(id) {
      this.jwt[3] = id;
      // if (this.feeds[idx].isStored) {
      //   this.feeds[idx].storageCnt -= 1
      // } else {
      //   this.feeds[idx].storageCnt += 1
      // }
      // this.feeds[idx].isStored = !this.feeds[idx].isStored
      this.$store.dispatch('fetchStore', this.jwt);
    },

    infiniteHandler($state) {
      axios
        .get(api, {
          params: {
            page: this.page,
          },
        })
        .then(({ data }) => {
          console.log(data);
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
      console.log(this.jwt.refresh);
      console.log(this.jwt.access);
      const config = {
        headers: {
          access_token: this.jwt.access,
          refresh_token: this.jwt.refresh,
        },
      };

      axios
        .get(`https://i5a309.p.ssafy.io/token/refresh`, config)
        // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
        .then((res) => {
          console.log(res);
        })
        .catch((fail) => console.log(fail));
    },

    // 닉네임, 사진 누르면 프로필로 간다
    onClickUser(feed) {
      this.enterUserprofile({
        userId: feed.user.id,
        access_token: this.jwt[0],
        jwtId: this.jwt[2],
      });
    },
  },
  watch: {
    // getterbrowserToken: function() {
    //   const user_id = this.jwt[2]
    //   console.log('여기 브라우저 토큰 보내는 시점')
    //   console.log(user_id)
    //   this.sendBrowerToken(user_id)
    // },
    getterbrowserToken: function() {
      const user_id = this.jwt[2];
      this.sendBrowerToken(user_id);
    },
    isLiked: function() {
      this.$store.dispatch('fetchLoginedFeeds', this.jwt);
    },
    isSaved: function() {
      this.$store.dispatch('fetchLoginedFeeds', this.jwt);
    },
  },
};
</script>

<style scoped>
.big-box {
  /* width: 95%; */
  margin-left: 25px;
  margin-right: 25px;
}
.main-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
  /* object-position: top; */
  border-radius: 50%;
}
.mid-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  /* left: 17.5%; */
  /* left: 50%; */
  justify-content: center;
  opacity: 25%;
}
.logo-image {
  width: 190px;
  height: 190px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  top: 2%;
  /* left: 19.3%; */
}
.route-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  /* left: 17.5%; */
  /* left: 50%; */
  /* justify-content: center; */
  /* opacity: 25%; */
}
.container {
  /* padding-top: 0; */
  margin: 0;
  padding: 0px;
  /* margin-bottom: 10px; */
}
/* 카드하나속성 */
.box {
  position: relative;
  margin-bottom: 40px;
}
.profile-font {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 20px;
  margin-left: -5px;
}
.name-font {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 20px;
}
.background-image {
  background-image: url('../../assets/background-color.jpg');
}
.list-prop {
  /* background-color: rgb(98, 98, 145); */
  background-color: #101423;
  color: white;
  border-radius: 30px;
  /* background: linear-gradient(145deg, #111525, #0e1220); */
  box-shadow: 10px 10px 5px #06080e, -10px -10px 5px #1a2038;
}
.route-description1 {
  width: 90%;
  /* height: 50px; */
  border-radius: 10px;
  text-align: center;
  /* background-color:#2a355d; */
  padding: 10px 10px 10px 10px;
  margin: 10px 10px 10px 10px;
  /* line-height: 50px; */
  /* background: #c1c8e4; */
  /* box-shadow: inset 5px 5px 10px #7a7e90,
              inset -5px -5px 10px #ffffff; */

  /* box-sizing: border-box; */
  /* display: inline-block; */
  border: 1px solid transparent;
  /* border-image: linear-gradient(to right, #01c9ca 0%, #3886FF 100%); */
  border-image: linear-gradient(to right, #5680e9 0%, #84ceeb 20%, #5ab9ea 20%, #c1c8e4, #8860d0);
  border-image-slice: 1;
  background: black;
  border-image-width: 1px;

  /* background-image: linear-gradient(to right, #fbfcb9be, #ffcdf3aa, #65d3ffaa); */
  /* background-image: linear-gradient(to right, #5680e9, #84ceeb, #5ab9ea, #c1c8e4, #8860d0); */
  /* background-origin: border-box;
  background-clip: content-box, border-box; */
}
.route-description2 {
  width: 90%;
  border-radius: 10px;
  text-align: center;
  background-color: #2a355d;
  padding: 10px 10px 10px 10px;
  margin: 10px 10px 10px 10px;
  /* background: #c1c8e4;
  box-shadow: inset 5px 5px 10px #7a7e90,
              inset -5px -5px 10px #ffffff; */
  background: #2a355d;
  box-shadow: inset 5px 5px 10px #181e35, inset -5px -5px 10px #3c4c85;
}
.route-description-font {
  font-family: 'Nanum Gothic Coding', monospace;
}
</style>
