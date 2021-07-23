<template>
  <v-flex xs12>
    <v-list
      outlined
      v-for="pointItem in pointedItems"
      :key="pointItem.id"
    >
      <v-list-item outlined ma-0 pa-0>
        <v-list-item-content>
          <v-file-input label="첨부파일"></v-file-input>
          <v-textarea label="장소에대한 짧은설명" rows="1" prepend-icon="mdi-comment"
          ></v-textarea>
          <v-btn>썸네일 활용유무(선택시 이미지 썸네일로 적용)</v-btn>
          {{ pointItem.lat }} // {{ pointItem.lng }} // {{ pointItem.pk }}
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-flex>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'MapPointList',
  computed: {
    ...mapGetters(['pointedItems'])
  }
}
</script>

<style>

</style>