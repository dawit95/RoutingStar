// images.js
// import postPointImages from '@/api/images.js'

const state = {
  routeImg: '',
  thumbnailImage: '',
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
  // POST_POINT_IMAGES(state, responseData) {
  //   state.pointedItems[pk].image = responseData
  // },
  UPDATE_ROUTE_IMG(state, routeImg) {
    state.routeImg = routeImg
  },
  UPDATE_THUMBNAIL_IMAGE(state, thumbnailImage) {
    state.thumbnailImage = thumbnailImage
  }
}

const actions = {
  // complete({ commit }) {
  //   const ins = state.pointedItems.length
  //   for (var x = 0; x < ins; x++) {
  //     const files = new FormData()
  //     const pk = state.pointedItems[x].pk
  //     if (state.imgList[pk] === null) {
  //       continue
  //     } else {
  //       const image =  state.imgList[pk]
  //       files.append('files', image)
  //       postPointImages(files, (res) => {
  //         const responseData = res.data.successDto.success.image
  //         commit('POST_POINT_IMAGES', responseData)
  //       }, (error) => console.log(error))
  //     }
  //   }
  // },
  updateRouteImg({ commit }, routeImg) {
    commit('UPDATE_ROUTE_IMG', routeImg)
  },
  updateThumbnailImage({ commit }, thumbnailImage) {
    commit('UPDATE_THUMBNAIL_IMAGE', thumbnailImage)
  }

}

export default {
  state, getters, mutations, actions
}