package com.example.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.entity.Admin;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 从http请求的header中获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 如果没拿到，再去参数里面拿  /api/admin?token=xxxxx
            token = request.getParameter("token");
        }
        System.out.println(token);
        // 2. 开始执行认证
        if (StrUtil.isBlank(token)||StrUtil.isNullOrUndefined(token)) {
            throw new CustomException("无token，请重新登录");
        }
        // 获取 token 中的userId
        String userId;
        Admin admin;
        Student student;
        Teacher teacher;

        try {
            userId = JWT.decode(token).getAudience().get(0);
            // 根据token中的userid查询数据库
            admin = adminService.findById(Integer.parseInt(userId));
            student = studentService.findById(Integer.parseInt(userId));
            teacher = teacherService.findById(Integer.parseInt(userId));
        } catch (Exception e) {
            String errMsg = "token验证失败，请重新登录";
            log.error(errMsg + ", token=" + token, e);
            throw new CustomException(errMsg);
        }
        if (admin == null && student == null && teacher == null) {
            throw new CustomException("用户不存在，请重新登录");
        }
        JWTVerifier jwtVerifier;
        try {
            // 用户密码加签验证 token
            if(admin != null){
                jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            }else if(student != null){
                jwtVerifier = JWT.require(Algorithm.HMAC256(student.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            }else if(teacher != null){
                jwtVerifier = JWT.require(Algorithm.HMAC256(teacher.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            }
        } catch (JWTVerificationException e) {
            throw new CustomException("token验证失败，请重新登录");
        }
        System.out.println(token);
        log.info("放行");
        return true;
    }
}