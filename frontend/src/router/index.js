import Vue from 'vue'
import VueRouter from 'vue-router'
import Header from '@/components/common/Header.vue'
import PostRouteView from '@/views/post/PostRouteView.vue'

Vue.use(VueRouter)

const routes = [
  // My Profile
  {
    path: '/mypage',
    name: 'MyPage',
    component: () => import('@/views/myPage/MyPage.vue')
  },
  // Accounts
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/accounts/LoginView.vue')
  },
  // 회원가입 전까지는 위의 프로필로 테스트
  // path: '/acocunts/profile/:username',
  {
    path: '/accounts/profile/',
    name: 'ProfileView',
    component: () => import('@/views/accounts/ProfileView.vue')
  },

  // Common
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
    path: '/home',
    name: 'HomeView',
    component: () => import('@/views/home/HomeView')
  },
  {
    path: '/nav',
    name: 'Nav',
    component: () => import('@/components/common/Nav')
  },
  
  // Routes
  {
    path: '/post',
    name: 'PostRouteView',
    component: PostRouteView,
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('@/views/Test'),
  },
  {
    path: '/draggable',
    name: 'draggable',
    component: () => import('@/views/draggable'),
  },
]



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  console.log(to.name)
  console.log(from.name)
  // console.log(next)
  // next( {name: 'PostView'})
  next()


  if (to.name === 'Nav' && from.name === 'Lines') {
    const result = confirm('뒤로 가면 데이터가 삭제됩니다.')
    if (result) {
      next(-1)
    } else {
      next('/nav')
    } 
  } else if (to.name === 'LoginView' && from.name === 'Lines') {
    const result = confirm('뒤로 가면 데이터가 삭제됩니다.')
    if (result) {
      next(-1)
    } else {
      next('/')
    } 
  }
  // if (from.name === 'Lines' && to.name === 'LoginView') {
  // //   next({ name : 'PostView' })
  //   next('/')
  
});

// router.beforeEach((to, from, next) => {
  //     // console.log(to)
  //     // console.log(from)
//     console.log(next)

//     if (from.name === 'Lines' && to.name === 'LoginView') {
//       console.log('aaaaaaaa')
      // alert('뒤로 가면 데이터가 삭제됩니다.')
      // next ({ name: 'LoginView'})
      // return
//     }
//   }
// )

export default router
