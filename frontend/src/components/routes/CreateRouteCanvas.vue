<template>
  <div>
    <canvas id="canvas"></canvas>
    <v-btn @click="canvasToPng">루트 그림으로 변환 확인</v-btn>
    <img v-bind:src="imgDataUrl" alt="">
  </div>
</template>

<script>
import { mapGetters, mapActions, } from 'vuex'

export default {
  name: 'CreateRouteCanvas',
  data() {
    return {
      imgDataUrl: '',
      file: '',
    }
  },
  computed: {
    ...mapGetters(['places', 'xyPoints'])
  },
  methods: {
    ...mapActions(['updateRouteImg',]),

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
          stroke: '#D2FDFF',
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
      this.canvasToPng()
      this.updateRouteImg(this.file)
    },
    canvasToPng() {
      var canvas = document.getElementById("canvas")
      this.imgDataUrl = canvas.toDataURL("image/png")
      
      // base64 암호화뎅 이미지 데이터 디코딩
      var blobBin = atob(this.imgDataUrl.split(',')[1])
      var array = []
      for (var i = 0; i < blobBin.length; i++) {
        array.push(blobBin.charCodeAt(i));
      }
      this.file = new Blob([new Uint8Array(array)], {type: 'image/png'});	// Blob 생성
      console.log(this.file)
      // var formdata = new FormData();	// formData 생성
      // formdata.append("file", file);	// file data 추가
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
        background-color: #2A355D;
      }

#canvas {
  width: 800px;
  height: 400px;
}
</style>