<template>
      <v-container >
    <!-- 프로필 이미지 / 게시물 / 팔로워 / 팔로우 -->
        <v-row>
          <v-col md="3">
            <div slot="activator">
              <!-- 아래의 if 분기는 프로필 이미지 없을때의 처리 -->
              <!-- <v-avatar size="150px" v-if="!userInfo.profileImage" class="grey lighten-3 mb-3">
                <span>프로필 이미지가 들어갈 공간입니다</span>
              </v-avatar>
              <v-avatar size="150px" v-else class="mb-3">
                <img :src="userInfo.profileImg" alt="profileImage">
              </v-avatar> -->
              <v-avatar size="150px" class="grey lighten-3 mb-3">
                <span>프로필 이미지가 들어갈 공간입니다(테스트용)</span>
              </v-avatar>
            </div>
          </v-col>

          <v-col md="9">
            <v-row class="d-flex justify-center">
              <div>
                <!-- <h1>{{ userInfo.name }}</h1> -->
                <h1>NICKNAME</h1>
              </div>
            </v-row>
          
            <v-row class="d=flex justify-center">
              <v-col md="6">
                <div class="mt-4 title">Followings</div>
                <!-- <div class="mt-4 title">{{ followingUserList.length }}</div> -->
                <div class="mt-4 title">0</div>
              </v-col>
              <v-col>
                <div class="mt-4 title">Followers</div>
                <div class="mt-4 title">0</div>
                <!-- <div class="mt-4 title">{{ followerUserList.length }}</div> -->
              </v-col>
            </v-row>
          </v-col>          
        </v-row>

        <v-row class="d-flex justify-center mb-4">
          <div class="mt-4 title">user description</div>
          <!-- <div class="mt-4 title">{{ userInfo.userDescription }}</div> -->
        </v-row>
    </v-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ProfileInfo',
  computed: {
    ...mapGetters(['userInfo', 'followingUserList', 'followerUserList'])
  },
  methods: {
    ...mapActions([]),
  }
}
</script>                                                                               

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>