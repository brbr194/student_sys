# 使用基础的Java镜像
FROM openjdk:8

# 将构建好的Spring Boot JAR文件复制到容器中
COPY target/springboot-0.0.1-SNAPSHOT.jar /usr/src/myapp/app.jar

# 设置工作目录
WORKDIR /usr/src/myapp

# 配置环境变量，例如设置端口号
ENV PORT=9090

# 暴露 Spring Boot 应用程序的端口
EXPOSE 9090

# 运行 Spring Boot 应用程序
CMD ["java", "-jar", "app.jar"]