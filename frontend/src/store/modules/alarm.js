// alarm.js
// // import axios from 'axios'
import { postBrowserToken } from '@/api/alarm.js'
// import accounts from "./accounts"

const state = {
  browserToken : ''
}


const getters = {
  getterbrowserToken(state) {
    return state.browserToken
  },

}

const mutations= {
  SET_BROWSER_TOKEN(state, browserToken){
    state.browserToken = browserToken
    console.log('넘어오나?')
    console.log(browserToken)
  },
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
  }
}

export default {
  state, getters, mutations, actions
}