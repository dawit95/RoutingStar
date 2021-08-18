<template>
  <div>
    <v-container v-if="routeInfo.user" class="mx-auto" background-color="#101423" max-width="400">
      <v-row class="moveDown moveRight moveUp my-1">
        <v-list-item-avatar color="grey darken-3">
          <v-img @click="onClickUser(routeInfo)" class="elevation-6 Nanum" alt="" :src="routeInfo.user.profileImg"></v-img>
        </v-list-item-avatar>
            <span @click="onClickUser(routeInfo)" class="moveDownBig Dohyeon pt-1" style="color: white">{{ routeInfo.user.name }}</span>
          <v-btn v-if="routeInfo.user.id === this.jwt[2] || routeInfo.isStored" @click="moveToRevisePage(routeInfo.id)" icon>
            <v-icon class="moveDown moveRight pt-3" color="white">mdi-pencil-outline</v-icon>
          </v-btn>
          
          <v-btn v-if="routeInfo.user.id === this.jwt[2]" @click="onClickdeleteRoute(routeInfo.id)" icon>
            <v-icon class="moveDown moveRight pt-3" color="white">mdi-trash-can-outline</v-icon>
          </v-btn>
          
      </v-row>    
      <hr>
        <RouteDetailMap />
      <hr>
      <v-row class="d-flex moveDown moveRight">
        <v-col cols="8" xs="8" class="content">
          <span v-for="(whatTag, idx) in routeInfo.whatTag" v-bind:key="idx +'i'">
            <button class="button">{{ whatTag.title }}</button>
          </span>

          <span v-for="(withTag, idx) in routeInfo.withTag" v-bind:key="idx+ 'j'">
            <button class="button" color="white">{{ withTag.title }}</button>
          </span>
        </v-col>

        <v-col cols="4" xs="4" class="d-flex">  
          <div v-if="routeInfo.isLiked">
            <v-icon color="red" @click="requestLike(routeInfo.id)" class="mr-1">mdi-heart</v-icon>
          </div>
          <div v-else>
            <v-icon color="white" @click="requestLike(routeInfo.id)" class="mr-1">mdi-heart-outline</v-icon>
          </div> 
          <div class="subheading mr-2" style="color:white">{{ routeInfo.likeCnt }}</div>

          <div v-if="routeInfo.isStored">
              <v-icon color="brown" @click="requestStore(routeInfo.id)" class="mr-1">mdi-bookmark</v-icon>
          </div>
          <div v-else>
            <v-icon color="white" @click="requestStore(routeInfo.id)" class="mr-1">mdi-bookmark-outline</v-icon>
          </div> 
          <div class="subheading" style="color:white">{{ routeInfo.storageCnt }}</div>
            </v-col>
      </v-row>
      <br/>

      <div >

        <div class="d-flex box moveUp" v-for="(place, idx) in routeInfo.places" :key="idx+'k'">
        <span v-if="place.isThumbnail===true">
          <div class="thumbnail d-flex"><img :src=place.placeImg alt=""></div>
          <img class="mid-image" src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/mid-image-black.png" alt="">
          <img class="logo-image" src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/LOGO1.png" alt="">
          <div class="routeImg d-flex"><img :src=routeInfo.routeImg alt=""></div>
        </span>
        </div>

      <br /><br /><br /><br /><br />><br /><br />


    <v-row class="d-flex justify-center underLine Dohyeon">
      {{ routeInfo.routeDescription }}
    </v-row>
      </div>    

        <div>
          <CommentBox/>
        </div>
    
    </v-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import RouteDetailMap from '@/components/routeDetail/RouteDetailMap.vue'
import CommentBox from '@/components/routeDetail/CommentBox.vue'

export default {
  name: 'RouteDetailView',
  components: {
    RouteDetailMap,
    CommentBox
  },
  data() {
    return {
      map: null,
    }
  },
  computed: {
    ...mapGetters(['routeInfo', 'jwt', 'isLiked', 'isSaved']),
  },
  methods: {
    ...mapActions(['enterUserprofile', 'fetchRouteInfo', 'clearRouteInfo', 'deleteRouteActions']),

    requestLike(id) {
      this.jwt[3] = id, 
      this.$store.dispatch('fetchLike', this.jwt)
    },
    requestStore(id) {
      this.jwt[3] = id
      this.$store.dispatch('fetchStore', this.jwt)
    },
    moveToRevisePage(routeId) {
      // feed를 작성한 userId와 현재 로그인한 userID와 같다면 => reviseroute/routeId
      if (this.routeInfo.user.id === this.jwt[2]) {
        this.$router.push({ name: 'ReviseRouteView', params: { routeId: `${routeId}`}})
        // 작성자와 로그인유저가 다르고, 로그인 유저가 저장한 feed라면 => reviseothers/routeId
      } else if (this.routeInfo.user.id !== this.jwt[2] && this.routeInfo.isStored) {
        this.$router.push({ name: 'ReviseOthersRouteView', params: { routeId: `${routeId}`}})
      }
    },
    // 닉네임, 사진 누르면 프로필로 간다
    onClickUser(feed) {
      this.enterUserprofile({
        userId: feed.user.id,
        access_token: this.jwt[0],
        jwtId: this.jwt[2]
      })
    },
    onClickdeleteRoute(routeId) {
      const payload = {
        userId: this.jwt[2],
        routeId: routeId,
        access_token: this.jwt[0]
      }
      this.deleteRouteActions(payload)
      this.$router.go(-1);
    }
  },
  created() {
    this.fetchRouteInfo({
      userId: this.jwt[2],
      routeId: this.$route.params.feedId,
      access_token: this.jwt[0]
    })
  },
  watch: {
    isLiked: function() {
      this.fetchRouteInfo({
        userId: this.jwt[2],
        routeId: this.$route.params.feedId,
        access_token: this.jwt[0]
        })
    },
    isSaved: function() {
      this.fetchRouteInfo({
        userId: this.jwt[2],
        routeId: this.$route.params.feedId,
        access_token: this.jwt[0]
        })
    },
  },
}
</script>

<style scoped>
.underLine {
  color: #B4DFE5;
  text-decoration: underline;
  text-decoration-color: white;
  text-decoration-thickness: 3px;
}
.Nanum {
  font-family: 'Nanum Gothic Coding', monospace;
}
.Dohyeon {
  font-family: 'Do Hyeon', sans-serif;
}
.content {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
}
.button {
  background-color: #B4DFE5;
  width: 50px;
  padding: auto;
  font-size: 0.4em;
  font-weight: bold;
  border-radius: 5px;
  /* font: bold; */
  }

hr {
  background-color: white;
}
.moveUp {
  margin-bottom: 12px;
}
.moveDown {
  margin-top: 12px;
}
.moveDownLittle {
  margin-top: 9px;
}
.moveDownBig {
  margin-top: 15px;
}
.moveRight {
  margin-left: 5px;
}
.moveLeft {
  margin-right: 50px;
}

.container {
  margin: 0px;
  padding: 0px;
}
img {
  width: 150px; height: 150px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
}
.box {
  position: relative;
  margin: 5px 32%;
}

.thumbnail {
  position: absolute;
  top: 0;
  left: 0;

}
.routeImg {
  position: absolute;
  top: 0;
  left: 0;
} 

.tmpBackground{
  background-color: aliceblue;
}
.mid-image {
  width: 150px; height: 150px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  /* left: 17.5%; */
  /* left: 50%; */
  justify-content: center;
  opacity: 25%;

}
.logo-image {
  width: 145px; height: 145px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 1%;
}
</style>