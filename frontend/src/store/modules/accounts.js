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
    console.log(token)
    // state.jwt.access = token.access
    // state.jwt.refresh = token.refresh
    state.jwt[0] = token.access
    state.jwt[1] = token.refresh
    // console.log(state.jwt)
    // console.log(state.jwt.access)
    // console.log(state.jwt.refresh)
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
    commit('CREATE_USER', token)
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token.access)
    console.log('decode', decodeAccessToken)
    const config = {
      headers: {
        'access_token': token.access,
      }
    }
    axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config)
      .then(res => commit('FETCH_LOGINED_FEEDS', res.data.success))
      .catch((fail) => console.log('fail: ', fail))
    },
  fetchAccess({commit}, accessToken) {
    commit('FETCH_ACCESS', accessToken)
  },
  fetchLoginedFeeds({commit}, token) {
    console.log('fetch로넘어옴')
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token[0])
    console.log('decodepk:', decodeAccessToken.pk)
    // 메인 페이지 데이터 받아오기 & access 토큰 받아오기
    // 돌아오는건 access 토큰만이고, data랑 같이 안에
    if ( decodeAccessToken.exp > Date.now()/1000 + 60) {
      console.log('갱신해야함')
      console.log(token[0])
      const config = {
        headers: {
          'access_token': token[0],
          'refresh_token': token[1],
        }
      }
      console.log('config', config.headers.access_token),
      // axios.all([axios.get(`http://localhost:8000/token/refresh`, config),
      //            axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config)])
      //       .then(axios.spread((res1, res2) => {console.log(res1, res2.data.success)}))
      //       // .then((res1) => {console.log(res1)})
      //       // .then((res2) => {console.log(res2)})
      //       .catch((err) => console.log(err))
      // 받아온 토큰으로 갱신해서 보내기
      async function test() {
        const response = await axios.get(`http://localhost:8000/token/refresh`, config)
        console.log(response)
        const response2 = await axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, response.data.success)
        console.log(response2)	
      }
      test()
      // test()
      // axios.get(`http://localhost:8000/token/refresh`, config)
      // .then((res) => axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config.headers.access_token=res.data.success)
      //               .then((res) => console.log('갱신된res', res)))
      // // .then((res) => console.log(res.data.success))
      // .catch((err) => {console.log(err)})

    } else {
      console.log('갱신안해도됨')
      const config = {
        headers: {
          'access_token': token[0],
        }
      }
      axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config)
        .then(res => commit('FETCH_LOGINED_FEEDS', res.data.success))
        .catch((err) => console.log(err))
        }   
      }
   }


export default {
  state, getters, mutations, actions
}