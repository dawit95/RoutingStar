<template>
  <v-container>
    <ProfileInfo/>
    <v-row class="button mx-8 d-flex justify-center Dohyeon" @click="onClickfollowOtherUser">{{ follow }}</v-row>
    <OtherUserRoutesTab/>
  </v-container>
</template>

<script>
import ProfileInfo from '@/components/myPage/ProfileInfo.vue'
import OtherUserRoutesTab from '@/components/myPage/OtherUserRoutesTab.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'OtherUserPageView',
  components: {
    ProfileInfo,
    OtherUserRoutesTab,
  },
  data() {
    return {
      follow: '',
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'jwt'])
  },
  methods: {
    ...mapActions(['followOtherUser', 'fetchLoginedToken', 'fetchWrittenRouteList']),
    
    setFollow() {
      console.log('setFollow 불려짐')
      if (this.userInfo.following === true ) {
        this.follow = 'unfollow'
      } else {
        this.follow = 'follow'
      }
    },
    onClickfollowOtherUser() {
      let followInfo = {
        userId: this.jwt[2],
        targetId: this.userInfo.id,
        access_token: this.jwt[0]
      }
      console.log(followInfo)
      this.followOtherUser(followInfo)
    },
  },
  created() {
    this.setFollow()
    const access_token = this.jwt
    this.fetchLoginedToken(access_token)
  },
  watch: {
    userInfo: function() {
      this.setFollow()
    }
  }
}
</script>

<style scoped>
/* 얘도 고쳐야함 */
.button {
  flex: 1 1 auto;
  padding: 5px;
  margin: 7px;
  border: 2px solid #D2FDFF;
  /* text-align: center; */
  text-align: justify;
  text-transform: uppercase;
  position: relative;
  overflow: hidden;
  transition: 0.3s;
  color: #D2FDFF;
  height: 30px;
  font-size: 11px;
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
.moveDown {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>