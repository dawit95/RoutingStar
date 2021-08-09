// profile.js

import { getUserInfoByUserId } from '@/api/accounts.js'
// import axios from 'axios'

const state = {
  userInfo: [],
  myRouteList: [],
  othersRouteList: [],
  followingRouteList: [],
  followingUserList: [],
  followerUserList: [],
}

const getters = {
  userInfo(state) {
    return state.userInfo
  },
  myRouteList(state) {
    return state.myRouteList
  },
  othersRouteList(state) {
    return state.othersRouteList
  },
  followingRouteList(state) {
    return state.followingRouteList
  },
  followingUserList(state) {
    return state.followingUserList
  },
  followerUserList(state) {
    return state.followerUserList
  },
}

const mutations = {
  SET_USER_INFO(state, userInfo) {
    state.userInfo = userInfo
  },
  SET_MY_ROUTE_LIST(state, myRouteList) {
    state.myRouteList = myRouteList
  },
  SET_OTHERS_ROUTE_LIST(state, othersRouteList) {
    state.othersRouteList = othersRouteList
  },
  SET_FOLLOWING_ROUTE_LIST(state, followingRouteList) {
    state.followingRouteList = followingRouteList
  },
  SET_FOLLOWING_USER_LIST(state, followingUserList) {
    state.followingUserList = followingUserList
  },
  SET_FOLLOWER_USER_LIST(state, followerUserList) {
    state.followerUserList = followerUserList
  },
}

const actions = {
  fetchUserInfo({ commit }, userId) {
    getUserInfoByUserId(userId),
    (res) => {
      commit('SET_USER_INFO', res)
    } 
  }

  // followOtherUser({ getters, dispatch }, userId) {
  //   axios.post(DRF.URL + DRF.ROUTES.follow(userId), null, getters.config)
  //     .then(() => dispatch('fetchOtherUserInfo', userId))
  //     // 팔로우, 팔로잉 리스트도 받아야와야함
  //     .catch(err => console.error(err))
  // },

}

export default {
  state, getters, mutations, actions
}