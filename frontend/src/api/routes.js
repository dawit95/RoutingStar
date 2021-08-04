//api/routes.js
// 아무일도 처리하지 않은 api 선언
import { createInstance } from './index.js'

const instance = createInstance();

// { params: { places, routeDescription, routeImg, whatTag, withTag } }
function postRoute(param, success, fail) {
  instance
  .post('route', param )
  .then(success)
  .catch(fail);

}

export { postRoute }