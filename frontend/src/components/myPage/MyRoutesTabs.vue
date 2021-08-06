<template>
<div class="menu" @click="writeOrSave">
    <!-- <v-row class="d-flex justify-center"> -->
      <!-- 버튼클릭참조: https://ts2ree.tistory.com/6?category=396091 -->
      <v-btn class="menu-link" id="1">Normal</v-btn>
      <v-btn class="menu-link" id="2">Primary</v-btn>
  

    
    <v-container>
      <v-row dense>
        <v-col v-for="savedRoute in savedRotues" :key="savedRoute.id">
          <v-card flat class="text-xs-center ma-2">Hello</v-card>
        </v-col>

      </v-row>
    </v-container>
  
</div>
</template>

<script>
export default {
  name: 'MyRoutes',
  data() {
    return {
      currentMenu: {},
      // 나와 관련된 routes들을 저장하고 위에서 로직을 통해 thumbnail 나타내주기
      // mapgetters filter 기능으로 나와 관련된 데이터 가져오기
      // 참조: https://www.youtube.com/watch?v=pUwyeWLOnP4
      savedRoutes: {},

    }
  },
  methods: {
    writeOrSave(e) {        
    if (this.currentMenu.classList){ 
         this.currentMenu.classList.remove('menu-active');
     }
    e.target.classList.add('menu-active');
    this.currentMenu = e.target;
    console.log(e.target.id)

    }
  }

}
</script>

<style>
  .menu-active {color: green;}

</style>