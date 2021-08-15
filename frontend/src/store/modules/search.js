// store/modules/search.js

import { postSearhFollow, postSearhNonFollow } from '@/api/search.js'
// import accounts from './accounts'
// import router from '@/router'


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

// import axios from 'axios'
// actions는 Backend API와 통신하여 Data fetching과 같은 작업 수행
// 동기적인 작업 뿐 만 아니라 비동기적인 작업을 포함 가능
// 항상 Context가 인자로 넘어온다, 오직 mutation 매서드를 commit 호출해서 조작 가능
const actions = {

  fetchSearchedRoutes({ commit }, payload) {
    console.log('첫번째 부른다')
    console.log(payload)
    postSearhFollow(payload.param, payload.userId, payload.access_token, 
      (success) => {
        console.log('받아졌니?')
        console.log(success.data.success)
        commit('SET_SEARCHED_FOLLOW_ROUTES', success.data.success)
        
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