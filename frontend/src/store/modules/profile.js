// profile.js 
import { getUserInfoByUserId } from '@/api/accounts.js'
import { follow, getFollowList } from '@/api/follow.js'
import { getWrittenRoute, getSavedRoute } from '@/api/routes.js'
// import accounts from './accounts'
// import axios from 'axios'

const state = {
  userInfo: [],
  writtenRouteList: [],
  savedRouteList: [],
  followUserList: [],
}

const getters = {
  userInfo(state) {
    return state.userInfo
  },
  writtenRouteList(state) {
    return state.myRouteList
  },
  savedRouteList(state) {
    return state.othersRouteList
  },
  followUserList(state) {
    return state.followUserList
  },
}

const mutations = {
  SET_USER_INFO(state, userInfo) {
    state.userInfo = userInfo
  },
  SET_WRITTEN_ROUTE_LIST(state, myRouteList) {
    state.myRouteList = myRouteList
  },
  SET_SAVED_ROUTE_LIST(state, othersRouteList) {
    state.othersRouteList = othersRouteList
  },
  SET_FOLLOW_USER_LIST(state, followUserList) {
    state.followingUserList = followUserList
  },
}

const actions = {
  // async fetchUserInfo({ commit }, userId, access_token) {
  fetchUserInfo({ commit }, payload ) {
    console.log('여기는 store')
    console.log(payload)
    // console.log(payload.userId)
    // console.log(payload.access_token)
    getUserInfoByUserId(payload.userId, payload.access_token,
    (res) => {
      console.log(res.data.success)
      commit('SET_USER_INFO', res.data.success)
    }, (error) => {
      console.log(error)
    });
  },
  
  followOtherUser({ dispatch }, payload ) {
    follow(payload.userId, payload.targetId, payload.access_token,
    (res) => {
      console.log(res.data.success)
      dispatch('fetchFollowUserList', payload.targetId)
    }, (error) => {
      console.log(error)
    });
  },

  fetchFollowUserList({ commit }, payload ) {
    getFollowList(payload.userId, payload.access_token,
    (res) => {
      console.log(res.data.success)
      commit('SET_FOLLOW_USER_LIST', res.data.success)
    }, (error) => {
      console.log(error)
    });
  },

  fetchWrittenRouteList({ commit }, payload ) {
    getWrittenRoute(payload.userId, payload.access_token,
    (res) => {
      console.log(res.data.success)
      commit('SET_WRITTEN_ROUTE_LIST', res.data.success)
    }, (error) => {
      console.log(error)
    });
  },

  fetchSavedRouteList({ commit }, payload ) {
    getSavedRoute(payload.userId, payload.access_token,
    (res) => {
      console.log(res.data.success)
      commit('SET_SAVED_ROUTE_LIST', res.data.success)
    }, (error) => {
      console.log(error)
    });
  },



  // followOtherUser({ getters, dispatch }, userI+d) {
  //   axios.post(DRF.URL + DRF.ROUTES.follow(userId), null, getters.config)
  //     .then(() => dispatch('fetchOtherUserInfo', userId))
  //     // 팔로우, 팔로잉 리스트도 받아야와야함
  //     .catch(err => console.error(err))
  // },

}

export default {
  state, getters, mutations, actions
}