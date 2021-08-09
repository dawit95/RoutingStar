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

export { postRoute }