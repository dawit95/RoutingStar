<template>
  <v-container class="mt-4">
    <v-row class="tabs d-flex justify-center" role="tablist">
        <v-col cols="8" xs="8" class="d-flex justify-center">
          <input type="radio" name="tabs" id="tab1" checked />
          <label for="tab1" 
                role="tab" 
                aria-selected="true" 
                aria-controls="panel1" 
                tabindex="0">유저가 작성한 루트</label>
          <div id="tab-content1" 
              class="tab-content" 
              role="tabpanel" 
              aria-labelledby="description" 
              aria-hidden="false">
        <v-row class="content">
          <RouteThumbnail
            v-for="route in writtenRouteList"
            :key="route.id"
            :route="route"
          />
        </v-row>           
          </div>
        </v-col>
    </v-row>

  </v-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import RouteThumbnail from '@/components/common/RouteThumbnail.vue'

export default {
  name: 'MyRoutesTabs',
  components: { RouteThumbnail },
  computed: {
    ...mapGetters(['writtenRouteList'])
  },
  methods: {
    ...mapActions([''])
  },
}
</script>

<style scoped>
/* 면밀 검토 too */
* {
  margin: 0;
  padding: 0;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
.content {
    display: flex;
    flex-wrap: wrap;
    gap: 50px;
}
body {
  padding: 10px;
  text-align: left;
  color: #fff;
}

h1 {
  font-weight: normal;
  font-size: 40px;
  font-weight: normal;
  text-transform: uppercase;
  float: left;
  margin: 20px 0 100px 10px;
}
h1 span {
  font-size: 13px;
  display: block;
  padding-left: 4px;
}

.tabs {
  width: 100%;
  float: none;
  list-style: none;
  position: relative;
  text-align: left;
}
.tabs li {
  float: left;
  display: block;
}
.tabs input[type=radio] {
  position: absolute;
  top: 0;
  left: -9999px;
}
.tabs label {
  display: block;
  padding: 9px 25px;
  font-size: 14px;
  font-weight: bold;
  text-transform: uppercase;
  background: #C1C8E4;
  cursor: pointer;
  position: relative;
  top: 4px;
  -webkit-transition: all 0.2s ease-in-out;
  -moz-transition: all 0.2s ease-in-out;
  -o-transition: all 0.2s ease-in-out;
  transition: all 0.2s ease-in-out;
  border-radius: 10px;
}
.tabs label:hover {
  background: #C1C8E4;
}
.tabs .tab-content {
  z-index: 2;
  display: none;
  overflow: hidden;
  width: 90%;
  height: 400px;
  font-size: 17px;
  line-height: 25px;
  padding: 25px;
  position: absolute;
  top: 40px;
  left: 20px;
  background: #2A355D;
  color: #D2FDFF;
  border-radius: 10px;
}
.tabs [id^=tab]:checked + label {
  top: 0;
  padding-top: 17px;
  background: #2A355D;
  color: #D2FDFF;
}
.tabs [id^=tab]:checked ~ [id^=tab-content] {
  display: block;
}

</style>