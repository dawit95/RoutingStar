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
    ...mapGetters(['pointedItems', 'xyPoints'])
  },
  methods: {
    addCanvasScript() {
      const script = document.createElement("script");

      script.onload = () => this.drawPolyLine();
      script.src = "https://cdnjs.cloudflare.com/ajax/libs/fabric.js/4.5.0/fabric.min.js";
      document.head.appendChild(script);
    },
    drawPolyLine() {
      const pointArray = []
      for (let j=0; j<this.xyPoints.length; j++) {
        if (j % 2 === 0) {
          // let tmpx = this.xyPoints[j]
          // let tmpy = this.xyPoints[j+1]
        }
      }
      var canvas = new window.fabric.Canvas("canvas");
      console.log(this.xyPoints)
      var canvasPolyline = new window.fabric.Polyline(
        pointArray,
        {
          stroke: 'white'
        })
      canvas.add(canvasPolyline)
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