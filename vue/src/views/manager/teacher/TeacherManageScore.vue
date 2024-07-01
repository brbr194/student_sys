<template >
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input  v-model="data.studentName" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入学生姓名进行查询" v-if="data.user.role !== 'STUDENT'"></el-input>
      <el-input  v-model="data.courseName" :prefix-icon="Search" style="width: 250px; margin-right: 10px" placeholder="请输入课程名进行查询"></el-input>
      <br>
      <el-input  v-model="data.studentNumber" :prefix-icon="Search" style="width: 250px; margin-right: 10px;margin-top: 10px"  placeholder="请输入学生学号进行查询" v-if="data.user.role !== 'STUDENT'"></el-input>
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
      是否筛选不及格学生名单:
      <el-switch v-model="sw1"
                 inline-prompt
                 active-text="是"
                 inactive-text="否"
                 active-value="1"
                 inactive-value="0"
                 @change="handleSwitchChange"
      />
      &ensp;&ensp;&ensp;&ensp;
      成绩排序规则：
      <el-select
          v-model="data.selectRule"
          clearable
          placeholder="选择成绩排序规则"
          style="width: 15%;margin-right: 10px"
          :value-on-clear="null"
      >
        <el-option
            v-for="rule in options"
            :key="rule.value"
            :label="rule.label"
            :value="rule.value"
        />
      </el-select>
      <el-upload :action="data.updateUrl" style="display: inline-block; margin-left: 10px; margin-right: 10px" :show-file-list="false" :on-success="successUpload">
        <el-button type="info">从Excel批量导入</el-button>
      </el-upload>
      <el-button type="info" @click="handleExport">批量导出到Excel</el-button>
      <el-table stripe :data="data.tableData" ref="tableRef">
        <el-table-column label="课程编号" prop="courseNo"></el-table-column>
        <el-table-column label="课程名" prop="courseName"></el-table-column>
        <el-table-column label="选课学生学号" prop="studentNumber"></el-table-column>
        <el-table-column label="选课学生" prop="studentName"></el-table-column>
        <el-table-column label="课程开设学期" prop="semester"></el-table-column>
        <el-table-column label="课程分数" prop="score">
          <template #default="scope">
            <el-tag :type="scope.row.score>=90?'success':scope.row.score>=80?'info':scope.row.score>=70?'primary':scope.row.score>=60?'warning':'danger'" effect="dark" size="large">{{scope.row.score}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="课程评语" prop="comment"></el-table-column>
        <el-table-column label="课程反馈" prop="feedback"></el-table-column>
        <el-table-column label="成绩状态" prop="state">
          <template #default="scope">
            <el-tag :type="scope.row.state==='审核通过'?'success':scope.row.state==='回退修改'?'warning':scope.row.state==='可编辑'?'info':'primary'" effect="dark">{{scope.row.state}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button type="primary" @click="handelEdit(scope.row)"  v-if=" scope.row.state === '可编辑' || scope.row.state === '回退修改'">编辑</el-button>
            <el-button type="primary" @click="handelAudit(scope.row)"  v-if=" scope.row.state === '可编辑' || scope.row.state === '回退修改'">成绩送审</el-button>
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
    <el-form :model="data.form" label-width="150px" style="padding-right: 50px" ref="formRef">
      <el-form-item label="课程编号：" prop="courseNo">
        <el-input v-model="data.form.courseNo"  autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="课程名称：" prop="courseName">
        <el-input v-model="data.form.courseName"  autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="分数：" prop="score" required>
        <el-input v-model="data.form.score"  autocomplete="off"  />
      </el-form-item>
      <el-form-item label="评语：" prop="comment">
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


  <el-dialog title="送审成绩信息" width="40%" v-model="data.formVisible2" :close-on-click-modal="false" destroy-on-close>
    <el-form :model="data.form2" label-width="150px" style="padding-right: 50px" ref="formRef">
      <el-form-item label="课程编号：" prop="courseNo">
        <el-input v-model="data.form2.courseNo"  autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="课程名称：" prop="courseName">
        <el-input v-model="data.form2.courseName"  autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="分数：" prop="score">
        <el-input v-model="data.form2.score"  autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="评语：" prop="comment">
        <el-input v-model="data.form2.comment" type="textarea" autocomplete="off" disabled/>
      </el-form-item>
      <el-form-item label="课程反馈：" prop="feedback" >
        <el-input v-model="data.form2.feedback" type="textarea" autocomplete="off" disabled/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="clear">取 消</el-button>
        <el-button type="primary" @click="confirmAudit">确认送审</el-button>
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
  form2:{},
  user:JSON.parse(localStorage.getItem('login_user') || '{}'),
  flag:0,
  formVisible2:false,
  selectRule:null,
  updateUrl:'',
  exportUrl:''
})

const formRef = ref();
//分页查询加载
const load = () =>{
  console.log(data.selectRule)
  if(data.selectRule === ''){
    data.selectRule = null
  }

  let params = {
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    courseName:data.courseName,
    studentName:data.studentName,
    semester:data.semester,
    studentNumber:data.studentNumber,
    flag:data.flag,
    selectRule:data.selectRule
  }
  if(data.user.role === 'TEACHER'){//如果是老师就查自己的数据
    params.teacherId = data.user.id;
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


const loadUpdateURL = () =>{
  switch (process.env.NODE_ENV) {
    case 'development':
      data.updateUrl = "http://localhost:9090/api/score/upload"  //开发环境url
      break
    case 'production':
      data.updateUrl = "http://carrocean.top:9090/api/score/upload"   //生产环境url
      break
  }
}
const loadExportURL = () =>{
  switch (process.env.NODE_ENV) {
    case 'development':
      data.exportUrl = "http://localhost:9090/api/score/Texport"  //开发环境url
      break
    case 'production':
      data.exportUrl = "http://carrocean.top:9090/api/score/Texport"   //生产环境url
      break
  }
}
loadUpdateURL()
loadExportURL()
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
    studentName: '',
    courseName:'',
    semester:'',
    studentNumber:'',
    flag:data.flag,
    selectRule: 0
  }
  if(data.user.role === 'TEACHER'){//如果是老师就查自己的数据
    params.teacherId = data.user.id;
  }
  request.get('/score/selectPage',{
    params:params
  }).then(res =>{
    if(res.code !== '200'){
      ElMessage.error(res.msg)
    }else{
      console.log(res.data.list)
      data.studentName=''
      data.courseName=''
      data.semester=''
      data.studentNumber=''
      data.tableData = res.data?.list || []
      data.total = res.data?.total || 0
      data.selectRule = null
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
const handelAudit = (row)=>{
  data.form2 = JSON.parse(JSON.stringify(row))
  data.formVisible2 = true
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

const confirmAudit = () =>{
  data.form2.state = '送审'
  console.log(data.form2)
  request.put('/score/update', data.form2).then(res => {
    if (res.code === '200') {
      load()
      data.formVisible2 = false  // 关闭弹窗
      ElMessage.success("操作成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const clear = () =>{
  data.form = {}
  data.form2 = {}
  data.formVisible = false
  data.formVisible2 = false
}

const handleSwitchChange = (value)=>{
  console.log(value)
  data.flag = value
  load()
}

const sw1 = ref(false)

const options = [
  {
    value: 1,
    label: '学生学号升序',
  },
  {
    value: 2,
    label: '学生学号降序',
  },
  {
    value: 3,
    label: '成绩升序',
  },
  {
    value: 4,
    label: '成绩降序',
  },
]

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
