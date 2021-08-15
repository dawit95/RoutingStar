import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // Accounts
  {
    path: '/',
    name: 'LoginView',
    component: () => import('@/views/accounts/LoginView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__fadeIn animate__slower",
      leaveActiveClass: "animate__animated animate__fadeOut animate__slow"
    }
  },
  
  // Common
  {
    path: '/main',
    name: 'App',
    component: () => import('@/App.vue')
  },
  {
    path: '/error',
    name: 'ErrorView',
    component: () => import('@/views/ErrorView'),
    meta: {
      enterActiveClass: "animate__animated animate__zoomIn",
      leaveActiveClass: "animate__animated animate__zoomOut"
    }
  },
  
  // Main Page
  {
    path: '/home',
    name: 'HomeView',
    component: () => import('@/views/home/HomeView'),
    meta: {
      enterActiveClass: "animate__animated animate__fadeIn animate__slow",
      leaveActiveClass: "animate__animated animate__fadeOut"
    }
  },
  
  // Search
  {
    path: '/search',
    name: 'SearchView',
    component: () => import('@/views/search/SearchView'),
    meta: {
      enterActiveClass: "animate__animated animate__slideInUp",
      leaveActiveClass: ""
    }
  },
  {
    path: '/searchresult',
    name: 'SearchResultView',
    component: () => import('@/views/search/SearchResultView'),
    meta: {
      enterActiveClass: "animate__animated animate__fadeIn",
      leaveActiveClass: "animate__animated animate__fadeOut"
    }
  },
  
  // Routes
  {
    path: '/post',
    name: 'PostRouteView',
    component: () => import('@/views/post/PostRouteView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__zoomIn",
      leaveActiveClass: "animate__animated animate__fadeOut"
    }
  },
  {
    path: '/routedetail/:feedId',
    name: 'RouteDetailView',
    component: () => import('@/views/routeDetail/RouteDetailView'),
    meta: {
      enterActiveClass: "animate__animated animate__slideInRight",
      leaveActiveClass: "animate__animated animate__slideOutRight"
    }
  },
  
  // Revise Routes
  {
    path: '/reviseroute/:routeId',
    name: 'ReviseRouteView',
    component: () => import('@/views/revise/ReviseRouteView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__zoomIn",
      leaveActiveClass: "animate__animated animate__fadeOut"
    }
  },
  // Revise Others' Route
  {
    path: '/reviseothers/:routeId',
    name: 'ReviseOthersRouteView',
    component: () => import('@/views/revise/ReviseOthersRouteView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__zoomIn",
      leaveActiveClass: "animate__animated animate__fadeOut"
    }
  },
  
  // My Page
  {
    path: '/mypage',
    name: 'MyPageView',
    component: () => import('@/views/myPage/MyPageView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__fadeIn animate__faster",
      leaveActiveClass: "animate__animated animate__fadeOut aimate__faster"
    }
  },
  {
    path: '/profilepage',
    name: 'OtherUserPageView',
    component: () => import('@/views/myPage/OtherUserPageView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__fadeIn animate__faster",
      leaveActiveClass: "animate__animated animate__fadeOut aimate__faster"
    }
  },
  {
    path: '/badge',
    name: 'BadgeView',
    component: () => import('@/views/myPage/BadgeView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__zoomIn",
      leaveActiveClass: "animate__animated animate__zoomOut"
    }
  },
  {
    path: '/followuserlist',
    name: 'FollowUserListView',
    component: () => import('@/views/myPage/FollowUserListView.vue'),
    meta: {
      enterActiveClass: "animate__animated animate__zoomIn",
      leaveActiveClass: "animate__animated animate__zoomOut"
    }
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
