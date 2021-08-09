// images.js
import { postPointImages, postThumbnailImage } from '@/api/images.js'
import routes from '@/store/modules/routes.js'
import AWS from 'aws-sdk'

const state = {
  routeImg: '',
  thumbnailImage: '',
  thumbnailChecked: false,
  albumBucketName: 'routingstar-photo-album',
  bucketRegion: 'ap-northeast-2',
  IdentityPoolId: 'ap-northeast-2:65af3722-b840-4cce-8c5f-956fb7ed025e',
}

const getters = {
  routeImg(state) {
    return state.routeImg
  },
  thumbnailImage(state) {
    return state.thumbnailImage
  }
}

const mutations= {
  // POST_POINT_IMAGES(state, data) {
  //   // console.log(state)
  //   // console.log(routes.state.places[data.x])
  //   routes.state.places[data.x] = data.responseData
  // },
  UPDATE_ROUTE_IMG(state, routeImg) {
    console.log(`여기는 MUTATION${routeImg}`)
    state.routeImg = routeImg
  },
  UPDATE_THUMBNAIL_IMAGE(state, thumbnailImage) {
    state.thumbnailImage = thumbnailImage
  },
  POST_THUMBNAIL_IMAGE(state, responseThumbnailImg) {
    state.thumbnailImage = responseThumbnailImg
  }
}

const actions = {
  complete({ commit }) {
    const ins = routes.state.places.length
    for (var x = 0; x < ins; x++) {
      const files = new FormData()
      const pk = routes.state.places[x].pk
      // 첨부파일이 없다면 패스
      if (routes.state.imgLst[pk] === null) {
        continue
        // 첨부파일이 있다면
      } else if (routes.state.imgLst[pk] !== null) {
        const imagefile =  routes.state.imgLst[pk]
        files.append('files', imagefile)
        postPointImages(files, (response) => {
          console.log(x)
          const responseData = response.data.success.image
          routes.state.places[pk].image = responseData
          // commit('POST_POINT_IMAGES', {responseData, pk})
        }, (error) => {
          console.log(error)
        })
      }
    }
    const file = new FormData()
    file.append('file', state.thumbnailImage)
    postThumbnailImage(file, (res) => {
      commit('POST_THUMBNAIL_IMAGE', res.data.success.image)
    }, (error) => {
      console.log(error)
    })
  },
  postPointImages() {
    console.log('업로드 이미지 들어옴 ㅇㅇ')
    const ins = routes.state.places.length
    for (var x = 0; x < ins; x++) {
      const pointItemPk = routes.state.places[x].createdOrder
      // 첨부파일이 없다면 pass
      if (routes.state.imgList[pointItemPk] === '') {
        continue
        // 첨부파일이 있다면 S3로 업로드 진행 및 응답 데이터를 서버로 보내는 이미지 리스트에 저장
      } else if (routes.state.imgList[pointItemPk] !== '') {
        const image = routes.state.imgList[pointItemPk]
        // S3로 업로드 후 반환값(이미지URL)을 저장

        // dispatch('upload', {image: image, num: x, bool: false})
        actions.upload({image: image, num: x, bool: false})
      }
    }
    // setTimeout(() => {
    //   console.log(routes.state.places)
    // }, 3000);
  },
  upload(payload) {
    // console.log('썸네일 업로드 들어옴')
    // console.log(payload.image, payload.num, payload.bool)
    if (payload.image === undefined) {
      return
    }
    // 이미지를 저장하고자하는 S3 버킷지정하기(지역, ID, 버킷이름)
    AWS.config.update({
      region: state.bucketRegion,
      credentials: new AWS.CognitoIdentityCredentials({
      IdentityPoolId: state.IdentityPoolId,
      })
    });

    var s3 = new AWS.S3({
      apiVersion: "2006-03-01",
      params: { Bucket: state.albumBucketName }
    });
    
    // const photoKey = payload.image.name
    const date = new Date().getTime();
    console.log(payload.image)
    s3.upload({
      Key: `${date + payload.image.name}`,
      Body: payload.image,
      ContentType: payload.image.type,
      ACL: 'public-read'
    }, (err, data) => {
      if (err) {
        console.log(err)
        return alert("There was an error uploading your photo: ", err.message);
      }
      routes.state.places[payload.num].placeImg = data.Location
    })
  },
  updateRouteImg({ commit }, routeImg) {
    console.log(`여기는 actions${routeImg}`)
    commit('UPDATE_ROUTE_IMG', routeImg)
  },
  // updateThumbnailImage({ commit }, thumbnailImg) {
  //   commit('UPDATE_THUMBNAIL_IMAGE', thumbnailImg)
  // },
  // complete2({ commit }, thumbnailImage) {
  //   const file = new FormData()
  //   file.append('file', thumbnailImage)
  //   postThumbnailImage(file, (res) => {
  //     console.log(res)
  //     commit('POST_THUMBNAIL_IMAGE', res.data.success.image)
  //   }, (error) => {
  //     console.log(error)
  //   })
  // }
}

export default {
  state, getters, mutations, actions
}