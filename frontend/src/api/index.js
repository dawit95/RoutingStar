// api/index.js

export default {
  URL: '',
  ROUTES: {
    login() {
      return '/'
    },
    profile(username) {
      return `accounts/profile/${username}`
    }
  },
}