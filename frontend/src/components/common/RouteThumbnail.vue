<template>
  <div>
    <!-- 루트랑 썸네일 합치는 컴포넌트 입니다 -->
    <div class="container" @click="onClick(route)">
      <div class="box">
        <div class="thumbnail"><img src="@/assets/temp_thumbnail.jpg" alt=""></div>
        <!-- <div class="thumbnail"><img :src="routeThumbnail" alt="thumnbnail"></div> -->
        <div class="routeImg"><img src="@/assets/tmp_route_img.png" alt=""></div>
        <!-- <div class="routeImg"><img :src="routeImg" alt="routeImage"></div> -->
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
      return this.route.find( e => e.isThumbnail === true ).placeImg
    },
    routeImg() {
      return this.route.routeImg
    }
  }
}
</script>

<style>
.container {
  margin: 0px;
  padding: 0px;
}
img {
  width: 60px; height: 60px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
}
.box {
  position: relative;
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