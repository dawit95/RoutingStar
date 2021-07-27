<template>
  <div>
    <h1>REVIEW DETAIL PAGE</h1>
    <v-container>
        <v-row justify="center">
          <v-dialog
            v-model="dialog"
            persistent
            max-width="80%"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark v-bind="attrs" v-on="on">
                NEXT
              </v-btn>
            </template>
            
            <v-card>
              <v-card-title>
                <span class="text-h3">Route Detail</span>
              </v-card-title>
              
              <v-container>
                <img class="card-image" :class="{grayscale:!isHovering}" @mouseover="isHovering = true" @mouseout="isHovering = false" src="@/assets/temp_image_for_test.png" alt="...">
              </v-container>


              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field 
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
                <TagForm/>
              </v-layout>
            
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="dialog = false"
                >
                  Close
                </v-btn>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="dialog = false"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
    </v-container>
  </div>
</template>

<script>
import TagForm from '@/components/routes/TagForm.vue'

export default {
  name: 'PostRouteDetailModal',
  components: {
    TagForm
  },
  data() {
    return {
      // 모달 띄우기 위한 조건
      dialog: false,
      // 모달에 작성되는 데이터
      RouteDetailData: {
        routeDiscription: '',
      },
      // 모달에 작성되는 데이터의 조건
      rules: [v => v.length <= 25 || '25자 이상입니다'],
      isHovering: false,
    }
  },
  computed: {
    // defaultUrl () {
    //   return "@/assets/temp_image_for_test.png"
    // }
  },
}
</script>

<style>
 .grayscale {
    filter: grayscale(100%);
  }
  .card-image {
  border-radius: 7px;
  margin: 4px;
  max-width: 80%;
  }

</style>