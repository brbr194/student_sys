package com.example.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.entity.CourseAnalysis;
import com.example.entity.Score;
import com.example.entity.Student;
import com.example.entity.StudentCourse;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.ScoreMapper;
import com.example.mapper.StudentCourseMapper;
import com.example.mapper.StudentMapper;
import com.example.service.ScoreService;
import com.example.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;
    @Resource
    private StudentCourseMapper studentCourseMapper;

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private CourseMapper courseMapper;
    @Override
    public PageInfo<Score> selectPage(Integer pageNum, Integer pageSize, Score score) {
        PageHelper.startPage(pageNum, pageSize);
        List<Score> scoreList = scoreMapper.selectAll(score);
        return PageInfo.of(scoreList);
    }

    @Override
    public void update(Score score) {
        scoreMapper.update(score);
    }

    @Override
    public void deleteById(Integer id) {
        //TODO
        Score dbscore = scoreMapper.findById(id);
        StudentCourse dbstudentCourse = studentCourseMapper.findByStudentAndCourseId(dbscore);
        if(dbstudentCourse != null){
            studentCourseMapper.updateStateById(dbstudentCourse.getId());
        }
        scoreMapper.deleteById(id);
    }

    @Override
    public List<Score> adminExport() {
        return scoreMapper.adminExport();
    }

    @Override
    public List<Score> teacherExport(Score Score) {
        return scoreMapper.teacherExport(Score);
    }

    @Override
    public Score findById(Integer id) {
        return scoreMapper.findById(id);
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        String resMsg = "";
        List<Score> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Score.class);
        int errCnt = 0;
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Score score : infoList) {
                Score dbscore = scoreMapper.findByCondition(score);
                List<StudentCourse> dbstudentCourse =  studentCourseMapper.selectByCondition2(score);
                Student dbstudent = studentMapper.findByStudentNumberAndName(score);
                if(!dbstudentCourse.isEmpty() && dbstudent != null && dbscore == null){
                    score.setStudentId(studentMapper.findByStudentNumber(score.getStudentNumber()).getId());
                    score.setCourseId(courseMapper.findByCno(score.getCourseNo()).getId());
                    StudentCourse studentCourse = new StudentCourse();
                    studentCourse.setCourseName(score.getCourseName());
                    studentCourse.setCourseNo(score.getCourseNo());
                    studentCourse.setCourseId(courseMapper.findByCno(score.getCourseNo()).getId());
                    studentCourse.setStudentId(studentMapper.findByStudentNumber(score.getStudentNumber()).getId());
                    studentCourse.setSemester(score.getSemester());
                    studentCourse.setTeacherName(score.getTeacherName());
                    studentCourse.setTeacherId(courseMapper.findByCno(score.getCourseNo()).getTeacherId());//
                    studentCourse.setState("已打分");
                    studentCourseMapper.insert(studentCourse);
                    scoreMapper.insert(score);
                }else{
                    errCnt++;
                }
            }
        }
        if(errCnt == infoList.size()){
          resMsg = "E" + "全部插入失败，插入的所有数据中课程不存在或学生不存在或成绩记录已存在！";
        }
        else if(errCnt>0&&errCnt<infoList.size()){
            resMsg = "S" + "部分数据插入成功，未插入的数据中有课程不存在或学生不存在或成绩记录已存在！";
        }
        return resMsg;
    }

    @Override
    public List<Score> findAll() {
        return scoreMapper.findAll();
    }

    @Override
    public PageInfo<Score> selectSubmittalScore(Integer pageNum, Integer pageSize, Score score) {
        PageHelper.startPage(pageNum, pageSize);
        List<Score> scoreList = scoreMapper.selectSubmittalScore(score);
        return PageInfo.of(scoreList);
    }

    @Override
    public CourseAnalysis analysisCourse(Integer id) {
        List<Score> scoreList = scoreMapper.findScoreByCourseId(id);
        if(scoreList.isEmpty()){
            throw new CustomException("该课程未有成绩信息，请更换选择！");
        }
        CourseAnalysis courseAnalysis = new CourseAnalysis();
        courseAnalysis.setMax(scoreMapper.getMax(id));
        courseAnalysis.setMin(scoreMapper.getMin(id));
        courseAnalysis.setAvg(scoreMapper.getAvg(id));

        int upCnt = scoreMapper.getUpCnt(id);//及格
        int lowCnt = scoreMapper.getLowCnt(id);//不及格
        int allCnt = scoreMapper.getAllCnt(id);
        courseAnalysis.setAllCnt(allCnt);
        courseAnalysis.setRate(((double) upCnt /allCnt)*100.0);

        int [] cnt = new int[5];
        Map<Double, String> map = new HashMap<>();

        for(int i = 0;i<5;i++){
            if(i == 0){
                cnt[i] = scoreMapper.getDifferentBandsCnt(0,59.99,id);
            }
            else if(i == 1){
                cnt[i] = scoreMapper.getDifferentBandsCnt(60,69.99,id);
            }
            else if(i == 2){
                cnt[i] = scoreMapper.getDifferentBandsCnt(70,79.99,id);
            }
            else if(i == 3){
                cnt[i] = scoreMapper.getDifferentBandsCnt(80,89.99,id);
            }
            else if(i == 4){
                cnt[i] = scoreMapper.getDifferentBandsCnt(90,100,id);
            }
        }
        courseAnalysis.setScoreCnt(cnt);
        return courseAnalysis;
    }

    @Override
    public void add(Score score) {
        Score dbscore = scoreMapper.selectByCondition(score);
        studentCourseMapper.updateState(score);
        if(dbscore != null){
            throw new CustomException("此课程已经打过分了，需要编辑成绩信息请前往课程成绩管理面板！");
        }
        scoreMapper.insert(score);
    }
}
