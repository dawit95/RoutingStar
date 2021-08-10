<template>
  <div>
    <p style="color: white">로그인한 유저가 보는 남의 페이지입니다.</p>
    <ProfileInfo/>
    <!-- <div class="button mx-8">Following</div> -->
    <div class="button mx-8" @click="onClickfollowOtherUser">{{ follow }}</div>
    <OtherUserRoutesTab/>

  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import ProfileInfo from '@/components/myPage/ProfileInfo.vue'
import OtherUserRoutesTab from '@/components/myPage/OtherUserRoutesTab.vue'

export default {
  name: 'OtherUserPageView',
  components: {
    ProfileInfo,
    OtherUserRoutesTab,
  },
  data() {
    return {
      follow: '',
      // followInfo :{
      //   userId: this.jwt[2],
      //   targetId: this.userInfo.Id,
      //   access_token: this.jwt[0]
      //   // userId: 3,
      //   // targetId: 1,
      // }
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'jwt'])
  },
  methods: {
    ...mapActions(['followOtherUser']),
    
    setFollow() {
      if (this.userInfo.followed == true ) {
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

  //   mounted() {
  //   const tmp_id = 2
  //   const access_token = this.jwt[0]
  //   console.log('여기 마운티드')
  //   console.log(this.jwt[0])
  //   this.fetchUserInfo({
  //     userId: tmp_id,
  //     access_token: access_token
  //   })
  //   this.fetchFollowUserList({
  //     userId: tmp_id,
  //     access_token: access_token
  //   })
  // }
  },
  created() {
    this.setFollow()
  },
  watch: {
    userInfo: function() {
      this.setFollow()
    }
  }
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