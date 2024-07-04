<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.major" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入专业进行查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="info" @click="handleExport">批量导出到Excel</el-button>
        <el-upload :action="data.updateUrl" style="display: inline-block; margin-left: 10px" :show-file-list="false" :on-success="successUpload">
          <el-button type="info">从Excel批量导入</el-button>
        </el-upload>
      </div>

      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="专业名" prop="major"></el-table-column>
        <el-table-column label="创建时间" prop="createdTime"></el-table-column>
        <el-table-column label="更新时间" prop="updatedTime"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)"  >编辑</el-button>
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


    <el-dialog title="新增或编辑专业信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef">
        <el-form-item label="专业名：" prop="major" required>
          <el-input v-model="data.form.major" autocomplete="off" />
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



//数据定义
const data = reactive({
  major: '',
  pageNum: 1,//当前页码
  formVisible: false,
  form: {},
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  majors:[],
  user : JSON.parse(localStorage.getItem('login_user') || '{}'),
  updateUrl:'',
  exportUrl:''
})
const formRef = ref();

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
    request.delete('/major/delete/' + id).then(res=>{
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


const loadUpdateURL = () =>{
  switch (process.env.NODE_ENV) {
    case 'development':
      data.updateUrl = "http://localhost:9090/api/major/upload"  //开发环境url
      break
    case 'production':
      data.updateUrl = "http://carrocean.top:9090/api/major/upload"   //生产环境url
      break
  }
}
const loadExportURL = () =>{
  switch (process.env.NODE_ENV) {
    case 'development':
      data.exportUrl = "http://localhost:9090/api/major/export"  //开发环境url
      break
    case 'production':
      data.exportUrl = "http://carrocean.top:9090/api/major/export"   //生产环境url
      break
  }
}
loadUpdateURL()
loadExportURL()
//分页查询加载
const load = () =>{
  request.get('/major/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      major:data.major
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
  request.get('/major/selectPage',{
    params:{
      pageNum:1,
      pageSize:data.pageSize,
      major:''
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.name = ''
      data.username = ''
      data.major= ''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
    }

  })
}

//把添加的信息保存到数据库
const save = () =>{
  formRef.value.validate((valid)=> {
    if (valid) {

      request.request({
        url: data.form.id ? '/major/update' : '/major/add',
        method: data.form.id ? 'PUT' : 'POST',
        data: data.form
      }).then(res => {
        if (res.code === '200') {
          load()
          ElMessage.success("操作成功")
          data.formVisible = false
        } else {
          ElMessage.error(res.msg)
        }
      })
    } else {
      ElMessage.error("请填写表单必填字段！")
    }
  })
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
