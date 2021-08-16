<template>
  <div class="canvas-body">
    <canvas id="canvas"></canvas>
    <!-- <v-btn @click="canvasToPng">루트 그림으로 변환 확인</v-btn>
    <img v-bind:src="imgDataUrl" alt=""> -->
  </div>
</template>

<script>
import { mapGetters, mapActions, } from 'vuex'
import AWS from 'aws-sdk'

export default {
  name: 'CreateRouteCanvas',
  data() {
    return {
      imgDataUrl: '',
      file: '',
      albumBucketName: 'routingstar-photo-album',
      bucketRegion: 'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
    }
  },
  props: {
    isCompleted: {
      type: Boolean
    }
  },
  computed: {
    ...mapGetters(['places', 'xyPoints', 'jwt'])
  },
  methods: {
    ...mapActions(['updateRouteImg', 'createRoute']),

    addCanvasScript() {
      console.log("addCanvasScript")
      const script = document.createElement("script");

      script.onload = () => this.drawPolyLine();
      script.src = "https://cdnjs.cloudflare.com/ajax/libs/fabric.js/4.5.0/fabric.min.js";
      document.head.appendChild(script);
    },
    createLine(s, e, lineWidth, lineColor, shadow ) {
      return new window.fabric.Line( [ s.x, s.y, e.x, e.y ], {
            //fill:'white',
            stroke:lineColor,
            strokeWidth: lineWidth,
            shadow: shadow,
        })
    },
    createLine2(s, e, lineColor, shadow ) {
      return new window.fabric.Line( [ s.x, s.y, e.x, e.y ], {
            //fill:'white',
            stroke:lineColor,
            strokeWidth: 3,
            shadow: shadow,
        })
    },
    createLine3(s, e, shadow ) {
      return new window.fabric.Line( [ s.x, s.y, e.x, e.y ], {
            //fill:'white',
            stroke:'white',
            strokeWidth: 4,
            shadow: shadow,
            opacity: 0.4
        })
    },
    // createCircle(point, lineWidth, lineColor, shadow) {
    createCircle(point, lineWidth) {
      return new window.fabric.Circle ({
        left: point.x - lineWidth / 2,
        top: point.y - lineWidth / 2,
        radius: lineWidth,
        stroke: '#8860D0',
        fill: '#8860D0',
        // stroke: 'white',
        // fill: 'white',
        opacity: 0.7,
        // shadow: shadow
      })
    },
    drawPolyLine() {
      // console.log("drawPolyLine")
      let canvas = new window.fabric.Canvas("canvas", {width:340, height:340 });
      // console.log(canvas.getWidth())
      // console.log(this.xyPoints)
      // var canvasPolyline = new window.fabric.Polyline(
      //   this.xyPoints,
      //   {
      //     stroke: '#D2FDFF',
      //     fill: 'rgba(0,0,0,0)',
      //     strokeWidth: 10,
      //   })

      // const canvasWidth = 400
      // const canvasHeight = 400
      // const margin = 0.6

      // let bounds = canvasPolyline.getBoundingRect()
      // let widthRatio = bounds.width / canvasWidth
      // let heightRatio = bounds.height / canvasHeight

      // if ( widthRatio > heightRatio ) {
      //   canvasPolyline.scaleToWidth( canvasWidth * margin )  
      // } else {
      //   canvasPolyline.scaleToHeight( canvasHeight * margin)  
      // }
      // canvas.add(canvasPolyline)
      // canvasPolyline.center()
      let shadow = new window.fabric.Shadow( {
        blur:17,
        // color:'#D2FDFF',
        color:'#96F9FF',
      })

      const lineWidth = 7
      // const lineColor = '#D2FDFF'
      const lineColor = '#96F9FF'

      let polyLine = []

      for ( let step = 0 ; step < this.xyPoints.length-1 ; step ++ ) {
        let s = this.xyPoints[step]
        let e = this.xyPoints[step+1]
        
        polyLine.push( this.createLine( s, e, lineWidth, lineColor, shadow ))
        polyLine.push( this.createLine2( s, e, lineColor, shadow ))
        polyLine.push( this.createLine3( s, e, shadow ))
        
      }

      for ( let step = 0 ; step < this.xyPoints.length ; step ++ ) {
        polyLine.push(this.createCircle( this.xyPoints[step], lineWidth, lineColor, shadow))
      }

      let group = new window.fabric.Group( polyLine, { 
        selectable: false,
        objectCaching: false,
      })

      canvas.add(group)
      group.center()
      canvas.renderAll()
    },
    canvasToPng() {
      console.log('canvasToPng')
      let canvas = document.getElementById("canvas")
      console.log(canvas)
      this.imgDataUrl = canvas.toDataURL("image/png")
      
      // base64 암호화뎅 이미지 데이터 디코딩
      let blobBin = atob(this.imgDataUrl.split(',')[1])
      let array = []
      for (let i = 0; i < blobBin.length; i++) {
        array.push(blobBin.charCodeAt(i));
      }
      this.file = new Blob([new Uint8Array(array)], {type: 'image/png'});	// Blob 생성
      console.log(this.file)
    }, 
    sendToS3() {  
      console.log('sendToS3')
      const image = this.file
      const date = new Date().getTime();
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
        IdentityPoolId: this.IdentityPoolId,
        })
      });
      // 썸네일 지정시 프론트에서 바로 업로드(리팩토링 필요)
      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.albumBucketName }
      });
      s3.upload({
          Key: `routeImage/${date}`,
          Body: image,
          ContentType: 'image/png',
          ACL: 'public-read'
        }, (err, data) => {
          if (err) {
            console.log(err)
            return alert("There was an error uploading your photo: ", err.message);
          }
          console.log(`data변환 완료`)
          this.updateRouteImg(data.Location)
          console.log(data)
          const access_token = this.jwt[0]
          console.log(access_token)
          console.log('createroute 실행전', this.jwt)
          this.createRoute(this.jwt)
        })
    },
    
  },
  watch: {
    isCompleted: function(isCompleted) {
      if (isCompleted) {
        this.canvasToPng()
        this.sendToS3()
      }
    }
  },
  mounted() {
    window.fabric
      ? this.drawPolyLine()
      : this.addCanvasScript();
  },
}
</script>

<style scoped>
.canvas-body {
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #2A355D;
      }
#canvas {
  width: 100%;
  height: 400px;
}
</style>