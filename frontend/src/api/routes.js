//api/routes.js
import { createInstance } from './index.js'

const instance = createInstance();

function postRoute(places, routeDescription, routeImg, whatTag, withTag, success, fail) {
  instance
  .post('route', { params: { places, routeDescription, routeImg, whatTag, withTag } } )
  .then(success)
  .catch(fail);

}

export { postRoute }