// images.js
import { postPointImages, postThumbnailImage } from '@/api/images.js'
import routes from '@/store/modules/routes.js'


const state = {
  thumbnailImage: '',
}

const getters = {

}

const mutations= {
  // POST_POINT_IMAGES(state, data) {
  //   // console.log(state)
  //   // console.log(routes.state.pointedItems[data.x])
  //   routes.state.pointedItems[data.x] = data.responseData
  // },
  UPDATE_THUMBNAIL_IMAGE(state, thumbnailImg) {
    state.thumbnailImage = thumbnailImg
  },
  POST_THUMBNAIL_IMAGE(state, responseThumbnailImg) {
    state.thumbnailImage = responseThumbnailImg
  }
}

const actions = {
  complete({ commit }) {
    const ins = routes.state.pointedItems.length
    for (var x = 0; x < ins; x++) {
      const files = new FormData()
      const pk = routes.state.pointedItems[x].pk
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
          routes.state.pointedItems[pk].image = responseData
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
  updateThumbnailImage({ commit }, thumbnailImg) {
    commit('UPDATE_THUMBNAIL_IMAGE', thumbnailImg)
  },
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