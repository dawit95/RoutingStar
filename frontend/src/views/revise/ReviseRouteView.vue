<template>
  <v-layout row swap>
    <UpdateMap :isFreeze="isFreeze" :resPlacesData="responseData.success.places"/>
    <UpdateMapPointForm @update-tumbnail-image="updateThumbnailImage"/>
    <PostRouteDetailModal :tempThumbnail="tempThumbnail" @freeze-map="freezeMap" @recover-freeze-map="recoverFreezeMap"/>
  </v-layout>
</template>

<script>
import UpdateMap from '@/components/revise/UpdateMap.vue'
// import MapPointFormS3 from '@/components/routes/MapPointFormS3'
import PostRouteDetailModal from '@/components/routes/PostRouteDetailModal.vue'
import UpdateMapPointForm from '@/components/revise/UpdateMapPointForm.vue'
// { lat: 37.501, lng: 127.039 },
// { lat: 37.501, lng: 127.001 },
// { lat: 37.500, lng: 127.039 },

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
            "id": 1,
            "title": "여기는 카페에요",
            "lat": 37.501,
            "lng": 127.039,
            "createdOrder": 1,
            "placeImg": "https://t1.daumcdn.net/liveboard/holapet/7dd0ffdc19294528b5de0ffb31829366.JPG",
            "isThumbnail": true
          },
          {
            "id": 2,
            "title": "여기는 존맛 밥집",
            "lat": 37.501,
            "lng": 127.001,
            "createdOrder": 2,
            "placeImg": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTexRTsmITUumVYDuDZStpL0349oRUfJ6McVkN0yl8MhYO4WmGxr23HhLJrbsWIyuZdmjU&usqp=CAU",
            "isThumbnail": false
          },
          {
            "id": 3,
            "title": "여기는 존맛 밥집",
            "lat": 37.500,
            "lng": 127.039,
            "createdOrder": 3,
            "placeImg": "https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/thumbnail/1628320246729Intro.jpg",
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
            "title": "유명 관광지는 필수"
          },
          {
            "id": 2,
            "title": "SNS 핫플레스"
          },
          {
            "id": 3,
            "title": "여유롭게 힐링"
          }
        ],
        "withTag": [
          {
            "id": 1,
            "title": "혼자"
          },
          {
            "id": 2,
            "title": "친구와"
          },
          {
            "id": 7,
            "title": "기타"
          }
        ]
      }
    },
   }
 },
  components: {
    UpdateMap,
    UpdateMapPointForm,
    PostRouteDetailModal,
  },
  methods: {
    // 모달이 열리면 map freeze하기
    freezeMap() {
      this.isFreeze = true
    },
    // 모달이 닫히면 map freeze 풀기=
    recoverFreezeMap() {
      this.isFreeze = false
      console.log(this.isFreeze)
    },
    updateThumbnailImage(image) {
      this.tempThumbnail = image
    }
  }
}
</script>

<style>

</style>