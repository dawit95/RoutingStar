<template>
  <v-container>
    <div class="mt-7">
      <button style="color:white" @click="goBack">뒤로가기</button>
    </div>
    <v-card class="followTabs" dark>
      <v-tabs
        v-model="tab"
        background-color="transparent"
        grow
      >
        <v-tab
          v-for="item in items"
          :key="item"
        >
          {{ item }}
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">

        <v-tab-item>
          <v-card>
            <div v-if="followUserList" >

                <div v-for="(follower, idx) of followUserList.followerList" v-bind:key="idx">
                  <div @click="onClickUser(follower.follower)">
                    <img class="followListImg" :src=follower.follower.profileImg alt="">
                    <p>{{ follower.follower.name }}</p>
                  </div>
                </div>

            </div>
            <div v-else >아직 루트메이트가 없습니다. 찾으러 가볼까요?</div>
          </v-card>
        </v-tab-item>

        <v-tab-item>
          <v-card>
            <div v-if="followUserList" >
              
              <div v-for="(following, idx) of followUserList.followingList" v-bind:key="idx">
                <div @click="onClickUser(following.following)">
                  <img class="followListImg" :src=following.following.profileImg alt="">
                  <p>{{ following.following.name }}</p>
                </div>
              </div>
              
            </div>
            <div v-else >아직 루트메이트가 없습니다. 찾으러 가볼까요?</div>
          </v-card>
        </v-tab-item>
      
      </v-tabs-items>
    </v-card>
  </v-container>

</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'FollowUserListView',
  data () {
    return {
    tab: null,
    items: ['Followers', 'Followings',],
    followUsers: {}
    }
  },
  computed: {
    ...mapGetters(['followUserList', 'jwt'])
  },
  methods: {
    ...mapActions(['enterUserprofile']),

    goBack() {
      this.$router.go(-1);
    },
    // 닉네임, 사진 누르면 프로필로 간다
    onClickUser(user) {
      this.enterUserprofile({
        userId: user.id,
        access_token: this.jwt[0],
        jwtId: this.jwt[2]
      })
    }
  },
  mounted() {
    // this.followUsers = this.followUserList
    console.log('여기 마운틷')
    console.log(this.followUserList)
    const friends = this.followUserList
    this.followUsers = friends
    console.log(friends)
  }

}
</script>

<style>
.followListImg {
  width: 30px; height: 30px;
  /* object-fit: cover; */
  /* object-position: top; */
  border-radius: 50%;
}
</style>