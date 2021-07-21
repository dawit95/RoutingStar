import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false

// 카카오 인증코드 요청
window.Kakao.init("0470967fe64eda9f5d9c8a5caede1e78")

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
