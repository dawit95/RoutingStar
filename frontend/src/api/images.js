import { createInstance } from './index.js'

const instance = createInstance();

// 사실 이거 되는지 확신 X
// 확인해봐야함 headers 바꾸는 것
// axios.defaults.headers.post['Content-Type'] = 'multipart/form-data';

function postPointImages(files, success, fail) {
  instance
  .post('http://192.168.1.214:8000/api/v1/img/place', files, {
    headers: {
      'Content-Type': 'multipart/form-data',
    }
  })
  .then(success)
  .catch(fail)
}

function postThumbnailImage(file, success, fail) {
  instance
  .post('http://192.168.1.214:8000/api/v1/img/place', file, {
    headers: {
      'Content-Type': 'multipart/form-data',
    }
  })
  .then(success)
  .catch(fail)
}

export { postPointImages, postThumbnailImage };

