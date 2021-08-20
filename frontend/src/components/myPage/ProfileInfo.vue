<template>
  <v-container class="profileInfo">
    <v-row class="moveDown">
      <v-col cols="5" xs="5" class="d-flex justify-center">
        <div slot="activator"><v-avatar size="100px" class="mb-3"><img :src="userInfo.profileImg" alt="profileImage"></v-avatar></div>
      </v-col>
      <v-col cols="7" xs="7">
        <v-row class="mt-1 d-flex justify-center pr-5" @click="onClickFollowList">
          <div class="Nanum" ><h4>{{ userInfo.name }}</h4></div>
        </v-row>
        <v-row class="d-flex justify-center">
          <v-col class="ma-1 pa-1 pr-5">
            <div class="mt-1 d-flex justify-center Nanum">Followers</div>
            <div v-if="followUserList" class="mt-1 d-flex justify-center" @click="onClickFollowList">{{ followerCnt }}</div>
            <div v-else class="mt-1 d-flex justify-center Nanum" @click="onClickFollowList">0</div>
          </v-col>
          <v-col class="ma-1 pa-1 pr-4">
            <div class="mt-1 d-flex justify-center Nanum">Followings</div>
            <div v-if="followUserList" class="mt-1 d-flex justify-center" @click="onClickFollowList">{{ followingCnt }}</div>
            <div v-else class="mt-1 d-flex justify-center Nanum" @click="onClickFollowList">0</div>
          </v-col>

        </v-row>
        <v-row>
          <div v-if="jwt[2]==userInfo.id" class="button Dohyeon mr-6 d-flex justify-center">Edit</div>
          <div v-else class="button Dohyeon mr-6 d-flex justify-center" @click="onClickfollowOtherUser">{{ follow }}</div>
             <!-- <div class="button mx-8 Dohyeon mr-3 d-flex justify-center">Edit</div> -->

        </v-row>
      </v-col>          
    </v-row>
   <!-- <v-row>  -->
     <!-- <div class="button mx-8 Dohyeon mr-3 d-flex justify-center">Edit</div> -->

     <!-- </v-row> -->
    
    <v-row class="d-flex justify-center mb-3">
      <div class="mt-3 Nanum moveRightLittle" v-if="userInfo.userDescription">{{ userInfo.userDescription }}</div>
      <div class="mt-3 Nanum moveRightLittle" v-else>{{ userInfo.email }}</div>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ProfileInfo',
  data () {
    return {
      followerCnt: 0,
      followingCnt: 0,
      follow: '',
    }
  },
  computed: {
    ...mapGetters(['jwt', 'userInfo', 'followUserList']),
  },
  methods: {
    ...mapActions(['followOtherUser', 'fetchUserInfo', 'fetchFollowUserList', 'fetchLoginedToken',]),
    setFollow() {
      console.log('setFollow 불려짐')
      if (this.userInfo.following === true ) {
        this.follow = 'unfollow'
      } else {
        this.follow = 'follow'
      }
    },
    setFollowCnt() {
      this.followerCnt = this.followUserList.followerList.length
      this.followingCnt = this.followUserList.followingList.length
    },
    onClickFollowList() {
      this.$router.push({ name: 'FollowUserListView' })
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
    const access_token = this.jwt[0]
    // this.fetchUserInfo({
    //   userId: this.jwt[2],
    //   access_token: access_token
    // }),
    this.fetchFollowUserList({
      targetId: this.userInfo.id,
      access_token: access_token
    })
    this.setFollow()
    // const access_token = this.jwt
    this.fetchLoginedToken(this.jwt)
  },
  watch: {
    followUserList: function() {
      this.setFollowCnt()
    },
    userInfo: function() {
      this.setFollow()
    }
  }
}
</script>                                                                               

<style scoped>
.Dohyeon {
  font-family: 'Do Hyeon', sans-serif;
}
.button {
  flex: 1 1 auto;
  padding: 3px;
  margin: 7px;
  border: 2px solid #D2FDFF;
  border-radius: 5px;
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
.Nanum {
  font-family: 'Nanum Gothic Coding', monospace;
}

p {
  padding: 0px 50px 0px 0px;
}
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  margin-top: 2px;
}
.profileInfo {
  color: #D2FDFF;
  padding: 10px;
}
.moveUp {
  /* top: 1px; */
  margin-bottom: 50px;
}
.moveDown {
  margin-top: 5px;
}
.moveLeft {
  position: absolute;
  left: 160px;
}
.moveLeftLittle {
  margin-right: 3px;
}
.moveRight {
  margin-left: 0px;
}
.moveRightLittle {
  margin-left: 10px;
}
</style>