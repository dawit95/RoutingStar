<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>

      <v-flex xs12>
        <div>
          <!-- 검색창 -->
          <input v-model="SearchWord.word"
            id="pac-input"
            class="controls"
            type="text"
            placeholder="Search Box"
          />
          <!-- 맵 -->
          <div id="map"></div>
        </div>
      </v-flex>

      <v-divider></v-divider>
    </v-layout>
    <v-btn @click='createMap'>Submit</v-btn>
  </v-container>
</template>

<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'
  
export default {
  name: 'Map',
  components: {  
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
    ...mapGetters(['pointedItems', 'latLstItems', 'lngLstItems', 'polyLine'])
  },
  methods: {
    ...mapMutations(['SET_POLYLINE',]),
    // 위에거 actions로 정리 할거면 정리할것
    ...mapActions(['addPointItem', 'addLatLngLst', 'sendImagesArray', 'refreshPolyline']),
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
      // 중심은 우선 첫번째 요소로 선택
      if (this.latLstItems.length) {
        this.map = new window.google.maps.Map(document.getElementById("map"), 
        {
          mapId: "8e0a97af9386fef",
          center: { lat:this.latLstItems[0], lng:this.lngLstItems[0] },
          zoom: 16,
          streetViewControl: false,
          mapTypeControl: false,
          zoomControl: false,
          fullscreenControl: false,
        })
        // 좌표
        const flightPlanCoordinates = []
        for (var j in this.latLstItems) {
          const myLatLng = {lat: this.latLstItems[j], lng: this.lngLstItems[j]}
          flightPlanCoordinates.push(myLatLng)
          new window.google.maps.Marker({
            position: myLatLng,
            map: this.map,
          });        
          // 선(rotue) 
          const flightPath = new window.google.maps.Polyline({
            path: flightPlanCoordinates,
            geodesic: true,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2,
          });
          flightPath.setMap(this.map);
        }

      } else {
        this.map = new window.google.maps.Map(document.getElementById("map"), 
        {
          mapId: "8e0a97af9386fef",
          center: { lat:37.501, lng: 127.039 },
          zoom: 16,
          streetViewControl: false,
          mapTypeControl: false,
          zoomControl: false,
          fullscreenControl: false,
          // mapTypeId: "roadmap",
        });
      }
      // 3. 검색창 만들기
      const input = document.getElementById("pac-input");
      const searchBox = new window.google.maps.places.SearchBox(input);
      this.map.controls[window.google.maps.ControlPosition.TOP_LEFT].push(input);
      // 4.검색어에 따라 바운더리를 바꾼다
      this.map.addListener("bounds_changed", () => {
        searchBox.setBounds(this.map.getBounds());
      });
      searchBox.addListener("places_changed", () => {
        const places = searchBox.getPlaces();
        if (places.length == 0) {
          return;
        }
        const bounds = new window.google.maps.LatLngBounds();
        places.forEach((place) => {
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
      })
      let newPoint = {
        pk: this.pointListPk,
        image : '',
        lat : event.latLng.lat(),
        lng : event.latLng.lng(),
        content: null,
        thumbnail : false,
        marker: {
          location: marker,
          pk: this.pointListPk,
        },
      }
      this.pointListPk = this.pointListPk + 1
      // Store actions로 연동
      this.addPointItem(newPoint)
      this.refreshPolyline();
    },
    // 6. 마커 삭제 구현 (마커 리스트를 제거한 뒤 다시 맵 refresh)
    removePoint(marker) {
      const latLng = marker.getPosition();
      const lat = latLng.lat();
      const lng = latLng.lng();
      const pointedItems = this.pointedItems
      const idx = pointedItems.findIndex( (e) => e.lat == lat && e.lng == lng );
      if ( idx != -1 ) {
        marker.setMap(null);
        pointedItems.splice(idx,1);
        this.refreshPolyline();
      }
    },
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const pointedItems = this.pointedItems
      path.clear();
      for( const point of pointedItems ) {
        path.push( new window.google.maps.LatLng( point.lat, point.lng));
      }
    },
    
    // 새로운 게시물을 생성했을 때 구글맵에 재요청을 보내서 다시 그려지는지 확인
    createMap() {
      let lat_lst = []
      let lng_lst = []
      for (var i in this.pointedItems){
        lat_lst.push(this.pointedItems[i].lat)
        lng_lst.push(this.pointedItems[i].lng)
      }
      let latLngLst = {
        latLst: lat_lst,
        lngLst: lng_lst,
      }
      this.addLatLngLst(latLngLst)
      this.initMap();
    }
  },
  mounted() {
    window.google && window.google.maps
      ? this.initMap()
      : this.addGoogleMapScript();
  }
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
