import { createInstance } from "./index.js";
//import getters from '../store'

// const access_token = accounts.state.jwt[0]
// console.log(access_token)
const instance = createInstance()

// function login(token){
//   const jwt = require('jsonwebtoken')
//   const decodeAccessToken = jwt.decode(token.access)
//   console.log('decodepk:', decodeAccessToken.pk)
//   console.log('token', token)
//   console.log('access_token', token.access)
//   // 메인 페이지 데이터 받아오기 & access 토큰 받아오기
//   // 돌아오는건 access 토큰만이고, data랑 같이 안에
//   if ( decodeAccessToken.exp < Date.now()/1000 + 60)  {
//     instance
//     console.log('갱신해야함')
//     .get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, {
//       headers: {
//         'access_token': token.access,
//         'refresh_token': token.refresh
//       }
//     })
//     // 응답 받아왔으면 그냥 받아온 access token 을 항상 갱신해주자
//     .then((success) => console.log(success))
//     .catch((fail) => console.log(fail))
//   } else {
//     console.log('갱신안해도됨')
//     instance
//     .get(`http://i5a309.p.ssafy.io:8000/userTest/routes/${decodeAccessToken.pk}`, {
//       headers: {
//         'access_token': token.access,
//       }
//     })
//     .then(success => {console.log(success)}
//     )
//     .catch((fail) => console.log('fail: ', fail))
//     }   
//   }


// function logout() {

// UserID만 있으면 가져오는 targetUser 정보
function getUserInfoByUserId(targetId, access_token, success, fail) {

  // const access_token = accounts.getters.jwt[0]
  console.log("g어쎄스토큰")
  console.log(access_token)
  instance
  .get(`api/user/Info/${targetId}`, {
    headers: {
      'access_token': access_token,
    }
  })
  .then(success)
  .catch(fail);
}

function editProfile(param, success, fail) {
  instance
    .post('profile/upload', param)
    .then(success)
    .catch(fail);
}


export { 
  getUserInfoByUserId,
  editProfile,
 }    

