import { createInstance } from "./index.js";

const instance = createInstance()

function login(token, success, fail){

  const jwt = require('jsonwebtoken')
  const decodeAccessToken = jwt.decode(token.access)
  if ( decodeAccessToken.exp < Date.now()/1000 + 60)  {
    instance
    .get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, {
      headers: {
        'access_token': token.access,
        'refresh_token': token.refresh
      }
    })
    // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
    .then((success) => console.log(success))
    .catch((fail) => console.log(fail))
  } else {
    instance
    .get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, {
      headers: {
        'access_token': token.access,
      }
    })
    // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
    .then(success, (success) => console.log('success: ', success))
    .catch(fail, (fail) => console.log(fail))
    }   
  }


function logout() {

}

export { login, logout }    

