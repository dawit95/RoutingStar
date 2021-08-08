// home.js
// import { login } from '@/api/user.js'
import axios from 'axios'

const state = {
  feeds: '',

}


const getters = {

}

const mutations= {
  // 전체 토큰 받아오기(처음 로그인)
  CREATE_HOME(state, fetechedFeeds){
    console.log('처음 로그인 메인페이지 저장 commit')
    console.log(fetechedFeeds)
    state.feeds = fetechedFeeds
    // state.jwt.access = token.access
    // state.jwt.refresh = token.refresh
  },
  // access 토큰 갱신용
  FETCH_LOGINED_FEEDS(state, fetechedFeeds){
    console.log('FETCH_LOGINE 메인페이지 다시 받아오기 commiteed', fetechedFeeds)
    state.feeds = fetechedFeeds
  }
}

const actions = {
  createHome({commit}, token) {
    console.log('createhome으로 넘어옴')
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token.access)
    console.log('decode', decodeAccessToken)
    const config = {
      headers: {
        'access_token': token.access,
      }
    }
    axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config)
      .then(res => commit('CREATE_HOME', res.data.success))
      .catch((fail) => console.log('fail: ', fail))
    },
  fetchLoginedFeeds({commit}, token) {
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(token[0])
    // 메인 페이지 데이터 받아오기 & access 토큰 받아오기
    // 돌아오는건 access 토큰만이고, data랑 같이 안에
    if ( decodeAccessToken.exp < Date.now()/1000 + 60) {
      const config = {
        headers: {
          'access_token': token[0],
          'refresh_token': token[1],
        }
      }
      axios.get(`http://localhost:8000/userTest/routes/${decodeAccessToken.pk}`, config)
      .then(res => commit('FETCH_LOGINED_FEEDS', res.data.success))
      .catch((err) => console.log(err))

      // console.log('config', config.headers.access_token),
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