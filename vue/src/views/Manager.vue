<template>
  <div>
    <div style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
      <div style="flex: 1">
        <div style="padding-left: 20px; display: flex; align-items: center">
          <img src="../assets/imgs/logo.png" alt="" style="width: 40px">
          <div style="font-weight: bold; font-size: 24px; margin-left: 5px">学生成绩管理系统</div>
        </div>
      </div>

<!--      <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
        <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="" style="width: 40px; height: 40px">
        <span style="margin-left: 5px" id="username">{{user.name}}</span>
      </div>-->
      <el-dropdown>
    <span class="el-dropdown-link">
<!--      <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="" style="width: 40px; height: 40px">-->
      <span style="margin-left: 5px" id="username">{{user.name}}</span>
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <div style="display: flex">
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu
            router
            style="border: none"
            :default-active="$route.path"
            :default-openeds="['/home']"
        >
          <el-menu-item index="/home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-sub-menu index="2" v-if="user.role === 'ADMIN'">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/student">
              <el-icon><UserFilled /></el-icon>
              <span>学生信息</span>
            </el-menu-item>
            <el-menu-item index="/teacher">
              <el-icon><UserFilled /></el-icon>
              <span>教师信息</span>
            </el-menu-item>
            <el-menu-item index="/department">
              <el-icon><SuitcaseLine /></el-icon>
              <span>学院信息</span>
            </el-menu-item>
            <el-menu-item index="/major">
              <el-icon><Stopwatch /></el-icon>
              <span>专业信息</span>
            </el-menu-item>
            <el-menu-item index="/grade">
              <el-icon><Suitcase /></el-icon>
              <span>年级信息</span>
            </el-menu-item>
            <el-menu-item index="/course">
              <el-icon><Calendar /></el-icon>
              <span>课程信息</span>
            </el-menu-item>
            <el-menu-item index="/semester">
              <el-icon><Calendar /></el-icon>
              <span>学期信息</span>
            </el-menu-item>
            <el-menu-item index="/admin">
              <el-icon><UserFilled /></el-icon>
              <span>管理员信息</span>
            </el-menu-item>
          </el-sub-menu>



          <el-sub-menu  index="4" v-if="user.role === 'TEACHER'">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>教师授课管理</span>
            </template>
            <el-menu-item index="/teacherCourseList">
              <el-icon><UserFilled /></el-icon>
              <span>授课列表</span>
            </el-menu-item>
            <el-menu-item index="/TeacherAddScore">
              <el-icon><UserFilled /></el-icon>
              <span>授课打分</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu  index="5"  v-if="user.role === 'STUDENT'">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>学生选课管理</span>
            </template>
            <el-menu-item index="/studentCourseList">
              <el-icon><Aim /></el-icon>
              <span>已选课列表</span>
            </el-menu-item>
            <el-menu-item index="/studentSelectCourse" v-if="user.role === 'STUDENT'">
              <el-icon><Pointer /></el-icon>
              <span>学生选课区</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu  index="6"  v-if="user.role === 'ADMIN'">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>授课选课管理</span>
            </template>
            <el-menu-item index="/studentCourseList">
              <el-icon><Aim /></el-icon>
              <span>已选课列表</span>
            </el-menu-item>
            <el-menu-item index="/teacherCourseList">
              <el-icon><Pointer /></el-icon>
              <span>授课列表</span>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/StudentScore" v-if="user.role === 'STUDENT'">
            <el-icon><Histogram /></el-icon>
            <span>课程成绩查看</span>
          </el-menu-item>
          <el-menu-item index="/AdminScore" v-if="user.role === 'ADMIN'">
            <el-icon><Histogram /></el-icon>
            <span>课程成绩管理</span>
          </el-menu-item>
          <el-menu-item index="/TeacherManageScore" v-if="user.role === 'TEACHER'">
            <el-icon><Histogram /></el-icon>
            <span>课程成绩管理</span>
          </el-menu-item>
          <el-menu-item index="/AuditScore" v-if="user.role === 'ADMIN'">
            <el-icon><CircleCheck /></el-icon>
            <span>送审成绩管理</span>
          </el-menu-item>
          <el-menu-item index="/AdminStatistics" v-if="user.role === 'ADMIN'">
            <el-icon><PieChart /></el-icon>
            <span>数据统计</span>
          </el-menu-item>
          <el-menu-item index="/TeacherStatistics" v-if="user.role === 'TEACHER'">
            <el-icon><PieChart /></el-icon>
            <span>数据统计</span>
          </el-menu-item>
          <el-menu-item index="/teacherPerson" v-if="user.role === 'TEACHER'">
            <el-icon><User /></el-icon>
            <span>教师个人信息管理</span>
          </el-menu-item>
          <el-menu-item index="/stuPerson" v-if="user.role === 'STUDENT'">
            <el-icon><User /></el-icon>
            <span>学生个人信息管理</span>
          </el-menu-item>
          <el-menu-item index="/adminPerson" v-if="user.role === 'ADMIN'">
            <el-icon><User /></el-icon>
            <span>管理员个人信息管理</span>
          </el-menu-item>
          <el-menu-item index="/log">
            <el-icon><Tickets /></el-icon>
            <span>日志查看</span>
          </el-menu-item>

        </el-menu>
      </div>

      <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
        <router-view />
      </div>
    </div>

  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import {reactive} from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import request from "@/utils/request";
const $route = useRoute()
const user = JSON.parse(localStorage.getItem('login_user') || '{}')

const logout = () => {
  console.log(user.token)
  let t_user={
    id:user.id
  }
  if(user.role === 'ADMIN'){
    request.post('/admin/logout',t_user).then(res=>{
      if (res.code === '200') {
        ElMessage.success("退出成功")
        localStorage.removeItem('login_user')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
  else if(user.role === 'TEACHER'){

    request.post('/teacher/logout',t_user).then(res=>{
      if (res.code === '200') {
        ElMessage.success("退出成功")
        localStorage.removeItem('login_user')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
  else if(user.role === 'STUDENT'){
    request.post('/student/logout', t_user ).then(res=>{
      console.log(res.code)
      if (res.code === '200') {
        ElMessage.success("退出成功")
        localStorage.removeItem('login_user')
      }else {
        ElMessage.error(res.msg)
      }
    })
  }
  router.push('/login')
}

const noUser = () =>{
  if(!user.id){
    router.push('/login')
  }
}
noUser()

</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #dcede9 !important;
}
.el-menu-item:hover {
  color: #59d1ec;
}
:deep(th)  {
  color: #333;
}
</style>