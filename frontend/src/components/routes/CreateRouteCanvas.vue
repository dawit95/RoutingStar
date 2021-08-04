<template>
  <div>
    <canvas id="canvas"></canvas>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'CreateRouteCanvas',
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters(['places', 'xyPoints'])
  },
  methods: {
    addCanvasScript() {
      const script = document.createElement("script");

      script.onload = () => this.drawPolyLine();
      script.src = "https://cdnjs.cloudflare.com/ajax/libs/fabric.js/4.5.0/fabric.min.js";
      document.head.appendChild(script);
    },
    drawPolyLine() {
      var canvas = new window.fabric.Canvas("canvas", {width:800, height:400 });
      console.log(canvas.getWidth())
      console.log(this.xyPoints)
      var canvasPolyline = new window.fabric.Polyline(
        this.xyPoints,
        {
          stroke: 'white',
          fill: 'rgba(0,0,0,0)',
          strokeWidth: 10,
        })

      const canvasWidth = 600
      const canvasHeight = 400
      const margin = 0.8

      var bounds = canvasPolyline.getBoundingRect()
      var widthRatio = bounds.width / canvasWidth
      var heightRatio = bounds.height / canvasHeight

      if ( widthRatio > heightRatio ) {
        canvasPolyline.scaleToWidth( canvasWidth * margin )  
      } else {
        canvasPolyline.scaleToHeight( canvasHeight * margin)  
      }
      canvas.add(canvasPolyline)
      canvasPolyline.center()
    },
  },

  mounted() {
    window.fabric
      ? this.drawPolyLine()
      : this.addCanvasScript();

  }
}
</script>

<style>
body {
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #f0f0f0;
      }

#canvas {
  width: 800px;
  height: 400px;
  background-color: green;
}
</style>