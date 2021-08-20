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
        // mapId: "8e0a97af9386fef",
        // 경로의 중앙에 포커스가 위치하도록 설정하였음(초기는 멀캠)
        // center: { lat:this.latLstItems[(Math.abs(this.latLstItems.length/2))], lng:this.lngLstItems[(Math.abs(this.latLstItems.length/2))] },
        center: { lat: 37.501, lng: 127.039 },
        zoom: 16,
        streetViewControl: false,
        mapTypeControl: false,
        zoomControl: true,
        fullscreenControl: false,
        styles: [
            { elementType: "geometry", stylers: [{ color: "#242f3e" }] },
            { elementType: "labels.text.stroke", stylers: [{ color: "#242f3e" }] },
            { elementType: "labels.text.fill", stylers: [{ color: "#746855" }] },
            {
              featureType: "administrative.locality",
              elementType: "labels.text.fill",
              stylers: [{ color: "#d59563" }],
            },
            {
              featureType: "poi",
              elementType: "labels.text.fill",
              stylers: [{ color: "#d59563" }],
            },
            {
              featureType: "poi.park",
              elementType: "geometry",
              stylers: [{ color: "#263c3f" }],
            },
            {
              featureType: "poi.park",
              elementType: "labels.text.fill",
              stylers: [{ color: "#6b9a76" }],
            },
            {
              featureType: "road",
              elementType: "geometry",
              stylers: [{ color: "#38414e" }],
            },
            {
              featureType: "road",
              elementType: "geometry.stroke",
              stylers: [{ color: "#212a37" }],
            },
            {
              featureType: "road",
              elementType: "labels.text.fill",
              stylers: [{ color: "#9ca5b3" }],
            },
            {
              featureType: "road.highway",
              elementType: "geometry",
              stylers: [{ color: "#746855" }],
            },
            {
              featureType: "road.highway",
              elementType: "geometry.stroke",
              stylers: [{ color: "#1f2835" }],
            },
            {
              featureType: "road.highway",
              elementType: "labels.text.fill",
              stylers: [{ color: "#f3d19c" }],
            },
            {
              featureType: "transit",
              elementType: "geometry",
              stylers: [{ color: "#2f3948" }],
            },
            {
              featureType: "transit.station",
              elementType: "labels.text.fill",
              stylers: [{ color: "#d59563" }],
            },
            {
              featureType: "water",
              elementType: "geometry",
              stylers: [{ color: "#17263c" }],
            },
            {
              featureType: "water",
              elementType: "labels.text.fill",
              stylers: [{ color: "#515c6d" }],
            },
            {
              featureType: "water",
              elementType: "labels.text.stroke",
              stylers: [{ color: "#17263c" }],
            },
          ],
      })
        
      const places = this.routeInfo.places
      for( const place of places ) {
        let latLng = new window.google.maps.LatLng( place.lat, place.lng)
        this.locations.push({ lat: place.lat, lng: place.lng })
        let marker = new window.google.maps.Marker({
          position: latLng,
          map: this.map,
          animation: window.google.maps.Animation.DROP,
          icon: 'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/pin_image.png'
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

          // const content = '<div class="iw-subTitle">abcdfef</div>'
          //                  <img src="${placeImg}" alt="" height="100" width="150"></img> +
          //                 `<div class="modalcontent">"${placeTitle}"</div>`
          //                 '</div>'

              
          const infowindow = new window.google.maps.InfoWindow({
            content: 
            // '<div class="iw-content">abcdef</div>'
            // '<div class="gm-style gm-style-iw">abcdfef</div>'
              // '<div class="iw-content">' + 
              `<img src="${placeImg}" alt="" height="100" width="150">` +
              `<div class="d-flex justify-center">"${placeTitle}"</div>`
              // '</div>'
          })

        
          // marker.addListener("click", () => {
          //         if (infowindow) {
          // console.log('infowindow', infowindow)
          // console.log('marker', marker)
          // console.log('닫아볼까')
          // // this.infoWindow.addListener('closeclick', ()=>{
          // infowindow.close();
          //         }
          //   infowindow.open({
          //     anchor: marker,
          //     map: this.map,
          //     shouldFocus: false,
          //   });
          // })


        window.google.maps.event.addListener(marker, 'click', function() {
        if (infowindow) {
          console.log('으히히히', infowindow)
          infowindow.close();
        }
        infowindow.open({
              anchor: marker,
              map: this.map,
              shouldFocus: false,
            });
        }
        )

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

<style scoped>
#map .iw-content { 
  font-size: 30px; 
  line-height: 18px; 
  font-weight: 400; 
  margin-right: 1px; 
  padding: 15px 5px 20px 15px; 
  max-height: 140px; 
  overflow-y: auto; 
  overflow-x: hidden; 
}
/* .iw-subTitle { 
  font-size: 150px; 
  font-weight: 700; 
  padding: 5px 0; } */
.gm-style .gm-style-iw {
  font-weight: 300;
  font-size: 30px;
}

.gm-style {
    font-weight: 300;
    font-size: 50px;
    overflow: hidden;
}
.gm-style-iw {
    font-weight: 300;
    font-size: 50px;
    overflow: hidden;
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
  height: 250px;
  width: 100%;
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
