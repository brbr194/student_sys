<template >
  <div class="login-container">
    <div style="width: 350px" class="login-box">
      <div style="font-weight: bold;font-size: 24px;text-align: center;margin-bottom: 30px">学生成绩管理系统登录</div>
      <el-form :model="data.form" ref="formRef" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="User" v-model="data.form.username" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item  prop="password">
          <el-input show-password prefix-icon="Lock" v-model="data.form.password" placeholder="请输入密码"/>
        </el-form-item>
        
        <el-select
            v-model="roles"
            clearable
            placeholder="选择登录角色"
            style="width: 100%;margin-bottom: 30px"
        >
          <el-option
              v-for="role in options"
              :key="role.value"
              :label="role.label"
              :value="role.value"
          />
        </el-select>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login()">登 录</el-button>
          </el-form-item>
        <div class="button-container">
        <el-form-item>
          还没有账号？
          <el-button type="info" class="button-right" style="width: 35%" @click="register()">去 注 册</el-button>
        </el-form-item>
        </div>
      </el-form>
<!--      <div style="margin-top: 30px;text-align: right">-->
<!--        还没有账号？请<a href="">注册</a>-->
<!--      </div>-->
    </div>
  </div>
</template>


<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";

const data = reactive({
  form:{
  }
})
const formRef = ref();
//登录调用
const login = () =>{
  formRef.value.validate((valid)=>{
    if(valid){
      if(roles.value === 'student'){
        data.form.studentNumber = data.form.username
      }
      if(roles.value === 'teacher'){
        data.form.teacherNumber = data.form.username
      }
      data.form.role = roles.value

      request.post(`/${roles.value}/login`,data.form).then(res=>{
        if (res.code === '200') {
          localStorage.setItem('login_user', JSON.stringify(res.data))
          router.push('/home') // 跳转到主页
          ElMessage.success('登录成功')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

const register = () =>{
  ElMessage.error("功能未开放，请联系后台管理员！")
}

//校验规则
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 15, message: '长度应为 3 到 15', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 20, message: '长度应为 3 到 20', trigger: 'blur' },
  ],
})

//角色选择
const roles = ref('')
const options = [
  {
    value: 'student',
    label: '学生',
  },
  {
    value: 'teacher',
    label: '教师',
  },
  {
    value: 'admin',
    label: '管理员',
  },
]
</script>



<style>
.login-container{
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("@/assets/imgs/background.png");
  background-size: cover;
}
.login-box{
  background-color: rgba(255,255,255,0.7);
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  padding: 30px;
  border-radius: 5px;
}
.button-container {
  display: flex;
  justify-content: flex-end; /* 这会把子元素推到容器的右边 */
}
</style>
