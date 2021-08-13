// accounts.js
import axios from 'axios'

const state = {
  jwt: [],
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
    // console.log('decodepk:', decodeAccessToken.pk)
    state.jwt[0] = token.access
    state.jwt[1] = token.refresh
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token.access)
    state.jwt[2] = decodeAccessToken.pk
    console.log(state.jwt)
  },
  FETCH_LOGINED_TOKEN(state, access){
    console.log('갱신된 토큰 받아오기 commiteed', access)
    state.jwt[0] = access
  }
}

const actions = {
  createUser({commit}, token) {
    commit('CREATE_USER', token)
  },
  fetchLoginedToken({commit}, token) {
    console.log('fetch로넘어옴')
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token[0])
    console.log('decodepk:', decodeAccessToken.pk)
    // 메인 페이지 데이터 받아오기 & access 토큰 받아오기
    // 돌아오는건 access 토큰만이고, data랑 같이 안에
    if ( decodeAccessToken.exp < Date.now()/1000 + 60) {
      console.log('갱신해야함')
      console.log(token[0])
      const config = {
        headers: {
          'access_token': token[0],
          'refresh_token': token[1],
        }
      }
     
      // axios.get(`http://localhost:8000/token/refresh`, config)
      axios.get(`http://i5a309.p.ssafy.io:8000/api/v1/token/refresh`, config)
        .then((res) => commit('FETCH_LOGINED_TOKEN', res.data.success))
        .catch((res) => {console.log(res)})

      // axios.all([axios.get(`http://localhost:8000/token/refresh`, config),
      //            axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config)])
      //       .then(axios.spread((res1, res2) => {console.log(res1, res2.data.success)}))
      //       .catch((err) => console.log(err))
      // 받아온 토큰으로 갱신해서 보내기
      // async test() {
      //   const response = await axios.get(`http://localhost:8000/token/refresh`, config)
      //   console.log(response)
      //   const response2 = await axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, response.data.success)
      //   console.log(response2)	
      // }
      // test()
      // async await 안되면 axios get 받고 const a 로 저장하고 if a 가 있다면 재요청보내는 방식으로 ㄱ


      // axios.get(`http://localhost:8000/token/refresh`, config)
      //           .then((res) => {console.log(res)}
      //           axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config.headers.access_token=res.data.success)
      //               .then((res) => console.log('갱신된res', res)))
      // // .then((res) => console.log(res.data.success))
      // .catch((err) => {console.log(err)})

    }  
  }
}


export default {
  state, getters, mutations, actions
}