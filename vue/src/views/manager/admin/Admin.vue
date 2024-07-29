<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.name" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入管理员姓名进行查询"></el-input>
      <el-input  v-model="data.username" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入管理员用户名进行查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="管理员姓名" prop="name"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="创建时间" prop="createdTime"></el-table-column>
        <el-table-column label="更新时间" prop="updatedTime"></el-table-column>
<!--        <el-table-column label="密码" prop="password"></el-table-column>-->
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)" v-if="scope.row.name !== login_user.name" >编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)" v-if="scope.row.name !== login_user.name">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
<!--    <div class="card">-->
<!--      <el-pagination v-model:page-size="data.pageSize"-->
<!--                     v-model:current-page="data.pageNum"-->
<!--                     v-model:total="data.total"-->
<!--                     @current-change="handleCurrentChange"-->
<!--                     background layout="prev, pager, next"  />-->
<!--    </div>-->

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


    <el-dialog title="新增或编辑管理员信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef" :rules="rules">
        <el-form-item label="管理员姓名：" prop="name" required>
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="管理员用户名：" prop="username" required>
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码：" prop="password" required>
          <el-input v-model="data.form.password" autocomplete="off" show-password  />
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
  username:''
})

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    console.log('删除')
    request.delete('/admin/delete/' + id).then(res=>{
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
  request.get('/admin/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name:data.name,
      username:data.username
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
  request.get('/admin/selectPage',{
    params:{
      pageNum:1,
      pageSize:data.pageSize,
      name : '',
      username:''
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.name = ''
      data.username = ''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
    }

  })
}
const formRef = ref();
//校验规则
const rules = reactive({
  username: [
    { required: true, message: '请输入管理员用户名', trigger: 'blur' },
    { min: 3, max: 15, message: '管理员用户名长度需在3-15个字符之内', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 20, message: '密码长度需在3-20个字符之内', trigger: 'blur' , },
  ],
  name: [
    { required: true, message: '请输入管理员姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '管理员姓名长度需在2-10个字符之内', trigger: 'blur' },
  ],
})

//把添加的信息保存到数据库
const save = () =>{
  formRef.value.validate((valid)=>{
  if(valid){
    request.request({
      url: data.form.id? '/admin/update':'/admin/add',
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

const login_user = JSON.parse(localStorage.getItem('login_user') || '{}')

</script>