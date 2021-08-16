<template>
  <v-container v-if="canRendering" class="pa-0">
    <ReviseOthersHeader 
    :tempThumbnail="tempThumbnail" 
    @freeze-map="freezeMap" 
    @recover-freeze-map="recoverFreezeMap"/>

    <UpdateOthersMap :isFreeze="isFreeze" :resPlacesData="responseData.success" /> 

    <UpdateOthersMapPointForm 
    :isthumbail="isthumbail"
    :listLength="responseData.success.length"
    @update-tumbnail-image="updateThumbnailImage" 
    @change-isthumbail="changeIsthumbail"/>

  </v-container>
</template>

<script>
import ReviseOthersHeader from '@/components/reviseothers/ReviseOthersHeader.vue'
import UpdateOthersMap from '@/components/reviseothers/UpdateOthersMap.vue'
import UpdateOthersMapPointForm from '@/components/reviseothers/UpdateOthersMapPointForm.vue'
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'

export default {
  name: 'Retry',
  components: {
    ReviseOthersHeader,
    UpdateOthersMap,
    UpdateOthersMapPointForm,
  },
  data() {
    return {
      responseData: 1,
      isFreeze: false,
      tempThumbnail: '',
      isthumbail: false,
      canRendering: false,
    }
  },
  computed: {
    ...mapGetters(['jwt'])
  },
  methods: {
    ...mapActions(['changeMethodType']),
    // 모달이 열리면 map freeze하기
    freezeMap() {
      this.isFreeze = true
    },
    // 모달이 닫히면 map freeze 풀기
    recoverFreezeMap() {
      this.isFreeze = false
    },
    changeIsthumbail() {
      this.isthumbail = !this.isthumbail
    },
    updateThumbnailImage(image) {
      console.log('updateThumbnailImage로 들어옴')
      this.tempThumbnail = image
    },
  },
  async created () {
    const token = this.jwt
    this.$store.dispatch('fetchLoginedToken', token)

    // const tmp_id = this.jwt[2]
    console.log(`userID확인 : ${this.jwt[2]}`)
    // prop받은 routeId로 변경필요
    const routeId = 3
    const access_token = this.jwt[0]
    // 수정페이지에서는 put요청을 보내야함
    this.changeMethodType('post')

    const config = {
      headers: {
        'access_token': access_token
      }
    }
    await axios.get(`http://i5a309.p.ssafy.io:8000/api/v1/place/${routeId}`, config)
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