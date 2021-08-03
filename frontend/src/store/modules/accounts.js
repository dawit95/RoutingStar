// accounts.js

const state = {
  jwt: []

}

const getters = {
  readUser(state) {
    return state.jwt
  },

}

const mutations= {
  CREATE_USER(state, token){
    state.jwt = token
  }
}

const actions = {
  createUser({commit}, token) {
    commit('CREATE_USER', token)
  }

}

export default {
  state, getters, mutations, actions
}