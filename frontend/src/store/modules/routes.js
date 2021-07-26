// routes.js

const state = {
  pointList: [],
  latLst: [],
  lngLst: [],
  imgLst: [],
}

const getters = {
  pointedItems(state) {
    return state.pointList
  },
  latLstItems(state) {
    return state.latLst
  },
  lngLstItems(state) {
    return state.lngLst
  },
  imgList(state) {
    return state.imgLst
  },
}

const mutations= {
  ADD_POINT_ITEM(state, newPoint) {
    state.pointList.push(newPoint)
  },
  ADD_LAT_LNG_LST(state, latLngLst) {
    // console.log(latLngLst)
    // console.log(latLngLst.latLst)
    for (var i in latLngLst.latLst){
      // console.log(latLngLst.latLst[i])
      state.latLst.push(latLngLst.latLst[i])
      state.lngLst.push(latLngLst.lngLst[i])
    }
    // console.log(state.latLst)
    // console.log(state.lngLst)
  },
  ADD_IMAGE(state, newPoint) {
    state.imgLst.push(newPoint.image)
  },
  SEND_IMAGES_ARRAY(state) {
    console.log(state)
  },
  // UPDATE_IMAGE(state, payload) {
  //   console.log(payload.fd)
  //   const imageData = payload.fd
  //   console.log(payload.idx)
  //   state.imgLst.push(imageData)
  //   console.log(state.imgLst)
  // }
}

import axios from 'axios'
const actions = {
  addPointItem ({ commit }, newPoint) {
    // console.log('잘 작동함 ㅇㅇ')
    // console.log(newPoint)
    commit('ADD_POINT_ITEM', newPoint)
    commit('ADD_IMAGE', newPoint)
  },
  addLatLngLst ({ commit }, latLngLst) {
    // console.log(payload)
    // console.log(latLngtLst.latLst)
    commit('ADD_LAT_LNG_LST', latLngLst)
  },
  sendImagesArray ({ commit }) {
    // console.log('작동함 ㅇㅇ')
    // commit('SEND_IMAGES_ARRAY', event)
    axios({
      method: 'post',
      url: 'http://192.168.1.215:9091/api/v1/img/place',
      data: state.imgLst,
      headers: {'Content-Type': 'multipart/form-data'}
    })
      .then(res => {
        commit('SEND_IMAGES_ARRAY')
        console.log('보내짐')
        console.log(res)
      })
      .catch(err => {
        console.log('안보내짐')
        console.log(err)
      })
  },
  // updateImage({commit}, payload) {
  //   commit('UPDATE_IMAGE', payload)
  // },
  }

export default {
  state, getters, mutations, actions
}