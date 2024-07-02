<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div><span style="color: deepskyblue ">{{ user.name }} </span> 欢迎您，祝您今天过得开心！</div>
    </div>
    <div class="card" style="margin-top: 20px">
      <el-button type="info" class="button-left" style="width: 100%" @click="toGit()">前 往 GitHub 仓 库</el-button>
    </div>
    <div class="card" style="margin-top: 20px">
      <el-collapse  class="custom-collapse">
        <el-collapse-item title="管理员功能" name="1" v-if="user.role ==='ADMIN'">
          <div>
            （1）	系统登录与退出，登录与退出时系统记录用户的登录、退出信息（用户名、时间、IP、状态：登录/退出）；
          </div>
          <div>
            （2）	数据维护：包括添加、修改、删除、查询学院、专业、年级、教师、学生、课程等基本信息；
          </div>
          <div>
            （3）	成绩审核：对教师送审的成绩进行审核，审核不通过可以退回让任课教师进行修改，审核通过的成绩不能再修改。
          </div>
          <div>
            （4）	数据导入导出：从Excel导入各种数据（学院、专业、年级、教师、学生、课程等数据）到数据库、将数据库中的各种数据导出到Excel；
          </div>
          <div>
            （5）	权限管理：在成绩管理中，管理员可以对系统的所有信息进行管理，并可以对任何一个学生的成绩进行查询和管理；教师只能对其所任教班级的学生成绩进行管理；学生只能对自己的成绩进行查看，而且不能对其进行更改。
          </div>
        </el-collapse-item>
        <el-collapse-item title="教师功能" name="2" v-if="user.role !=='STUDENT'">
          <div>
            （1）	系统登录与退出，登录与退出时系统记录用户的登录、退出信息（用户名、时间、IP、状态：登录/退出）；
          </div>
            （2）	个人信息管理：查看、修改个人基本信息；
          <div>
            （3）	查询教师本人所讲授的课程：可按学期查询；
          </div>
          <div>
            （4）	成绩管理：选择某学期某班级某门课程，可按学号、按姓名录入、修改、送审学生成绩（成绩在送审状态和审核通过不能修改）、查看学生成绩、成绩排序（可按学号、按成绩排序）
          </div>
          <div>
            （5）	成绩查询：选择某学期某班级某门课程，可按学号、姓名查询学生成绩，不及格学生名单；
          </div>
          <div>
            （6）	数据导入导出：从Excel导入学生成绩到数据库、将数据库中的学生成绩导出到Excel；
          </div>
          <div>
            （7）	数据统计：按班级按课程统计学生的成绩，如某门课的最高分、最低分、平均分、及格率，不同分数段的学生人数及比例；
          </div>
        </el-collapse-item>
        <el-collapse-item title="学生功能" name="3" v-if="user.role !=='TEACHER'">
          <div>
            （1）	系统登录与退出，登录与退出时系统记录用户的登录、退出信息（用户名、时间、IP、状态：登录/退出）；
          </div>
          <div>
            （2）	个人信息管理：查看、修改个人基本信息；
          </div>
          <div>
            （3）	查询课程成绩：可按某学期、某门课程进行查询，也可列出该生的所有课程成绩；
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>

  </div>
</template>

<script setup>
  import request from "@/utils/request";
  import {ElMessage} from "element-plus";

  const user = JSON.parse(localStorage.getItem('login_user') || '{}')


  console.log(user)
  // 检查 user 是否为空对象
  if (Object.keys(user).length === 0) {
    // 如果 user 是空对象，显示错误消息
    ElMessage.error("无token，请先登录！！！");
  }

  const toGit = ()=>{
    //location.href = "https://github.com/brbr194/student_sys"
    window.open('https://github.com/brbr194/student_sys', '_blank');
  }
</script>

<style>
.custom-collapse {
  font-size: 100px; /* 设置你想要的字体大小 */
}
</style>