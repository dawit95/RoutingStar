<template>
  <v-container fluid>
    <v-layout row swap>
      <v-flex xs12>
        <div class="pac-card" id="pac-card">
          <!-- 검색창 -->
          <div id="pac-container">
            <input v-model="SearchWord.word"
              id="pac-input"
              class="controls"
              type="text"
              placeholder="장소를 검색하세요."
            />
          </div>

          <!-- 맵 -->
          <div>
            <div id="map"></div>
          </div>
        </div>
      </v-flex>

      <v-divider></v-divider>
    </v-layout>
  </v-container>
</template>

<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'

export default {
  name: 'UpdatOtherseMap',
  components: {  
  },
  props: {
    isFreeze: {
      type: Boolean
    },
    resPlacesData: {
      type: Array
    },
  },
  data() {
    return {
      SearchWord: {
        word: '',
      },
      map: null,
      pointListPk: 0,
    }
  },
  computed: {
    ...mapGetters(['places', 'polyLine', 'imgList'])
  },
  methods: {
    ...mapMutations(['SET_POLYLINE',]),
    ...mapActions(['addPlace', 'sendImagesArray', 'setXYPoints', 'refreshPlaces']),

    // 0. HTML에 Script 삽입
    // API key 보호를 위해 변수로 삽입
    addGoogleMapScript() {
      const script = document.createElement("script");

      script.onload = () => this.initMap(false);
      script.src =
        "https://maps.googleapis.com/maps/api/js?key=" + process.env.VUE_APP_GOOGLEMAPS_API_KEY + "&libraries=places&region=KR&language=ko&v=weekly";
      script.defer = true;
      document.head.appendChild(script);
    },
    // 1. Map 세팅
    initMap( freeze ) {
      // 맵이 중복으로 만들어지는 걸 막기 위해 함수를 분리
      if ( this.map === null ) {
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
        // Map생성시 기존 data에 대한 마커 생성
        const length = this.resPlacesData.length
        // console.log(this.resPlacesData)
        for (let x = 0; x < length; x++) {
          let pk = this.pointListPk
          this.pointListPk = this.pointListPk + 1
          const marker = new window.google.maps.Marker({
            position: { lat: this.resPlacesData[x].lat, lng: this.resPlacesData[x].lng },
            map: this.map,
            icon: 'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/pin_image.png'
          })
          // if (this.resPlacesData[x].isThumbnail) {
          //   this.$emit('thumbnail-checked')
          //   this.$store.state.images.thumbnailImage = this.resPlacesData[x].placeImg
          //   this.$store.state.images.thumbnailChecked = true
          //   this.$emit('update-tumbnail-image', this.resPlacesData[x].placeImg)
          // }
          let newPlace = {
            createdOrder: pk,
            imageUpload: false,
            placeImg : '',
            lat : this.resPlacesData[x].lat,
            lng : this.resPlacesData[x].lng,
            content: this.resPlacesData[x].title,
            isThumbnail : false,
            marker: {
              location: marker,
              createdOrder: pk,
            },
          }
          this.addPlace(newPlace)
          
          this.SET_POLYLINE(new window.google.maps.Polyline
            ({
              strokeColor: "#303C6C",
              strokeOpacity: 0.3,
              strokeWeight: 8,
            })
          )
          this.polyLine.setMap(this.map);
          marker.addListener('dblclick', (e) => {
            console.log(e.latLng)
            this.removePoint(marker)
            marker.setMap(null);
          });
          marker.addListener('click', function () {
          marker.setAnimation(window.google.maps.Animation.BOUNCE);
            setTimeout((function() {
              marker.setAnimation(null)
            }).bind(marker), 1400)
          });
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
        this.map.addListener("click", this.addPoint);
      }
      // 실제 Google Map 객체를 생성하는 것은 null 일때만 
      // 루트를 그릴 때 freeze 상태라면 bound 조정하고, 아니라면 검색창 붙인다
      if(freeze === true) {
        this.freezeBound()
      } else {
        this.attachSearch()
        this.refreshPolyline()
        this.freezeBound()
      }
    },

    // 3. 검색창 붙이기
    attachSearch() {
      let input = document.getElementById("pac-input");
      let searchBox = new window.google.maps.places.SearchBox(input)
      this.map.controls[window.google.maps.ControlPosition.TOP_LEFT].clear()
      this.map.controls[window.google.maps.ControlPosition.TOP_LEFT].push(input)
      // 검색어에 따라 바운더리를 바꾼다
      this.map.addListener("bounds_changed", () => {
        searchBox.setBounds(this.map.getBounds());
      });
      searchBox.addListener("places_changed", () => {
        const searchPlaces = searchBox.getPlaces();
        if (searchPlaces.length == 0) {
          return;
        }
        const bounds = new window.google.maps.LatLngBounds();
        searchPlaces.forEach((place) => {
          if (!place.geometry || !place.geometry.location) {
            console.log("Returned place contains no geometry");
            return;
          }
          if (place.geometry.viewport) {
            bounds.union(place.geometry.viewport);
          } else {
            bounds.extend(place.geometry.location);
          }
        });
        this.map.fitBounds(bounds);
      });
    },
    
    // 4. place를 생성(마커)
    makePlace(latLng) {
      let pk = this.pointListPk
      this.pointListPk = this.pointListPk + 1

      let marker = new window.google.maps.Marker({
        position: latLng,
        map: this.map,
        animation: window.google.maps.Animation.DROP,
        icon: 'https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/pin_image.png'
      });
      // 마커 더블클릭시 삭제
      marker.addListener('dblclick', () => {
        // console.log(e.latLng)
        this.removePoint(marker)
      });
      // 마커 클릭시 바운스효과
      marker.addListener('click', function () {
        marker.setAnimation(window.google.maps.Animation.BOUNCE);
        setTimeout((function() {
          marker.setAnimation(null)
        }).bind(marker), 1400)
      });

      return {
        createdOrder: pk,
        imageUpload: false,
        placeImg : '',
        lat : latLng.lat(),
        lng : latLng.lng(),
        content: null,
        isThumbnail : false,
        marker: {
          location: marker,
          createdOrder: pk,
        },
      }
    },
     
    addPoint(event) {
      let newPlace = this.makePlace(event.latLng)
      // Store actions로 연동 & 루트 새로고침
      this.addPlace(newPlace)
      this.refreshPolyline();
    },

    // 5. 마커 삭제(place를 리스트에서 제거한 뒤 다시 맵 refresh)
    removePoint(marker) {
      const latLng = marker.getPosition();
      const lat = latLng.lat();
      const lng = latLng.lng();
      const places = this.places
      const idx = places.findIndex( (e) => e.lat == lat && e.lng == lng );
      if ( idx != -1 ) {
        marker.setMap(null);
        places.splice(idx,1);
        this.refreshPlaces(places)
        this.refreshPolyline();
      }
    },

    // 6. 루트를 새로고침
    refreshPolyline() {
      // console.log('refreshPolyline')
      // console.log(this.polyLine)
      // console.log(this.polyLine.getPath())
      const path = this.polyLine.getPath();
      const places = this.places
      const bounds = new window.google.maps.LatLngBounds();

      path.clear();
      for( const place of places ) {
        let latLng = new window.google.maps.LatLng( place.lat, place.lng)
        path.push(latLng)
        bounds.extend(latLng)
      }
      return bounds
    },

    // 7. 루트 이미지 생성 위해 맵 멈추고 바운드 재정렬, polyline에서 xy값 좌표 떼오기
    freezeBound() {
      const bounds = this.refreshPolyline()
      const path = this.polyLine.getPath();
      // bound 찾았으면 폴리라인 중복을 위해 path clear
      path.clear();
      this.map.fitBounds(bounds);      

      var overlay = new window.google.maps.OverlayView() 
      overlay.places = this.places
      overlay.points = []

      overlay.draw = function() {}
      overlay.onAdd = function() {
        let projection = this.getProjection()
        let region = projection.getVisibleRegion()
        let ne = projection.fromLatLngToDivPixel(region.latLngBounds.getNorthEast())
        let sw = projection.fromLatLngToDivPixel(region.latLngBounds.getSouthWest())

        let left = sw.x
        let top = ne.y

        // freeze된 바운드에서 xy좌표값을 가져온다
        for ( const place of this.places ) {
            let latLng = new window.google.maps.LatLng( place.lat, place.lng )
            var pixel = projection.fromLatLngToDivPixel(latLng)

            this.points.push( { x:pixel.x - left, y:pixel.y - top} )
        }
      }      
      overlay.setMap(this.map)
      // console.log(overlay.points)
      this.setXYPoints(overlay.points)

      // 좌표 확인했으면 없엔다
      for ( var i = 0; i < overlay.points.length; i++ ) {
        overlay.points[i].setMap(null)
      }
      overlay.points.length = 0;
    }
  },
  watch: {
    isFreeze: function() {
      if (this.isFreeze) {
        this.initMap(true)
      } else {
        this.initMap(false)
      }
    }
  },
  mounted() {
    window.google && window.google.maps
      ? this.initMap(false)
      : this.addGoogleMapScript();
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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

/* #pac-container {
  padding-bottom: 12px;
  margin-right: 12px;
} */
#pac-input {
  background-color: #fff;
  opacity: 0.8;
  font-family: Roboto;
  font-size: 15px;
  font-weight: 300;
  margin-left: 2%;
  margin-top: 10px;
  padding: 0 11px 0 13px;
  text-overflow: ellipsis;
  width: 95%;
  height: 45px;
  border: 3px solid #303C6C;
  border-radius: 12px;
}

/* #pac-input:focus {
  border-color: none transparent;
} */

.pac-controls {
  display: inline-block;
  padding: 5px 11px;
  color: teal;
}
.pac-controls label {
  font-family: Roboto;
  font-weight: 50;
  font-size: 15px;
}

</style>
