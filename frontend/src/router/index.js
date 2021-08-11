import Vue from 'vue'
import VueRouter from 'vue-router'
import Header from '@/components/common/Header.vue'
import PostRouteView from '@/views/post/PostRouteView.vue'

Vue.use(VueRouter)

const routes = [
  // Accounts
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/accounts/LoginView.vue')
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
    path: '/error',
    name: 'ErrorView',
    component: () => import('@/views/ErrorView'),
  },
  
  // Search
  {
    path: '/search',
    name: 'SearchView',
    component: () => import('@/views/search/SearchView')
  },
  {
    path: '/searchresult',
    name: 'SearchResultView',
    component: () => import('@/views/search/SearchResultView')
  },
  
  // Main Page
  {
    path: '/home',
    name: 'HomeView',
    component: () => import('@/views/home/HomeView')
  },
  {
    path: '/homedetail/:feedId',
    name: 'HomeDetailView',
    component: () => import('@/views/home/HomeDetailView')
  },
  
  // Routes
  {
    path: '/post',
    name: 'PostRouteView',
    component: PostRouteView,
  },
  
  // Revise Routes
  {
    path: '/reviseroute',
    name: 'ReviseRouteView',
    component: () => import('@/views/revise/ReviseRouteView.vue')
  },
  
  // My Page
  {
    path: '/mypage',
    name: 'MyPageView',
    component: () => import('@/views/myPage/MyPageView.vue')
  },
  {
    path: '/profilepage',
    name: 'OtherUserPageView',
    component: () => import('@/views/myPage/OtherUserPageView.vue')
  },
  {
    path: '/editprofile',
    name: 'EditProfileView',
    component: () => import('@/views/myPage/EditProfileView.vue')
  },
  {
    path: '/followuserlist',
    name: 'FollowUserListView',
    component: () => import('@/views/myPage/FollowUserListView.vue')
  },

  // tmp
  {
    path: '/loginprocess',
    name: 'LoginProcessView',
    component: () => import('@/views/LoginProcessView.vue')
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

  // 없는 곳으로 오려 한다면
  if (!to.name) {
    next({ name: 'NotFound' })
  }
  // if (from.name === 'Lines' && to.name === 'LoginView') {
  // //   next({ name : 'PostView' })
  //   next('/')
  
});

// 라우터 이동이 끝나고 해야하는 일들
router.afterEach(() => {
  console.log("router moved")
})

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
