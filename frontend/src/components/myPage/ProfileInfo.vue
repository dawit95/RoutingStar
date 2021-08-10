<template>
      <v-container class="profileInfo">
    <!-- 프로필 이미지 / 팔로워수 / 팔로우수 / username/ userDesrciption -->
        <v-row>
          <v-col class="my-3 pa-1">
            <div slot="activator">
              <!-- 아래의 if 분기는 프로필 이미지 없을때의 처리 -->
              <!-- <v-avatar size="100px" v-if="userInfo.profileImage" class="grey lighten-3 mb-3">
                <span>프로필 이미지가 들어갈 공간입니다</span>
              </v-avatar> -->
              <v-avatar size="100px" class="mb-3">
                <img :src="userInfo.profileImg" alt="profileImage">
              </v-avatar>
              <!-- <v-avatar size="100px" class="grey lighten-3 mb-3">
                <span>프로필 이미지가 들어갈 공간입니다(테스트용)</span>
              </v-avatar> -->
            </div>
          </v-col>

          <v-col class="mt-1">
            <v-row class="d-flex justify-center mt-1">
              <div>
                <h4>{{ userInfo.name }}</h4>
                <!-- <h4>NICKNAME</h4> -->
              </div>
            </v-row>
          
            <v-row class="d=flex justify-center">
              <v-col class="ma-1 pa-1">
                <div class="mt-1">Followings</div>
                <div v-if="followUserList" class="mt-1">0</div>
                <div v-else class="mt-1">{{ followUserList[0].length }}</div>
              </v-col>
              <v-col class="ma-1 pa-1">
                <div class="mt-1">Followers</div>
                <div v-if="followUserList" class="mt-1">0</div>
                <div v-else class="mt-1">{{ followUserList[1].length }}</div>
              </v-col>
            </v-row>
          </v-col>          
        </v-row>

        <v-row class="d-flex justify-center mb-1">
          <!-- <div class="mt-3">user description</div> -->
          <div class="mt-3" v-if="userInfo.userDescription">{{ userInfo.userDescription }}</div>
          <div class="mt-3" v-else>{{ userInfo.email }}</div>
        </v-row>
    </v-container>
</template>

{
  "success": {
    "id": 1,
    "email": "tkahgksekdbw@naver.com",
    "name": "김유진",
    "profileImg": "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
    "userDescription": null,
    "followed": null
  }
}


<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ProfileInfo',
  data () {
    return {
    }
  },
  computed: {
    ...mapGetters(['jwt', 'userInfo', 'followUserList']),
  },
  methods: {
    ...mapActions(['fetchUserInfo', 'fetchFollowUserList']),
  },
  created() {
    const access_token = this.jwt[0]
    console.log('여기 마운티드')
    console.log(this.jwt[0])
    this.fetchUserInfo({
      userId: this.jwt[2],
      access_token: access_token
    })
    console.log("왜")
    console.log(this.jwt[2])
    this.fetchFollowUserList({
      userId: this.jwt[2],
      access_token: access_token
    })
  },
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