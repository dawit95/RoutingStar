// routes.js

const state = {
  pointedItems: [],
  imgLst: [],
  polyLine: null,
  routeDescription: '',
  xyPoints: [],
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
  // UPDATE_DRAGGERBLE_ITEMS(state, event) {
  //   var ary = [...state.pointedItems]

  //   var tmp = ary[event.newIndex]
  //   ary[event.newIndex] = ary[event.oldIndex]
  //   ary[event.oldIndex] = tmp

  //   state.pointedItems = ary;
  // },
  UPDATE_DRAGGERBLE_ITEMS(state, event) {
   var selected = state.pointedItems[event.oldIndex]
   var step = event.oldIndex
   if( event.newIndex < event.oldIndex) {
     // 앞으로 온 경우
     for( ; step > event.newIndex ; step-- ) {
       state.pointedItems[step] = state.pointedItems[step-1]
     }
   } else {
     // 뒤로 간 경우
     for(  ; step < event.newIndex ; step++ ) {
       state.pointedItems[step] = state.pointedItems[step+1]
     }
   }
   state.pointedItems[event.newIndex] = selected
  //  console.log(state.pointedItems)
  },
  CREATE_ROUTE_DESCRIPTION(state, routeDescription) {
    state.routeDescription = routeDescription
  },
  SET_XY_POINTS(state, points) {
    state.xyPoints = points
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
}

export default {
  state, getters, mutations, actions
}