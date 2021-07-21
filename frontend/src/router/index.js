import Vue from 'vue'
import VueRouter from 'vue-router'
import LinesView from '@/views/lines/LinesView.vue'
import Header from '@/components/common/Header.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/accounts/LoginView.vue')
  },
  {
    path: '/Header',
    name: 'Header',
    component: Header,
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
