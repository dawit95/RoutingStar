//api/routes.js
// 아무일도 처리하지 않은 api 선언
import { createInstance } from './index.js'

const instance = createInstance();

// { params: { places, routeDescription, routeImg, whatTag, withTag } }
function postRoute(param, jwt, success, fail) {
  instance
  .post('api/v1/route', param, {
    headers: {
      'access_token': jwt[0],
    }
  })
  .then(success)
  .catch(fail);
}

// 유저가 작성한 루트 목록 조회
function getWrittenRoute(userId, access_token, success, fail) {
  instance
    .get(`api/user/mypage/routes/${userId}`, {
      headers: {
        'access_token': access_token,
      }
    })
    .then(success)
    .catch(fail);
}

// 유저가 저장한 루트 목록 조회
function getSavedRoute(userId, access_token, success, fail) {
  instance
    .get(`api/v1/routes/${userId}`, {
      headers: {
        'access_token': access_token,
      }
    })
    .then(success)
    .catch(fail);
}

function getRouteInfoByRouteId(userId, routeId, access_token, success, fail) {
  console.log("getRouteInfoByRouteId잘 들어옴")
  console.log(userId, routeId, access_token)
  instance
  .get(`api/v1/route/${userId}/${routeId}`, {
    headers: {
      'access_token': access_token,
    }
  })
  .then(success)
  .catch(fail);
}

function updateRoute(param, routeId, jwt, success, fail) {
  instance
  .put(`api/v1/route/${routeId}`, param, {
    headers: {
      'access_token': jwt[0],
    }
  })
  .then(success)
  .catch(fail);
}

export { 
  postRoute,
  getWrittenRoute,
  getSavedRoute,
  getRouteInfoByRouteId,
  updateRoute,
 }