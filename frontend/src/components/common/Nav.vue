<template>
  <!-- <v-card> -->
  <v-bottom-navigation
    fixed
    grow
    color="#FBE8A6"
    dark
    background-color="#101423"
    class="pa-1"
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
export default {
  name: "Nav",
  data() {
    return {
    };
  },
  watch: {
    $route(to, from) {
      console.log(from.name + "에서 " + to.name + "으로 온 경우");
      if(from.name != null) {
        const fromObject = document.getElementById(from.name);
        if(fromObject != null) {
          fromObject.classList.add("v-btn—inactive");
          fromObject.classList.remove("v-btn—active");
        }
      }

      const toObject = document.getElementById(to.name);

      if(toObject != null) {
        document.getElementById(to.name).classList.remove("v-btn—inactive");
        document.getElementById(to.name).classList.add("v-btn—active");
      }
    }
  },
  computed: {},
  methods: {
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
        this.$router.push({ name: "HomeView" }).catch(() => {});
    },
  },
};
</script>

<style>
.background-color {
  background-color: #101423;
}

.theme—dark.v-btn:hover::before {
    opacity: 0.00;
}
</style>