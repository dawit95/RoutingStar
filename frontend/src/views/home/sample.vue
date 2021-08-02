<template>
  <div class="home wrap2">
    <Navi id="navi"/>
    <div class="online_bg">
      <div class="home_logo">
        <img class="home_logo_img" src="../../assets/main/logo_img.png" alt="">
        <span class="home_logo_text">ARTMATE</span>
      </div>
      <!-- Online -->
      <div class="online_box">
        <!-- <div class="online_tlt">온라인 전시회</div> -->
        <carousel
          :items=1
          :autoplay="true"
          :autoplayTimeout=3000
          :autoplayHoverPause="true"
          :nav=false
          :loop=true
          :dots=false
          :margin=10
        >                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
          <div 
            class="online_exhibition"
            v-for="(item,idx) in onlineList"
            :key="idx"
          >
            <img
              @click="onClickOnline"
              class="online_exhibition_poster"
              :src="item.exImg"
              :data-id="item.id"
              alt=""
            >
            <div class="online_exhibition_info">
              <div>
                <p class="online_exhibition_tlt">{{item.name}}</p>
                <!-- <p class="online_exhibition_place">{{item.place}}</p> -->
              </div>
            </div>
          </div>
        </carousel>
      </div>
    </div>
    

    <!-- Recommend -->
    
    <div class="recommend_box">
      <div class="recommend_tlt">Recommend</div>
      <div class="recommend_keyword_list">
        <div @click="onClickTag" class="recommend_keyword" v-for="(tag,idx) in user.myTag" :key="idx" :id="tag">{{tag}}</div>
      </div>
      <div class="slide_box">
        <carousel-3d
          :autoplay=true
          :autoplayHoverPause=true
          :style="carouselStyle"
          :space="carouselSpace"
          :display="3"
          :perspective="0"
          :border="0"
          :width="carouselWidth"
          :height="carouselHeight"
          :count="filteredRecList.length"
          ref="mycarousel"
          @after-slide-change="onAfterSlideChange"
          @before-slide-change="onBeforeSlideChange"
        >
          <slide style="background-color: transparent;cursor: pointer;" v-for="(item, i) in filteredRecList" :data-id="item.id" :key="i" :index="i">
            <img @click="onClickRecEx" class="recommend_exhibition_poster a" :src="item.exImg" alt="" >
            <div class="recommend_exhibition_info b">
              <p class="recommend_exhibition_tlt">{{item.name}}</p>
              <!-- <p class="recommend_exhibition_place">{{item.location}}</p>
              <p class="recommend_exhibition_duration">{{item.startDate}} ~ {{item.endDate}}</p> -->
            </div>
          </slide>
        </carousel-3d>
      </div>
    </div>
    <!-- Feed -->
    <div class="feed_box">
      <div class="feed_header">
        <div class="feed_tlt">Popular Feed</div>
        <button @click="onClickMoreFeed" class="feed_more_btn">
          <span>더보기 </span>
          <font-awesome-icon class="online_slide_right_btn" icon="chevron-right"/>
        </button>
      </div>
      <div class="feed_message">
        <span>인기있는 피드를 만나보세요 📸</span>
      </div>
      <div class="feed_list">
        <img
          @click="onClickFeed"
          class="feed_item"
          v-for="(item,idx) in popularList"
          :data-feedno="item.id"
          :key="idx"
          :src="item.feedImg"
          alt=""
        >
      </div>
    </div>

    <!-- Around -->
    <div class="home_around_box">
      <div class="home_around_header">
        <div class="home_around_tlt">Around</div>
        <button @click="onClickMoreAround" class="home_around_more_btn">
          <span>더보기</span>
          <font-awesome-icon class="online_slide_right_btn" icon="chevron-right"/>
        </button>
      </div>
      <div class="home_around_message">
        <span>{{locationInfo.location}} 주변 리스트입니다 🎨</span>
      </div>
      <div class="home_around_list">
        <div
          @click="onClickAround"
          class="home_around_exhibition"
          v-for="(item,idx) in aroundEx"
          :key="idx"
          :data-id="item.id"
        >
          <img :src="item.exImg" alt="" class="home_around_exhibition_img" :data-id="item.id">
          <div class="home_around_exhibition_info" :data-id="item.id">
            <div class="home_around_exhibition_tlt" :data-id="item.id">
              <font-awesome-icon class="home_around_message_icon" :icon="['fab', 'envira']" :data-id="item.id"/> {{item.name}}
            </div>
            <div class="home_around_exhibition_place">
              <font-awesome-icon class="home_around_message_icon" icon="map-marker-alt" :data-id="item.id"/>
              {{item.location}}
            </div>
            <div class="home_around_exhibition_duration" :data-id="item.id">{{$moment(item.startDate).format('YYYY-MM-DD')}} ~ {{$moment(item.endDate).format('YYYY-MM-DD')}}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <footer class="footer_box">
      <div class="footer_tlt">Artmate</div>
      <div class="footer_section">
        <p>상호: 아트메이트 / 대표 : 유진이</p>
        <p>주소: 대전 유성구 동서대로 98-39</p>
      </div>
      <div class="footer_section" >
        <p id="last">Copyright@2021 아트메이트 All Rights Reserved</p>
      </div>
    </footer>
  </div>
