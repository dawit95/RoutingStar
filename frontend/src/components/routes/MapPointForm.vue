<template>
  <v-flex xs12>
    <draggable>
      <v-list
        outlined
        v-for="(pointItem, idx) in pointedItems"
        :key="idx"
      >


        <v-icon drak large right style="cursor: pointer;">mdi-drag-horizontal-variant</v-icon>

        <v-icon right large style="cursor: pointer;" @click="deleteItem(pointItem, idx)">mdi-alpha-x-circle-outline</v-icon>
        {{idx}}
        <v-list-item outlined ma-0 pa-0 @click="forcheck(pointItem)">
          <v-list-item-content>
            <input @change="onFileSelected(pointItem)" accept="image/*" type="file">
            <v-textarea v-model="pointItem.content" @click="activePoint(pointItem)" @mouseout="stopPoint(pointItem)" label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"></v-textarea>
            <v-btn>썸네일 활용유무(선택시 이미지 썸네일로 적용)</v-btn>
          </v-list-item-content>
        </v-list-item>


      </v-list>
    </draggable>
    <button @click="tmp_complete">확인용 버튼</button>
    <button @click="dataInitCheck">데이터 저장됐는지 확인용 버튼</button>
  </v-flex>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import axios from 'axios'
import draggable from 'vuedraggable'

export default {
  name: 'MapPointForm',
  components: {
    draggable,
  },
  data () {
    return {
      isActive: false,
      selectedFile: null,
    }
  },
  methods: {
    activePoint(item) {
      item.marker.setAnimation(window.google.maps.Animation.BOUNCE);
    },
    stopPoint(item) {
      item.marker.setAnimation(null);
    },
    onFileSelected(pointItem) {
      this.selectedFile = event.target.files[0]
      const idx = this.$store.getters.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
      this.$store.getters.imgList[idx] = this.selectedFile
      // console.log( this.$store.getters.imgList)
    },
    check() {
      const ins = this.$store.getters.imgList.length
      const files = new FormData()
      for (var x = 0; x < ins; x++) {
        if (this.$store.getters.imgList[x] === null) {
          files.append('files['+ x + ']', null)
        } else {
          const image =  this.$store.getters.imgList[x]
          files.append('files', image)
        }
      }
      axios({
        method: 'post',
        url: 'http://192.168.1.214:9091/api/v1/img/place',
        data: files,
        headers: {'Content-Type': 'multipart/form-data'}
      })
      .then(res => {
        console.log('보내짐')
        console.log(res)
      })
      .catch(err => {
        console.log('안보내짐')
        console.log(err)
      })
    },
    // 첨부파일 업로드되면 지속 업로드
    tmp_update(pointItem) {
      this.selectedFile = event.target.files[0]
      // 핀이 등록되면 해당 핀에의해 만들어진 리스트 인덱스를 가져와서 해당 인덱스를 활용하여 imgList에 이미지 넣기
      const idx = this.$store.getters.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
      this.$store.getters.imgList[idx] = this.selectedFile
    },
    // 완료버튼이 눌러진다면
    tmp_complete() {
      const ins = this.$store.getters.pointedItems.length
      for (var x = 0; x < ins; x++) {
        const files = new FormData()
        const pk = this.$store.getters.pointedItems[x].pk
        console.log(pk)
        if (this.$store.getters.imgList[pk] === null) {
          continue
        } else {
          const image =  this.$store.getters.imgList[pk]
          // console.log('-------')
          // console.log(pk)
          files.append('files', image)
          axios({
            method: 'post',
            url:'http://192.168.1.214:8000/api/v1/img/place',
            data: files,
            headers: {'Content-Type': 'multipart/form-data'}
          })
          .then(res => {
            console.log('보내짐')
            const responseData = res.data.successDto.success.image
            this.$store.getters.pointedItems[pk].image = responseData
          })
          .catch(err => {
            console.log('안보내짐')
            console.log(err)
          })
        }
      }
    },
    forcheck(item) {
      console.log(`${item.pk}번째로 생성된 마커의 pk`)
    },
    dataInitCheck() {
      console.log(this.$store.getters.pointedItems)
    },
    deleteItem(pointItem, idx) {
      // console.log(idx)
      // console.log(pointItem.pk)
      // console.log(this.$store.getters.pointedItems)
      // const pk = pointItem.pk
      // const pointItems = this.$store.getters.pointedItems
      // this.$store.getters.pointedItems.splice(idx)
      // this.$delete(this.$store.getters.pointedItems, idx)
      // this.$store.getters.pointedItems = this.$store.getters.pointedItems.filter(item => item.pk !== idx)
      // this.$store.state.routes.pointList = this.$store.state.routes.pointList.filter(item => item.pk !== idx)
      // this.$store.state.routes.pointList

      const marker = pointItem.marker
      this.removePoint(marker)
      this.$store.state.routes.pointList.splice(idx,1)
    },

    removePoint(marker) {
      const latLng = marker.getPosition();
      const lat = latLng.lat();
      const lng = latLng.lng();
      const pointedItems = this.$store.getters.pointedItems
      console.log(pointedItems)
      const idx = pointedItems.findIndex( (e) => e.lat == lat && e.lng == lng );
      if ( idx != -1 ) {
        marker.setMap(null);
        pointedItems.splice(idx,1);
        this.refreshPolyline();
      }
    },
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const pointedItems = this.$store.getters.pointedItems
      path.clear();
      for( const point of pointedItems ) {
        path.push( new window.google.maps.LatLng( point.lat, point.lng));
      }
    },
  },
  computed: {
    ...mapGetters(['pointedItems', 'imgList']),
    ...mapState(['pointList',])
  }
}
</script>

<style>

</style>