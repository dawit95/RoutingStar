// alarm.js
// // import axios from 'axios'
import { postBrowserToken } from '@/api/alarm.js'
// import accounts from "./accounts"

const state = {
  browserToken : '',
  alertMessage: "",
  likeMessage: "",
  storeMessage: '',
}


const getters = {
  getterbrowserToken(state) {
    return state.browserToken
  },
  alertMessage(state) {
    return state.alertMessage;
  },
  likeMessage(state) {
    return state.likeMessage;
  },
  storeMessage(state) {
    return state.storeMessage;
  }
}

const mutations= {
  SET_BROWSER_TOKEN(state, browserToken){
    state.browserToken = browserToken
    console.log('넘어오나?')
    console.log(browserToken)
  },
  SHOW_ALERT_MODAL(state) {
    state.alertModal = false;
    console.log(state.alertModal + "is state of alert modal!!");
  },
  SET_ALERT_MESSAGE(state, info) {
    state.alertMessage = info;
    setTimeout(() => {
      state.alertMessage = '';
    }, 3000);
  },
  SET_LIKE_MESSAGE(state, info) {
    state.likeMessage = info;
    setTimeout(() => {
      state.likeMessage = '';
    }, 3000);
  },
  SET_STORE_MESSAGE(state, info) {
    state.storeMessage = info;
    setTimeout(() => {
      state.storeMessage = '';
    }, 3000);
  }
}

const actions = {
  setBrowserToken({commit}, browserToken) {
    commit('SET_BROWSER_TOKEN', browserToken)
  },

  sendBrowerToken(payload) {
    console.log('여기 엑션스')
    console.log(payload.getters.jwt[2])
    const user_id = payload.getters.jwt[2]
    const browser_token = state.browserToken
    console.log(browser_token)
    // postBrowserToken(payload.getters.jwt[2], payload.state.browserToken,
    postBrowserToken(user_id, browser_token,
      (res) => {
        console.log(res)
      }, (error) => {
        console.log(error)
    })
  },

  showAlertModal({commit}, info) {
    console.log(info + "is message!!");
    commit('SET_ALERT_MESSAGE', info);
  },
  showLikeModal({commit}, info) {
    console.log(info + "is message!!!");
    commit('SET_LIKE_MESSAGE', info);
  },
  showStoreModal({commit}, info) {
    console.log(info + "is message!!!") 
    commit('SET_STORE_MESSAGE', info);
  }
}

export default {
  state, getters, mutations, actions
}