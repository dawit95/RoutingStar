import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

import accounts from './modules/accounts.js'
import profile from './modules/profile.js'
import routes from './modules/routes.js'
import images from './modules/images.js'

Vue.use(Vuex)

export default new Vuex.Store({
  // state: {
  // },
  // mutations: {
  // },
  // actions: {
  // },
  plugins: [
    createPersistedState],
    // {
  //     storage: window.sessionStorage,
  //   }),
  // ],
  modules: {
    accounts,
    profile,
    routes,
    images,
  }
})
