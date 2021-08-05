// accounts.js
import { login } from '@/api/user.js'

const state = {
  jwt: [],
  feeds: '',

}


const getters = {
  jwt(state) {
    return state.jwt
  },


}

const mutations= {
  // 전체 토큰 받아오기(처음 로그인)
  CREATE_USER(state, token){
    state.jwt = token
  },
  // access 토큰 갱신용
  // RENEW_ACCESS(state, access){
  //   state.jwt.access = access
  // },
  FETCH_ACCESS(state, accesstoken){
    console.log(accesstoken)
    state.jwt.access = accesstoken
  },
  FETCH_LOGINED_FEEDS(state, fetechedFeeds){
    state.feeds = fetechedFeeds
  }
}

const actions = {
  createUser({commit}, token) {
    console.log(token)
    commit('CREATE_USER', token)
  },
  fetchAccess({commit}, accessToken) {
    commit('FETCH_ACCESS', accessToken)
  },
  fetchLoginedFeeds({commit}, token) {
    console.log('commit 전')
    console.log(token)
    const fetechedFeeds = login(token)
    console.log('fetechfeeds:', fetechedFeeds)
    // console.log(fetechedFeeds.then(success => {console.log(success)})
      // console.log('success:', success)
    // }))
    console.log(fetechedFeeds.then().data)
    // console.log(fetechedFeeds.PromiseResult)
    // 만약 access token을 갱신해야된다면 commit 요청을 두 개 보내자(access toekn if문으로 확인) 
    commit('FETCH_LOGINED_FEEDS', fetechedFeeds)

   }
}


export default {
  state, getters, mutations, actions
}

// export { returnLogin }   