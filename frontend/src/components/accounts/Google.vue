<template>
<div>
    <!-- <a href="https://192.168.1.51:8080/oauth2/authorization/google">google1</a>
    <a href="http://192.168.1.51:8080/oauth2/authorization/google">google2</a>
    <a href="http://localhost:8080/oauth2/authorization/google">google3</a> -->

    <a href="http://i5a309.p.ssafy.io:8000/oauth2/authorization/kakao">
      <v-img class="mx-auto logoTopM" max-height="300" max-width="300" :src="require('../../assets/kakao_login_medium_wide.png')" />
    </a>
    <div class="rounded-lg">

    <a href="http://i5a309.p.ssafy.io:8000/oauth2/authorization/google">
      <v-img class="mx-auto loginButtonM circle" max-height="40" max-width="315" :src="require('../../assets/btn_google_signin_dark_pressed_web@2x.png')" />
    </a>
    </div>
    <!-- <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess" :onFailure="onFailure"></GoogleLogin> -->
</div>
</template>

<script>

// import GoogleLogin from 'vue-google-login';
import axios from 'axios'
const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
 
 export default {
  name: 'Google',
  data() {
    return {
    params: {
        client_id: API_KEY
    },
    renderParams: {
        width: 250,
        height: 50,
        longtitle: true
            }
        }
  },
  components: {
    // GoogleLogin,
  },
  methods: {
    // onSuccess(googleUser) {
    // console.log(googleUser);
    // // This only gets the user information: id, name, imageUrl and email
    // console.log(googleUser.getBasicProfile());
    // }
    
    test() {
      axios.post('http://i5a309.p.ssafy.io:8000/oauth2/authorization/kakao')
      .then((res) => {console.log(res)})
      .catch((err) => {console.log(err)})    
       
    // }
    //   )
    },
  }
 }

</script>

<style>
.logoTopM {margin-top: 70px;}
.loginButtonM {margin-top: 30px;}
.circle {border-radius: 15px;}
</style>