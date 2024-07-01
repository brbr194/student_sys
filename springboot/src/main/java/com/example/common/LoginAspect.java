package com.example.common;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Admin;
import com.example.entity.Log;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.service.LogService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 处理切面的“监控”
 */
@Component
@Aspect
public class LoginAspect {

    @Resource
    private LogService logService;
    @Around("@annotation(loginLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, LoginLog loginLog) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String contest = loginLog.value();//操作内容
        String time = DateUtil.now();//当前时间
        String operatorName = "";
        String ip = "";
        Integer userId = 0;

        Result result = (Result) joinPoint.proceed();//继续执行

        Object data = result.getData();
        //获取操作人
        if("管理员登录".equals(contest)) {
            Admin admin =  (Admin) data;
            if(ObjectUtil.isNotNull(admin)){
                operatorName = admin.getUsername();
                ip = request.getRemoteAddr();
                userId = admin.getId();
            }
        }else if("学生登录".equals(contest)) {
            Student student =  (Student) data;
            if(ObjectUtil.isNotNull(student)){
                operatorName = student.getName();
                ip = request.getRemoteAddr();
                userId = student.getId();
            }
        }else if("教师登录".equals(contest)) {
            Teacher teacher = (Teacher) data;
            if (ObjectUtil.isNotNull(teacher)) {
                operatorName = teacher.getName();
                ip = request.getRemoteAddr();
                userId = teacher.getId();
            }
        }

        Log log = new Log(contest,userId,time, operatorName,ip);
        logService.add(log);

        return result;
    }

}
