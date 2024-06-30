<template >
  <div>
    <div class="card" style="line-height: 30px;margin-bottom: 10px">
      <div style="color: #883bea;text-align: center" >课程统计模块</div>
    </div>
    <el-button type="info" @click="data.dialogVisible = true">
      选择要查看统计的课程
    </el-button>

    <el-dialog
        v-model="data.dialogVisible"
        title="选择一门课程查看统计情况"
        width="75%"

    >
      <div class="card" style="margin-bottom: 10px;">
        根据学期筛选：
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

      <div class="card" style="margin-bottom: 10px">
        <el-table stripe :data="data.tableData" ref="tableRef" height="300px">
          <el-table-column label="课程编号" prop="courseNo"></el-table-column>
          <el-table-column label="课程名" prop="courseName"></el-table-column>
          <el-table-column label="任课教师" prop="teacherName" v-if="data.user.role !== 'TEACHER'"></el-table-column>
          <el-table-column label="课程开设学期" prop="semester"></el-table-column>
          <el-table-column label="操作" align="center" width="160">
            <template #default="scope">
              <el-button type="info" @click="handleSelect(scope.row)">查看统计情况</el-button>
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
    </el-dialog>

    <div class="card" style="margin-top: 10px">

      <h3 style="color: #e5315b">课程编号：{{data.courseNo}}</h3>
      <h3 style="color: #e5315b">课程名称：{{data.courseName}}</h3>

      <el-row>
        <el-col :span="6">
          <el-statistic title="课程最高分" :value="data.statistics.max" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="课程最低分" :value="data.statistics.min" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="课程平均分" :value="data.statistics.avg" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="课程及格率（%）" :value="data.statistics.rate" precision="2"/>
        </el-col>
      </el-row>
      <el-row :gutter="10" style="margin-top: 50px">
        <el-col :span="12">
          <div ref="chart1" style="width: 500px;height: 400px"></div>
        </el-col>
        <el-col :span="12">
          <div ref="chart2" style="width: 500px;height: 400px"></div>
        </el-col>
      </el-row>



    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import * as echarts from "echarts";

//数据定义
const data = reactive({
  courseName:'请选择课程',
  courseNo:'请选择课程',
  semester:'',
  pageNum: 1,//当前页码
  tableData: [],
  total:0,
  pageSize:5,//一页的条数
  semesters:[],
  course:{},
  user:JSON.parse(localStorage.getItem('login_user') || '{}'),
  drawer:false,
  dialogVisible:false,
  rate1:0,
  rate2:0,
  rate3:0,
  rate4:0,
  rate5:0,

  statistics:{
    max:0,
    min:0,
    avg:0,
    rate:0.00,
    allCnt:0,
    category:['60分以下','60-69.99','70-79.99','80-89.99','90-100'],
    scoreCnt:[0,0,0,0,0]
  }
})

const formRef = ref();

let option = {
  xAxis: {
    type: 'category',
    data: data.statistics.category
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: data.statistics.scoreCnt,
      type: 'bar'
    }
  ]
}

let option2 = {
  title: {
    text: '各分数段人数占比',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '所占比例（%）',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 0, name: '60分以下' },
        { value: 0, name: '60-69.99分' },
        { value: 0, name: '70-79.99分' },
        { value: 0, name: '80-89.99分' },
        { value: 0, name: '90-100分' },
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}
const handleSelect = (row) => {
  /*let params = {
   courseId: row.id,
  }
  if(data.user.role === 'TEACHER'){//如果是老师就查自己的数据
    params.teacherId = data.user.id;
  }*/
  console.log(row)
  request.get('/score/analysis/'+ row.id).then(res=>{
    if(res.code === '200'){
      init()
      console.log(res.data)
      data.statistics = res.data
      console.log(data.statistics)
      option.series[0].data = res.data.scoreCnt
      data.courseNo = row.courseNo
      data.courseName = row.courseName
      data.rate1 = res.data.scoreCnt[0]/res.data.allCnt
      data.rate2 = res.data.scoreCnt[1]/res.data.allCnt
      data.rate3 = res.data.scoreCnt[2]/res.data.allCnt
      data.rate4 = res.data.scoreCnt[3]/res.data.allCnt
      data.rate5 = res.data.scoreCnt[4]/res.data.allCnt
      option2.series[0].data = [
        { value: (data.rate1*100).toFixed(2), name: '60分以下' },
        { value: (data.rate2*100).toFixed(2), name: '60-69.99分' },
        { value: (data.rate3*100).toFixed(2), name: '70-79.99分' },
        { value: (data.rate4*100).toFixed(2), name: '80-89.99分' },
        { value: (data.rate5*100).toFixed(2), name: '90-100分' },
      ]
      init()
      data.dialogVisible = false
      ElMessage.success("统计成功！")
    }else{
      ElMessage.error(res.msg)
    }
  })
}

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


// 一进来就调用
onMounted(() => {
  init()
})
const chart1 = ref();
const chart2 = ref();
const init = () => {
  const myChart1 = echarts.init(chart1.value);
  const myChart2 = echarts.init(chart2.value);
  myChart1.setOption(option);
  myChart2.setOption(option2);
}

</script>
