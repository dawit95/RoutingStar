<template>
  <v-container class="profileInfo">
    <v-row>
      <v-col class="my-3 pa-1">
        <div slot="activator">
          <v-avatar size="100px" class="mb-3"><img :src="userInfo.profileImg" alt="profileImage"></v-avatar>
        </div>
      </v-col>
      <v-col class="mt-1">
        <v-row class="d-flex justify-center mt-1">
          <div><h4>{{ userInfo.name }}</h4></div>
        </v-row>
        <v-row class="d=flex justify-center" @click="onClickFollowList">
          <v-col class="ma-1 pa-1">
            <div class="mt-1">Followers</div>
            <div v-if="followUserList" class="mt-1">{{ followerCnt }}</div>
            <div v-else class="mt-1">0</div>
          </v-col>
          <v-col class="ma-1 pa-1">
            <div class="mt-1">Followings</div>
            <div v-if="followUserList" class="mt-1">{{ followingCnt }}</div>
            <div v-else class="mt-1">0</div>
          </v-col>
        </v-row>
      </v-col>          
    </v-row>

    <v-row class="d-flex justify-center mb-1">
      <div class="mt-3" v-if="userInfo.userDescription">{{ userInfo.userDescription }}</div>
      <div class="mt-3" v-else>{{ userInfo.email }}</div>
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

<style>
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
</style>