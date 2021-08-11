<template>
    <v-app-bar class="pa-5"
      app
      color="#fcb69f"
      dark
      src="https://picsum.photos/1920/1080?random"
    >
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(19,84,122,.5), rgba(128,208,199,.8)"
        ></v-img>
      </template>

      <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->

      <v-app-bar-title>LOGO</v-app-bar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-bell</v-icon>
      </v-btn>

      <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
          icon
        >        
        <v-icon>mdi-account</v-icon>
          </v-btn>
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

    <!-- <v-sheet
      id="scrolling-techniques-2"
      class="overflow-y-auto"
      max-height="600"
    >
      <v-container style="height: 1000px;"></v-container>
    </v-sheet> -->

</template>


<script>


  export default {
    name: 'Header',
    data: () => ({
      items: [
        { title: 'MyPage' },
        { title: 'Click Me 1' },
        { title: 'Logout' },
      ],
    }),
    methods: {
      selectSection(item) {
      switch (item.title) {
        case 'MyPage':
          console.log('MyPage')
          this.$router.push({ name: 'MyPageView' })
          break
        case 'Click Me 1':
          console.log('Click ME')
          break
          // 토큰 정보 제거 이후에 로그인창으로 이동
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