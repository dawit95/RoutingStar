<template>
  <v-container class="pt-0 px-0">
    <h3 class="ml-5 post-detail-title-font">Tags</h3>
    <div class="tag-box mx-4">
      <v-container class="tag-container">
        <p class="tag-title-font ml-3 mb-0">누구와 함께하는 루트인가요?</p>
        <div v-for="withTag in withTagObj" :key="withTag.idx" class="d-inline" style="flex">
          <button 
            class="tag-button mx-3" 
            :class="{chooseButton: withTag.isSelcted}"
            @click="addWithTag(withTag.tagnum); addWithTagButtonClass(withTag.idx);"
          >
            <span>
              {{ withTag.tag }}
            </span>
          </button>
        </div>
      </v-container>
      
      <v-container class="tag-container mb-1">
        <p class="tag-title-font ml-3 mb-0">어떤 것을 할수있는 루트인가요?</p>
        <div v-for="whatTag in whatTagObj" :key="whatTag.idx" class="d-inline" style="flex">
          <button 
            class="tag-button mx-3" 
            :class="{chooseButton: whatTag.isSelcted}"
            @click="addWhatTag(whatTag.tagnum); addWhatTagButtonClass(whatTag.idx);"
          >
            <span>
              {{ whatTag.tag }}
            </span>
          </button>
        </div>
      </v-container>
    </div>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'

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
  methods: {
    ...mapActions(['setWhatTag', 'setWithTag']),

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
  },
}
</script>

<style>
.tag-title-font{
  font-family: 'Do Hyeon', sans-serif;
  font-weight: 100;
  color: #FBE8A6;
  font-size: 15px;

}
.tag-box {
  border-radius: 20px;
  background: #2A355D;
  box-shadow: inset 5px 5px 11px #1a213a,
              inset -5px -5px 11px #3a4980;
}
.tag-button {
  width: 60px;
  height: 23px;
  background: #C1C8E4;
  border-radius: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3x ease-in-out;
  filter: drop-shadow(0px 6px 6px rgba(0, 0, 0, 0.25));
  margin-top: 6px;
}
.tag-button span {
  line-height: 10px;
  font-size: 13px;
  font-weight: bold;
  color: #303C6C;
  font-family: 'Do Hyeon', sans-serif;
}
.chooseButton {
  width: 60px;
  height: 23px;
  background: #B4DFE5;
  border-radius: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3x ease-in-out;
  box-shadow: inset 0px 6px 6px rgba(0, 0, 0, 0.25);
  animation: jello-horizontal .9s both;

}
.search-button {
  width: 90px;
  background: #101423;
  color: #FBE8A6;
  font-family: 'Do Hyeon', sans-serif;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3x ease-in-out;
  border-radius: 16px;
  filter: drop-shadow(0px 6px 6px rgba(0, 0, 0, 0.25));
}
@keyframes jello-horizontal {
  0% {
    transform: scale3d(1, 1, 1);
  }
  30% {
    transform: scale3d(1.25, 0.75, 1);
  }
  40% {
    transform: scale3d(0.75, 1.25, 1);
  }
  50% {
    transform: scale3d(1.15, 0.85, 1);
  }
  65% {
    transform: scale3d(0.95, 1.05, 1);
  }
  75% {
    transform: scale3d(1.05, 0.95, 1);
  }
  100% {
    transform: scale3d(1, 1, 1);
  }
}

</style>