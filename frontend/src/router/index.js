import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Kakao from '../components/accounts/Kakao.vue'
import LoginView from '@/views/accounts/LoginView.vue'
import LinesView from '@/views/lines/LinesView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/kakao',
    name: 'Kakao',
    component: Kakao
  },
  {
    path: '/Lines',
    name: 'Lines',
    component: LinesView,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
