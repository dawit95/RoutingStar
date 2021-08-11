// api/search.js

import { createInstance } from './index.js'

const instance = createInstance();


// 
// { params: { whatTag, withTag } }
function postSearhFollow(param, userId, access_token, success, fail) {
  instance
  .post(`api/v1/following/routes/${userId}`, param, {
    headers: {
      'access_token': access_token,
    }
  })
  .then(success)
  .catch(fail);
}

function postSearhNonFollow(param, userId, access_token, success, fail) {
  instance
  .post(`api/v1/nonfollowing/routes/${userId}`, param, {
    headers: {
      'access_token': access_token,
    }
  })
  .then(success)
  .catch(fail);
}


export { 
  postSearhFollow,
  postSearhNonFollow,
 }