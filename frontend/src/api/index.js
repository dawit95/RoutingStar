// API 설정
import axios from 'axios'
import {API_BASE_URL} from '@/config/index.js'

function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    // 보낼때 json 형식으로(client->server)
    // 일단 디폴트 값은 json 이 외에는 따로 명시해주어야 한다
    headers: {
      "Content-Type": "application/json" 
    }
  })  
  return instance;
}

export { createInstance };
