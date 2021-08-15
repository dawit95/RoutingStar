<template>
  <div class="thumbnailContainer" >
    <!-- 루트랑 썸네일 합치는 컴포넌트 입니다 -->
    <div @click="$router.push({name: 'RouteDetailView', params: { feedId: `${route.id}` }})">
      <div class="box">     
        <div v-if="route" class="thumbnail"><img class="profileThumbnailImg" :src="this.routeThumbnail" alt="thumnbnail"></div>
        <div v-else class="thumbnail"><img class="profileThumbnailImg" src="@/assets/temp_thumbnail.jpg" alt=""></div>
        <div v-if="route" class="routeImg"><img class="profileThumbnailImg" :src="this.routeImg" alt="routeImage"></div>
        <div v-else class="routeImg"><img class="profileThumbnailImg" src="@/assets/tmp_route_img.png" alt=""></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RouteThumbnail',
  props: {
    // 어떤 루트인지는 상위 컴포넌트에서 지정된다
    route: Object,
  },
  computed: {
    // 썸네일 이미지 찾아내기
    routeThumbnail () {
      let place = this.route.places.find( e => e.isThumbnail === true )
      if (place !== undefined) {
        return place.placeImg
      }
      return "@/assets/temp_thumbnail.jpg"
    },
    routeImg() {
      return this.route.routeImg
    }
  },
}
</script>

<style>
.thumbnailContainer {
  margin: 0px;
  padding: 0px;
  width: 50px;
}
.profileThumbnailImg {
  width: 70px; height: 70px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
}
.box {
  position: relative;
  width: 10px;
}
.thumbnail {
  top: 0;
  left: 0;
}
.routeImg {
  position: absolute;
  top: 0;
  left: 0;
  transform: translate( 10%, 10% );
}
</style>