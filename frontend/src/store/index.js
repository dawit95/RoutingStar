import Vue from 'vue'
import Vuex from 'vuex'

import accounts from './modules/accounts.js'
import lines from './modules/lines.js'

Vue.use(Vuex)

export default new Vuex.Store({
  // state: {
  // },
  // mutations: {
  // },
  // actions: {
  // },
  modules: {
    accounts,
    profile,
    lines,
  }
})
