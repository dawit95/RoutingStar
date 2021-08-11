<template>
  <v-container>
    <div class="mt-7">
      <button style="color:white" @click="goBack">뒤로가기</button>
    </div>
    <v-card class="followTabs" dark>
      <v-tabs
        v-model="tab"
        background-color="transparent"
        grow
      >
        <v-tab
          v-for="item in items"
          :key="item"
        >
          {{ item }}
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">

        <v-tab-item>
          <v-card>
            <div v-if="searchedFollowRoutes.length">
                <RouteCard
                  v-for="feed in searchedFollowRoutes"
                  :key="feed.id"
                  :feed="feed"
                />
            </div>
            <div v-else >검색 결과가 없습니다. 다시 검색해볼까요?</div>
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
            <div v-else >검색 결과가 없습니다. 다시 검색해볼까요?</div>
          </v-card>
        </v-tab-item>
      
      </v-tabs-items>
    </v-card>
  </v-container>

</template>

<script>
import { mapGetters } from 'vuex'
import RouteCard from '@/components/common/RouteCard.vue'

export default {
  name: 'SearchResultView',
  components: {
    RouteCard
  },
  data () {
    return {
    tab: null,
    items: ['친구들과 나의 루트', '지금 인기있는 루트',]
    }
  },
  computed: {
    ...mapGetters(['searchedFollowRoutes', 'searchedNonFollowRoutes'])
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  },
}
</script>

<style>

</style>