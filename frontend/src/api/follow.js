//api/follow.js

import { createInstance } from './index.js'

const instance = createInstance();

function follow(from, to, success, fail) {
  instance
    .get('profile/follow', { params: { from, to } })
    .then(success)
    .catch(fail);
}

function getFollowerList(email, success, fail) {
  instance
    .get('profile/follower', { params: { email } })
    .then(success)
    .catch(fail);
}

function getFollowingList(email, success, fail) {
  instance
    .get('profile/following', { params: { email } })
    .then(success)
    .catch(fail);
}

export { 
  follow,
  getFollowerList,
  getFollowingList,
 }