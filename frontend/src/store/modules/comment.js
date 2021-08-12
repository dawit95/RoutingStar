// {
//   "comment": "우와 낼 여기 가야겠어요 감사합니당!",
//   "id": 2
// }

// store/modules/comments.js

import { postComment, deleteComment } from '@/api/comment.js'
// import accounts from '@/store/modules/accounts.js'
// import accounts from './accounts'
// import router from '@/router'


const state = {
}

const getters = {
}

const mutations= {
}

const actions = {
  createComment(payload) {
    console.log('댓글 액션스')
    console.log(payload)
    postComment( payload.routeId, payload.access_token, payload.param,
      (res) => {
        console.log(res)
        // 다시 코멘트 정보 받아오기
      },(error) => {
        console.log(error)
      }
    );
  },

  deleteCommentActions(payload) {
    console.log('댓글 삭제 엑션스')
    console.log(payload)
    deleteComment(payload.commentId, payload.access_token,
      (res) => {
        console.log(res)
        // 다시 코멘트 정보 받아오기
      }, (error) => {
        console.log(error)
    })
  },
}

export default {
  state, getters, mutations, actions
}