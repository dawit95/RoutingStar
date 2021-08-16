<template>
  <div>
    <!-- 태그는 번호로 DB에 저장되는 것으로 논의 -->
    <v-container>
      <H3>누구랑??</H3>
      <div v-for="withTag in withTagObj" :key="withTag.idx" class="d-inline" style="flex">
        <button 
          class="button mx-3" 
          :class="{chooseButton: withTag.isSelcted}" 
          @click="addWithTag(withTag.tagnum); addWithTagButtonClass(withTag.idx);"
        >
          <span>
            {{ withTag.tag }}
          </span>
        </button>
      </div>
    </v-container>
    <v-container>
      <H3>무엇을???</H3>
      <div v-for="whatTag in whatTagObj" :key="whatTag.idx" class="d-inline" style="flex">
        <button 
          class="button mx-3" 
          :class="{chooseButton: whatTag.isSelcted}" 
          @click="addWhatTag(whatTag.tagnum); addWhatTagButtonClass(whatTag.idx);"
        >
          <span>
            {{ whatTag.tag }}
          </span>
        </button>
      </div>
    </v-container>
    <div>
      <button @click="onClickSearch">Search!!!</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'TagForm',
  data() {
    return {
      // 우리가 가지고 있는
      whatTagObj: [
        { idx: 0, tag: '식사하기',  tagnum: 1, isSelcted: false },
        { idx: 1, tag: '산책하기',  tagnum: 2, isSelcted: false }, 
        { idx: 2, tag: '운동하기',  tagnum: 3, isSelcted: false }, 
        { idx: 3, tag: '여행하기',  tagnum: 4, isSelcted: false }, 
        { idx: 4, tag: '힐링하기',  tagnum: 5, isSelcted: false }, 
        { idx: 5, tag: '체험하기',  tagnum: 6, isSelcted: false }, 
        { idx: 6, tag: '학습하기',  tagnum: 7, isSelcted: false },
      ],
      withTagObj: [
        { idx: 0, tag: '나혼자', tagnum: 1, isSelcted: false },
        { idx: 1, tag: '친구', tagnum: 2, isSelcted: false },
        { idx: 2, tag: '가족', tagnum: 3, isSelcted: false },
        { idx: 3, tag: '반려동물', tagnum: 4, isSelcted: false },
        { idx: 4, tag: '연인', tagnum: 5, isSelcted: false },
        { idx: 5, tag: '아이', tagnum: 6, isSelcted: false },
        { idx: 6, tag: '낯선사람', tagnum: 7, isSelcted: false },
      ],
      whatTag: [],
      withTag: [],
    }
  },
  computed: {
    ...mapGetters(['jwt'])
  },
  methods: {
    ...mapActions(['setWhatTag', 'setWithTag', 'fetchSearchedRoutes',]),

    // 태그 데이터에 있으면 삭제 없으면 추가
    addWhatTag(tagnum) {
      if (this.whatTag.includes(tagnum)) {
        const idx = this.whatTag.indexOf(tagnum)
        this.whatTag.splice(idx, 1)
      } else {
        this.whatTag.push(tagnum)
      }
      console.log(this.whatTag)
      this.setWhatTag(this.whatTag)
    },
    addWithTag(tagnum) {
      if (this.withTag.includes(tagnum)) {
        const idx = this.withTag.indexOf(tagnum)
        this.withTag.splice(idx, 1)
      } else {
        this.withTag.push(tagnum)
      }
      console.log(this.withTag)
      this.setWithTag(this.withTag)
    },
    addWhatTagButtonClass(idx) {
      this.whatTagObj[idx].isSelcted = !this.whatTagObj[idx].isSelcted
    },
    addWithTagButtonClass(idx) {
      this.withTagObj[idx].isSelcted = !this.withTagObj[idx].isSelcted
    },
    onClickSearch() {
      if (this.withTag && this.whatTag) {
        console.log('지금부터 서치를 시작')
        const data = {
          userId : this.jwt[2],
          access_token: this.jwt[0],
          param: {
            whatTag: this.whatTag,
            withTag: this.withTag
          }
        }
        console.log(data.param)
        this.fetchSearchedRoutes(data)
        this.$router.push({ name: 'SearchResultView' })

      } else {
        console.log('없어요!! 막아주세요!! 알람!!')
      }
    }
  },
  // watch: {
  //   searchedFollowRoutes: function() {
  //       this.$router.push({ name: 'SearchResultView' })
  //   }
  // },
}
</script>

<style>

</style>