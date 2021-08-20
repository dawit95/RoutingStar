// api/alarms.js

import { createInstance } from './index.js'

const instance = createInstance();


function postBrowserToken(user_id, browser_token, success, fail) {
  instance
    .get(`push/${user_id}`, {
      headers: {
        'browser_token': browser_token,
      }
    })
    .then(success)
    .catch(fail);
}

export { 
  postBrowserToken,
 } 