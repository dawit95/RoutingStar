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
    }
  },
  computed: {
    ...mapGetters(['pointedItems', 'xyPoints'])
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
        points: this.xyPoints,
        stroke: 'red',
        strokeWidth: 15,
        lineCap: 'round',
        lineJoin: 'round',
      });

      // 심심하니까 애니메이션 넣어보자
      var period = 2000;
      var anim = new window.Konva.Animation(function (frame) {
        var scale = Math.sin((frame.time * 2 * Math.PI) / period) + 0.001;
        // scale x and y
        redLine.scale({ x: scale, y: scale });
      }, layer);

      anim.start();
      anim.stop();

      /*
       * since each line has the same point array, we can
       * adjust the position of each one using the
       * move() method
       */
      // redLine.move({
      //   x: -300,
      //   y: -50,
      // });
      

      layer.add(redLine);
      // layer.add(greenLine);
      // layer.add(blueLine);

      // add the layer to the stage
      stage.add(layer);
    },
    // map divistion 사이즈에 따른 리사
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