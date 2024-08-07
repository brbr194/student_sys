package com.example.common;

import com.example.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller统一的接口前缀
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }



    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/student/upload")
                .excludePathPatterns("/api/score/upload")
                .excludePathPatterns("/api/teacher/upload")
                .excludePathPatterns("/api/major/upload")
                .excludePathPatterns("/api/student/login")
                .excludePathPatterns("/api/teacher/login")
                .excludePathPatterns("/api/teacher/logout")
                .excludePathPatterns("/api/student/logout")
                .excludePathPatterns("/api/admin/logout")
                .excludePathPatterns("/api/register");
    }
}