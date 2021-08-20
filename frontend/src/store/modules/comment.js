// {
//   "comment": "우와 낼 여기 가야겠어요 감사합니당!",
//   "id": 2
// }

// store/modules/comments.js

import { postComment, deleteComment } from '@/api/comment.js'
// import accounts from '@/store/modules/accounts.js'
// import routes from './routes'
// import router from '@/router'


const state = {
  newComment: '',
  deletedComment: '',
}

const getters = {
  newComment(state) {
    return state.newComment
  },
  deletedComment(state) {
    return state.deletedComment
  }
}

const mutations= {
  SET_NEW_COMMENT(state, comment) {
    state.newComment = comment
  },
  SET_DELETED_COMMENT(state, comment) {
    state.deletedComment = comment
  }
}

const actions = {
  createComment({ commit }, payload) {
    console.log('댓글 액션스')
    console.log(payload)
    postComment( payload.routeId, payload.access_token, payload.param,
      (res) => {
        console.log(res)
        commit('SET_NEW_COMMENT', payload.param.comment)
      },(error) => {
        console.log(error)
      }
    );
  },

  deleteCommentActions({ commit }, payload) {
    console.log('댓글 삭제 엑션스')
    console.log(payload)
    deleteComment(payload.commentId, payload.access_token,
      (res) => {
        console.log(res)
        commit('SET_DELETED_COMMENT', payload.commentId)
      }, (error) => {
        console.log(error)
    })
  },
}

export default {
  state, getters, mutations, actions
}