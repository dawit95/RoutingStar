<template>

  <div>
    <h1>This is Place Search Box</h1>
    <div>
      <!-- 인풋박스 -->
    <input
      id="pac-input"
      class="controls"
      type="text"
      placeholder="Search Box"/>
    </div>
    <h1>test</h1>
    <!-- 맵 -->
    <div id ="map">
    </div>
  </div>

</template>

<script>
export default {
  name: 'Map_backup',
  props: {
    msg: String
  },
  data() {
    return {
      map: null,
      polyLine: null,
    }
  },
  methods: {
    addGoogleMapScript() {
      const script = document.createElement("script");

      script.onload = () => this.showMap();
      script.src =
        "https://maps.googleapis.com/maps/api/js?key=" + process.env.VUE_APP_GOOGLEMAPS_API_KEY + "&libraries=places&region=KR&language=ko&v=quarterly";
      script.async = true;
      script.defer = true;
      document.head.appendChild(script);
    },
    test() {
      console.log('done');
    },
    showMap() {
      this.map = new window.google.maps.Map(document.getElementById('map'), {
            center: { lat:37.501, lng: 127.039 },
            zoom: 16,
            streetViewControl: false,
            mapTypeControl: false,
            zoomControl: false,
            fullscreenControl: false
      })
      console.log(this.map)
      this.polyLine = new window.google.maps.Polyline({
        strokeColor: "#E64398",
        strokeOpacity: 0.3,
        strokeWeight: 8,
      });
      this.polyLine.setMap(this.map);
      this.map.addListener("click", this.addPoint);

      // 1. 검색창 띄우기
      // Create the search box and link it to the UI element.
      var input = document.getElementById('pac-input');
      console.log(input);
      const searchBox = window.google.maps.places.SearchBox(input);
      console.log( searchBox );

      /*
      this.map.controls[window.google.maps.ControlPosition.TOP_LEFT].push(input);
      // Bias the SearchBox results towards current map's viewport.
      // 2. 검색어에 따라 지도의 바운더리를 바꾼다
      this.map.addListener("bounds_changed", () => {
        searchBox.setBounds(this.map.getBounds());
      });

      // Listen for the event fired when the user selects a prediction and retrieve
      // more details for that place.
      searchBox.addListener("places_changed", () => {
        const places = searchBox.getPlaces();

        if (places.length == 0) {
          return;
        }

      // For each place, get the icon, name and location.
        const bounds = new window.google.maps.LatLngBounds();
        places.forEach((place) => {
          if (!place.geometry || !place.geometry.location) {
            console.log("Returned place contains no geometry");
            return;
          }
          if (place.geometry.viewport) {
            // Only geocodes have viewport.
            bounds.union(place.geometry.viewport);
          } else {
            bounds.extend(place.geometry.location);
          }          
        });
        this.map.fitBounds(bounds);
      });
      */
    }, 
    addPoint(event) {
      const path = this.polyLine.getPath();
      path.push( event.latLng );

      console.log( event.latLng.lat());
      new window.google.maps.Marker( { position:event.latLng, map:this.map});
    }
  },
  mounted() {
    window.google && window.google.maps
      ? this.showMap()
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
