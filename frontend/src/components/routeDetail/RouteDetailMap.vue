<template>
  <v-container fluid grid-list-md>
    <v-layout row swap>
      <v-flex xs12>

        
          <!-- 맵 -->
          <div>
            <div id="map"></div>
          </div>
      </v-flex>

      <v-divider></v-divider>
    </v-layout>
  </v-container>
</template>

<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'
  
export default {
  name: 'RouteDetailMap',
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
      locations: [],
    }
  },
  computed: {
    ...mapGetters(['feed', 'polyLine'])
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
      // 맵이 중복으로 만들어지는 걸 막기 위해 함수를 분리
      if ( this.map === null ) {
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
        
      const places = this.feed.places
     
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
        console.log(e.latLng)
        console.log(e.latLng.lat())
        console.log(e.latLng.lng())
        this.removePoint(marker,e.latLng.lat(), e.latLng.lng())
        // this.locations.push({ lat: e.latLng.lat(), lng: e.latLng.lng()})
        // this.removePoint(marker)
        // const contentString =  'ulruslfawejfiawfoawejf'
        // const infowindow = new window.google.maps.InfoWindow({
        //   content: 'ajvioawefjawiofjawioefjao',
        // })
        // marker.position = a
        // marker.addListener("mouseup", () => {
        //   // console.log(marker)
        //   infowindow.open({
        //     anchor: marker,
        //     map: this.map,
        //     shouldFocus: false,
        //   });
        // })

        })
      }
      // console.log('이거슨로케이션', this.locations)
      // const infowindow = new window.google.maps.InfoWindow({
      //     content: 'ajvioawefjawiofjawioefjao',
      //   })
      // marker.addListener("mouseup", () => {
      //     // console.log(marker)
      //     infowindow.open({
      //       anchor: marker,
      //       map: this.map,
      //       shouldFocus: false,
      //     });
      //   })


        // 2. 폴리라인을 쓸 수 있도록 객체를 생성해서 map에 얹는다
        this.SET_POLYLINE(new window.google.maps.Polyline
          ({
            strokeColor: "#2A355D",
            strokeOpacity: 0.3,
            strokeWeight: 8,
          })
        )
        this.polyLine.setMap(this.map);
        console.log('polyline')
        this.map.addListener("click", this.addPoint);
      }
      // 실제 Google Map 객체를 생성하는 것은 null 일때만 
      // 루트를 그릴 때 freeze 상태라면 bound 조정하고, 아니라면 검색창 붙인다
      if(freeze === true) {
        this.freezeBound()
      } else {
        this.refreshPolyline()
      }
    },
     
    addPoint(event) {
      let newPlace = this.makePlace(event.latLng)

      // Store actions로 연동 & 루트 새로고침
      this.addPlace(newPlace)
      this.refreshPolyline();
    },
  

    removePoint(marker, lat, lng) {
    for( const place of this.feed.places ) {
      if (place.lat == lat && place.lng == lng) { 
        var a = place.placeImg
        var b = place.title
        console.log('ab', a, b)


        const infowindow = new window.google.maps.InfoWindow({
          content:    
          `<img src="${a}" alt="" height="100" width="150">` +
                     `<div class="modalcontent">"${b}"</div>`
        })
        // .theme--dark.v-card iw-subTitle 
   
      marker.addListener("mouseup", () => {
          // console.log(marker)
          infowindow.open({
            anchor: marker,
            map: this.map,
            shouldFocus: false,
          });
        })

      }
    }},
    //  console.log(lat, lng)
    // 
    //  console.log(this.feed)
    //  console.log('marker', marker)
    //  console.log('marker', marker.latLng)
    //  console.log('이거슨로케이션', this.locations)
    //   const infowindow = new window.google.maps.InfoWindow({
    //       content: 'ajvioawefjawiofjawioefjao',
    //     })
    //   console.log('infowindow', infowindow)
    //   console.log('infowindow', infowindow.content)
    //   marker.addListener("mouseup", () => {
    //       // console.log(marker)
    //       infowindow.open({
    //         anchor: marker,
    //         map: this.map,
    //         shouldFocus: false,
    //       });
    //     })

      // const latLng = marker.getPosition();
      // const lat = latLng.lat();
      // const lng = latLng.lng();
      // const places = this.places
      // const idx = places.findIndex( (e) => e.lat == lat && e.lng == lng );
      // if ( idx != -1 ) {
      //   marker.setMap(null);
      //   places.splice(idx,1);
      //   this.refreshPlaces(places)
      //   this.refreshPolyline();
      // }
    // },


    // 6. 루트를 새로고침
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const places = this.feed.places
      const bounds = new window.google.maps.LatLngBounds();

      path.clear();
      for( const place of places ) {
        let latLng = new window.google.maps.LatLng( place.lat, place.lng)
        path.push(latLng)
        bounds.extend(latLng)
      }
      return bounds
    },
  },
  watch: {
    isFreeze: function(isFreeze) {
      if (isFreeze) {
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

/* .theme--dark.v-card {
    background-color: #1E1E1E;
    color: #ff0202;
} */
/* .theme--dark.v-card {
  background-color: red;
    color: #1976d2;
}
  .v-card {
      background-color: red;
      color: #1976d2;
  } */

/* .theme--dark {
    background-color: red;
    color: #1976d2;
}
.v-sheet {
    background-color: red;
    color: #1976d2;
} */

/* .theme--dark.v-card {
    background-color: red;
    color: #1976d2;
} */




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
