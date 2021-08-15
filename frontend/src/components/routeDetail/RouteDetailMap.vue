<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>
      <v-flex xs12>
          <!-- 맵 -->
          <div>
            <div id="map"></div>
          </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'
  
export default {
  name: 'RouteDetailMap',
  components: {  
  },
  data() {
    return {
      SearchWord: {
        word: '',
      },
      map: null,
      pointListPk: 0,
      locations: [],
    }
  },
  computed: {
    ...mapGetters(['routeInfo', 'polyLine'])
  },
  methods: {
    ...mapMutations(['SET_POLYLINE',]),
    ...mapActions(['addPlace', 'sendImagesArray', 'setXYPoints', 'refreshPlaces']),

    // 0. HTML에 Script 삽입
    // API key 보호를 위해 변수로 삽입
    addGoogleMapScript() {
      const script = document.createElement("script");

      script.onload = () => this.initMap();
      script.src =
        "https://maps.googleapis.com/maps/api/js?key=" + process.env.VUE_APP_GOOGLEMAPS_API_KEY + "&libraries=places&region=KR&language=ko&v=weekly";
      // script.async = true;
      script.defer = true;
      document.head.appendChild(script);
    },
    // 1. Map 세팅
    initMap() {
      this.map = new window.google.maps.Map(document.getElementById("map"),
      {
        mapId: "8e0a97af9386fef",
        // 경로의 중앙에 포커스가 위치하도록 설정하였음(초기는 멀캠)
        // center: { lat:this.latLstItems[(Math.abs(this.latLstItems.length/2))], lng:this.lngLstItems[(Math.abs(this.latLstItems.length/2))] },
        center: { lat: 37.501, lng: 127.039 },
        zoom: 16,
        streetViewControl: false,
        mapTypeControl: false,
        zoomControl: true,
        fullscreenControl: false,
      })
        
      const places = this.routeInfo.places
      for( const place of places ) {
        let latLng = new window.google.maps.LatLng( place.lat, place.lng)
        this.locations.push({ lat: place.lat, lng: place.lng })
        let marker = new window.google.maps.Marker({
          position: latLng,
          map: this.map,
          animation: window.google.maps.Animation.DROP
        });
        // 마커 더블클릭시 삭제a
        marker.addListener('mousedown', (e) => {
          this.openPlaceInfo(marker,e.latLng.lat(), e.latLng.lng())
        })
      }

      // 2. 폴리라인을 쓸 수 있도록 객체를 생성해서 map에 얹는다
      this.SET_POLYLINE(new window.google.maps.Polyline
        ({
          strokeColor: "#2A355D",
          strokeOpacity: 0.3,
          strokeWeight: 8,
        })
      )
      this.polyLine.setMap(this.map);
    
      // 실제 Google Map 객체를 생성하는 것은 null 일때만 
      // 루트를 그릴 때 freeze 상태라면 bound 조정하고, 아니라면 검색창 붙인다
      this.freezeBound()
      this.refreshPolyline()
    },

    openPlaceInfo(marker, lat, lng) {
      for( const place of this.routeInfo.places ) {
        if (place.lat == lat && place.lng == lng) { 
          var placeImg = place.placeImg
          var placeTitle = place.title

          const infowindow = new window.google.maps.InfoWindow({
            content:    
            `<img src="${placeImg}" alt="" height="100" width="150">` +
                      `<div class="modalcontent">"${placeTitle}"</div>`
          })
          // .theme--dark.v-card iw-subTitle 
    
          marker.addListener("click", () => {
            console.log(marker)
            infowindow.open({
              anchor: marker,
              map: this.map,
              shouldFocus: false,
            });
          })
        }
      }
    },

    // 6. 루트를 새로고침
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const places = this.routeInfo.places
      const bounds = new window.google.maps.LatLngBounds();
      path.clear();
      for( const place of places ) {
        let latLng = new window.google.maps.LatLng( place.lat, place.lng)
        path.push(latLng)
        bounds.extend(latLng)
      }
      return bounds
    },
    // 지도 바운더리 리프레시
    freezeBound() {
      const bounds = this.refreshPolyline()
      const path = this.polyLine.getPath();
      // bound 찾았으면 폴리라인 중복을 위해 path clear
      path.clear();
      this.map.fitBounds(bounds);      
    },
  },
  mounted() {
    window.google && window.google.maps
      ? this.initMap()
      : this.addGoogleMapScript();
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

/*style the box which holds the text of the information window*/  
.gm-style .gm-style-iw {
  background-color: #252525 !important;
  top: 0 !important;
  left: 0 !important;
  width: 100% !important;
  height: 100% !important;
  min-height: 120px !important;
  padding-top: 10px;
  display: block !important;
}    

/*style the paragraph tag*/
.gm-style .gm-style-iw #google-popup p{
  padding: 10px;
}


/*style the annoying little arrow at the bottom*/
.gm-style div div div div div div div div {
  background-color: #252525 !important;
  margin: 0;
  padding: 0;
  top: 0;
  color: #fff;
  font-size: 16px;
}

/*style the link*/
.gm-style div div div div div div div div a {
  color: #f1f1f1;
  font-weight: bold;
}

.modalcontent {
    background-color: #1E1E1E;
    color: purple;

}
.map.theme--dark.v-card {
    background-color: #1E1E1E;
    color: #ff0606;
}

h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

#map {
  height: 400px;
  /* The height is 400 pixels */
  width: 100%;
  /* The width is the width of the web page */
}

.pac-card {
  margin: 0 0 0 0;
  border-radius: 2px 0 0 2px;
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  outline: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  background-color: #fff;
  font-family: Roboto;
}

#pac-container {
  padding-bottom: 12px;
  margin-right: 12px;
}
#pac-input {
  background-color: #fff;
  font-family: Roboto;
  font-size: 15px;
  font-weight: 300;
  margin-left: 12px;
  padding: 0 11px 0 13px;
  text-overflow: ellipsis;
  width: 400px;
}

#pac-input:focus {
  border-color: #4d90fe;
}

.pac-controls {
  display: inline-block;
  padding: 5px 11px;
}

.pac-controls label {
  font-family: Roboto;
  font-size: 13px;
  font-weight: 300;
}

</style>
