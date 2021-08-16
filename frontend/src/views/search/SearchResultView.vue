<template>
  <v-container class="search-result-tabs backColor">
    <!-- 뒤로가기 버튼 -->
    <v-btn
      @click="goBack"
      icon
      style="color:white"
    >
      <v-icon x-large>mdi-chevron-left</v-icon>
    </v-btn>
  
      <v-tabs
        @change="changeTabNum"
        v-model="$store.state.search.tab"
        background-color="#101423"
        rounded-pill
        dark
        grow
        class="mt-3;"
      >
        <v-tab
          v-for="item in items"
          :key="item"
          style="font-size:16px; color:#D2FDFF;"
          class="search-result-tab"
        >
          {{ item }}
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="$store.state.search.tab" >
          <v-tab-item>
            <v-card >
              <div v-if="searchedFollowRoutes.length">
                <RouteCard
                  v-for="feed in searchedFollowRoutes"
                  :key="feed.id"
                  :feed="feed"
                />
              </div>
              <div class="my-5" style="text-align:center" v-else >
                <h4>검색 결과가 없습니다. 다시 검색해볼까요?</h4>
              </div>
            </v-card>
          </v-tab-item>

          <v-tab-item>
            <v-card>
              <div v-if="searchedNonFollowRoutes.length">
                  <RouteCard
                    v-for="feed in searchedNonFollowRoutes"
                    :key="feed.id"
                    :feed="feed"
                  />
              </div> 
              <div class="my-5" style="text-align:center" v-else>
                <h4>검색 결과가 없습니다. 다시 검색해볼까요?</h4>
              </div>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
  </v-container>

</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import RouteCard from '@/components/common/RouteCard.vue'

export default {
  name: 'SearchResultView',
  components: {
    RouteCard
  },
  data () {
    return {
    items: ['친구들과 나의 루트', '지금 인기있는 루트',]
    }
  },
  computed: {
    ...mapGetters(['searchedFollowRoutes', 'searchedNonFollowRoutes', 'isLiked', 'isSaved', 'jwt', 'whatTag', 'withTag'])
  },
  methods: {
    ...mapActions(['setTabNum', 'fetchSearchedRoutes']),
    goBack() {
      this.$router.go(-1);
    },
    changeTabNum(event) {
      this.setTabNum(event)
    }
  },
  watch: {
    isLiked: function() {
      console.log('불려야돼')
      const data = {
        userId : this.jwt[2],
        access_token: this.jwt[0],
        param: {
          whatTag: this.whatTag,
          withTag: this.withTag,
          }
      }
      this.$store.dispatch('fetchSearchedRoutes', data)
    },
    isSaved: function() {
      console.log('얘도 불려야돼')
      const data = {
        userId : this.jwt[2],
        access_token: this.jwt[0],
        param: {
          whatTag: this.whatTag,
          withTag: this.withTag,
          }
      }
      this.$store.dispatch('fetchSearchedRoutes', data)
    }
  }
}
</script>

<style scoped>
.backColor {
  background-color: black;
}
.search-result-tabs {
  font-family: 'Do Hyeon', sans-serif;
}
.search-result-tab{
  color: #D2FDFF;
  background-color: #101423;
  border-radius: 10px; 
  
}
.result-div {
  background-color: #101423;
}
</style>