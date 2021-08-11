// import axios from 'axios'
// import accounts from '@/store/modules/accounts.js'


const state = {
  feed: [],

}

const getters = {
  feed(state) {
    return state.feed
  },

}

const mutations = {
}

const actions = {
  selectedFeed( {commit}, feeds){
    console.log(commit)
    console.log(feeds)
    for (let i = 0; i < feeds.length; i++){
      console.log('i', i)
      console.log('feeds id', feeds[i].id)
      console.log('feeds len', feeds[feeds.length-1])
      if (feeds[i].id == feeds[feeds.length-1] ){
        state.feed = feeds[i]
      }
    }
    console.log('selected', state.feed)
  }
}

export default {
  state, getters, mutations, actions
}