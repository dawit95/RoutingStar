<template>
  <v-card-text align="center" class="py-2">
    <v-btn @click="kakaoLogin" color="transparent" depressed icon>
      <v-img :src="require('../../assets/kakao_login_medium_wide.png')" />
    </v-btn>
  </v-card-text>
</template>

<script>
export default {
  name: 'Kakao',
  methods: {
    kakaoLogin() {
      // 2. 인증코드로 토큰요청
      window.Kakao.Auth.login({
        scope: 'profile_nickname, account_email, gender',
        success: function (authObj) {

          console.log(authObj);
          // 3. 토큰으로  API 호출
          window.Kakao.API.request({
            url:'/v2/user/me',
            success: res => {
              const kakao_account = res.kakao_account;
              console.log(res)
              console.log(kakao_account)
            }
          });
        }
      });
    }
  }
}
// export default {
//   name: 'Kakao',
//   methods: {
//     kakaoLogin() {
//       window.Kakao.Auth.login({
//         scope: 'profile_nickname, account_email, gender',
//         success: this.getMe,
//       })
//     },
//     getMe(authObj) {
//       console.log(authObj);
//       window.Kakao.API.request({
//         url: '/v2/user/me',
//         success: res => {
//           const kakao_account = res.kakao_account;
//           console.log(kakao_account)
//         },
//         fail : error => {
//             console.error(error);
//         },
//       });
//     },
//   },
// }
</script>

<style>

</style>