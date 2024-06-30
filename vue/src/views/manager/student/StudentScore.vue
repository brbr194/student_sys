<template >
  <div>

    <div class="card" style="margin-bottom: 10px;">
         <el-input  v-model="data.courseName" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程名进行查询"></el-input>
      <el-select
          v-model="data.semester"
          clearable
          placeholder="选择学期"
          style="width: 250px; margin-right: 10px;margin-top: 10px"
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
    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="课程编号" prop="courseNo"></el-table-column>
        <el-table-column label="课程名" prop="courseName"></el-table-column>
        <el-table-column label="任课教师" prop="teacherName" ></el-table-column>
        <el-table-column label="课程开设学期" prop="semester"></el-table-column>
        <el-table-column label="课程分数" prop="score">
          <template #default="scope">
            <el-tag :type="scope.row.score>=90?'success':scope.row.score>=80?'info':scope.row.score>=70?'primary':scope.row.score>=60?'warning':'danger'" effect="dark" size="large">{{scope.row.score}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="课程评语" prop="comment"></el-table-column>
        <el-table-column label="课程反馈" prop="feedback"></el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button type="info" @click="handelEdit(scope.row)" v-if="data.user.role ==='STUDENT'">课程反馈</el-button>
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
  <el-dialog title="反馈信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
    <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef">
      <el-form-item label="分数：" prop="score" v-if="data.user.role ==='ADMIN'">
        <el-input v-model="data.form.score"  autocomplete="off" />
      </el-form-item>
      <el-form-item label="评语：" prop="comment" v-if="data.user.role ==='ADMIN'">
        <el-input v-model="data.form.comment" type="textarea" autocomplete="off" />
      </el-form-item>
      <el-form-item label="课程反馈：" prop="feedback" v-if="data.user.role ==='STUDENT'">
        <el-input v-model="data.form.feedback" type="textarea" autocomplete="off" />
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="clear">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
    </template>
  </el-dialog>


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
  studentNumber:'',
  pageNum: 1,//当前页码
  tableData: [],
  total:0,
  studentName:'',
  pageSize:5,//一页的条数
  semesters:[],
  form:{},
  user:JSON.parse(localStorage.getItem('login_user') || '{}')
})

const formRef = ref();

//分页查询加载
const load = () =>{
  let params = {
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    courseName:data.courseName,
    semester:data.semester,
  }
  if(data.user.role === 'STUDENT'){//如果是老师就查自己的数据
    params.studentId = data.user.id;
  }
  console.log(params)
  request.get('/score/selectPage',{
    params:params
  }).then(res =>{
    console.log(res.data)
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
    courseName:'',
    semester:'',
  }
  if(data.user.role === 'STUDENT'){//如果是学生就查自己的数据
    params.studentId = data.user.id;
  }
  request.get('/score/selectPage',{
    params:params
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.courseName=''
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
const handelEdit =  (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const save = () => {

  request.put('/score/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      data.formVisible = false  // 关闭弹窗
      ElMessage.success("操作成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const clear = () =>{
  data.formVisible = false
  data.form = {}
}
</script>
