// images.js
import postPointImages from '@/api/images.js'

const state = {

}

const getters = {

}

const mutations= {
  POST_POINT_IMAGES(state, responseData) {
    state.pointedItems[pk].image = responseData
  }
}

const actions = {
  complete() {
    const ins = state.pointedItems.length
    for (var x = 0; x < ins; x++) {
      const files = new FormData()
      const pk = state.pointedItems[x].pk
      if (state.imgList[pk] === null) {
        continue
      } else {
        const image =  state.imgList[pk]
        files.append('files', image)
        postPointImages(files, (res) => {
          const responseData = res.data.successDto.success.image
          commit('POST_POINT_IMAGES', responseData)
        }, (error) => console.log(error))
      }
    }
  },
}

export default {
  state, getters, mutations, actions
}