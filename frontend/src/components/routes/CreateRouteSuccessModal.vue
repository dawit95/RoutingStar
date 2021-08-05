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
          >
            Complete!
          </v-btn>
        </template>

        <v-card>
          <v-card-title class="text-h5 grey lighten-2">
            완성 시 나오는 페이지 입니다.
          </v-card-title>
          
          <v-layout>
            <CreateRouteCanvas/>
          </v-layout>
          
          <v-card-text>
            당신만의 루팅스타가 생성 완료되었습니다. 
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="createRoute"
            >
              Check!
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
    }
  },
  methods: {
    ...mapActions(['createRoute']),
  }

}
</script>

<style>


</style>