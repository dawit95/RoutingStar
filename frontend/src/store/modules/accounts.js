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
    state.jwt = token
  },
  // access 토큰 갱신용
  RENEW_ACCESS(state, access){
    state.jwt.access = access
  },
  FETCH_LOGINED_FEEDS(state, fetechedFeeds){
    console.log('commiteed', fetechedFeeds)
    state.feeds = fetechedFeeds
  }
}

const actions = {
  createUser({commit}, token) {
    console.log(token)
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
            .then(axios.spread((res1, res2) => {console.log('갱신해야함성공:', res1, res2)}))
            .catch((err) => console.log(err))
      // axios.get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, config)
      //   // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
      //   .then(res => {console.log(res)})
      //   .catch((fail) => console.log(fail))
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