<template>
  <div id ="map">
 </div>

</template>

<script>
export default {
  name: 'App',
  props: {
    msg: String
  },
  data() {
    return {
      map:null,
    }
  },
  methods: {
    showMap() {
      this.map = new window.google.maps.Map(document.getElementById('map'), {
            center: { lat:-25.34, lng: 131.036 },
            zoom: 8,
            streetViewControl: false,
            mapTypeControl: false,
            zoomControl: false,
            fullscreenControl: false
      })
      this.polyLine = new window.google.maps.Polyline({
        strokeColor: "#000000",
        strokeOpacity: 1.0,
        strokeWeight: 3,
      });
      this.polyLine.setMap(this.map);
      this.map.addListener("click", this.addPoint);
    }, 
    
    addGoogleMapScript() {
      const script = document.createElement("script");

      script.onload = () => this.showMap();
      script.src =
        "https://maps.googleapis.com/maps/api/js?key=" + process.env.VUE_APP_GOOGLEMAPS_API_KEY;
      document.head.appendChild(script);
    },
    addPoint(event) {
      const path = this.polyLine.getPath();
      path.push( event.latLng);

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
</style>