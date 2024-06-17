<template>
  <div class="card" style="line-height: 30px;margin-bottom: 10px">
    <div style="color: #6E77F2">您可以在此处更新您的个人信息</div>
  </div>
  <div>
    <div class="card" style="width: 60%; padding: 40px">
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef" :rules="rules">

        <el-form-item label="学生学号：" prop="studentNumber" required >
          <el-input v-model="data.form.studentNumber" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="密码：" prop="password" required>
          <el-input v-model="data.form.password" autocomplete="off" show-password  />
        </el-form-item>
        <el-form-item label="学生姓名：" prop="name" required>
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别：" prop="gender" required>
          <el-select
              v-model="data.form.gender"
              clearable
              placeholder="选择性别"
              style="width: 100%;margin-bottom: 5px"
          >
            <el-option
                v-for="gender in options"
                :key="gender.value"
                :label="gender.label"
                :value="gender.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="年级：" prop="grade" required>
          <el-select
              v-model="data.form.grade"
              clearable
              placeholder="选择年级"
              style="width: 100%;margin-bottom: 5px"
          >
            <el-option
                v-for="gra in data.grades"
                :key="gra.grade"
                :label="gra.grade"
                :value="gra.grade"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄：" prop="age" required>
          <el-input v-model="data.form.age" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学院：" prop="department" required>
          <el-select
              v-model="data.form.department"
              clearable
              placeholder="选择学院"
              style="width: 100%;margin-bottom: 5px"
          >
            <el-option
                v-for="dept in data.departments"
                :key="dept.department"
                :label="dept.department"
                :value="dept.department"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="专业：" prop="major" required>
          <el-input v-model="data.form.major" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号：" prop="phoneNumber" required>
          <el-input v-model="data.form.phoneNumber" autocomplete="off" />
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
  departments:[]

})

const loadGrade = () =>{
  request.get('/grade/all',).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{

      if(res.data !== []){
        data.grades = JSON.parse(JSON.stringify(res.data))
      }else{
        data.grades = []
      }
    }
  })
}

const loadDepartment = ()=>{
  request.get('/department/all',).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{

      if(res.data !== []){
        data.departments = JSON.parse(JSON.stringify(res.data))
      }else{
        data.departments = []
      }
    }
  })
}
loadGrade()
loadDepartment()

const update = () =>{
  formRef.value.validate((valid)=>{
    if(valid){
      request.request({
        url: '/student/update',
        method: 'PUT',
        data: data.form
      }).then(res =>{
        if(res.code === '200'){
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
    { required: true, message: '请输入学生学号', trigger: 'blur' },
    { min: 3, max: 15, message: '学生学号长度需在3-15个字符之内', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { pattern:/^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{1,50}$/ ,min: 6, max: 15, message: '密码长度需在6-15个字符之内，并且必须包含数字和字母', trigger: 'blur' , },
  ],
  name: [
    { required: true, message: '请输入学生姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '学生姓名长度需在2-10个字符之内', trigger: 'blur' },
  ],
  age: [
    { required: true, message: '请输入学生年龄', trigger: 'blur' },
    { pattern:/^(0|[1-9]|[1-9][0-9]|100)$/ ,message: '学生年龄需在0-100之内', trigger: 'blur' },
  ],
  phoneNumber: [
    { required: true, message: '请输入学生手机号', trigger: 'blur' },
    { pattern:/^1[3-9]\d{9}$/ ,message: '手机号格式有误', trigger: 'blur' },
  ],
})
const options = [
  {
    value: '男',
    label: '男',
  },
  {
    value: '女',
    label: '女',
  },

]

</script>

