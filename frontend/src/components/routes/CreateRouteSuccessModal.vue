<template>
  <div class="text-center create-success-modal">
    <v-dialog v-model="dialog2" persistent width="800px" class="text-center mx-0">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon
          color="#F4976C"
          dark
          v-bind="attrs"
          v-on="on"
          @click="postPointImages"
          class="mb-2 detail-compelete-button"
        >
          <v-icon x-large>mdi-check-circle-outline</v-icon>
        </v-btn>
      </template>

      <v-card id="createRouteSuccessModal">
        <div>
          <CreateRouteCanvas :isCompleted="isCompleted"/>
        </div>
        
        <div class="text-center mb-9">
          <h3 class="datil-complete-text mb-2" style="color:#D2FDFF;">우리 동네의 별자리가</h3>
          <h3 class="datil-complete-text">새롭게 떠올랐습니다.</h3>
        </div>

        <v-divider></v-divider>

        <v-card-actions class="mt-2">
          <v-spacer></v-spacer>
          <v-btn
            color="#FBE8A6"
            text
            class="create-route-success-button"
            @click="onClickComplete"
          >
            Complete!
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import CreateRouteCanvas from '@/components/routes/CreateRouteCanvas.vue'

export default {
  name: 'CreateRouteSuccessModal',
  components: {
    CreateRouteCanvas
  },
  data () {
    return {
      dialog2: false,
      isCompleted: false,
    }
  },
  methods: {
    ...mapActions(['createRoute', 'postPointImages']),

    // CreateRoute to Png to S3 to axios 오류 해결하기 위해
    // 먼저 complete 버튼 클릭이 되면 props 보낸다
    onClickComplete() {
      this.isCompleted = true
      console.log('test')
    },
  }

}
</script>

<style scoped>
#createRouteSuccessModal {
  background-color: #2A355D;
}
.datil-complete-text {
  /* color: #D2FDFF; */
  color: #FBE8A6;
  font-family: 'Do Hyeon', sans-serif;
  font-weight: lighter;
  font-size: 20px;
}

.detail-compelete-button {
  position: absolute;
  right:    13px;
  bottom:   0px
}
.create-route-success-button {
  width: 80px;
  background: #101423;
  color: #FBE8A6;
  font-family: 'Do Hyeon', sans-serif;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3x ease-in-out;
  border-radius: 16px;
  filter: drop-shadow(0px 6px 6px rgba(0, 0, 0, 0.25));
}
</style>