<template>
  <v-container fluid grid-list-md>

  </v-container>
</template>

<script>
export default {
  name: 'Map_delete',
  components: {

  },
  // data() {
  //   return {
  //     SearchWord: {
  //       word: '',
  //     },
  //     map: null,
  //     polyLine: null,
  //     lat: '',
  //     lng: '',
  //     image: null,
  //     pointInfo: '',
  //     pointList: [],
  //   }
  // },
  // methods: {
  //   // 0. HTML에 Script 삽입
  //   // API key 보호를 위해 변수로 삽입
  //   addGoogleMapScript() {
  //     const script = document.createElement("script");

  //     script.onload = () => this.initMap();
  //     script.src =
  //       "https://maps.googleapis.com/maps/api/js?key=" + process.env.VUE_APP_GOOGLEMAPS_API_KEY + "&libraries=places&region=KR&language=ko&v=weekly";
  //     // script.async = true;
  //     script.defer = true;
  //     document.head.appendChild(script);
  //   },
  //   // This example adds a search box to a map, using the Google Place Autocomplete
  //   // feature. People can enter geographical searches. The search box will return a
  //   // pick list containing a mix of places and predicted search terms.
  //   // This example requires the Places library. Include the libraries=places
  //   // parameter when you first load the API. For example:
  //   // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
    
  //   initMap() {
  //     this.map = new window.google.maps.Map(document.getElementById("map"), {
  //       mapId: "8e0a97af9386fef",
  //       center: { lat:37.501, lng: 127.039 },
  //       zoom: 16,
  //       streetViewControl: false,
  //       mapTypeControl: false,
  //       zoomControl: false,
  //       fullscreenControl: false,
  //       // mapTypeId: "roadmap",
  //     });
  //     // 1. 검색창 만들기
  //     // Create the search box and link it to the UI element.
  //     const input = document.getElementById("pac-input");
  //     const searchBox = new window.google.maps.places.SearchBox(input);
  //     this.map.controls[window.google.maps.ControlPosition.TOP_LEFT].push(input);
  //     // Bias the SearchBox results towards current map's viewport.
  //     // 2.검색어에 따라 바운더리를 바꾼다
  //     this.map.addListener("bounds_changed", () => {
  //       searchBox.setBounds(this.map.getBounds());
  //     });
  //     // let markers = [];
  //     // Listen for the event fired when the user selects a prediction and retrieve
  //     // more details for that place.
  //     searchBox.addListener("places_changed", () => {
  //       const places = searchBox.getPlaces();
  //       // 검색된 단어 (엔터 후) 찍어보기
  //       // console.log(this.SearchWord.word)
  //       if (places.length == 0) {
  //         return;
  //       }
  //       // // Clear out the old markers.
  //       // markers.forEach((marker) => {
  //       //   marker.setMap(null);
  //       // });
  //       // markers = [];
  //       // For each place, get the icon, name and location.
  //       const bounds = new window.google.maps.LatLngBounds();
  //       places.forEach((place) => {
  //         if (!place.geometry || !place.geometry.location) {
  //           console.log("Returned place contains no geometry");
  //           return;
  //         }
  //       //   const icon = {
  //       //     url: place.icon,
  //       //     size: new google.maps.Size(71, 71),
  //       //     origin: new google.maps.Point(0, 0),
  //       //     anchor: new google.maps.Point(17, 34),
  //       //     scaledSize: new google.maps.Size(25, 25),
  //       //   };
  //       //   // Create a marker for each place.
  //         // markers.push(
  //         //   new google.maps.Marker({
  //         //     map,
  //         //     icon,
  //         //     title: place.name,
  //         //     position: place.geometry.location,
  //         //   })
  //         // );

  //         if (place.geometry.viewport) {
  //           // Only geocodes have viewport.
  //           bounds.union(place.geometry.viewport);
  //         } else {
  //           bounds.extend(place.geometry.location);
  //         }
  //       });
  //       this.map.fitBounds(bounds);
  //     });

  //     // 3. 폴리라인(루트 라인)을 만든다
  //     this.polyLine = new window.google.maps.Polyline({
  //       strokeColor: "#E64398",
  //       strokeOpacity: 0.3,
  //       strokeWeight: 8,
  //     });
  //     this.polyLine.setMap(this.map);
  //     this.map.addListener("click", this.addPoint);
    

  //     // 폴리라인의 vertex 삭제 구현
  //     /**
  //      * A menu that lets a user delete a selected vertex of a path.
  //      */
  //     class DeleteMenu extends window.google.maps.OverlayView {
  //       div_;
  //       divListener_;
  //       constructor() {
  //         super();
  //         this.div_ = document.createElement("div");
  //         this.div_.className = "delete-menu";
  //         this.div_.innerHTML = "Delete";
  //         const menu = this;
  //         window.google.maps.event.addDomListener(this.div_, "click", () => {
  //           menu.removeVertex();
  //         });
  //       }
  //       onAdd() {
  //         const deleteMenu = this;
  //         const map = this.getMap();
  //         this.getPanes().floatPane.appendChild(this.div_);
  //         // mousedown anywhere on the map except on the menu div will close the
  //         // menu.
  //         this.divListener_ = window.google.maps.event.addDomListener(
  //           map.getDiv(),
  //           "mousedown",
  //           (e) => {
  //             if (e.target != deleteMenu.div_) {
  //               deleteMenu.close();
  //             }
  //           },
  //           true
  //         );
  //       }
  //       onRemove() {
  //         if (this.divListener_) {
  //           window.google.maps.event.removeListener(this.divListener_);
  //         }
  //         this.div_.parentNode.removeChild(this.div_);
  //         // clean up
  //         this.set("position", null);
  //         this.set("path", null);
  //         this.set("vertex", null);
  //       }
  //       close() {
  //         this.setMap(null);
  //       }
  //       draw() {
  //         const position = this.get("position");
  //         const projection = this.getProjection();

  //         if (!position || !projection) {
  //           return;
  //         }
  //         const point = projection.fromLatLngToDivPixel(position);
  //         this.div_.style.top = point.y + "px";
  //         this.div_.style.left = point.x + "px";
  //       }
  //       /**
  //        * Opens the menu at a vertex of a given path.
  //        */
  //       open(map, path, vertex) {
  //         this.set("position", path.getAt(vertex));
  //         this.set("path", path);
  //         this.set("vertex", vertex);
  //         this.setMap(map);
  //         this.draw();
  //       }
  //       /**
  //        * Deletes the vertex from the path.
  //        */
  //       removeVertex() {
  //         const path = this.get("path");
  //         const vertex = this.get("vertex");

  //         if (!path || vertex == undefined) {
  //           this.close();
  //           return;
  //         }
  //         path.removeAt(vertex);
  //         this.close();
  //       }
  //     }
  //     const deleteMenu = new DeleteMenu();
  //     window.google.maps.event.addListener(this.polyLine, "contextmenu", (e) => {
  //       // Check if click was on a vertex control point
  //       if (e.vertex == undefined) {
  //         return;
  //       }
  //       deleteMenu.open(this.map, this.polyLine.getPath(), e.vertex);
  //     });
  //   },
  //   // 4. 폴리라인을 위한 정점(포인트)를 만들어 마커로 찍는다
  //   addPoint(event) {
  //     var marker = new window.google.maps.Marker( { position:event.latLng, map:this.map});
  //     marker.addListener( "contextmenu", (e)=> { 
  //       console.log(e.latLng);
  //       this.removePoint(marker);
  //     });  
  //     let newPoint = {
  //       image : null,
  //       lat : event.latLng.lat(),
  //       lng : event.latLng.lng(),
  //       content: null,
  //       thumbnail : false,
  //       marker: marker
  //     }    
  //     this.pointList.push(newPoint)
  //     this.refreshPolyline();
  //   },
  //   removePoint(marker) {
  //     const latLng = marker.getPosition();
  //     const lat = latLng.lat();
  //     const lng = latLng.lng();

  //     const idx = this.pointList.findIndex( (e) => e.lat == lat && e.lng == lng );
  //     if ( idx != -1 ) {
  //       marker.setMap(null);
  //       this.pointList.splice(idx,1);
  //       this.refreshPolyline();
  //     }
  //   },
  //   refreshPolyline() {
  //     const path = this.polyLine.getPath();
  //     path.clear();

  //     for( const point of this.pointList ) {
  //       path.push( new window.google.maps.LatLng( point.lat, point.lng));
  //     }
  //   },
  //     // console.log('작동함 ㅇㅇ')
  //   /*
  //   addPoint(event) {
  //     const path = this.polyLine.getPath();
  //     path.push( event.latLng );
  //     console.log(event)
  //     // console.log( event.latLng.lat());
  //     var marker = new window.google.maps.Marker( { position:event.latLng, map:this.map});
  //     marker.addListener( "contextmenu", (e)=> { 
  //       console.log( e.latLng);
  //       marker.setMap(null);
  //     });
  //     this.addPointItem(event)
  //   },
  //   addPointItem (event) {
  //     // console.log('작동함 ㅇㅇ')
  //     let newPoint = {
  //       image : null,
  //       lat : event.latLng.lat(),
  //       lng : event.latLng.lng(),
  //       content: null,
  //       thumbnail : false,
  //     }
  //     this.pointList.push(newPoint)
  //   },*/
  // },
  // mounted() {
  //   window.google && window.google.maps
  //     ? this.initMap()
  //     : this.addGoogleMapScript();
  // }
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

.delete-menu {
  position: absolute;
  background: white;
  padding: 3px;
  color: #666;
  font-weight: bold;
  border: 1px solid #999;
  font-family: sans-serif;
  font-size: 12px;
  box-shadow: 1px 3px 3px rgba(0, 0, 0, 0.3);
  margin-top: -10px;
  margin-left: 10px;
  cursor: pointer;
}

.delete-menu:hover {
  background: #eee;
}
</style>
