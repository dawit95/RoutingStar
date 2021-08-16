import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import vuetify from './plugins/vuetify'


// 알람
// fcm import
import firebase from 'firebase/app'
import 'firebase/messaging'

const firebaseConfig = {
  apiKey: "AIzaSyDCY5XBXSuuv0nfRF-0u6MD279yBR3gmys",
  authDomain: "routingstar.firebaseapp.com",
  projectId: "routingstar",
  storageBucket: "routingstar.appspot.com",
  messagingSenderId: "491426382770",
  appId: "1:491426382770:web:e3dbd34248c419c77b5aab",
  measurementId: "G-XNC8P2FST1"
};

firebase.initializeApp(firebaseConfig)

if(firebase.messaging.isSupported()) {
  console.log("browser is okay")
  const messaging = firebase.messaging()
  messaging.usePublicVapidKey('BMMJoWJwKmI8qbxhiR2nTZQh4-jGV_PuhGXyZnopEW60Avhok_9kWShokK0OO_w6OLnJ90jcLKO-hh7hsOt4aFA')
  // 알림 수신을 위한 사용자 권한 요청
Notification.requestPermission()
.then((permission) => {
  console.log('permission ', permission)
  if (permission !== 'granted') {
    alert('알림을 허용해주세요')
  }
})

// TODO: Send token to server for send notification
messaging.getToken()
.then((res) => 
  store.dispatch('setBrowserToken', res))
  

// Handle received push notification at foreground
messaging.onMessage(payload => {
console.log(payload)
// alert(payload.data.message)
if(payload.data.message.includes("follow")) {
  store.dispatch('showAlertModal', payload.data.message);
} else if (payload.data.message.includes("피드좋아요")) {
  store.dispatch('showLikeModal', payload.data.message);
} else if(payload.data.message.includes("루트 저장")) {
  store.dispatch('showStoreModal', payload.data.message);
}
})
// 알람 끝

} else {
  console.log("this browser is not supported!!");
}

Vue.config.productionTip = false

// 카카오 인증코드 요청
// window.Kakao.init("0470967fe64eda9f5d9c8a5caede1e78")

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
