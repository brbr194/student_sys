import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Manager',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
        { path: 'admin', name: 'admin', component: () => import('@/views/manager/Admin.vue')},
        { path: 'teacher', name: 'teacher', component: () => import('@/views/manager/Teacher.vue')},
        { path: 'student', name: 'student', component: () => import('@/views/manager/Student.vue')},
        { path: 'person', name: 'person', component: () => import('@/views/manager/Person.vue')},
        { path: 'log', name: 'log', component: () => import('@/views/manager/Log.vue')},

      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue')
    }


  ]
})

export default router
