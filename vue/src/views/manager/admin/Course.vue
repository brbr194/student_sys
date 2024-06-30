<template >
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.courseNo" :prefix-icon="Search" style="width: 250px; margin-right: 10px; " placeholder="请输入课程编号进行查询"></el-input>
      <el-input  v-model="data.courseName" :prefix-icon="Search" style="width: 250px; margin-right: 10px;" placeholder="请输入课程名进行查询"></el-input>
<!--      <el-input  v-model="data.credits" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程学分进行查询"></el-input>-->
      <el-select
          v-model="data.semester"
          clearable
          placeholder="选择学期进行查询"
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

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="课程编号" prop="courseNo"></el-table-column>
        <el-table-column label="课程名" prop="courseName"></el-table-column>
        <el-table-column label="授课教师" prop="teacherName"></el-table-column>
        <el-table-column label="课程开设学期" prop="semester"></el-table-column>
        <el-table-column label="课程学分" prop="credits"></el-table-column>
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

    <el-dialog title="新增或编辑年级信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef">
        <el-form-item label="课程编号：" prop="courseNo" required>
          <el-input v-model="data.form.courseNo" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程名：" prop="courseName" required>
          <el-input v-model="data.form.courseName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程开设学期：" prop="semester" required>
          <el-select
              v-model="data.form.semester"
              clearable
              placeholder="选择学期"
              style="width: 100%;margin-bottom: 5px"
          >
            <el-option
                v-for="se in data.semesters"
                :key="se.semesterName"
                :label="se.semesterName"
                :value="se.semesterName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="授课教师：" prop="teacherId" required>
          {{data.form.teacherName}}
          <el-select
              v-model="data.form.teacherId"
              clearable
              placeholder="选择授课教师"
              style="width: 100%;margin-bottom: 5px"
          >
            <el-option
                v-for="te in data.teachers"
                :key="te.id"
                :label="te.name"
                :value="te.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程学分：" prop="credits" required>
          <el-input v-model="data.form.credits" autocomplete="off" />
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
  courseName:'',
  courseNo:'',
  semester:'',
  credits: '',
  pageNum: 1,//当前页码
  formVisible: false,
  form: {},
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  semesters:[],
  teachers:[]
})
const formRef = ref();

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
  loadSemester()
  loadTeacher()
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
  loadSemester()
  loadTeacher()
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
const loadTeacher = ()=>{
  request.get('/teacher/all',).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data)
      if(res.data !== []){
        data.teachers = JSON.parse(JSON.stringify(res.data))
      }else{
        data.teachers = []
      }
    }
  })
}

const handleDelete = (id) => {
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
loadSemester()
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

//把添加的信息保存到数据库
const save = () =>{
  formRef.value.validate((valid)=> {
    if (valid) {
      request.request({
        url: data.form.id ? '/course/update' : '/course/add',
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
    }else{
      ElMessage.error("请填写表单必填字段！")
    }
  })
}


</script>
