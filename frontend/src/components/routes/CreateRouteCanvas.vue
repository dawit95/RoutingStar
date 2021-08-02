<template>
  <div>
    <div id="container"></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'CreateRouteCanvas',
  data() {
    return {
      XYfromlatlng: []
    }
  },
  computed: {
    ...mapGetters(['pointedItems'])
  },
  methods: {
    addCanvasScript() {
      const script = document.createElement("script");

      script.onload = () => this.initCanvas();
      script.src = "https://unpkg.com/konva@8.1.1/konva.min.js";
      // script.async = true;
      document.head.appendChild(script);
    },
    initCanvas() {
      console.log(this.pointedItems)
      var width = 800
      var height = 400

      var stage = new window.Konva.Stage({
        container: 'container',
        width: width,
        height: height,
      });

      var layer = new window.Konva.Layer();

      var redLine = new window.Konva.Line({
        points: [],
        stroke: 'red',
        strokeWidth: 15,
        lineCap: 'round',
        lineJoin: 'round',
      });


      // // dashed line
      // var greenLine = new window.Konva.Line({
      //   points: [5, 70, 140, 23, 250, 60, 300, 20],
      //   stroke: 'green',
      //   strokeWidth: 2,
      //   lineJoin: 'round',
      //   /*
      //    * line segments with a length of 33px
      //    * with a gap of 10px
      //    */
      //   dash: [33, 10],
      // });

      // // complex dashed and dotted line
      // var blueLine = new window.Konva.Line({
      //   points: [5, 70, 140, 23, 250, 60, 300, 20],
      //   stroke: 'blue',
      //   strokeWidth: 10,
      //   lineCap: 'round',
      //   lineJoin: 'round',
      //   /*
      //    * line segments with a length of 29px with a gap
      //    * of 20px followed by a line segment of 0.001px (a dot)
      //    * followed by a gap of 20px
      //    */
      //   dash: [29, 20, 0.001, 20],
      // });

      /*
       * since each line has the same point array, we can
       * adjust the position of each one using the
       * move() method
       */
      redLine.move({
        x: 10,
        y: 20,
      });
      // greenLine.move({
      //   x: 0,
      //   y: 55,
      // });
      // blueLine.move({
      //   x: 0,
      //   y: 105,
      // });

      layer.add(redLine);
      // layer.add(greenLine);
      // layer.add(blueLine);

      // add the layer to the stage
      stage.add(layer);
      this.getXYfromlatlng()
    },
    getXYfromlatlng() {
      for (const point of this.pointedItems) {
        let lat = point.lat
        let lng = point.lng
        this.XYfromlatlng.push(lat)
        this.XYfromlatlng.push(lng)
      }
    },
  },
  mounted() {
    window.Konva
      ? this.initCanvas()
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
</style>