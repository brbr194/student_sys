<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.courseNo" :prefix-icon="Search" style="width: 250px; margin-right: 10px; margin-bottom: 10px" placeholder="请输入课程编号进行查询"></el-input>
      <el-input  v-model="data.courseName" :prefix-icon="Search" style="width: 250px; margin-right: 10px;margin-bottom: 10px" placeholder="请输入课程名进行查询"></el-input>
      <br>
      <el-input  v-model="data.credits" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程学分进行查询"></el-input>
      <el-input  v-model="data.semester" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程开设学期进行查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="课程编号" prop="courseNo"></el-table-column>
        <el-table-column label="课程名" prop="courseName"></el-table-column>
        <el-table-column label="课程开设学期" prop="semester"></el-table-column>
        <el-table-column label="课程学分" prop="credits"></el-table-column>
        <el-table-column label="创建时间" prop="createdTime"></el-table-column>
        <el-table-column label="更新时间" prop="updatedTime"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
<!--            <el-button type="primary" @click="handleEdit(scope.row)"  >编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)" >删除</el-button>-->
            <el-button type="info" @click="selectCourse(scope.row)" >选择授课</el-button>
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
  credits: '',
  pageNum: 1,//当前页码
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  semesters:[],
  user:JSON.parse(localStorage.getItem('login_user') || '{}')
})
const formRef = ref();

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

//分页查询加载
const load = () =>{
  request.get('/course/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      courseNo:data.courseNo,
      courseName:data.courseName,
      credits:data.credits,
      semester:data.semester
    }
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
  request.get('/course/selectPage',{
    params:{
      pageNum:1,
      pageSize:data.pageSize,
      courseNo:'',
      courseName:'',
      credits:'',
      semester:''
    }
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.courseNo=''
      data.courseName=''
      data.credits=''
      data.semester=''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
    }

  })
}

const selectCourse = (row) =>{
  console.log(row)
  request.post('/teacherCourse/add',
      {teacherId:data.user.id,
        courseName: row.courseName,
        courseNo: row.courseNo,
        courseId:row.id,
        semester:row.semester
      }).then(res=>{
        if(res.code ==='200'){
          ElMessage.success("选择成功")
        }else{
          ElMessage.error(res.msg)
        }
  })
}
</script>
