<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.operatorName" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入操作人姓名进行查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="操作人姓名" prop="operatorName"></el-table-column>
        <el-table-column label="操作内容" prop="contest">
          <template #default="scope">
            <el-tag :type="scope.row.contest==='管理员登录' || scope.row.contest==='学生登录' || scope.row.contest==='教师登录'?'success':'danger'" effect="dark">{{scope.row.contest}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作IP" prop="ip"></el-table-column>
        <el-table-column label="操作时间" prop="time"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="danger" @click="handleDelete(scope.row.id)" v-if="data.user.role === 'ADMIN' ">删除</el-button>
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

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Search} from "@element-plus/icons-vue";



//数据定义
const data = reactive({
  pageNum: 1,//当前页码
  formVisible: false,
  form: {},
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  user:JSON.parse(localStorage.getItem('login_user') || '{}'),
  operatorName:''
})
const formRef = ref();

const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    console.log('删除')
    request.delete('/log/delete/' + id).then(res=>{
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
  let params = {
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    operatorName:data.operatorName,
  }
  if(data.user.role !== 'ADMIN'){
    params.userId = data.user.id
  }
  request.get('/log/selectPage',{
    params:params
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
  let params = {
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    operatorName:'',
  }
  if(data.user.role !== 'ADMIN'){
    params.userId = data.user.id
  }
  request.get('/log/selectPage',{
    params:params
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.name = ''
      data.username = ''
      data.operatorName= ''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
    }

  })
}

</script>
