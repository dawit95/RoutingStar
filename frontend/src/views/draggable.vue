<template>
  <v-card>
    <v-toolbar dense color="success" dark flat>
      드래그 테스트
    </v-toolbar>
    <v-card-text>
      <draggable v-model="headers">
        <v-chip
          v-for="item in headers" :key="item.value"
          class="ma-1"
          @click="item.selected = !item.selected"
          draggable
          >
          <v-icon v-if="item.selected">mdi-check</v-icon>
          {{item.text}}
        </v-chip>
      </draggable>
      
    </v-card-text>
    <v-data-table
    :headers="selectedHeaders"
    :items="items"
    >
    </v-data-table>
  </v-card>
</template>

<script>
import draggable from 'vuedraggable'

export default {
  name: 'Draggable',
  components: {
      draggable
  },
  data () {
    return {
      headers: [
        { value: 'no', text: '번호', selected: true},
        { value: 'title', text: '제목', selected: true},
        { value: 'createdAt', text: '생성일', selected: true},
        { value: 'UpdatedAt', text: '수정일', selected: false},
        { value: 'readCount', text: '읽음', selected: true},
        { value: 'commentCount', text: '댓글', selected: false},
      ],
      items: []
    }
  },
  computed: {
    selectedHeaders () {
      return this.headers.filter(v => v.selected)
    }
  }
}
</script>

<style>

</style>