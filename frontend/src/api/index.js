import axios from 'axios'
import {API_BASE_URL} from '@/config/index.js'

function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    // 보낼때 json 형식으로(client->server)
    headers: {
      "Content-Type": "application/json" 
    }
  }),  
  return instance
}

export { createInstance }
