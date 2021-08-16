<template>
  <v-app-bar app color="#0E111E" height="60px" class="moveToUp">
    <v-avatar class="" @click="moveToHome"><v-img class="mx-auto" max-height="200" max-width="400" :src="require('../../assets/LogoWithoutWord.png')" /></v-avatar>
    <v-spacer></v-spacer>
    <v-btn class="moveToRight" color="grey" x-large icon>
      <v-icon>mdi-bell</v-icon>
    </v-btn>
    <tool-tip-dialog/>
    <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="account-btn" color="grey" dark v-bind="attrs" v-on="on" x-large icon>
        <v-icon>mdi-account</v-icon>
      </v-btn>
    </template>
    <v-list color="black">
      <v-list-item-group
        active-class="bg-active"
        white
      >
      <v-list-item
        value="true"
        v-for="(item, index) in items"
        :key="index"
        @click="selectSection(item)"
        >
          <v-list-item-title color="red">{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import ToolTipDialog from './ToolTipDialog.vue';

export default {
 name: 'TempHeader', 
 data: () => ({
    items: [
      { title: 'MyPage' },
      { title: 'Logout' },
    ],
  }),
  components: {
    ToolTipDialog
  },
  computed: {
    ...mapGetters(['jwt'])
  },
  methods: {
    ...mapActions(['enterUserprofile']),

    selectSection(item) {
      switch (item.title) {
        case 'MyPage':
          console.log('MyPage')
          this.enterUserprofile({ 
            userId: this.jwt[2],
            access_token: this.jwt[0],
            jwtId: this.jwt[2]
          })
          break
        case 'Logout':
          console.log('Logout')
          this.$store.state.accounts.jwt = []
          this.$store.state.home.feeds = ''
          localStorage.removeItem('vuex')
          this.$router.push('/')
      }
    },
    moveToHome() {
      if (this.$route.name != "HomeView") {
        this.$router.push({ name: "HomeView" }).catch(() => {});
      }
    },

    
  }
}
</script>

<style scoped>
.moveToRight {
  left: 20px;
}

.bg-active {
  background-color: black;
  color : white !important;
}
/* .moveToUp {
  top: 1px;
  margin-bottom: -1px;
} */
/* .account-btn {
  background-color: transparent;
  border-radius: 0;
  box-shadow: none;
  flex: 0 1 auto;
  font-size: 0.75rem;
  height: inherit;
  max-width: 168px;
  min-width: 80px;
  position: relative;
  text-transform: none;


  color: inherit;
  display: flex;

  line-height: normal;
  position: relative;
  transition: inherit;
  transition-property: opacity;
} */
</style>