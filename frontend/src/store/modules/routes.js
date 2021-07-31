// routes.js

const state = {
  pointList: [],
  latLst: [],
  lngLst: [],
  imgLst: [],
  polyLine: null,
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
  polyLine(state) {
    return state.polyLine
  } 
}

const mutations= {
  ADD_LAT_LNG_LST(state, latLngLst) {
    for (var i in latLngLst.latLst){
      state.latLst.push(latLngLst.latLst[i])
      state.lngLst.push(latLngLst.lngLst[i])
    }
  },
  ADD_POINT_ITEM(state, newPoint) {
    state.pointList.push(newPoint)
  },
  ADD_IMAGE(state, newPoint) {
    state.imgLst.push(newPoint.image)
  },
  SEND_IMAGES_ARRAY(state) {
    console.log(state)
  },
  SET_POLYLINE(state, polyline) {
    state.polyLine = polyline
    // console.log(state)
  },
  REFRESH_POINT_LIST(state, newPointList) {
    state.pointList = newPointList
  }
}

// import axios from 'axios'
const actions = {
  addLatLngLst ({ commit }, latLngLst) {
    // console.log(payload)
    // console.log(latLngtLst.latLst)
    commit('ADD_LAT_LNG_LST', latLngLst)
  },
  addPointItem ({ commit }, newPoint) {
    // console.log('잘 작동함 ㅇㅇ')
    // console.log(newPoint)
    commit('ADD_POINT_ITEM', newPoint)
    commit('ADD_IMAGE', newPoint)
  },
  refreshPointList ( { commit }, newPointList) {
    commit('REFRESH_POINT_LIST', newPointList)
  }
}

export default {
  state, getters, mutations, actions
}