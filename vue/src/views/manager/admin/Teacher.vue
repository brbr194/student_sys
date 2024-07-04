<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.name" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入教师姓名进行查询"></el-input>
      <el-input  v-model="data.teacherNumber" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入教师工号进行查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-upload :action="data.updateUrl" style="display: inline-block; margin-left: 10px; margin-right: 10px" :show-file-list="false" :on-success="successUpload">
          <el-button type="info">从Excel批量导入</el-button>
        </el-upload>
        <el-button type="info" @click="handleExport">批量导出到Excel</el-button>
      </div>

      <el-table stripe :data="data.tableData" ref="tableRef" >
        <el-table-column fixed label="教师工号" prop="teacherNumber" ></el-table-column>
        <el-table-column label="教师姓名" prop="name" ></el-table-column>
        <el-table-column label="学院" prop="departmentName"></el-table-column>
        <el-table-column label="手机号" prop="phone" ></el-table-column>
        <el-table-column label="邮箱" prop="email" ></el-table-column>
        <el-table-column label="创建时间" prop="createdTime" ></el-table-column>
        <el-table-column label="更新时间" prop="updatedTime" ></el-table-column>
        <el-table-column fixed="right" label="操作" align="center" width="200px" >
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


    <el-dialog title="新增或编辑教师信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef" :rules="rules">

        <el-form-item label="教师工号：" prop="teacherNumber" required >
          <el-input v-model="data.form.teacherNumber" autocomplete="off" />
        </el-form-item>

        <el-form-item label="密码：" prop="password" required>
          <el-input v-model="data.form.password" autocomplete="off" show-password  />
        </el-form-item>

        <el-form-item label="教师姓名：" prop="name" required>
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>

        <el-form-item label="学院：" prop="departmentId" required>
          {{data.form.departmentName}}
          <el-select
              v-model="data.form.departmentId"
              clearable
              placeholder="请选择学院"
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
  teacherNumber:'',
  departments:[],
  user : JSON.parse(localStorage.getItem('login_user') || '{}'),
  updateUrl:'',
  exportUrl:''
})

const loadUpdateURL = () =>{
  switch (process.env.NODE_ENV) {
    case 'development':
      data.updateUrl = "http://localhost:9090/api/teacher/upload"  //开发环境url
      break
    case 'production':
      data.updateUrl = "http://carrocean.top:9090/api/teacher/upload"   //生产环境url
      break
  }
}
const loadExportURL = () =>{
  switch (process.env.NODE_ENV) {
    case 'development':
      data.exportUrl = "http://localhost:9090/api/teacher/export"  //开发环境url
      break
    case 'production':
      data.exportUrl = "http://carrocean.top:9090/api/teacher/export"   //生产环境url
      break
  }
}
loadUpdateURL()
loadExportURL()
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
  loadDepartment()
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
  loadDepartment()
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
    request.delete('/teacher/delete/' + id).then(res=>{
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
const loadDeptName = ()=>{
  for(let i = 0;i<data.tableData.length;i++){
    let row = data.tableData[i];
    for(let j = 0;j<data.departments.length;j++){
      if(row.departmentId === data.departments[j].id){
        data.tableData[i].departmentName = data.departments[j].department;
      }
    }
  }
}
//分页查询加载
const load = () =>{
  request.get('/teacher/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name:data.name,
      teacherNumber:data.teacherNumber
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      loadDepartment()
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
      loadDeptName()
    }
  })
}
load()
loadDepartment()
loadDeptName()
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
  request.get('/teacher/selectPage',{
    params:{
      pageNum:1,
      pageSize:data.pageSize,
      name : '',
      username:'',
      teacherNumber:''
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.name = ''
      data.username = ''
      data.teacherNumber=''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
      loadDeptName()
    }

  })
}
const formRef = ref();
//校验规则
const rules = reactive({
  teacherNumber: [
    { required: true, message: '请输入教师工号', trigger: 'blur' },
    { min: 3, max: 15, message: '教师工号长度需在3-15个字符之内', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { pattern:/^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{1,50}$/ ,min: 6, max: 15, message: '密码长度需在6-15个字符之内，并且必须包含数字和字母', trigger: 'blur' , },
  ],
  name: [
    { required: true, message: '请输入教师姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '教师姓名长度需在2-10个字符之内', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern:/^1[3-9]\d{9}$/ ,message: '手机号格式有误', trigger: 'blur' },
  ],
})

//把添加的信息保存到数据库
const save = () =>{
  formRef.value.validate((valid)=>{
    if(valid){
      request.request({
        url: data.form.id? '/teacher/update':'/teacher/add',
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


const clear = (f)=>{
  if(f === true){
    data.form.departmentId = null
  }
}


const successUpload = (res) =>{
  if (res.code === '200') {
    console.log(res.msg)
    if(res.msg === ''){
      ElMessage.success("批量导入成功")
    }else{
      ElMessage.success(res.msg)
    }
    load()
  } else {
    ElMessage.error(res.msg)
  }
}


const handleExport = () =>{
  console.log(data.exportUrl)
  location.href = data.exportUrl +'?token='  + data.user.token
}

</script>