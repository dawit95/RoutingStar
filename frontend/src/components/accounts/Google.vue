<template>
    <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccess" :onFailure="onFailure"></GoogleLogin>
</template>

<script>

import GoogleLogin from 'vue-google-login';
const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
 
 export default {
  name: 'Google',
  data() {
    return {
    // client_id is the only required property but you can add several more params, full list down bellow on the Auth api section
    params: {
        client_id: API_KEY
    },
    // only needed if you want to render the button with the google ui
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