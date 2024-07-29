package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.Admin;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.service.AdminService;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);
    private static AdminService staticAdminService;
    private static StudentService staticStudentService;
    private static TeacherService staticTeacherService;
    @Resource
    private AdminService adminService;

    /**
     * 生成token
     */
    public static String genToken(String userId, String password) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Admin getCurrentAdmin() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败， token: {}", token);
                return null;
            }
            // 解析token，获取用户的id
            String adminId = JWT.decode(token).getAudience().get(0);
            return staticAdminService.findById(Integer.valueOf(adminId));
        } catch (Exception e) {
            log.error("获取当前登录的管理员信息失败, token={}", token, e);
            return null;
        }
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Student getCurrentStudent() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败， token: {}", token);
                return null;
            }
            // 解析token，获取用户的id
            String studentId = JWT.decode(token).getAudience().get(0);
            return staticStudentService.findById(Integer.valueOf(studentId));
        } catch (Exception e) {
            log.error("获取当前登录的用户信息失败, token={}", token, e);
            return null;
        }
    }

    public static Teacher getCurrentTeacher() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败， token: {}", token);
                return null;
            }
            // 解析token，获取用户的id
            String teacherId = JWT.decode(token).getAudience().get(0);
            return staticTeacherService.findById(Integer.valueOf(teacherId));
        } catch (Exception e) {
            log.error("获取当前登录的教师信息失败, token={}", token, e);
            return null;
        }
    }


}