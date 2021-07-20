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
  methods: {
    kakaoLogin() {
      // 2. 인증코드로 토큰요청
      window.Kakao.Auth.login({
        // 내가 받아 볼 사용자 정보
        scope: 'profile_nickname, account_email, gender',
        // 로그인 성공 시
        success: (authObj) => {
          console.log(authObj)
          const token = authObj.access_token
          localStorage.setItem("access-token", token)
          this.$router.push({ name: 'About' })
          
          // 3. 토큰으로  API 호출
          window.Kakao.API.request({
            url: '/v2/user/me',
            success: function (res) {
              console.log(res)
            }
          })
        },
        // 로그인 실패 시
        fail: err => console.error(err)
      })
    },
  },
}
</script>

<style>

</style>