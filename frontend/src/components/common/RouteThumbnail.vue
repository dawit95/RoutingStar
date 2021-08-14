<template>
  <div class="thumbnailContainer" >
    <!-- 루트랑 썸네일 합치는 컴포넌트 입니다 -->
    <div @click="$router.push({name: 'RouteDetailView', params: { feedId: `${route.id}` }})"
>
      <div class="box">
        
        <div v-if="route" class="thumbnail"><img class="profileThumbnailImg" :src="this.routeThumbnail" alt="thumnbnail"></div>
        <div v-else class="thumbnail"><img class="profileThumbnailImg" src="@/assets/temp_thumbnail.jpg" alt=""></div>
        <div v-if="route" class="routeImg"><img class="profileThumbnailImg" :src="this.routeImg" alt="routeImage"></div>
        <div v-else class="routeImg"><img class="profileThumbnailImg" src="@/assets/tmp_route_img.png" alt=""></div>
      
       <!--
       <div class="thumbnail"><img :src="this.placeImage" alt="thumnbnail"></div>
       <div class="routeImg"><img :src="this.routeImage" alt="routeImage"></div>
       -->
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
  /*
  data() {
    return {
      placeImage: '',
      routeImage: '',
    }
  },*/
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
  /*
  mounted() {
    // this.$nextTick( function() {
    //   this.$refs.thumbnail.src = 
    //   console.log(this.route)
    // })
    console.log('여기 route 마운틷')
    console.log(this.route)
    
    if( this.route !== undefined ) {
      this.placeImage = 'ROUTE NONE'
    } else if ( this.route.places.find( e => e.isThumbnail === true ) !== undefined ) {
      this.placeImage = this.route.places.find( e => e.isThumbnail === true ).placeImg
    } else {
      this.placeImage = 'NONE'
    }
    this.routeImage = this.route.routeImg
  },*/
}
</script>

<style scoped>
.thumbnailContainer {
  margin: 0px;
  padding: 0px;
  width: 100px;
}
.profileThumbnailImg {
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