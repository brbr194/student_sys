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
        { path: 'admin', name: 'admin', component: () => import('@/views/manager/admin/Admin.vue')},
        { path: 'teacher', name: 'teacher', component: () => import('@/views/manager/admin/Teacher.vue')},
        { path: 'student', name: 'student', component: () => import('@/views/manager/admin/Student.vue')},
        { path: 'stuPerson', name: 'stuPerson', component: () => import('@/views/manager/student/stuPerson.vue')},
        { path: 'log', name: 'log', component: () => import('@/views/manager/Log.vue')},
        { path: 'department', name: 'department', component: () => import('@/views/manager/admin/Department.vue')},
        { path: 'grade', name: 'grade', component: () => import('@/views/manager/admin/Grade.vue')},
        { path: 'course', name: 'course', component: () => import('@/views/manager/admin/Course.vue')},
        { path: 'score', name: 'score', component: () => import('@/views/manager/student/Score.vue')},
        { path: 'semester', name: 'semester', component: () => import('@/views/manager/admin/Semester.vue')},
        { path: 'teacherPerson', name: 'teacherPerson', component: () => import('@/views/manager/teacher/teacherPerson.vue')},
        { path: 'teacherCourseList', name: 'teacherCourseList', component: () => import('@/views/manager/teacher/teacherCourseList.vue')},
        { path: 'studentCourseList', name: 'studentCourseList', component: () => import('@/views/manager/student/StudentCourseList.vue')},
        { path: 'teacherSelectCourse', name: 'teacherSelectCourse', component: () => import('@/views/manager/teacher/teacherSelectCourse.vue')},
        { path: 'studentSelectCourse', name: 'studentSelectCourse', component: () => import('@/views/manager/student/StudentSelectCourse.vue')},

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
