<template>
  <div>
    <p style="color: white">로그인한 유저가 보는 자기 페이지입니다.</p>
    <ProfileInfo/>
    <div class="button mx-8">Edit Profile</div>
    <MyRoutesTabs/>

  </div>
</template>

<script>
import ProfileInfo from '@/components/myPage/ProfileInfo.vue'
import MyRoutesTabs from '@/components/myPage/MyRoutesTabs.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'MyPageView',
  components: {
    ProfileInfo,
    MyRoutesTabs,
  },
  data() {
    return {
      }
  },
  computed: {
    ...mapGetters(['jwt']),
  },
  methods: {
    ...mapActions(['fetchLoginedToken', 'fetchWrittenRouteList', 'fetchSavedRouteList'])
  },
  mounted() {
    const access_token = this.jwt
    console.log(access_token)
    this.fetchLoginedToken(access_token)
    this.fetchWrittenRouteList({
      userId: this.jwt[2], 
      access_token: this.jwt[0]
    })
    this.fetchSavedRouteList({
      userId: this.jwt[2], 
      access_token: this.jwt[0]
    })
  },
}
</script>

<style>
.button {
  flex: 1 1 auto;
  padding: 3px;
  margin: 7px;
  border: 2px solid #D2FDFF;
  text-align: center;
  text-transform: uppercase;
  position: relative;
  overflow: hidden;
  transition: 0.3s;
  color: #D2FDFF;
  height: 30px;
  font-size: 13px;
  background-color: #101423;
}
.button:after {
  position: absolute;
  transition: 0.3s;
  content: "";
  width: 0;
  left: 50%;
  bottom: 0;
  height: 3px;
  background: #D2FDFF;
}
.button:hover {
  cursor: pointer;
}
.button:hover:after {
  width: 100%;
  left: 0;
}
.button:hover {
  box-shadow: 0px 0px 0px 3px #f7f7f7;
}
.button:hover:after {
  width: 0%;
}
</style>