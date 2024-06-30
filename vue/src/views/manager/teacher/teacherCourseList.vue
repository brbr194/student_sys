<template >
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-select
          v-model="data.semester"
          clearable
          placeholder="选择学期"
          style="width: 250px; margin-right: 10px"
      >
        <el-option
            v-for="se in data.semesters"
            :key="se.semesterName"
            :label="se.semesterName"
            :value="se.semesterName"
        />
      </el-select>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div class="card" style="line-height: 30px;margin-bottom: 10px">
      <div style="color: #6E77F2">以下是所有教授的课程的情况</div>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="课程编号" prop="courseNo"></el-table-column>
        <el-table-column label="课程名" prop="courseName"></el-table-column>
        <el-table-column label="任课教师" prop="teacherName" v-if="data.user.role !== 'TEACHER'"></el-table-column>
        <el-table-column label="课程开设学期" prop="semester"></el-table-column>

        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <!--            <el-button type="primary" @click="handleEdit(scope.row)"  >编辑</el-button>
                        <el-button type="danger" @click="handleDelete(scope.row.id)" >删除</el-button>-->
            <el-button type="danger" @click="deleteSelect(scope.row.id)" v-if="data.user.role === 'ADMIN'">删除此课程</el-button>
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
  courseName:'',
  courseNo:'',
  semester:'',
  pageNum: 1,//当前页码
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  semesters:[],
  user:JSON.parse(localStorage.getItem('login_user') || '{}')
})

const formRef = ref();

//分页查询加载
const load = () =>{
  let params = {
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    semester:data.semester
  }
  if(data.user.role === 'TEACHER'){//如果是老师就查自己的数据
    params.teacherId = data.user.id;
  }
  request.get('/course/selectPage',{
    params:params
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
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
    pageNum:1,
    pageSize:data.pageSize,
    semester:''
  }
  if(data.user.role === 'TEACHER'){//如果是老师就查自己的数据
    params.teacherId = data.user.id;
  }
  request.get('/course/selectPage',{
    params:params
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      //data.courseNo=''
      //data.courseName=''
      data.semester=''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
    }

  })
}

const loadSemester = ()=>{
  request.get('/semester/all',).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data)
      if(res.data !== []){
        data.semesters = JSON.parse(JSON.stringify(res.data))
      }else{
        data.semesters = []
      }
    }
  })
}
loadSemester()
const deleteSelect = (id)=>{
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    console.log('删除')
    request.delete('/course/delete/' + id).then(res=>{
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
</script>
