<template>
  <div>
     <div class="text-center">
      <v-dialog v-model="dialog2" persistent width="800">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="blue darken-1"
            dark
            v-bind="attrs"
            v-on="on"
            @click="postPointImages"
          >
            Complete!
          </v-btn>
        </template>

        <v-card id="createRouteSuccessModal">
          <v-card-title class="text-h5 textColor">
            완성 시 나오는 페이지 입니다.
          </v-card-title>
          
          <v-layout>
            <CreateRouteCanvas :isCompleted="isCompleted"/>
          </v-layout>
          
          <v-card-text>
            <p class="textColor">
              당신만의 루팅스타가 생성 완료되었습니다.
            </p>
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="onClickComplete"
            >
              Complete!
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
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

<style>
#createRouteSuccessModal {
  background-color: #101423;
}

.textColor {
  color: #D2FDFF;
}

</style>