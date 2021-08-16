<template>
    <div class="d-flex revise-header" dark>

      <v-btn @click="goBack" icon class="pb-1">
        <v-icon color="white" x-large>mdi-chevron-left</v-icon>
      </v-btn>
      <v-spacer></v-spacer>


      <v-dialog
        v-model="dialog"
        persistent
        max-width="90%"
      >
        
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="button-font" color="#101423" dark right v-bind="attrs" v-on="on" @click="freezeMap">
            선택완료
          </v-btn>
        </template>
          
        <v-card>
          
          <!-- 썸네일 이미지 보여주는 란 (루트가 올라간) -->
          <div>
            <img v-if="tempThumbnail" class="card-image mx-auto" :src="tempThumbnail" alt="thumbnail image preview">
            <img v-else class="card-image mx-auto" src="../../assets/temp_thumbnail.jpg" alt="thumbnail image preview">
          </div>

          <!-- 설명 작성 란 -->
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    @change="createRouteDescription"
                    label="Description"
                    hint="25자 내외의 짧은 설명을 기록해주세요"
                    v-model="RouteDetailData.routeDescription"
                    :rules="rules"
                    counter="25">
                  </v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        
          <v-layout>
            <TagForm />
          </v-layout>
        
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="blue darken-1"
              text
              @click="onDialogClose"
            >
              Close
            </v-btn>
          </v-card-actions>
          
          <v-layout>
            <CreateRouteSuccessModal />
          </v-layout>
        
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
      rules: [v => v.length <= 25 || '25자 이상입니다'],
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

<style scoped>
.grayscale {
  filter: grayscale(100%);
}
.card-image {
  border-radius: 7px;
  margin: 4px;
  max-width: 80%;
}
.button-font {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 18px;
}
.revise-header {
  height: 56px;
  align-items: center;
}
</style>