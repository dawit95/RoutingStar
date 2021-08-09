<template>
      <v-container class="profileInfo">
    <!-- 프로필 이미지 / 게시물 / 팔로워 / 팔로우 -->
        <v-row>
          <v-col class="my-3 pa-1">
            <div slot="activator">
              <!-- 아래의 if 분기는 프로필 이미지 없을때의 처리 -->
              <!-- <v-avatar size="150px" v-if="!userInfo.profileImage" class="grey lighten-3 mb-3">
                <span>프로필 이미지가 들어갈 공간입니다</span>
              </v-avatar>
              <v-avatar size="150px" v-else class="mb-3">
                <img :src="userInfo.profileImg" alt="profileImage">
              </v-avatar> -->
              <v-avatar size="100px" class="grey lighten-3 mb-3">
                <span>프로필 이미지가 들어갈 공간입니다(테스트용)</span>
              </v-avatar>
            </div>
          </v-col>

          <v-col class="mt-1">
            <v-row class="d-flex justify-center mt-1">
              <div>
                <!-- <h1>{{ userInfo.name }}</h1> -->
                <h4>NICKNAME</h4>
              </div>
            </v-row>
          
            <v-row class="d=flex justify-center">
              <v-col class="ma-1 pa-1">
                <div class="mt-1">Followings</div>
                <!-- <div class="mt-4">{{ followingUserList.length }}</div> -->
                <div class="mt-1">0</div>
              </v-col>
              <v-col class="ma-1 pa-1">
                <div class="mt-1">Followers</div>
                <div class="mt-1">0</div>
                <!-- <div class="mt-4">{{ followerUserList.length }}</div> -->
              </v-col>
            </v-row>
          </v-col>          
        </v-row>

        <v-row class="d-flex justify-center mb-1">
          <div class="mt-3">user description</div>
          <!-- <div class="mt-4">{{ userInfo.userDescription }}</div> -->
        </v-row>
    </v-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ProfileInfo',
  computed: {
    ...mapGetters(['jwt', 'userInfo', 'followUserList'])
  },
  methods: {
    ...mapActions(['fetchUserInfo', 'fetchFollowUserList']),
  },
  mounted() {
    const tmp_id = 1
    const access_token = this.jwt[0]
    console.log('여기 마운티드')
    console.log(this.jwt[0])
    this.fetchUserInfo({
      userId: tmp_id, 
      access_token: access_token
    })
    this.fetchFollowUserList({
      userId: tmp_id,
      access_token: access_token
    })
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
</style>