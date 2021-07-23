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
    path: '/main',
    name: 'App',
    component: () => import('@/App.vue')
  },
  {
    path: '/search',
    name: 'SearchView',
    component: () => import('@/views/search/SearchView')
  },
  {
    path: '/post',
    name: 'PostView',
    component: () => import('@/views/post/PostView')
  },
  {
    path: '/home',
    name: 'HomeView',
    component: () => import('@/views/home/HomeView')
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

// router.beforeRouteLeave((Home, Lines, next) => {
//   alert('!!!!!');
//   next("/");
//   // const answer = window.confirm('저장되지 않은 작업이 있습니다! 정말 나갈까요?');
//   // if (answer) {
//   // next();
//   // } else {
//   // next(false);
//   // }
// })

export default router
