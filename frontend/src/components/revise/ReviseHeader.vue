<template>
    <div class="d-flex revise-header" dark>
      <v-btn @click="goBack" icon class="pb-1">
        <v-icon color="white" x-large>mdi-chevron-left</v-icon>
      </v-btn>
      <v-spacer></v-spacer>

      <!-- <v-btn @click="handleClick2" color="#101423" right dark>선택완료</v-btn> -->

      <v-dialog
            v-model="dialog"
            persistent
            max-width="80%"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn class="button-font" color="#101423" dark right v-bind="attrs" v-on="on" @click="freezeMap">
                선택완료
              </v-btn>
            </template>
            
            <v-card>
              <!-- <v-card-title>
                <span class="text-h3">Route Detail</span>
              </v-card-title> -->
              
              <!-- 썸네일 이미지 보여주는 란 (루트가 올라간) -->
              <!-- 현재 임시 이미지로 대체되어 있음 -->
              <!-- <v-container>
                <img class="card-image" :class="{grayscale:!isHovering}" @mouseover="isHovering = true" @mouseout="isHovering = false" :src="tempThumbnail" alt="...">
              </v-container> -->
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
                <UpdateTagForm :reswhatTag="whatTag" :reswithTag="withTag"/>
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
                <CreateRouteSuccessModal/>
              </v-layout>
            
            </v-card>
          </v-dialog>
    </div>
</template>

<script>
import UpdateTagForm from '@/components/revise/UpdateTagForm.vue'
import CreateRouteSuccessModal from '@/components/routes/CreateRouteSuccessModal.vue'
import { mapActions } from 'vuex'
export default {
  name: 'ReviseHeader',
  components: {
    UpdateTagForm,
    CreateRouteSuccessModal,
  },
  props: {
    tempThumbnail: {
      type: String
    },
    routeDescription: {
      type: String
    },
    whatTag: {
      type: Array
    },
    withTag: {
      type: Array
    }
  },
  data() {
    return {
      // 모달 띄우기 위한 조건
      dialog: false,
      // 모달에 작성되는 데이터
      RouteDetailData: {
        routeDescription: this.routeDescription,
      },
      // 모달에 작성되는 데이터의 조건
      rules: [v => v.length <= 25 || '25자 이상입니다'],
      isHovering: false,
    }
  },
  methods: {
    ...mapActions(['createRouteDescription']),
    goBack() {
      this.$router.go(-1)
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
  },
  created() {
    if (this.routeDescription) {
      this.createRouteDescription(this.routeDescription)
    }
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