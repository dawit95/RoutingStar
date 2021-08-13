import axios from 'axios'
// import accounts from '@/store/modules/accounts.js'


const state = {
  isLiked: '',
  isSaved: '',
}

const getters = {
  isLiked(state) {
    return state.isLiked
  },
  isSaved(state) {
    return state.isSaved
  },
}

const mutations = {
  // 전체 토큰 받아오기(처음 로그인)
  REFRESH_ISLIKED(state, likeOrNot){
    console.log('likeorNot', likeOrNot)
    state.isLiked = likeOrNot
    // console.log(fetechedFeeds)
    // state.feeds = fetechedFeeds
    // state.jwt.access = token.access
    // state.jwt.refresh = token.refresh
  },
  REFRESH_ISSAVED(state, saveOrNOt) {
    console.log('likeorNot', saveOrNOt)
    state.isSaved = saveOrNOt
  }

}

const actions = {
  fetchLike( {commit}, jwt) {
    console.log(jwt)
    console.log('id', jwt[3])
    console.log('commit', commit)
    const config = {
        headers: {
          'access_token': jwt[0],
        }
      }
    axios.get(`http://i5a309.p.ssafy.io:8000/api/v1/reaction/like/${jwt[2]}/${jwt[3]}`, config)
      .then(res => commit('REFRESH_ISLIKED', (res.data.success)))
      .catch((err) => console.log(err))
    
  },
  fetchStore( {commit}, jwt) {
    console.log(jwt)
    console.log('id', jwt[3])
    const config = {
        headers: {
          'access_token': jwt[0],
        }
      }
    axios.get(`http://i5a309.p.ssafy.io:8000/api/v1/reaction/store/${jwt[2]}/${jwt[3]}`, config)
      .then(res => commit('REFRESH_ISSAVED', (res.data.success)))
      .catch((err) => console.log(err))
  },
}

export default {
  state, getters, mutations, actions
}