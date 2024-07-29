<template>
  <el-container>
    <el-aside :width="data.asideWidth" style="min-height: 100vh;background-color: #001529">
      <div
          style="height: 60px; line-height: 60px; font-size: 20px; display: flex; align-items: center; justify-content: center">
        <img alt="" src="../assets/imgs/logo.png" style="width: 30px;">
        <span v-show="!data.isCollapse" class="logo-title" style="color: white;">学生成绩管理系统</span>
      </div>
      <el-menu
          router
          :collapse="data.isCollapse"
          :collapse-transition="false"
          :default-active="$route.path"
          :default-openeds="['/home']"
          background-color="#001529"
          class="el-menu-vertical-demo"
          style="border: none"
          text-color="rgba(255, 255, 255, 0.65)"
      >
        <el-menu-item index="/home">
          <el-tooltip content="系统首页" placement="right">
          <el-icon><HomeFilled /></el-icon>
          </el-tooltip>
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
          <el-tooltip content="课程成绩查看" placement="right">
          <el-icon><Histogram /></el-icon>
          </el-tooltip>
          <span>课程成绩查看</span>
        </el-menu-item>
        <el-menu-item index="/AdminScore" v-if="user.role === 'ADMIN'">
          <el-tooltip content="课程成绩管理" placement="right">
          <el-icon><Histogram /></el-icon>
          </el-tooltip>
          <span>课程成绩管理</span>
        </el-menu-item>
        <el-menu-item index="/TeacherManageScore" v-if="user.role === 'TEACHER'">
          <el-tooltip content="课程成绩管理" placement="right">
            <el-icon><Histogram /></el-icon>
          </el-tooltip>
          <span>课程成绩管理</span>
        </el-menu-item>
        <el-menu-item index="/AuditScore" v-if="user.role === 'ADMIN'">
          <el-tooltip content="送审成绩管理" placement="right">
          <el-icon><CircleCheck /></el-icon>
          </el-tooltip>
          <span>送审成绩管理</span>
        </el-menu-item>
        <el-menu-item index="/AdminStatistics" v-if="user.role === 'ADMIN'">
          <el-tooltip content="数据统计" placement="right">
          <el-icon><PieChart /></el-icon>
          </el-tooltip>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/TeacherStatistics" v-if="user.role === 'TEACHER'">
          <el-tooltip content="数据统计" placement="right">
            <el-icon><PieChart /></el-icon>
          </el-tooltip>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/teacherPerson" v-if="user.role === 'TEACHER'">
          <el-tooltip content="教师个人信息管理" placement="right">
          <el-icon><User /></el-icon>
          </el-tooltip>
          <span>教师个人信息管理</span>
        </el-menu-item>
        <el-menu-item index="/stuPerson" v-if="user.role === 'STUDENT'">
          <el-tooltip content="学生个人信息管理" placement="right">
            <el-icon><User /></el-icon>
          </el-tooltip>
          <span>学生个人信息管理</span>
        </el-menu-item>
        <el-menu-item index="/adminPerson" v-if="user.role === 'ADMIN'">
          <el-tooltip content="管理员个人信息管理" placement="right">
            <el-icon><User /></el-icon>
          </el-tooltip>
          <span>管理员个人信息管理</span>
        </el-menu-item>
        <el-menu-item index="/log">
          <el-tooltip content="日志查看" placement="right">
          <el-icon><Tickets /></el-icon>
          </el-tooltip>
          <span>日志查看</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <el-button :icon="data.CollapseIcon" round="true" size="medium" text @click="handleCollapse"></el-button>
<!--        <el-breadcrumb :separator-icon="ArrowRight" style="margin-left: 20px">
          <el-breadcrumb-item :to="{ path: '/home' }">homepage</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/ORC' }">promotion management</el-breadcrumb-item>
          <el-breadcrumb-item>promotion list</el-breadcrumb-item>
          <el-breadcrumb-item>promotion detail</el-breadcrumb-item>
        </el-breadcrumb>-->

        <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
          <el-button :icon="FullScreen" text  @click="handleFull"></el-button>
          <el-dropdown placement="bottom">
            <div style="display: flex; align-items: center; cursor: default">
            <img src="@/assets/imgs/logo.png"  style="width: 40px; height: 40px; margin: 0 5px">
              <span id="username" style="margin-left: 5px">{{user.name}}</span>
            <el-icon class="el-icon--right">
              <arrow-down/>
            </el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <div style="flex: 1; width: 100%; background-color: #f8f8ff; padding: 10px">
          <router-view />
        </div>
      </el-main>
    </el-container>
  </el-container>

</template>

<script setup>
import {useRoute} from 'vue-router'
import {reactive, ref} from "vue";
import {ArrowRight, FullScreen, Menu} from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import request from "@/utils/request";
const $route = useRoute()
const user = JSON.parse(localStorage.getItem('login_user') || '{}')


const data = reactive({
  asideWidth: '250px',
  isCollapse: false,
  textCollapse: '收起',
  CollapseIcon: 'Fold'
})


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
const handleCollapse = () => {
  data.isCollapse = !data.isCollapse
  data.asideWidth = data.isCollapse ? '64px' : '250px'
  data.textCollapse = data.isCollapse ? '展开' : '收起'
  data.CollapseIcon = data.isCollapse ? 'Expand' : 'Fold'
}

const  handleFull = () => {
  document.documentElement.requestFullscreen()
}
</script>


<style scoped>

.el-menu--inline {
  background-color: #000c17 !important;
}

.el-menu--inline .el-menu-item {
  background-color: #000c17 !important;
  padding-left: 49px !important;
}

.el-menu-item:hover, .el-sub-menu__title:hover {
  color: #fff !important;
}

.el-submenu__title:hover i {
  color: #fff !important;
}

.el-menu-item:hover i {
  color: #fff !important;
}

.el-menu-item.is-active {
  background-color: #1890ff !important;
  border-radius: 10px !important;
  width: calc(100% - 8px);
  margin-left: 4px !important;
}

.el-menu-item.is-active {
  color: #f3f3f3;
}

.el-menu-item {

  height: 50px !important;
  line-height: 50px !important;
}

.el-submenu__title {

  height: 50px !important;
  line-height: 50px !important;
}

.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip {
  margin-left: -4px;
}

.el-submenu .el-menu-item {

  min-width: 0 !important;
}

.el-menu--inline .el-menu-item.is-active {
  padding-left: 49px !important;
}

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;
}

.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  display: flex;
  align-items: center;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
  min-height: 400px;
}
</style>