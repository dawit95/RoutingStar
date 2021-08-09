<template>
  <v-layout row swap>
    <UpdateMap :isFreeze="isFreeze" :resPlacesData="responseData.success.places" 
    @thumbnail-checked="thumbnailChecked" @update-tumbnail-image="updateThumbnailImage"/> 

    <UpdateMapPointForm @update-tumbnail-image="updateThumbnailImage" :isthumbail="isthumbail" 
    :listLength="responseData.success.places.length"
    @change-isthumbail="changeIsthumbail"/>

    <UpdatePostRouteDetailModal :tempThumbnail="tempThumbnail" :routeDescription="responseData.success.routeDescription"
    :whatTag="responseData.success.whatTag" :withTag="responseData.success.withTag"
    @freeze-map="freezeMap" @recover-freeze-map="recoverFreezeMap"/>
  </v-layout>
</template>

<script>
import UpdateMap from '@/components/revise/UpdateMap.vue'
import UpdateMapPointForm from '@/components/revise/UpdateMapPointForm.vue'
import UpdatePostRouteDetailModal from '@/components/revise/UpdatePostRouteDetailModal.vue'
import axios from 'axios'
import { mapGetters } from 'vuex'

export default {
  name: 'ReviseRouteView',
  data() {
   return {
     isFreeze: false,
     tempThumbnail: '',
     responseData: {
      "success": {
        "id": 1,
        "routeImg": "루트 사진이 들어가있음",
        "routeDescription": "우리동네 최고 코스에요",
        "modifiedAt": "2021-08-06T14:07:18.742",
        "places": [
          {
            "id": 0,
            "title": "여기는 카페에요",
            "lat": 37.501,
            "lng": 127.039,
            "createdOrder": 1,
            "placeImg": "https://t1.daumcdn.net/liveboard/holapet/7dd0ffdc19294528b5de0ffb31829366.JPG",
            "isThumbnail": true
          },
          {
            "id": 1,
            "title": "여기는 존맛 밥집",
            "lat": 37.501,
            "lng": 127.001,
            "createdOrder": 1,
            "placeImg": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTexRTsmITUumVYDuDZStpL0349oRUfJ6McVkN0yl8MhYO4WmGxr23HhLJrbsWIyuZdmjU&usqp=CAU",
            "isThumbnail": false
          },
          {
            "id": 2,
            "title": "여기는 존맛 밥집",
            "lat": 37.486,
            "lng": 127.039,
            "createdOrder": 2,
            "placeImg": "https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/routingstar-photo-album/%EA%B7%B8%EB%A6%BC1.jpg",
            "isThumbnail": false
          },

        ],
        "user": {
          "id": 1,
          "email": "user1@naver.com",
          "name": "사용자1",
          "profileImg": null
        },
        "whatTag": [
          {
            "id": 1,
            "title": "산책하기"
          },
          {
            "id": 2,
            "title": "운동하기"
          },
          {
            "id": 3,
            "title": "여행하기"
          }
        ],
        "withTag": [
          {
            "id": 1,
            "title": "친구"
          },
          {
            "id": 2,
            "title": "가족"
          },
          {
            "id": 6,
            "title": "낯선사람"
          }
        ]
      }
    },
    isthumbail: false,
   }
 },
  components: {
    UpdateMap,
    UpdateMapPointForm,
    UpdatePostRouteDetailModal,
  },
  computed: {
    ...mapGetters(['jwt'])
  },
  methods: {
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
      this.tempThumbnail = image
    },
    thumbnailChecked() {
      this.isthumbail = true
    },
    changeIsthumbail() {
      this.isthumbail = !this.isthumbail
    }
  },
  created () {
    
  // 로그인 되 있는 사용자인지 / 처음으로 로그인 된 사용자인지 / 로그인 안된 사용자인지
  if (this.jwt[0]) {
    console.log('token이 이미 저장이 되어있음')
    this.$store.dispatch('fetchLoginedToken', this.jwt)
    this.$store.dispatch('fetchLoginedFeeds', this.jwt)
  }
    // main page 들어오자마자 피드 정보들 받아오기
    // 참조: https://jasonwatmore.com/post/2020/07/23/vue-axios-http-get-request-examples
    // 현재 내가 로그인되있어서 user pk가 가지고 있다면
    // likes, routes_storage, routes에 요청을 보내야 필요한 정보를 모두 얻을 수 있음
    // store/home.js 생성 후 state 에 정보 저장

  },
}
</script>

<style>

</style>