// routes.js

const state = {
  pointList: [],
}

const getters = {
  pointedItems(state) {
    return state.pointList
  }
}

const mutations= {
  ADD_POINT_ITEM(state, newPoint) {
    state.pointList.push(newPoint)
  }
}

const actions = {
  addPointItem ({ commit }, payload) {
    // console.log('작동함 ㅇㅇ')
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
}

export default {
  state, getters, mutations, actions
}