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
  // RENEW_ACCESS(state, access){
  //   state.jwt.access = access
  // },
  FETCH_ACCESS(state, accesstoken){
    console.log(accesstoken)
    state.jwt.access = accesstoken
  },
  // FETCH_LOGINED_FEEDS(state, fetechedFeeds){
  //   state.feeds = fetechedFeeds
  // }
}

const actions = {
  createUser({commit}, token) {
    console.log(token)
    commit('CREATE_USER', token)
  },
  fetchAccess({commit}, accessToken) {
    commit('FETCH_ACCESS', accessToken)
  },
  fetchLoginedFeeds(token) {
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
          'access_token': this.jwt.access,
          'refresh_token': this.jwt.refresh,
        }
      }
      axios.get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, config)
        // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
        .then(res => {console.log(res)})
        .catch((fail) => console.log(fail))
    } else {
      console.log('갱신안해도됨')
      const config = {
        headers: {
          'access_token': this.jwt.access,
        }
      }
      axios.get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, config)
        .then(res => {console.log(res)})
        .catch((fail) => console.log('fail: ', fail))
        }   
      }


    // const fetechedFeeds = login(token)
    // console.log('fetechfeeds:', fetechedFeeds)
    // console.log(fetechedFeeds.then(success => {console.log(success)})
      // console.log('success:', success)
    // }))
    // console.log(fetechedFeeds.then().data)
    // console.log(fetechedFeeds.PromiseResult)
    // 만약 access token을 갱신해야된다면 commit 요청을 두 개 보내자(access toekn if문으로 확인) 
    // commit('FETCH_LOGINED_FEEDS', fetechedFeeds)

   }
// }


export default {
  state, getters, mutations, actions
}

// export { returnLogin }   