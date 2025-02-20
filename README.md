
# Online Judge System (Backend)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/yourusername/oj-system-backend/blob/main/LICENSE)

一个基于 Spring Boot 的在线判题系统后端，提供题目管理、代码提交和用户认证等功能。
## 由于SpringCloud版本因为某种原因请求请求会跳过网关的跨域配置直接请求子服务，而导致无法正常给前端响应，无法正常投入应用，故不发布SpringCloud版本（在同源情况下，可以正常工作）。
## 功能特性

### 题目管理
- 题目创建、修改、删除
- 题目列表查看与搜索
- 题目难度分类

### 提交功能
- 代码提交与判题逻辑
- 提交记录查询

### 用户系统
- 用户登录与认证
- 基础权限控制

## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **持久层**: MyBatis 3.5.9 Mybatis-Plus 3.5.2
- **数据库**: MySQL 8.x
- **项目管理**: Maven 3.8.4
- **文档生成**: Swagger2 knife4j 4.4.0
## 快速开始

### 环境要求
- JDK 21+
- MySQL 8.x
- Maven 3.8+

### 数据库配置
1. 创建数据库：
```sql
CREATE DATABASE shiyioj;
```

2. 执行建表语句（参考 `/sql/shiyioj.sql`）

### 运行步骤
1. 克隆仓库：
```bash
git clone https://github.com/2743305544/shiyioj-backend.git
```

2. 修改数据库配置：
```yaml
# src/main/resources/application.yml
datasource:
  driver-class-name: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://localhost:3306/shiyioj
  username: xxxxx
  password: xxxxx
```

3. 构建项目：
```bash
mvn clean install
```

4. 启动应用：
```bash
mvn spring-boot:run
```

## 项目结构
```
src/main/java
├── com.shiyi.shiyioj
│   ├── controller      # API 接口层
│   ├── service         # 业务逻辑层
│   ├── mapper          # MyBatis 映射接口
│   ├── entity          # 数据库实体类
│   ├── dto             # 数据传输对象
│   ├── config          # 配置类
│   └── exception       # 自定义异常处理
```

## 接口文档
访问 Swagger UI：  
`http://localhost:8101/swagger-ui.html`  
（需先集成 Swagger 依赖）
访问 Knife4j：  
`http://localhost:8101/doc.html`  
（需先集成 Knife4j 依赖）
## 许可证
[MIT License](https://github.com/yourusername/oj-system-backend/blob/main/LICENSE)
```

---

