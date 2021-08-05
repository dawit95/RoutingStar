import { createInstance } from "./index.js";

const instance = createInstance()

function login(token){
  const jwt = require('jsonwebtoken')
  const decodeAccessToken = jwt.decode(token.access)
  console.log('decodepk:', decodeAccessToken.pk)
  console.log('token', token)
  console.log('access_token', token.access)
  // 메인 페이지 데이터 받아오기 & access 토큰 받아오기
  // 돌아오는건 access 토큰만이고, data랑 같이 안에
  if ( decodeAccessToken.exp < Date.now()/1000 + 60)  {
    instance
    console.log('갱신해야함')
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
    console.log('갱신안해도됨')
    instance
    .get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, {
      headers: {
        'access_token': token.access,
      }
    })
    // 응답 받아왔으면 받아온 Main page data를 vuex로 넘겨주기(access는 안 돌아옴)
    // 다윗왈 - success가 http 객체이므로 파싱하고 넘기면 return 될듯
    .then(success => {console.log(success)}
      // console.log('success:', success)
    )
    // console.log('success: ', success))
    // .catch((fail) => console.log('fail: ', fail))
    }   
  }


function logout() {

}

export { login, logout }    

