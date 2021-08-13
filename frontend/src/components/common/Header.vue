<template>
  <div>
    <v-app-bar color="#0E111E" dense height="50px" class="moveToUp">
      <v-avatar @click="$router.push({name: 'HomeView'})"><v-img class="mx-auto" max-height="200" max-width="400" :src="require('../../assets/LogoWithoutWord.png')" /></v-avatar>
      <v-spacer></v-spacer>
      <v-btn class="moveToRight" color="grey" x-large icon><v-icon>mdi-bell</v-icon></v-btn>
      <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ml-3 pr-3" color="grey" dark v-bind="attrs" v-on="on" x-large icon><v-icon>mdi-account</v-icon></v-btn>
      </template>
      <v-list>
        <v-list-item
          value="true"
          v-for="(item, index) in items"
          :key="index"
          @click="selectSection(item)"
          >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
      </v-menu>
    </v-app-bar>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
 name: 'TempHeader', 
 data: () => ({
    items: [
      { title: 'MyPage' },
      { title: 'Logout' },
    ],
  }),
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
        this.$store.state.accounts.feeds = ''
        localStorage.removeItem('vuex')
        this.$router.push('/')
    }
  }
  }
}
</script>

<style>
.moveToRight {
  left: 20px;
}
.moveToUp {
  top: 1px;
  margin-bottom: -1px;
}
</style>