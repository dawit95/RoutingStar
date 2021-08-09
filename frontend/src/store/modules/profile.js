// profile.js 
import { getUserInfoByUserId } from '@/api/accounts.js'
// import accounts from './accounts'
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
  }

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