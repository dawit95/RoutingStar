<template>
  <!-- <v-card class="overflow-hidden mx-auto"> -->
  <v-bottom-navigation
    app grow dark
    background-color="#101423"
    color="#FBE8A6"
  >
    <v-btn id="SearchView" @click="moveToSearch">
      <span>Search</span>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn id="PostRouteView" @click="moveToPost">
      <span>Post</span>
      <v-icon>mdi-star-circle-outline</v-icon>
    </v-btn>

    <v-btn id="HomeView" @click="moveToHome">
      <span>Home</span>
      <v-icon>mdi-home</v-icon>
    </v-btn>
  </v-bottom-navigation>

  <!-- <v-sheet align-self-end>
      <v-container class="background-color" style="width: 100%; height: 600px;">
      </v-container>
    </v-sheet> -->
  <!-- </v-card> -->
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: "Nav",
  data() {
    return {
    };
  },
  watch: {
    $route(to, from) {
      console.log(from.name + "에서 " + to.name + "으로 온 경우");
      if(to.name != from.name && from.name != null) {
        const fromObject = document.getElementById(from.name);
        if(fromObject != null) {
          fromObject.classList.remove("v-btn--active");
        }
      }

      const toObject = document.getElementById(to.name);

      if(toObject != null) {
        toObject.classList.add("v-btn--active");
      }

      if (from.name === 'PostRouteView' && to.name !== 'PostRouteView' && this.postingCheck === false) {
        alert('작성중인 내용이 삭제됩니다.')
        this.refreshPlaces([])
      }
      if (from.name !== 'PostRouteView' && to.name === 'PostRouteView') {
        this.refreshPlaces([])
      }
      // detail에서 tab으로 돌아가면 기존의 tab상태 유지
      if (from.name === 'RouteDetailView' && to.name === 'SearchResultView') {
        this.setTabNum(this.tab)
      }

    }
  },
  computed: {
    ...mapGetters(['postingCheck', 'tab'])
  },
  methods: {
    ...mapActions(['refreshPlaces', 'setTabNum']),
    moveToSearch() {
      if (this.$route.name != "SearchView") {
        this.$router.push({ name: "SearchView" });
      }
    },
    moveToPost() {
      if (this.$route.name != "PostRouteView") {
        this.$router.push({ name: "PostRouteView" });
      }
    },
    moveToHome() {
      /*
      if (this.$route.name != "HomeView") {
        this.$router.push({ name: "HomeView" }).catch(() => {});
      }
      */
      document.documentElement.webkitRequestFullscreen();
    },
  },
};
</script>

<style scoped>
.background-color {
  background-color: #101423;
}

.theme—dark.v-btn—active:hover::before, .theme—dark.v-btn—active::before {
    opacity: 0.00;
}

.theme—dark.v-btn:hover::before {
    opacity: 0.00;
}

</style>