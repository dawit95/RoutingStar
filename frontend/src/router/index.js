import Vue from 'vue'
import VueRouter from 'vue-router'
import LinesView from '@/views/lines/LinesView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/accounts/LoginView.vue')
  },
  {
    path: '/main',
    name: 'App',
    component: () => import('@/App.vue')
  },
  {
    path: '/nav',
    name: 'Nav',
    component: () => import('@/components/common/Nav')
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
