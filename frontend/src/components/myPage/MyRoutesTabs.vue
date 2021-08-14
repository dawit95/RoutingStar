<template>
  <div class="mt-4">
    <ul class="tabs" role="tablist">
      <li>
        <input type="radio" name="tabs" id="tab1" checked />
          <label for="tab1" role="tab" aria-selected="true" aria-controls="panel1" tabindex="0">내가 작성한 루트</label>
          <div id="tab-content1" class="tab-content" role="tabpanel" aria-labelledby="description" aria-hidden="false">
            <div>
              <RouteThumbnail
                v-for="route in writtenRouteList"
                :key="route.id"
                :route="route"
              />
            </div>           
          </div>
      </li>
      
      <li>
        <input type="radio" name="tabs" id="tab2" />
          <label for="tab2" role="tab" aria-selected="false" aria-controls="panel2" tabindex="0">내가 저장한 루트</label>
          <div id="tab-content2" class="tab-content" role="tabpanel" aria-labelledby="specification" aria-hidden="true">
          <div>
            <RouteThumbnail
              v-for="route in savedRouteList"
              :key="route.id"
              :route="route"
            />
          </div>
          </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import RouteThumbnail from '@/components/common/RouteThumbnail.vue'

export default {
  name: 'MyRoutesTabs',
  components: { RouteThumbnail },
  computed: {
    ...mapGetters(['jwt', 'userInfo', 'writtenRouteList', 'savedRouteList'])
  },
  methods: {
    ...mapActions(['fetchWrittenRouteList', 'fetchSavedRouteList' ])
  },
}
</script>

<style scoped>
/* 얘도 면밀히 검토 필요 */
* {
  margin: 0;
  padding: 0;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
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
  padding: 9px 21px;
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