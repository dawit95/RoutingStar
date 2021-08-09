//api/routes.js
// 아무일도 처리하지 않은 api 선언
import { createInstance } from './index.js'

const instance = createInstance();

// { params: { places, routeDescription, routeImg, whatTag, withTag } }
function postRoute(param, access_token, success, fail) {
  instance
  .post('api/v1/route', {
    data: param,
    headers: {
      'access_token': access_token,
    },
  })
  .then(success)
  .catch(fail);
}

function getRouteInfoByRouteId(routeId, access_token, success, fail) {
  console.log("getRouteInfoByRouteId잘 들어옴")
  // console.log(routeId, access_token)
  instance
  .get(`api/v1/route/${routeId}`, {
    headers: {
      'access_token': access_token,
    }
  })
  .then(success)
  .catch(fail);
}
export { postRoute, getRouteInfoByRouteId }