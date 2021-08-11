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
                  <img class="followListImg" :src=follower.follower.profileImg alt="">
                  {{ follower.follower.name }}
                </div>

            </div>
            <div v-else >아직 루트메이트가 없습니다. 찾으러 가볼까요?</div>
          </v-card>
        </v-tab-item>

        <v-tab-item>
          <v-card>
            <div v-if="followUserList" >
              
              <div v-for="(following, idx) of followUserList.followingList" v-bind:key="idx">
                <img class="followListImg" :src=following.following.profileImg alt="">
                {{ following.following.name }}
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
import { mapGetters } from 'vuex'

export default {
  name: 'FollowUserListView',
  data () {
    return {
    tab: null,
    items: ['Followers', 'Followings',],
    // text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    followUsers: {}
    }
  },
  computed: {
    ...mapGetters(['followUserList'])
  },
  methods: {
    goBack() {
      this.$router.go(-1);
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