<template>
    <div class="d-flex revise-header" dark>

      <v-btn @click="goBack" icon class="pb-1">
        <v-icon color="white" x-large>mdi-chevron-left</v-icon>
      </v-btn>
      <v-spacer></v-spacer>


      <v-dialog
        v-model="dialog"
        persistent
        class="mx-0"
      >
        
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="mr-3 post-detail-button-font" color="#101423" dark right v-bind="attrs" v-on="on" @click="freezeMap">
            선택완료
          </v-btn>
        </template>
          
        <v-card class="rounded-xl" color="#2A355D">
          <v-card-actions class="pb-0 pt-1">
            <v-btn
              icon
              color="#D2FDFF"
              @click="onDialogClose"
              class="mt-1"
            >
              <v-icon x-large>mdi-chevron-left</v-icon>
            </v-btn>
          </v-card-actions>
          
          <!-- 썸네일 이미지 보여주는 란 (루트가 올라간) -->
          <div>
            <img v-if="tempThumbnail" class="post-detail-card-image mx-auto" :src="tempThumbnail" alt="thumbnail image preview">
            <img v-else class="post-detail-card-image mx-auto" src="../../assets/temp_thumbnail.jpg" alt="thumbnail image preview">
          </div>

          <!-- 설명 작성 란 -->
          <v-container>
            <h3 class="ml-5 post-detail-title-font">Route Description</h3>
            <v-textarea
              @change="createRouteDescription"
              hint="루트에 대해 소개해주세요!"
              rows="3"
              counter
              :rules="rules"
              row-height="30"
              color="#D2FDFF"
              class="mt-0 pa-4 mx-4 post-detail-text-area"
              dark
              v-model="RouteDetailData.routeDescription"
            ></v-textarea>

          </v-container>

          <v-container class="pb-9">
            <TagForm />
          </v-container>
          
          <div class="Create-Route-Success-Modal">
            <CreateRouteSuccessModal />
          </div>
        
        </v-card>
      </v-dialog>
    </div>
</template>

<script>
import TagForm from '@/components/routes/TagForm.vue'
import CreateRouteSuccessModal from '@/components/routes/CreateRouteSuccessModal.vue'
import { mapActions } from 'vuex'
export default {
  name: 'ReviseOthersHeader',
  components: {
    TagForm,
    CreateRouteSuccessModal,
  },
  props: {
    tempThumbnail: {
      type: String
    },
  },
  data() {
    return {
      // 모달 띄우기 위한 조건
      dialog: false,
      // 모달에 작성되는 데이터
      RouteDetailData: {
        routeDescription: '',
      },
      // 모달에 작성되는 데이터의 조건
      rules: [v => v.length > 0 || '너무 짧아요!!', 
      v=> v.length <= 300 || '너무 길어요!!'],
      isHovering: false,
    }
  },
  methods: {
    ...mapActions(['createRouteDescription', 'refreshPlaces']),
    goBack() {
      this.$router.go(-1)
      this.refreshPlaces([])
    },
    // 모달이 열리면 map이 freeze된다 (그림 그려서 좌표 찾기 위함)
    freezeMap(event) {
      this.$emit('freeze-map', event)
    },
    recoverFreezeMap(event) {
      this.$emit('recover-freeze-map', event)
    },
    onDialogClose() {
      this.dialog = false
      this.recoverFreezeMap()
    },
  }
}
</script>

<style>
.v-toolbar__content, .v-toolbar__extension {
  padding-right: 0px;
  padding-left: 8px;
}
.post-detail-card-image {
  width: 160px; height: 160px;
  border-radius: 50%;
  display: block;
}
.post-detail-button-font {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 18px;
}
.revise-header {
  height: 56px;
  align-items: center;
}
.post-detail-text-area {
  font-family: 'Nanum Gothic Coding', monospace;
  font-size: 14px;
  border-radius: 20px;
  background: #2A355D;
  box-shadow: inset 5px 5px 11px #1a213a,
              inset -5px -5px 11px #3a4980;
}
.theme--dark.v-input input, .theme--dark.v-input textarea {
    color: #D2FDFF;
}
.post-detail-title-font {
  font-family: 'Do Hyeon', sans-serif;
  color: #D2FDFF;
  font-weight: lighter;
}
.post-detail-dialog {
  border-radius: 30px;
}
.Create-Route-Success-Modal{
  position: relative;
}
</style>