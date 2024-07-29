package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.auth0.jwt.JWT;
import com.example.common.Result;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.mapper.StudentCourseMapper;
import com.example.service.ScoreService;
import com.example.service.StudentCourseService;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private ScoreService scoreService;

    @Resource
    private StudentCourseService studentCourseService;
    @PostMapping("/add")
    public Result add(@RequestBody Score score){
        scoreService.add(score);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Score score){
        PageInfo<Score> studentPageInfo = scoreService.selectPage(pageNum, pageSize,score);
        return Result.success(studentPageInfo);
    }

    @GetMapping("/selectSubmittalScore")
    public Result selectSubmittalScore(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Score score){
        PageInfo<Score> scorePageInfo = scoreService.selectSubmittalScore(pageNum, pageSize,score);
        return Result.success(scorePageInfo);
    }


    @PutMapping("/update")
    public Result update(@RequestBody Score score) {
        scoreService.update(score);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        scoreService.deleteById(id);

        return Result.success();
    }


    @GetMapping("/analysis/{id}")
    public Result analysis(@PathVariable Integer id){
        CourseAnalysis courseAnalysis = scoreService.analysisCourse(id);
        return Result.success(courseAnalysis);
    }


    /**
     * 管理员导出
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/Aexport")
    public Result Aexp(HttpServletResponse response) throws IOException {
        // 1. 从数据库中查询出所有数据
        List<Score> scoreList = scoreService.adminExport();
        if(scoreList.isEmpty()){
            throw new CustomException("无数据，不可导出！");
        }
        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> list = new ArrayList<>();
        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for(Score s : scoreList){
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("课程编号", s.getCourseNo());
            row.put("课程名", s.getCourseName());
            row.put("任课教师", s.getTeacherName());
            row.put("选课学生学号", s.getStudentNumber());
            row.put("选课学生姓名", s.getStudentName());
            row.put("课程开设学期", s.getSemester());
            row.put("课程分数", s.getScore());
            row.put("教师评语", s.getComment());
            row.put("学生反馈信息", s.getFeedback());
            row.put("成绩状态", s.getState());
            list.add(row);
        }
        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.write(list, true);
        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=StudentScore.xlsx");
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(System.out);
        return Result.success();
    }

    @GetMapping("/Texport")
    public Result Texp(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 1. 从数据库中查询出所有数据

        ////老师只能导出自己能看到的数据
        // 1. 从http请求的header中获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 如果没拿到，我再去参数里面拿一波试试  /api/admin?token=xxxxx
            token = request.getParameter("token");
        }
        System.out.println(token);
        String teacherId = JWT.decode(token).getAudience().get(0);
        Score tScore = new Score();
        tScore.setTeacherId(Integer.valueOf(teacherId));
        List<Score> scoreList = scoreService.teacherExport(tScore);
        if(scoreList.isEmpty()){
            throw  new CustomException("无数据，不可导出！");
        }
        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> list = new ArrayList<>();
        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for(Score s : scoreList){
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("课程编号", s.getCourseNo());
            row.put("课程名", s.getCourseName());
            row.put("任课教师", s.getTeacherName());
            row.put("选课学生学号", s.getStudentNumber());
            row.put("选课学生姓名", s.getStudentName());
            row.put("课程开设学期", s.getSemester());
            row.put("课程分数", s.getScore());
            row.put("教师评语", s.getComment());
            row.put("学生反馈信息", s.getFeedback());
            row.put("成绩状态", s.getState());
            list.add(row);
        }
        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.write(list, true);
        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=StudentScore.xlsx");
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(System.out);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String msg = scoreService.upload(file);
        if(msg.isEmpty()){
            return Result.success("导入成功！");
        }
        if(msg.charAt(0) == 'E'){
            return Result.error(msg.substring(1));
        }else if(msg.charAt(0) == 'S'){
            return Result.success(msg.substring(1));
        }
        return Result.success();
    }

    @GetMapping("/all")
    public Result findAll(){
        List<Score> all = scoreService.findAll();
        return Result.success(all);
    }
}
