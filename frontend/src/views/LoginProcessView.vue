<template>
  <div></div>
</template>

<script>
import axios from 'axios';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'LoginProcessView',
  computed: {
    ...mapGetters(['jwt', 'getterbrowserToken']),
  },
  methods: {
    ...mapActions(['sendBrowerToken']),
  },
  mounted() {
    console.log('여기 로그인 프로세스 마운트');
    // if (this.jwt[0].legth) {
    //   this.$router.push( {name: 'LoginProcessView'} )
    // }
    this.$router.push({ name: 'HomeView' });
  },
  // watch: {
  //   jwt: function(jwt) {
  //     console.log(jwt)
  //     if (jwt[0]) {
  //       this.$router.push( {name: 'LoginProcessView'} )
  //     }
  //   }
  // },
  created() {
    console.log('loginprocess is created!!!!!!!' + this.$route);
    // 로그인 되 있는 사용자인지 / 처음으로 로그인 된 사용자인지 / 로그인 안된 사용자인지
    //  if (this.jwt[0]) {
    //    console.log('token이 이미 저장이 되어있음')
    //    this.$store.dispatch('fetchLoginedToken', this.jwt)
    //    this.$store.dispatch('fetchLoginedFeeds', this.jwt)
    //  }
    //  else if (this.$route.query.access && this.$route.query.refresh){
    if (this.$route.query.access && this.$route.query.refresh) {
      console.log('token을 지금 처음 저장함');
      const token = {
        access: this.$route.query.access,
        refresh: this.$route.query.refresh,
      };
      this.$store.dispatch('createUser', token);
      this.$store.dispatch('createHome', token);
    } else {
      console.log('token이 없음');
      // axios.get('http://i5a309.p.ssafy.io:8000/api/v1/routes')
      axios
        .get('https://i5a309.p.ssafy.io/api/guest/routes')
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    }
    // main page 들어오자마자 피드 정보들 받아오기
    // 참조: https://jasonwatmore.com/post/2020/07/23/vue-axios-http-get-request-examples
    // 현재 내가 로그인되있어서 user pk가 가지고 있다면
    // likes, routes_storage, routes에 요청을 보내야 필요한 정보를 모두 얻을 수 있음
    // store/home.js 생성 후 state 에 정보 저장
  },
  // watch: {
  //   getterbrowserToken: function() {
  //     console.log(this.getterbrowserToken)
  //     const browser_token = this.getterbrowserToken
  //     const user_id = this.jwt[2]
  //     const data = {
  //       user_id: user_id,
  //       browser_token: browser_token
  //     }
  //     console.log('여기 브라우저 토큰 보내는 시점')
  //     console.log(data)
  //     this.sendBrowerToken(data)
  //   }
  // }
};
</script>

<style></style>
