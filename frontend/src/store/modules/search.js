// store/modules/search.js

import { postSearhFollow, postSearhNonFollow } from '@/api/search.js'


const state = {
  searchedFollowRoutes: [],
  searchedNonFollowRoutes: [],
  tab: 0,
}

const getters = {
  searchedFollowRoutes(state) {
    return state.searchedFollowRoutes
  },
  searchedNonFollowRoutes(state) {
    return state.searchedNonFollowRoutes
  },
  tab(state) {
    return state.tab
  }
}

const mutations= {
  SET_SEARCHED_FOLLOW_ROUTES(state, searchResults) {
    state.searchedFollowRoutes = searchResults
  },
  SET_SEARCHED_NON_FOLLOW_ROUTES(state, searchResults) {
    state.searchedNonFollowRoutes = searchResults
  },
  SET_TAB(state, num) {
    state.tab = num
  }
}

const actions = {
  // 두가지 결과를 한번에 가져올 때
  fetchSearchedRoutes({ dispatch }, payload) {
    console.log('첫번째 부른다')
    console.log(payload)
    dispatch('fetchSearchedFollowRoutes', payload)
    dispatch('fetchSearchedNonFollowRoutes', payload)
  },  
  fetchSearchedFollowRoutes({ commit }, payload) {
    postSearhFollow(payload.param, payload.userId, payload.access_token, 
      (success) => {
        console.log('받아졌니?')
        console.log(success.data.success)
        commit('SET_SEARCHED_FOLLOW_ROUTES', success.data.success)
      }, (error) => {
        console.log(error)
      })
  },
  fetchSearchedNonFollowRoutes({ commit }, payload) {
    // 성공하면 두번째 요청도 보내버려
    console.log('두번째 부른다')
    console.log(payload)
    postSearhNonFollow(payload.param, payload.userId, payload.access_token, 
      (success) => {
        console.log('받아졌니?')
        console.log(success.data.success)
        commit('SET_SEARCHED_NON_FOLLOW_ROUTES', success.data.success)
        // router.push({ name: 'SearchResultView' })
      }, (error) => {
        console.log(error)
    })
  },
  setTabNum({commit}, num) {
    commit('SET_TAB', num)
  }
}

export default {
  state, getters, mutations, actions
}