import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '@/views/accounts/LoginView.vue'
import LinesView from '@/views/lines/LinesView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/accounts/LoginView.vue')
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
