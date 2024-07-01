package com.example.common;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Admin;
import com.example.entity.Log;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.service.LogService;
import com.example.utils.JwtTokenUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogoutAspect {

    @Resource
    private LogService logService;

    @Around("@annotation(logoutLog)")
    public Object logLogout(ProceedingJoinPoint joinPoint, LogoutLog logoutLog) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String contest = logoutLog.value();//操作内容
        String time = DateUtil.now();//当前时间
        String operatorName = "";
        String ip = "";
        Integer userId = 0;

        Result result = (Result) joinPoint.proceed();//继续执行
        Object data = result.getData();
        if ("管理员退出登录".equals(contest)) {
            Admin admin =  (Admin) data;
            if(ObjectUtil.isNotNull(admin)){
                operatorName = admin.getUsername();
                ip = request.getRemoteAddr();
                userId = admin.getId();
            }
        } else if ("学生退出登录".equals(contest)) {
            Student student =  (Student) data;
            if(ObjectUtil.isNotNull(student)){
                operatorName = student.getName();
                ip = request.getRemoteAddr();
                userId = student.getId();
            }
        } else if ("教师退出登录".equals(contest)) {
            Teacher teacher = (Teacher) data;
            if (ObjectUtil.isNotNull(teacher)) {
                operatorName = teacher.getName();
                ip = request.getRemoteAddr();
                userId = teacher.getId();
            }
        }

        Log log = new Log(contest, userId, time, operatorName, ip);
        logService.add(log);

        return result;

    }
}