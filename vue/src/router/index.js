import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'NewManager',
      component: () => import('@/views/NewManager.vue'),
      redirect: '/login',
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
         { path: 'major', name: 'major', component: () => import('@/views/manager/admin/Major.vue')},
        { path: 'semester', name: 'semester', component: () => import('@/views/manager/admin/Semester.vue')},
        { path: 'teacherPerson', name: 'teacherPerson', component: () => import('@/views/manager/teacher/teacherPerson.vue')},
        { path: 'teacherCourseList', name: 'teacherCourseList', component: () => import('@/views/manager/teacher/teacherCourseList.vue')},
        { path: 'TeacherAddScore', name: 'TeacherAddScore', component: () => import('@/views/manager/teacher/TeacherAddScore.vue')},
        { path: 'studentCourseList', name: 'studentCourseList', component: () => import('@/views/manager/student/StudentCourseList.vue')},
        { path: 'studentSelectCourse', name: 'studentSelectCourse', component: () => import('@/views/manager/student/StudentSelectCourse.vue')},
        { path: 'adminPerson', name: 'adminPerson', component: () => import('@/views/manager/admin/adminPerson.vue')},
        { path: 'StudentScore', name: 'StudentScore', component: () => import('@/views/manager/student/StudentScore.vue')},
        { path: 'AdminScore', name: 'AdminScore', component: () => import('@/views/manager/admin/AdminScore.vue')},
        { path: 'TeacherManageScore', name: 'TeacherManageScore', component: () => import('@/views/manager/teacher/TeacherManageScore.vue')},
        { path: 'AuditScore', name: 'AuditScore', component: () => import('@/views/manager/admin/AuditScore.vue')},
        { path: 'AdminStatistics', name: 'AdminStatistics', component: () => import('@/views/manager/admin/AdminStatistics.vue')},
        { path: 'TeacherStatistics', name: 'TeacherStatistics', component: () => import('@/views/manager/teacher/TeacherStatistics.vue')},

      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/Login.vue')
    }


  ]
})

export default router
