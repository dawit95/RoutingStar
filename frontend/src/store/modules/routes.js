// routes.js

const state = {
  pointList: [],
  latLst: [],
  lngLst: [],
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
  }
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
  }
}

const actions = {
  addPointItem ({ commit }, payload) {
    // console.log(payload)
    let newPoint = {
      image : null,
      lat : payload.event.latLng.lat(),
      lng : payload.event.latLng.lng(),
      content: null,
      thumbnail : false,
      marker: payload.marker,
    }
    commit('ADD_POINT_ITEM', newPoint)
  },
  addLatLngLst ({ commit }, payload) {
    // console.log(payload)
    let latLngLst = {
      latLst: payload.lat_lst,
      lngLst: payload.lng_lst,
    }
    // console.log(latLngtLst.latLst)
    commit('ADD_LAT_LNG_LST', latLngLst)
    },
  }

export default {
  state, getters, mutations, actions
}