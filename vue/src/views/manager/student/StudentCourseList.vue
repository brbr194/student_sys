<template >
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.courseNo" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程编号进行查询"></el-input>
      <el-input  v-model="data.courseName" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程名进行查询"></el-input>
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
      <div style="color: #6E77F2">以下是你所选择的课程情况</div>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="课程编号" prop="courseNo"></el-table-column>
        <el-table-column label="课程名" prop="courseName"></el-table-column>
        <el-table-column label="任课教师" prop="teacherName" v-if="data.user.role !== 'TEACHER'"></el-table-column>
        <el-table-column label="选课学生" prop="studentName" v-if="data.user.role !== 'STUDENT'"></el-table-column>
        <el-table-column label="课程开设学期" prop="semester"></el-table-column>
        <el-table-column label="打分状态" prop="state" v-if="data.user.role === 'ADMIN' ">
          <template #default="scope">
            <el-tag :type="scope.row.state==='已打分'?'success':'warning'" effect="dark">{{scope.row.state}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button type="primary" @click="addScore(scope.row)" v-if="data.user.role !== 'STUDENT' && scope.row.state === '未打分'" >打分</el-button>
            <el-button type="danger" @click="deleteSelect(scope.row.id)" v-if="data.user.role !== 'STUDENT'">取消选课</el-button>
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

  <el-dialog title="成绩信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
    <el-form :model="data.scoreForm" label-width="150px" style="padding-right: 50px" ref="formRef">
      <el-form-item label="课程名：" prop="courseName" >
        <el-input v-model="data.scoreForm.courseName" autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="分数：" prop="score" required>
        <el-input v-model="data.scoreForm.score" autocomplete="off" />
      </el-form-item>
      <el-form-item label="评语：" prop="comment" >
        <el-input v-model="data.scoreForm.comment" type="textarea" autocomplete="off" />
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
  pageNum: 1,//当前页码
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  semesters:[],
  user:JSON.parse(localStorage.getItem('login_user') || '{}'),
  scoreForm:{},
  formVisible:false
})

const formRef = ref();

//分页查询加载
const load = () =>{
  let params = {
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    courseNo:data.courseNo,
    courseName:data.courseName,
    semester:data.semester
  }
  if(data.user.role === 'STUDENT'){//如果是学生就查自己的数据
    params.studentId = data.user.id;
  }
  request.get('/studentCourse/selectPage',{
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
    courseNo:'',
    courseName:'',
    semester:''
  }
  if(data.user.role === 'STUDENT'){//如果是学生就查自己的数据
    params.studentId = data.user.id;
  }
  request.get('/studentCourse/selectPage',{
    params:params
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.courseNo=''
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
const deleteSelect = (id)=>{
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    console.log('删除')
    request.delete('/studentCourse/delete/' + id).then(res=>{
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

const addScore =  (row) => {
  data.formVisible = true
  data.scoreForm = JSON.parse(JSON.stringify(row))
  console.log(data.scoreForm)
}
const save = () => {
  formRef.value.validate((valid)=> {
    if (valid) {
      data.scoreForm.state = '已打分'
      console.log(data.scoreForm)
      request.post('/score/add', data.scoreForm).then(res => {
        if (res.code === '200') {
          load()
          data.formVisible = false  // 关闭弹窗
          data.scoreForm = {}
          ElMessage.success("操作成功")
        } else {
          ElMessage.error(res.msg)
        }
      })
    }else {
      ElMessage.error("请填写表单必填字段！")
    }
  })
}

const clear = () =>{
  data.formVisible = false
  data.scoreForm = []
}
</script>