</template>

<script defer>
import Navi from '@/components/Common/Navi.vue';
import carousel from 'vue-owl-carousel';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import {getFeedList} from '@/api/home.js';
import {getExhibitRecommend,getListForMap,getOnlineExhibit} from '@/api/exhibit.js';
export default {
  name: 'Home',
  components: {
    carousel,
    Navi,
    Carousel3d,
    Slide
  },
  data() {
    return{
      locationInfo:{},  
      recommend_tag:"",
      user:null,
      onlineCarouselClass:"",
      carouselWidth:0,
      carouselHeight:0,
      carouselSpace:0,
      carouselStyle:"",
      onlineList:[{},{},{},{},{}],
      recommendList:[],
      filteredRecList:[],
      popularList:[],
      aroundList:[],
      filteredAroundList:[],
    }
  },
  created(){
    let place = "";
    const vue = this;
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position)=> {
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        var geocoder = new kakao.maps.services.Geocoder();
        geocoder.coord2Address(lon,lat, (res)=>{
          place=res[0].address.region_1depth_name + ' ' + res[0].address.region_2depth_name;
          const info={
            location:place,
            lat: lat,
            lon: lon,
          }
          vue.$store.commit("setCurrentLocation",info);
          vue.locationInfo=info;
          getListForMap(
            (res)=>{
              vue.aroundList=res.data;
              const tmp=vue.locationInfo.location.split(" ");
              let ps = new kakao.maps.services.Places();
              for(let i=0; i<vue.aroundList.length; i++){
                if(vue.aroundList[i].location===undefined || vue.aroundList[i].location === "온라인") continue;
                ps.keywordSearch(vue.aroundList[i].location,(data)=>{
                  for(let j=0; j<data.length; j++){
                    if(data[j].address_name.includes(tmp[0]) && data[j].category_group_name==="문화시설"){
                      vue.filteredAroundList.push(vue.aroundList[i]);
                      break;
                    }
                  }
                })
              }
            },
            (err)=>{
              console.error(err);
            }
          );
        });
      });
    }
    else{
      const info={
        location:place,
        lat: 37.56682420267543,
        lon: 126.978652258823,
      }
      this.$store.commit("setCurrentLocation",info);
      this.location=this.$store.getters.getCurrentPosition;
      getListForMap(
        (res)=>{
          // console.log(this.location);
          vue.aroundList=res.data;
          const tmp=vue.locationInfo.location.split(" ");
          let ps = new kakao.maps.services.Places();
          for(let i=0; i<vue.aroundList.length; i++){
            if(vue.aroundList[i].location===undefined || vue.aroundList[i].location === "온라인") continue;
            ps.keywordSearch(vue.aroundList[i].location,(data)=>{
              for(let j=0; j<data.length; j++){
                if(data[j].address_name.includes(tmp[0]) && data[j].category_group_name==="문화시설"){
                  vue.filteredAroundList.push(vue.aroundList[i]);
                  break;
                }
              }
            })
          }
        },
        (err)=>{
          console.error(err);
        }
      );
    }
    this.user=this.$store.getters.getUser;
    this.recommend_tag=this.user.myTag[0];
    getExhibitRecommend(
      this.user.userId,
      (res)=>{
        this.recommendList=res.data;
        this.filteredRecList=this.recommendList.filter((item) => item.tagList.includes(this.recommend_tag));
        document.querySelector(`#${this.recommend_tag}`).classList.add('active');
        document.addEventListener('scroll',this.handleNavi);
        if(window.innerWidth<=1024){
          this.carouselWidth=120;
          this.carouselHeight=270;
          this.carouselSpace=170;
          this.carouselStyle='margin: 20px 0 0 90px; overflow-x:hidden';
        }
        else{
          this.carouselWidth=200;
          this.carouselHeight=400;
          this.carouselSpace=320;
          this.carouselStyle='margin: 40px 0 0 130px; overflow-x:hidden';
        }
        window.addEventListener('resize',()=>{
          if (window.innerWidth<=1024){
              this.carouselWidth=120;
              this.carouselHeight=270;
              this.carouselSpace=170;
              this.carouselStyle='margin: 20px 0 0 90px; overflow-x:hidden';
          }
          else{
              this.carouselWidth=200;
              this.carouselHeight=400;
              this.carouselSpace=320;
              this.carouselStyle='margin: 40px 0 0 130px; overflow-x:hidden';
          }
        });
      },
      (err)=>{
        console.error(err);
      }
    );
    getFeedList(
      (res)=>{
        console.log(res);
        this.popularList=res.data.sort((a,b)=>{b.likeCnt-a.likeCnt}).slice(0,9);
      },
      (err)=>{
        console.error(err);
      }
    )
    getOnlineExhibit(
      this.user.userId,
      (res)=>{
        for(const ex of res.data){
          if(ex.id<360 || ex.id>364) continue;
          this.onlineList.push(ex);
          this.onlineList.shift();
        }
        console.log(this.onlineList);
      },
      (err)=>{
        console.error(err);
      }
    )
  },
  // mounted(){
  //     console.log(this.$refs.mycarousel.$children[0]);
  //     console.log(this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[0].elm.classList);
  //     console.log(this.$refs.mycarousel.currentIndex);
  //     this.$refs.mycarousel.currentIndex=0;
  //     this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[0].elm.classList.remove('a');
  //     this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[1].elm.classList.remove('b');
  // },
  destroyed(){
    document.removeEventListener('scroll',this.handleNavi);
  },
  computed:{
    aroundEx(){
      if(this.filteredAroundList.length===0) return [];
      else return this.filteredAroundList.slice(0,4);
    }
  },
  methods:{
    handleNavi() {
      const navbar = document.querySelector('#navi');
      const navbarHeight = navbar.getBoundingClientRect().height;
      if(window.scrollY > navbarHeight){
        navbar.style.background="white";
      }
      else{
        navbar.style.background="transparent";
      }
    },
    onClickOnline(e){
      this.$router.replace({
        name:"ExhibitionDetail",
        params:{
          id:e.target.dataset.id,
        }
      })
    },
    onClickTag(e){
      console.log(this.recommend_tag);
      document.querySelector(`#${this.recommend_tag}`).classList.remove('active');
      this.recommend_tag = e.target.id;
      this.filteredRecList=this.recommendList.filter((item) => item.tagList.includes(this.recommend_tag));
      console.log(this.filteredRecList);
      console.log(this.recommend_tag);
      console.log(this.$refs.mycarousel.currentIndex);
      console.log(this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[0].elm.classList);
      this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[0].elm.classList.add('a');
      this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[1].elm.classList.add('b');
      this.$refs.mycarousel.currentIndex=0;
      this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[0].elm.classList.remove('a');
      this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[1].elm.classList.remove('b');
      console.log(this.$refs.mycarousel.$children[this.$refs.mycarousel.currentIndex].$slots.default[0].elm.classList);
      console.log(this.$refs.mycarousel.currentIndex);
      document.querySelector(`#${this.recommend_tag}`).classList.add('active');
    },
    onClickRecEx(e){
      const slide = e.path[1];
      const ex_no = slide.dataset.id;
      if(slide.className.includes('current')){
        this.$router.replace({
          name:"ExhibitionDetail",
          params:{
            id:ex_no,
          }
        })
      }
    },
    onClickFeed(e){
      const feedno = e.target.dataset.feedno;
      this.$router.replace({
        name: "UserFeedDetail",
        params: {feedno: feedno}
      });
    },
    onClickAround(e){
      this.$router.replace({
        name:"ExhibitionDetail",
        params:{
          id:e.target.dataset.id,
        }
      })
    },
    onClickMoreFeed(){
      this.$router.replace({
        name: "FeedAll"
      })
    },
    onClickMoreAround(){
      this.$router.replace({
        name: "Around"
      })
    },
    onAfterSlideChange(idx){
      const before = idx-1<0 ? this.filteredRecList.length-1 : idx-1;
      this.$refs.mycarousel.$children[before].$slots.default[0].elm.classList.add('a');
      this.$refs.mycarousel.$children[before].$slots.default[1].elm.classList.add('b');
      this.$refs.mycarousel.$children[idx].$slots.default[0].elm.classList.remove('a');
      this.$refs.mycarousel.$children[idx].$slots.default[1].elm.classList.remove('b');
    },
    onBeforeSlideChange(idx){
      const next = idx+1>=this.filteredRecList.length ? 0 : idx+1;
      this.$refs.mycarousel.$children[next].$slots.default[0].elm.classList.add('a');
      this.$refs.mycarousel.$children[next].$slots.default[1].elm.classList.add('b');
      this.$refs.mycarousel.$children[idx].$slots.default[0].elm.classList.remove('a');
      this.$refs.mycarousel.$children[idx].$slots.default[1].elm.classList.remove('b');
    }
  }
}
</script>
<style scoped src="../../components/css/Home/home.css">
</style>