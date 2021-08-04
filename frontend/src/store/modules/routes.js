// routes.js
// import { createRoute } from '@/api/routes.js'

const state = {
  places: [],
  imgLst: [],
  polyLine: null,
  routeDescription: '',
  xyPoints: [],
  whatTag: [],
  withTag: [],
}

const getters = {
  places(state) {
    return state.places
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
  ADD_PLACE(state, newPlace) {
    state.places.push(newPlace)
  },
  ADD_IMAGE(state, newPlace) {
    state.imgLst.push(newPlace.image)
  },
  SEND_IMAGES_ARRAY(state) {
    console.log(state)
  },
  SET_POLYLINE(state, polyline) {
    state.polyLine = polyline
    // console.log(state)
  },
  REFRESH_PLACES(state, newPlace) {
    state.places = newPlace
  },
  // UPDATE_DRAGGERBLE_ITEMS(state, event) {
  //   var ary = [...state.places]

  //   var tmp = ary[event.newIndex]
  //   ary[event.newIndex] = ary[event.oldIndex]
  //   ary[event.oldIndex] = tmp

  //   state.places = ary;
  // },
  UPDATE_DRAGGERBLE_ITEMS(state, event) {
   var selected = state.places[event.oldIndex]
   var step = event.oldIndex
   if( event.newIndex < event.oldIndex) {
     // 앞으로 온 경우
     for( ; step > event.newIndex ; step-- ) {
       state.places[step] = state.places[step-1]
     }
   } else {
     // 뒤로 간 경우
     for(  ; step < event.newIndex ; step++ ) {
       state.places[step] = state.places[step+1]
     }
   }
   state.places[event.newIndex] = selected
  //  console.log(state.places)
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
  addPlace ({ commit }, newPlace) {
    // console.log('잘 작동함 ㅇㅇ')
    // console.log(newPlace)
    commit('ADD_PLACE', newPlace)
    commit('ADD_IMAGE', newPlace)
  },
  refreshPlaces ( { commit }, newPlace) {
    commit('REFRESH_PLACES', newPlace)
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
  },
  // createRoute(params) => {
  //   createRoute(params)
  // }
}

export default {
  state, getters, mutations, actions
}