<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.name" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入学生姓名进行查询"></el-input>
      <el-input  v-model="data.studentNumber" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入学生学号进行查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table stripe :data="data.tableData" ref="tableRef" >
        <el-table-column fixed label="学生学号" prop="studentNumber" width="150px"></el-table-column>
        <el-table-column label="学生姓名" prop="name" width="125px"></el-table-column>
        <el-table-column label="性别" prop="gender" width="125px"></el-table-column>
        <el-table-column label="年龄" prop="age" width="125px"></el-table-column>
        <el-table-column label="年级" prop="grade" width="125px"></el-table-column>
        <el-table-column label="学院" prop="department" width="125px"></el-table-column>
        <el-table-column label="专业" prop="major" width="125px"></el-table-column>
        <el-table-column label="手机号" prop="phoneNumber" width="125px"></el-table-column>
        <el-table-column label="邮箱" prop="email" width="125px"></el-table-column>
        <el-table-column label="创建时间" prop="createdTime" width="170px"></el-table-column>
        <el-table-column label="更新时间" prop="updatedTime" width="170px"></el-table-column>
                <el-table-column fixed="right" label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)" >编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)" >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>


    <div class="card">
      <div style="margin-top: 10px">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="data.pageNum"
            :page-sizes="[3, 5, 10, 15, 20]"
            :page-size="data.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="新增或编辑学生信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef" :rules="rules">

        <el-form-item label="学生学号：" prop="studentNumber" required >
          <el-input v-model="data.form.studentNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码：" prop="password" required>
          <el-input v-model="data.form.password" autocomplete="off" show-password  />
        </el-form-item>
        <el-form-item label="学生姓名：" prop="name" required>
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
<!--        <el-form-item label="性别：" prop="gender" required>-->
<!--          <el-input v-model="data.form.gender" autocomplete="off" />-->
<!--        </el-form-item>-->
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

<!--        <el-form-item label="学院：" prop="department" required>
          <el-input v-model="data.form.department" autocomplete="off" />
        </el-form-item>-->

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


      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Search} from "@element-plus/icons-vue";
import {column} from "element-plus/es/components/table-v2/src/common";


request.get('/').then(res => {
  console.log(res)
})

//数据定义
const data = reactive({
  name: '',
  pageNum: 1,//当前页码
  formVisible: false,
  form: {},
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  username:'',
  studentNumber:'',
  grades:[],
  departments:[]
})

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
  loadGrade()
  loadDepartment()
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
  loadGrade()
  loadDepartment()
}

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
const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    console.log('删除')
    request.delete('/student/delete/' + id).then(res=>{
      if(res.code === '200'){
        load()
        ElMessage.success("删除成功")
      }else{
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
    ElMessage({
      type:'info',
      message:'取消删除操作'
    })
  })
}

//分页查询加载
const load = () =>{
  request.get('/student/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name:data.name,
      username:data.username,
      studentNumber:data.studentNumber
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
      //console.log(res.data.list)
    }else{
      //console.log(res.data.list)
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
    }

  })
}
load()
//处理当前页的变化
const handleCurrentChange = (pageNum)=>{
  data.pageNum = pageNum
  load()
}
//处理每一页条数的变化
const handleSizeChange=(pageSize)=> {
  data.pageSize = pageSize
  load()
}
//重置搜索框
const reset = () =>{
  request.get('/student/selectPage',{
    params:{
      pageNum:1,
      pageSize:data.pageSize,
      name : '',
      username:'',
      studentNumber:''
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.name = ''
      data.username = ''
      data.studentNumber=''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
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

//把添加的信息保存到数据库
const save = () =>{
  formRef.value.validate((valid)=>{
    if(valid){
      request.request({
        url: data.form.id? '/student/update':'/student/add',
        method:data.form.id? 'PUT':'POST',
        data: data.form
      }).then(res =>{
        if(res.code === '200'){
          load()
          ElMessage.success("操作成功")
          data.formVisible = false
        }else{
          ElMessage.error(res.msg)
        }
      })
    }else {
      ElMessage.error("请填写表单必填字段！")
    }
  })
}

//性别选择
const gender = ref('')
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

const login_user = JSON.parse(localStorage.getItem('login_user') || '{}')

</script>