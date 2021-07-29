<template>
    <v-app>
      <v-container>
    <!-- 프로필 이미지 / 게시물 / 팔로워 / 팔로우 -->
        <v-row>
          <v-col md="3">
          <image-input v-model="avatar">
            <div slot="activator">
              <v-avatar size="150px" v-ripple v-if="!avatar" class="grey lighten-3 mb-3">
                <span>Click to add avatar</span>
              </v-avatar>
              <v-avatar size="150px" v-ripple v-else class="mb-3">
                <img :src="avatar.imageURL" alt="avatar">
              </v-avatar>
            </div>
          </image-input>
          <!-- 이미지 저장할 때 모션 및 메서드로 나중에 submit 으로 대체할 예정 -->
          <v-slide-x-transition>
            <div v-if="avatar && saved == false">
              <v-btn class="primary" @click="uploadImage" :loading="saving">Save Avatar</v-btn>
            </div>
          </v-slide-x-transition>
          </v-col>
          <v-col md="9">
            <v-row class="d-flex justify-center">
              <div>NICKNAME</div>
            </v-row>
          
            <v-row class="d=flex justify-center">
              <v-col md="6">
                <div class="mt-4 title">0</div>
                <div class="mt-4 title">좋아요</div>
              </v-col>
              <v-col>
                <div class="mt-4 title">0</div>
                <div class="mt-4 title">싫어요</div>
              </v-col>
            </v-row>
          </v-col>          
        </v-row>

        <v-row class="d-flex justify-center">
          <div class="mt-4 title">user description</div>
        </v-row>

    </v-container>
  </v-app>
</template>

<script>
import ImageInput from '@/components/myPage/ImageInput.vue'
// D:\SNS\frontend\src\components\myPage\ImageInput.vue
export default {
  name: 'ProfileInfo',
  data () {
    return {
      avatar: null,
      saving: false,
      saved: false
    }
  },
  components: {
    ImageInput: ImageInput
  },
  watch:{
    avatar: {
      handler: function() {
        this.saved = false
      },
      deep: true
    }
  },
  methods: {
    uploadImage() {
      this.saving = true
      setTimeout(() => this.savedAvatar(), 1000)
    },
    savedAvatar() {
      this.saving = false
      this.saved = true
    }
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