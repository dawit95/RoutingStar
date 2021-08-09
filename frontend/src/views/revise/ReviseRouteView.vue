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
// import axios from 'axios'
import { mapGetters, mapActions } from 'vuex'

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
    ...mapActions(['fetchLoginedToken', 'fetchRouteInfo']),
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
    const token = this.jwt
    this.fetchLoginedToken(token)
  },
  mounted() {
    const tmp_id = 1
    const routeId = 1
    const access_token = this.jwt[0]
    // console.log('여기 마운티드')
    // console.log(this.jwt[0])
    this.fetchRouteInfo({
      userId: tmp_id,
      routeId: routeId,
      access_token: access_token
    })
  },
}
</script>

<style>

</style>