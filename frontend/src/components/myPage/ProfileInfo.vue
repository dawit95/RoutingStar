<template>
  <v-container class="profileInfo">
    <v-row class="moveDown">
      <v-col cols="5" xs="5" class="d-flex justify-center">
        <div slot="activator"><v-avatar size="100px" class="mb-3"><img :src="userInfo.profileImg" alt="profileImage"></v-avatar></div>
      </v-col>
      <v-col cols="7" xs="7">
        <v-row class="mt-1 d-flex justify-center pr-5">
          <div class="Nanum"><h4>{{ userInfo.name }}</h4></div>
        </v-row>
        <v-row class="d-flex justify-center" @click="onClickFollowList">
          <v-col class="ma-1 pa-1 pr-5">
            <div class="mt-1 d-flex justify-center Nanum">Followers</div>
            <div v-if="followUserList" class="mt-1 d-flex justify-center">{{ followerCnt }}</div>
            <div v-else class="mt-1 d-flex justify-center Nanum">0</div>
          </v-col>
          <v-col class="ma-1 pa-1 pr-4">
            <div class="mt-1 d-flex justify-center Nanum">Followings</div>
            <div v-if="followUserList" class="mt-1 d-flex justify-center">{{ followingCnt }}</div>
            <div v-else class="mt-1 d-flex justify-center Nanum">0</div>
          </v-col>
        </v-row>
      </v-col>          
    </v-row>

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
    }
  },
  computed: {
    ...mapGetters(['jwt', 'userInfo', 'followUserList']),
  },
  methods: {
    ...mapActions(['fetchUserInfo', 'fetchFollowUserList']),

    setFollowCnt() {
      this.followerCnt = this.followUserList.followerList.length
      this.followingCnt = this.followUserList.followingList.length
    },
    onClickFollowList() {
      this.$router.push({ name: 'FollowUserListView' })
    }
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
  },
  watch: {
    followUserList: function() {
      this.setFollowCnt()
    }
  }
}
</script>                                                                               

<style scoped>
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
.moveRightLittle {
  margin-left: 10px;
}
</style>