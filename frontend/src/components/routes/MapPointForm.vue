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
        {{pointItem.pk}}
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
  </v-flex>
</template>

<script>
import axios from 'axios'
import { mapGetters, mapActions, } from 'vuex'
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
  computed: {
    ...mapGetters(['pointedItems', 'imgList', 'polyLine']),
  },
  methods: {
    ...mapActions(['refreshPointList']),

    activePoint(item) {
      item.marker.location.setAnimation(window.google.maps.Animation.BOUNCE);
    },
    stopPoint(item) {
      item.marker.location.setAnimation(null);
    },
    // 첨부파일 업로드되면 지속 업로드
    // 핀이 등록되면 해당 핀에의해 만들어진 리스트 인덱스를 가져와서 해당 인덱스를 활용하여 imgList에 이미지 넣기
    onFileSelected(pointItem) {
      this.selectedFile = event.target.files[0]
      const idx = this.pointedItems.findIndex((e) => e.lat == pointItem.lat && e.lng == pointItem.lng );
      this.imgList[idx] = this.selectedFile
    },
    // 완료버튼이 눌러진다면
    // 임시로 여기다 두고, api 파일 소화 후 이동 예정 이동 후 PostRouteDetail과 연동
    tmp_complete() {
      const ins = this.pointedItems.length
      for (var x = 0; x < ins; x++) {
        const files = new FormData()
        const pk = this.pointedItems[x].pk
        console.log(pk)
        if (this.imgList[pk] === null) {
          continue
        } else {
          const image =  this.imgList[pk]
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
            this.pointedItems[pk].image = responseData
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
    deleteItem(pointItem, idx) {
      const marker = pointItem.marker
      this.removePoint(marker, idx)
    },
    removePoint(marker, idx) {
      if ( idx != -1 ) {
        marker.location.setMap(null);
        const oldList = this.pointedItems
        const newPointList = oldList.filter((point) => {
          return point.pk !== marker.pk
        })
        this.refreshPointList(newPointList)
        // this.$store.dispatch('refreshPointList', newPointList)
        this.refreshPolyline();
      }
      // console.log(pointedItems)
    },
    refreshPolyline() {
      const path = this.polyLine.getPath();
      const pointedItems = this.pointedItems
      path.clear();
      for( const point of pointedItems ) {
        path.push( new window.google.maps.LatLng( point.lat, point.lng));
      }
    },
  },
}
</script>

<style>

</style>