<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>

      <v-flex xs12>
        <div class="pac-card" id="pac-card">
          <!-- 검색창 -->
          <div id="pac-container">
            <input v-model="SearchWord.word"
              id="pac-input"
              class="controls"
              type="text"
              placeholder="Search Box"
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
  name: 'Map',
  components: {  
  },
  props: {
    isFreeze: {
      type: Boolean
    }
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
    ...mapGetters(['places', 'polyLine'])
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
      // script.async = true;
      script.defer = true;
      document.head.appendChild(script);
    },
    // 1. Map 세팅
    initMap( freeze ) {
      // 중심은 우선 첫번째 요소로 선택
      if (this.places.length) {
        this.map = new window.google.maps.Map(document.getElementById("map"),
        {
          mapId: "8e0a97af9386fef",
          // 경로의 중앙에 포커스가 위치하도록 설정하였음
          // center: { lat:this.latLstItems[(Math.abs(this.latLstItems.length/2))], lng:this.lngLstItems[(Math.abs(this.latLstItems.length/2))] },
          center: { lat: 37.501, lng: 127.039 },
          zoom: 16,
          streetViewControl: false,
          mapTypeControl: false,
          zoomControl: true,
          fullscreenControl: false,
        })
        // 좌표
        for (const place of this.places) {
          let lat = place.lat
          let lng = place.lng
          const tmp = {lat: lat, lng: lng}
          new window.google.maps.Marker({
            position: tmp,
            map: this.map,
          });        
        }
        // 선(rotue) 
        this.polyLine.setMap(this.map);

      } else {
        this.map = new window.google.maps.Map(document.getElementById("map"), 
        {
          mapId: "8e0a97af9386fef",
          center: { lat:37.501, lng: 127.039 },
          zoom: 16,
          streetViewControl: false,
          mapTypeControl: false,
          zoomControl: true,
          fullscreenControl: false,
          // mapTypeId: "roadmap",
        });
      }

      if(freeze === true) {
        this.freezeBound()
      } else {
        this.attachSearch()
      }

      // 5. 폴리라인(루트 라인)을 만든다
      this.SET_POLYLINE(new window.google.maps.Polyline
        ({
          strokeColor: "#E64398",
          strokeOpacity: 0.3,
          strokeWeight: 8,
        })
      )
      this.polyLine.setMap(this.map);
      this.map.addListener("click", this.addPoint);
      
      // if (this.places.length) {
      //   const bounds = this.refreshPolyline()
      //   this.map.fitBounds(bounds);
      // }
    },
    attachSearch() {
      // 3. 검색창 만들기
      const input = document.getElementById("pac-input");
      console.log(input)
      const searchBox = new window.google.maps.places.SearchBox(input);
      this.map.controls[window.google.maps.ControlPosition.TOP_LEFT].push(input);
      // 4.검색어에 따라 바운더리를 바꾼다
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
    // 5. 폴리라인을 위한 정점(포인트)를 만들어 마커로 찍는다
    addPoint(event) {
      const marker = new window.google.maps.Marker({
        position:event.latLng,
        map:this.map,
        animation: window.google.maps.Animation.DROP
      });
      // 마커 더블클릭시 삭제
      marker.addListener('dblclick', (e) => {
        console.log(e.latLng)
        this.removePoint(marker)
      });
      // 마커 클릭시 바운스효과
      marker.addListener('click', function () {
        marker.setAnimation(window.google.maps.Animation.BOUNCE);
        setTimeout((function() {
          marker.setAnimation(null)
        }).bind(marker), 1400)
      });
      let newPlace = {
        createdOrder: this.pointListPk,
        imageUpload: false,
        placeImg : '',
        lat : event.latLng.lat(),
        lng : event.latLng.lng(),
        content: null,
        isThumbnail : false,
        marker: {
          location: marker,
          createdOrder: this.pointListPk,
        },
      }
      this.pointListPk = this.pointListPk + 1
      // Store actions로 연동
      this.addPlace(newPlace)
      this.refreshPolyline();
    },
    // 6. 마커 삭제 구현 (마커 리스트를 제거한 뒤 다시 맵 refresh)
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
    refreshPolyline() {
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

    // 맵 멈추고 바운드 재정렬, polyline에서 xy값 좌표 떼오기
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
  margin: 10px 10px 0 0;
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
