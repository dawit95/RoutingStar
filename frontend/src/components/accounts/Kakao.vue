<template>
  <v-card-text align="center" class="py-2">
    <v-btn @click="kakaoLogin" color="transparent" depressed icon>
      <v-img :src="require('../../assets/kakao_login_medium_wide.png')" />
    </v-btn>
  </v-card-text>
</template>

<script>
// import axios from 'axios'

export default {
  name: 'Kakao',
  data: function () {
    return {
      isLogin: false
    }
  },
  watch: {
    isLogin () {
      this.$router.push({ name: 'About'})
    }
  },
  methods: {
    kakaoLogin() {
      // 2. 인증코드로 토큰요청
      window.Kakao.Auth.login({
        scope: 'profile_nickname, account_email, gender',
        success: function (authObj) {
          // console.log(authObj);
          const token = authObj.access_token
          if (token) {
            localStorage.setItem("access-token", token)
          }
          // 3. 토큰으로  API 호출
          window.Kakao.API.request({
            url:'/v2/user/me',
            success: res => {
              console.log(res)
              // console.log(res.kakao_account);
              // const kakao_account = res.kakao_account;
              // const userInfo = {
              //   nickname : kakao_account.profile.nickname,
              //   email : kakao_account.email,
              //   password : '',
              //   account_type : 1,
              }
            //   axios({
            //     method: 'post',
            //     url: `https://server_url/api/user/login/kakao`,
            //     data: {
            //       userId : userInfo.email,
            //       userName : userInfo.nickname,
            //     }
            //   })
            //   .then(res => {
            //     const token = res.data['auth_token'];
            //     if (token) {
            //       localStorage.setItem("access-token", token)
            //       this.router.push("/home")
            //     }
            //   })
            //   .catch(err => console.error(err))
            // },
            // fail : error => {
            //     console.error(error);
            // }
          });
          // this.$router.push({ name: 'About'})
        }
      });
      const token = localStorage.getItem('access-token')
      if (token) {
        this.$router.push("/about")
      }
    }
  }
}
</script>

<style>

</style>