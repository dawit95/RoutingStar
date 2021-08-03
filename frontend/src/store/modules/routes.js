// routes.js

const state = {
  pointedItems: [],
  imgLst: [],
  polyLine: null,
  routeDescription: '',
  xyPoints: [],
  whatTag: [],
  withTag: [],
}

const getters = {
  pointedItems(state) {
    return state.pointedItems
  },
  imgList(state) {
    return state.imgLst
  },
  polyLine(state) {
    return state.polyLine
  },
  routeDescription(state) {
    return state.routeDescription
  },
  xyPoints(state) {
    return state.xyPoints
  },
  whatTag(state) {
    return state.whatTag
  },
  withTag(state) {
    return state.withTag
  }
}

const mutations= {
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
  },
  CREATE_ROUTE_DESCRIPTION(state, routeDescription) {
    state.routeDescription = routeDescription
  },
  SET_XY_POINTS(state, points) {
    state.xyPoints = points
  },
  SET_WHAT_TAG(state, whatTag) {
    state.whatTag = whatTag
  },
  SET_WITH_TAG(state, withTag) {
    state.withTag = withTag
  },
}

// import axios from 'axios'
// actions는 Backend API와 통신하여 Data fetching과 같은 작업 수행
// 동기적인 작업 뿐 만 아니라 비동기적인 작업을 포함 가능
// 항상 Context가 인자로 넘어온다, 오직 mutation 매서드를 commit 호출해서 조작 가능
const actions = {
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
  createRouteDescription( { commit }, event) {
    commit('CREATE_ROUTE_DESCRIPTION', event)
  },
  setXYPoints( {commit}, points) {
    commit('SET_XY_POINTS', points)
  },
  setWhatTag( {commit}, whatTag) {
    commit('SET_WHAT_TAG', whatTag)
  },
  setWithTag( {commit}, withTag) {
    commit('SET_WITH_TAG', withTag)
  }
}

export default {
  state, getters, mutations, actions
}