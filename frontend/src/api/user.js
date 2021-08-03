import { createInstance } from "./index.js";

const instance = createInstance()

function login(user, success, fail){
    // 토큰이 있는지 없는지 큰 if 문으로 조건 줘야함

    // 라이브러리 호출
    const jwt = require('jsonwebtoken')
    const decodeAccessToken = jwt.decode(this.$store.jwt.access)
    // const decodeAccessToken = jwt.decode(this.$route.query.access.headers['at-jwt-access-token']);
    // console.log('decodeAccessToken data', decodeAccessToken)
    // console.log()
    // console.log(Date.now()/1000 + 60)
    if(decodeAccessToken.exp < Date.now()/1000 + 60)
    {
      headers = {
        'access_token': this.$store.jwt.access,
        'refresh_token': this.$store.jwt.refresh
      }
    } else {
      headers = {
        'access_token': this.$store.jwt.access,
      }   
    }
}

function logout() {

}

export { login, logout }    

