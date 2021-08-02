<template>
<div>
    <a href="https://192.168.1.51:8080/oauth2/authorization/google">google1</a>
    <a href="http://192.168.1.51:8080/oauth2/authorization/google">google2</a>
    <a href="http://localhost:8080/oauth2/authorization/google">google3</a>

    <a href="http://i5a309.p.ssafy.io:8000/oauth2/authorization/kakao">google2</a>
    <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess" :onFailure="onFailure"></GoogleLogin>
</div>
</template>

<script>

import GoogleLogin from 'vue-google-login';
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
    GoogleLogin,
  },
  methods: {
    onSuccess(googleUser) {
    console.log(googleUser);
    // This only gets the user information: id, name, imageUrl and email
    console.log(googleUser.getBasicProfile());
    }
  },
}
</script>

<style>

</style>