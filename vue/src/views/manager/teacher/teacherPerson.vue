<template>
  <div class="card" style="line-height: 30px;margin-bottom: 10px">
    <div style="color: #6E77F2">您可以在此处更新您的个人信息</div>
  </div>
  <div>
    <div class="card" style="width: 60%; padding: 40px">
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef" :rules="rules">

        <el-form-item label="教师工号：" prop="teacherNumber" required >
          <el-input v-model="data.form.teacherNumber" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="密码：" prop="password" required>
          <el-input v-model="data.form.password" autocomplete="off" show-password  />
        </el-form-item>
        <el-form-item label="教师姓名：" prop="name" required>
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>

        <el-form-item label="学院：" prop="departmentName" required>
          {{data.form.departmentName}}
          <el-select
              v-model="data.form.departmentId"
              clearable
              placeholder="请选择要更改成的学院"
              style="width: 90%;margin-bottom: 5px"
              @visible-change="clear"

          >
            <el-option
                v-for="dept in data.departments"
                :key="dept.id"
                :label="dept.department"
                :value="dept.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号：" prop="phone" required>
          <el-input v-model="data.form.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱：" prop="email" required>
          <el-input v-model="data.form.email" autocomplete="off" />
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


const loadDepartment = ()=>{
  request.get('/department/all',).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{

      if(res.data !== []){
        data.departments = JSON.parse(JSON.stringify(res.data))
        loadDeptName()
      }else{
        data.departments = []
      }
    }
  })
}

loadDepartment()

const loadDeptName = ()=>{
    for(let j = 0;j<data.departments.length;j++){
      if(data.form.departmentId === data.departments[j].id){
        data.form.departmentName = data.departments[j].department;
      }
    }
}

const logout = () => {
  let t_user={
    id:login_user.id
  }
  request.post('/teacher/logout', t_user ).then(res=>{
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
        url: '/teacher/update',
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
    { required: true, message: '请输入教师工号', trigger: 'blur' },
    { min: 3, max: 15, message: '教师工号长度需在3-15个字符之内', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 20, message: '密码长度需在3-20个字符之内', trigger: 'blur' , },
  ],
  name: [
    { required: true, message: '请输入教师姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '教师姓名长度需在2-10个字符之内', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '请输入教师手机号', trigger: 'blur' },
    { pattern:/^1[3-9]\d{9}$/ ,message: '手机号格式有误', trigger: 'blur' },
  ],
})

const clear = (f)=>{
  if(f === true){
    data.form.departmentId = null
  }
}
</script>

