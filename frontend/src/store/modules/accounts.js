// accounts.js
// import { login } from '@/api/user.js'
import axios from 'axios'

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
    console.log('처음 로그인 jwt 저장 commit')
    state.jwt = token
  },
  // access 토큰 갱신용
  RENEW_ACCESS(state, access){
    console.log('로그인 jwt 갱신 commit')
    state.jwt.access = access
  },
  FETCH_LOGINED_FEEDS(state, fetechedFeeds){
    console.log('메인페이지 받아오기 commiteed', fetechedFeeds)
    state.feeds = fetechedFeeds
  }
}

const actions = {
  createUser({commit}, token) {
    console.log('createuser')
    console.log('createuser', token)
    commit('CREATE_USER', token)
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token.access)
    const config = {
      headers: {
        'access_token': token.access,
      }
    }
    axios.get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, config)
      .then(res => commit('FETCH_LOGINED_FEEDS', res.data.success))
      .catch((fail) => console.log('fail: ', fail))
    },
  fetchAccess({commit}, accessToken) {
    commit('FETCH_ACCESS', accessToken)
  },
  fetchLoginedFeeds({commit}, token) {
    console.log('commit 전')
    console.log(token)


    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token.access)
    console.log('decodepk:', decodeAccessToken.pk)
    console.log('token', token)
    console.log('access_token', token.access)
    // 메인 페이지 데이터 받아오기 & access 토큰 받아오기
    // 돌아오는건 access 토큰만이고, data랑 같이 안에
    if ( decodeAccessToken.exp < Date.now()/1000 + 60) {
      console.log('갱신해야함')
      const config = {
        headers: {
          'access_token': token.access,
          'refresh_token': token.refresh,
        }
      }
      axios.all([axios.get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, config),
                axios.get(`http://i5a309.p.ssafy.io:8000/token/refresh`), config])
            .then(axios.spread((res1, res2) => {commit('FETCH_LOGINED_FEEDS', res1), commit('FETCH_LOGINED_FEEDS', res2)}))
            .catch((err) => console.log(err))

    } else {
      console.log('갱신안해도됨')
      const config = {
        headers: {
          'access_token': token.access,
        }
      }
      axios.get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, config)
        .then(res => commit('FETCH_LOGINED_FEEDS', res.data.success))
        .catch((fail) => console.log('fail: ', fail))
        }   
      }
   }


export default {
  state, getters, mutations, actions
}