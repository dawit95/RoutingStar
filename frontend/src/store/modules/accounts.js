// accounts.js
import { login } from '@/api/user.js'

const state = {
  jwt: [],
  feeds: '',

}


const getters = {
  readUser(state) {
    return state.jwt
  },


}

const mutations= {
  CREATE_USER(state, token){
    state.jwt = token
  },
  FETCH_ACCESS(state, accesstoken){
    state.jwt.access = accesstoken
  },
  FETCH_LOGINED_FEEDS(state, fetechedFeeds){
    state.feeds = fetechedFeeds
  }
}

const actions = {
  createUser({commit}, token) {
    commit('CREATE_USER', token)
  },
  fetchAccess({commit}, accessToken) {
    commit('FETCH_ACCESS', accessToken)
  },
  fetchLoginedFeeds({commit}, token) {
    console.log('commit 전')
    console.log(token)
    // console.log(a)
    // console.log(this.readUser)
    login(token, (res) => {
      console.log('commit 후')
      const fetechedFeeds = res.data
      commit('FETCH_LOGINED_FEEDS', fetechedFeeds)
    },
      (success) => 
    {
      console.log(success)
    },
      (err) => {console.log(err)}
    )
   }
}


export default {
  state, getters, mutations, actions
}

// export { returnLogin }   