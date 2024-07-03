<template>
  <div class="card" style="line-height: 30px;margin-bottom: 10px">
    <div style="color: #6E77F2">您可以在此处更新您的个人信息</div>
  </div>
  <div>
    <div class="card" style="width: 60%; padding: 40px">
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef" :rules="rules">

        <el-form-item label="管理员账号：" prop="username" required >
          <el-input v-model="data.form.username" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="密码：" prop="password" required>
          <el-input v-model="data.form.password" autocomplete="off" show-password  />
        </el-form-item>
        <el-form-item label="管理员姓名：" prop="name" required>
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">保 存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request";
import router from "@/router";
import {ElMessage} from "element-plus";

const login_user = JSON.parse(localStorage.getItem('login_user') || '{}')

const data = reactive({
  form: JSON.parse(localStorage.getItem('login_user') || "{}"),
  grades:[],
  departments:[],
  tempDeptId:''

})


const logout = () => {
  let t_user={
    id:login_user.id
  }
  request.post('/admin/logout', t_user ).then(res=>{
    console.log(res.code)
    if (res.code === '200') {
      ElMessage.success("退出成功")
      localStorage.removeItem('login_user')
    }else {
      ElMessage.error(res.msg)
    }
  })
}


const update = () =>{
  formRef.value.validate((valid)=>{
    if(valid){
      request.request({
        url: '/admin/update',
        method: 'PUT',
        data: data.form
      }).then(res =>{
        if(res.code === '200'){
          logout()
          ElMessage.success("更新成功，请重新登录！")
          router.push('/login')
          localStorage.removeItem("login_user")

        }else{
          ElMessage.error(res.msg)
        }
      })
    }
  })
}




const formRef = ref();
//校验规则
const rules = reactive({
  studentNumber: [
    { required: true, message: '请输入管理员账号', trigger: 'blur' },
    { min: 3, max: 15, message: '管理员账号长度需在3-15个字符之内', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { pattern:/^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{1,50}$/ ,min: 6, max: 15, message: '密码长度需在6-15个字符之内，并且必须包含数字和字母', trigger: 'blur' , },
  ],
  name: [
    { required: true, message: '请输入管理员姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '管理员姓名长度需在2-10个字符之内', trigger: 'blur' },
  ],

})

const clear = (f)=>{
  if(f === true){
    data.form.departmentId = null
  }
}
</script>

