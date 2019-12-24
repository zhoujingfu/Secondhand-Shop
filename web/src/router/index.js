import Vue from 'vue'
import Router from 'vue-router'

import Dashboard from '@/components/Dashboard'
import Main from '@/components/Main'
import Login from '@/components/Login'



Vue.use(Router)

let routes = [{
  path: '/Login',
  component: Login,
  hidden: true
},{
    path:'/',
    component: Main,
    hidden: true,
    children: [{
        path: '/',
        component:Dashboard,
        name:'首页'
    }]
}]

import {
    SystemRouter
} from './system'

for (let i in SystemRouter){
    routes.push(SystemRouter[i])
}

const router = new Router({
    routes: routes
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = sessionStorage.getItem('user');
  if (!user && to.path != '/login') {
    next({
      path: '/login'
    })
  } else {
    next()
  }
})

export default router;

