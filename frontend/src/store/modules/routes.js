// routes.js

const state = {
  pointedItems: [],
  latLst: [],
  lngLst: [],
  imgLst: [],
  polyLine: null,
}

const getters = {
  pointedItems(state) {
    return state.pointedItems
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
    state.pointedItems.push(newPoint)
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
  REFRESH_POINTED_ITEMS(state, newPointedItems) {
    state.pointedItems = newPointedItems
  },
  UPDATE_DRAGGERBLE_ITEMS(state, event) {
    var tmp = state.pointedItems[event.newIndex]
    state.pointedItems[event.newIndex] = state.pointedItems[event.oldIndex]
    state.pointedItems[event.oldIndex] = tmp
  }
}

// import axios from 'axios'
// actions는 Backend API와 통신하여 Data fetching과 같은 작업 수행
// 동기적인 작업 뿐 만 아니라 비동기적인 작업을 포함 가능
// 항상 Context가 인자로 넘어온다, 오직 mutation 매서드를 commit 호출해서 조작 가능
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
  refreshPointItems ( { commit }, newPointedItems) {
    commit('REFRESH_POINTED_ITEMS', newPointedItems)
  },
  updateDraggerbleItems ( { commit }, event ) {
    commit('UPDATE_DRAGGERBLE_ITEMS', event)
  },
}

export default {
  state, getters, mutations, actions
}