<template>
  <v-layout v-if="canRendering" row swap>

    <ReviseHeader :tempThumbnail="tempThumbnail"
    @freeze-map="freezeMap" @recover-freeze-map="recoverFreezeMap"/>

    <UpdateMap :isFreeze="isFreeze" 
    :resPlacesData="responseData.success.places" 
    @thumbnail-checked="thumbnailChecked" 
    @update-tumbnail-image="updateThumbnailImage"/> 

    <ImageInput @update-tumbnail-image="updateThumbnailImage"/>

    <UpdateMapPointForm @update-tumbnail-image="updateThumbnailImage" 
    :isthumbail="isthumbail" 
    :listLength="responseData.success.places.length"
    @change-isthumbail="changeIsthumbail"/>

  </v-layout>
</template>

<script>
import ReviseHeader from '@/components/revise/ReviseHeader.vue'
import UpdateMap from '@/components/revise/UpdateMap.vue'
import ImageInput from '@/components/revise/ImageInput.vue'
import UpdateMapPointForm from '@/components/revise/UpdateMapPointForm.vue'
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'

export default {
  name: 'ReviseOthersRouteView',
  components: {
    ReviseHeader,
    UpdateMap,
    ImageInput,
    UpdateMapPointForm
  },
  data() {
   return {
    responseData: this.routeInfo,
    isFreeze: false,
    tempThumbnail: '',
    isthumbail: false,
    canRendering: false,
   }
  },
  computed: {
    ...mapGetters(['jwt', 'routeInfo',])
  },
  methods: {
    ...mapActions(['fetchLoginedToken', 'fetchRouteInfo', 'changeMethodType']),
    // 모달이 열리면 map freeze하기
    freezeMap() {
      this.isFreeze = true
    },
    // 모달이 닫히면 map freeze 풀기=
    recoverFreezeMap() {
      this.isFreeze = false
      // console.log(this.isFreeze)
    },
    updateThumbnailImage(image) {
      console.log('updateThumbnailImage로 들어옴')
      this.tempThumbnail = image
    },
    thumbnailChecked() {
      this.isthumbail = true
    },
    changeIsthumbail() {
      this.isthumbail = !this.isthumbail
    },
    changeRendering() {
      this.canRendering = !this.canRendering
    }
  },
  async created () {
    const token = this.jwt
    this.$store.dispatch('fetchLoginedToken', token)
    console.log(this.jwt)
    const tmp_id = this.jwt[2]
    console.log(`userID확인 : ${this.jwt[2]}`)
    // prop받은 routeId로 변경필요
    const routeId = 2
    const access_token = this.jwt[0]
    // 수정페이지에서는 put요청을 보내야함
    this.changeMethodType('post')

    const config = {
      headers: {
        'access_token': access_token
      }
    }
    await axios.get(`http://i5a309.p.ssafy.io:8000/api/v1/route/${tmp_id}/${routeId}`, config)
      .then((res) => {
        console.log('응답받음')
        console.log(res)
        this.responseData = res.data
        this.canRendering = true
      })
      .catch((err) => {console.log(err)})
  },
}
</script>

<style>

</style>