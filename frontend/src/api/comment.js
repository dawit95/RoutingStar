// api/comment.js

import { createInstance } from './index.js'

const instance = createInstance();

function postComment(routeId, access_token, param, success, fail) {
  console.log(routeId, access_token)
  instance
    .post(`api/v1/comment/${routeId}`, param, {
      headers: {
        'access_token': access_token,
      }
    })
    .then(success)
    .catch(fail);
}

function deleteComment(commentId, access_token, success, fail) {
  instance
    .delete(`api/v1/comment/${commentId}`, {
      headers: {
        'access_token': access_token,
      }
    })
    .then(success)
    .catch(fail);
}


export { 
  postComment,
  deleteComment,
 }