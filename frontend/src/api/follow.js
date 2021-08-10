//api/follow.js

import { createInstance } from './index.js'

const instance = createInstance();

function follow(userId, targetId, access_token, success, fail) {
  console.log(userId, targetId, access_token)
  instance
    .get(`api/v1/reaction/follow/${userId}/${targetId}`, {
      headers: {
        'access_token': access_token,
      }
    })
    .then(success)
    .catch(fail);
}

function getFollowList(userId, access_token, success, fail) {
  instance
    .get(`api/v1/reaction/followlist/${userId}`, {
      headers: {
        'access_token': access_token,
      }
    })
    .then(success)
    .catch(fail);
}


export { 
  follow,
  getFollowList,
 }