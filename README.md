# 项目介绍
在当今数字化教育的前沿领域，一个高效、智能且功能完备的高校成绩管理系统无疑是提升教育质量和管理效率的关键利器。本课程设计精心构建的基于 MVC 模式的高校成绩管理系统，正是这样一个集先进技术与创新功能于一体的杰出成果。
## 系统架构与技术选型
### （一）前端技术

1. **Vue3**：作为一款先进的 JavaScript 框架，Vue3 具有高效的组件化开发模式、灵活的响应式系统和优秀的性能。它能够帮助我们构建出结构清晰、易于维护和扩展的前端应用。
2. **Element Plus**：这是一套基于 Vue3 的高质量 UI 组件库，提供了丰富的组件，如表单、表格、按钮等，使我们能够快速搭建出美观、交互性良好的用户界面。
### （二）后端技术

1. **Spring Boot**：Spring Boot 是基于 Spring 框架的一套快速开发框架，它简化了配置，提供了自动装配和内置的服务器，极大地提高了开发效率和系统的可部署性。
2. **MyBatis**：MyBatis 是一款优秀的持久层框架，通过灵活的 SQL 映射和对象关系映射，能够高效地与数据库进行交互，实现数据的存储和查询。
### （三）其他技术

1. **JWT（JSON Web Token）**：用于实现用户登录鉴权，确保只有合法用户能够访问系统的相应功能和数据。
2. **Echarts**：用于前端的数据可视化，能够将复杂的成绩统计数据以直观、清晰的图表形式展示给用户。
3. **Spring Boot AOP**：通过自定义注解实现用户登录退出时的日志记录，方便进行系统监控和安全审计。
4. **POI 插件**：实现数据库数据与 xlsx 表格的相互导入导出，方便数据的处理和共享。
5. **GitHub Action 工作流**：用于自动化部署发布，确保系统能够快速、稳定地部署到阿里云服务器上。
## 前端工程：Vue3 与 Element Plus 的精妙协作
前端运用 Vue3 进行页面路由管理，以其卓越的组件化架构和高效的响应机制，为用户提供了流畅且无缝的页面切换体验。与此同时，Element Plus 组件的融入犹如锦上添花，凭借其丰富多样且精心设计的界面元素，成功搭建出兼具美观性与实用性的页面。用户在操作过程中，不仅能感受到页面的简洁与直观，更能享受到流畅、自然的交互乐趣。
## 后端架构：Spring Boot 与 MyBatis 的强强联手
后端选择了强大的 Spring Boot 框架作为支撑，其出色的自动配置和简化的开发流程极大地提高了开发效率，为系统的稳定运行奠定了坚实基础。与 MyBatis 持久层框架的结合更是如虎添翼，实现了对数据库操作的精细控制和高效管理，确保成绩数据的准确存储、快速检索与安全保护。
## 安全机制：JWT 技术铸就坚不可摧的登录防线
为保障系统的安全性和用户数据的保密性，引入了先进的 JWT（JSON Web Token）技术进行用户登录鉴权。这一技术如同为系统安装了一道智能门锁，在用户登录时生成独一无二且加密的令牌，后续的每一次请求都需通过严格的令牌验证，只有合法用户才能获准访问系统的核心功能和敏感数据，从而为用户的隐私和系统的安全构建了铜墙铁壁。
## 数据可视化：Echarts 呈现成绩洞察的绚丽画卷
前端集成的 Echarts 库犹如一位神奇的画师，将枯燥的数据转化为生动直观的动态图表。无论是课程成绩的分布情况、成绩的变化趋势，还是不同课程之间的对比分析，都能通过柱状图、折线图、饼图等多种形式清晰展现。这为教学决策提供了精准依据，也为学生自我评估开启了一扇清晰的窗口。
## 日志监控：Spring Boot AOP 与自定义注解的精准追踪
借助 Spring Boot 的 AOP 技术，并结合精心设计的自定义注解，实现了对用户登录和退出行为的精确记录。这一举措如同在系统中安装了一双敏锐的眼睛，能够实时捕捉用户的每一次操作轨迹，不仅为系统的安全性提供了深度洞察，更为后续的优化工作和问题排查积累了宝贵的历史数据。
## 数据交互：POI 插件实现高效的数据流转
集成的 POI 插件成为了系统中的数据桥梁，实现了数据库数据到 xlsx 表格的顺畅导出以及 xlsx 表格数据向数据库的精准导入。这一功能极大地增强了数据的灵活性和可用性，无论是数据的备份与共享，还是外部数据的整合与利用，都变得轻而易举。
## GitHub Action 与阿里云的高效协同
通过使用 GitHub Action 工作流，实现了自动化部署发布，并将系统稳定运行在阿里云服务器上。这一高效的协同组合，如同为系统找到了一个可靠的云端家园，确保系统能够随时随地为用户提供稳定、快速且不间断的服务。

本高校成绩管理系统以 MVC 模式为核心架构，融合了 Vue3、Spring Boot、MyBatis 等一系列前沿技术，并结合 JWT 鉴权、Echarts 可视化、AOP 日志监控以及 POI 数据交互插件等创新功能，实现了从前端用户体验到后端数据管理，从安全保障到数据流通的全方位优化与提升。它不仅是技术的结晶，更是为高校教育管理带来变革的有力工具，将有力推动教育管理向更加智能化、高效化的方向迈进。
# 需求分析
## 一、引言

随着高校教育规模的不断扩大，学生数量的逐渐增多，传统的成绩管理方式已经难以满足高效、准确和便捷的管理需求。因此，开发一个基于 MVC 模式的高校成绩管理系统具有重要的现实意义。

## 二、系统目标

本系统旨在为高校提供一个高效、准确、便捷的成绩管理平台，实现学生、教师和管理员对成绩相关信息的有效管理和操作。

## 三、用户角色及需求
### 学生
#### 系统登录与退出

1. 能够通过正确的用户名和密码登录系统，并在登录与退出时系统准确记录用户的登录、退出信息，包括用户名、时间、IP 以及状态（登录/退出）。
#### 个人信息管理

1. 可以查看个人的基本信息，如姓名、学号、班级、专业等。
2. 能够在允许的范围内修改个人的基本信息，例如联系电话、家庭地址等。
#### 查询课程成绩

1. 可以按照某学期查询课程成绩。
2. 能够针对某门具体课程进行成绩查询。
3. 能够列出自己所有课程的成绩。

### 教师
#### 系统登录与退出

1. 同学生一样，能够顺利登录和退出系统，且系统能准确记录相关登录和退出信息。
#### 个人信息管理

1. 查看个人的基本信息，包含姓名、工号、所属学院、教授课程等。
2. 对个人部分信息进行修改，如联系方式等。
#### 查询所讲授课程

1. 能够按照学期查询自己所讲授的课程。
#### 成绩管理

1. 选择某学期、某班级、某门课程后，可以按照学号或姓名录入学生成绩。
2. 对处于送审状态和审核通过的成绩不能进行修改，对于未送审或审核未通过的成绩可以进行修改。
3. 将录入的成绩送审。
4. 查看学生的成绩。
5. 按照学号或成绩对学生成绩进行排序。
#### 成绩查询

1. 针对某学期、某班级、某门课程，按照学号或姓名查询学生成绩。
2. 查询不及格学生的名单。
#### 数据导入导出

1. 从 Excel 表格中导入学生成绩到系统数据库。
2. 将系统数据库中的学生成绩导出为 Excel 表格。
#### 数据统计

1. 按班级、按课程统计学生的成绩，包括某门课的最高分、最低分、平均分、及格率等。
2. 统计不同分数段的学生人数及比例。

### （三）管理员


#### 系统登录与退出

1. 登录和退出系统，系统完整记录登录和退出详情。
#### 数据维护

1. 能够添加、修改、删除、查询学院的相关信息，如学院名称、学院简介等。
2. 对专业的信息进行管理，包括专业名称、所属学院、专业介绍等。
3. 维护年级的信息，如入学年份、学制等。
4. 管理教师的信息，涵盖教师姓名、工号、所属学院、教授课程等。
5. 处理学生的信息，包括学生姓名、学号、班级、专业等。
6. 对课程的信息进行操作，如课程名称、课程代码、学分、授课教师等。
#### 成绩审核

1. 对教师送审的成绩进行审核。
2. 对于审核不通过的成绩，能够退回让任课教师进行修改。
3. 审核通过的成绩不允许再修改。
#### 数据导入导出

1. 从 Excel 导入学院、专业、年级、教师、学生、课程等各种数据到数据库。
2. 将数据库中的相关数据导出为 Excel 表格。
#### 权限管理

1. 对系统的所有信息具备管理权限。
2. 可以对任何一个学生的成绩进行查询和管理。
3. 对教师和学生的权限进行合理分配和限制，确保教师只能管理其所任教班级的学生成绩，学生只能查看自己的成绩且不能更改。

## 四、非功能性需求
### （一）性能需求



1. 系统在处理大量数据时，如成绩录入、查询等操作，响应时间应在可接受范围内，确保用户体验流畅。
2. 系统应具备高并发处理能力，以满足在学期末等高峰时期大量用户同时登录和操作的需求。

### （二）安全性需求



1. 对用户的登录进行严格的身份验证，确保只有合法用户能够登录系统。
2. 对用户的操作进行权限控制，防止越权操作。
3. 对系统中的数据进行加密存储，保障数据的安全性。

### （三）可用性需求



1. 系统应具备简洁、直观的用户界面，方便用户操作。
2. 提供清晰明确的操作提示和帮助文档，使用户能够快速上手。

### （四）可维护性需求



1. 系统应采用模块化、分层的架构设计，便于后续的维护和升级。
2. 代码应具备良好的可读性和可扩展性，方便开发人员进行维护和修改。

## 五、界面设计需求



1. 登录界面应简洁明了，包含用户名、密码输入框以及登录和忘记密码按钮。
2. 学生、教师和管理员的主界面应根据其角色和功能需求进行个性化设计，突出常用功能和重要信息。
3. 成绩查询和录入界面应清晰展示相关信息，输入框和按钮布局合理，便于操作。

## 六、数据需求



1. 系统需要存储学生的基本信息，如姓名、学号、班级、专业等。
2. 存储教师的信息，包括姓名、工号、所属学院、教授课程等。
3. 保存课程的详细信息，如课程名称、课程代码、学分等。
4. 记录学生的课程成绩，包括学期、课程、学号、成绩等。
5. 存储系统的登录和操作日志，包含用户名、时间、IP、操作内容和状态等。
## 七、总结

通过以上详细的需求分析，明确了基于 MVC 模式的高校成绩管理系统的功能和非功能需求，为后续的系统设计、开发和测试提供了有力的依据，有助于开发出满足高校成绩管理需求的高效、稳定和安全的系统。

# 数据库设计

## 1. 数据库结构概述
本文档提供了一个基于MVC模式的高校成绩管理系统的数据库设计细节。此设计用于存储和管理学生、教师、课程、成绩等相关信息。
## 2. 数据表设计
### 2.1 admin 表（管理员信息）

- **id**: 管理员ID，自增主键。
- **username**: 管理员用户名，唯一。
- **password**: 密码。
- **name**: 管理员姓名。
- **created_time**: 创建时间，默认为当前时间戳。
- **updated_time**: 更新时间，自动更新为当前时间戳。
- **role**: 角色，默认为 'ADMIN'。
### 2.2 courses 表（课程信息）

- **id**: 课程ID，自增主键。
- **course_no**: 课程编号，唯一标识。
- **course_name**: 课程名称。
- **credits**: 学分。
- **semester**: 开课学期。
- **created_time**: 创建时间。
- **updated_time**: 更新时间。
- **teacher_id**: 授课教师ID。
- **teacher_name**: 授课教师姓名。
### 2.3 departments 表（院系信息）

- **id**: 院系ID，自增主键。
- **department**: 院系名称。
- **created_time**: 创建时间。
- **updated_time**: 更新时间。
### 2.4 grades 表（年级信息）

- **id**: 年级ID，自增主键。
- **grade**: 年级名称。
- **created_time**: 创建时间。
- **updated_time**: 更新时间。
### 2.5 score 表（成绩信息）

- **id**: 成绩ID，自增主键。
- **course_id**: 课程ID。
- **student_id**: 学生ID。
- **score**: 分数，精确到小数点后一位。
- **comment**: 教师评语。
- **feedback**: 学生反馈。
- **semester**: 成绩所属学期。
- **state**: 成绩状态（可编辑、送审、回退修改、审核通过）。
### 2.6 semesters 表（学期信息）

- **id**: 学期ID，自增主键。
- **semester_name**: 学期名称。
- **start_date**: 学期开始日期。
- **end_date**: 学期结束日期。
- **created_time**: 创建时间。
- **updated_time**: 更新时间。
- **flag**: 学期标识（上学期、下学期）。
### 2.7 student 表（学生信息）

- **id**: 学生ID，自增主键。
- **student_number**: 学号，唯一。
- **password**: 密码。
- **name**: 姓名。
- **grade**: 年级。
- **department**: 院系。
- **major**: 专业。
- **email**: 邮箱。
- **phone_number**: 电话号码。
- **created_time**: 创建时间。
- **updated_time**: 更新时间。
- **role**: 角色，默认为 'STUDENT'。
- **age**: 年龄。
- **gender**: 性别（男/女）。
### 2.8 student_course 表（学生课程关系）

- **id**: 关系ID，自增主键。
- **course_name**: 课程名称。
- **course_no**: 课程编号。
- **course_id**: 课程ID。
- **student_id**: 学生ID。
- **semester**: 学期。
- **teacher_name**: 授课教师姓名。
- **teacher_id**: 授课教师ID。
- **state**: 状态（已打分、未打分）。
### 2.9 teacher 表（教师信息）

- **id**: 教师ID，自增主键。
- **teacher_number**: 教师工号，唯一。
- **name**: 教师姓名。
- **email**: 邮箱。
- **phone**: 电话。
- **created_time**: 创建时间。
- **updated_time**: 更新时间。
- **password**: 密码。
- **role**: 角色，默认为 'TEACHER'。
- **department_id**: 院系ID。

### 2.10 majors 表（专业信息）

- **id: 专业唯一ID，自增主键。**
- **major: 专业名称，唯一。**
- **created_time: 记录创建时间。**
- **updated_time: 记录更新时间。**
### 2.11 log 表（操作日志）

- **id: 日志唯一ID，自增主键。**
- **user_id: 执行操作的用户ID。**
- **content: 操作内容或描述。**
- **time: 操作时间。**
- **operator_name: 执行操作的用户名或操作者姓名。**
- **ip: 操作者的IP地址。**



## 3.数据表结构详解
以下的表格详细描述了各个数据表的结构：
#### 1. admin 表（管理员信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 管理员ID |
| username | varchar(255) | not null, unique | 用户名 |
| password | varchar(255) | not null | 密码 |
| name | varchar(255) | not null | 姓名 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |
| role | enum('ADMIN') | default 'ADMIN' | 角色（默认为管理员） |

#### 2. courses 表（课程信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 课程ID |
| course_no | varchar(100) | not null | 课程编号 |
| course_name | varchar(100) | not null | 课程名称 |
| credits | int | not null | 学分 |
| semester | varchar(50) |  | 开课学期 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |
| teacher_id | int | not null | 授课教师ID |
| teacher_name | varchar(50) |  | 授课教师姓名 |

#### 3. departments 表（院系信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 院系ID |
| department | varchar(255) | not null | 院系名称 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |

#### 4. grades 表（年级信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 年级ID |
| grade | varchar(50) | not null | 年级名称 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |

#### 5. score 表（成绩信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 成绩ID |
| course_id | int |  | 课程ID |
| student_id | int |  | 学生ID |
| score | double(10,1) |  | 分数 |
| comment | varchar(255) |  | 教师评语 |
| feedback | varchar(255) |  | 学生反馈 |
| semester | varchar(50) | not null | 成绩所属学期 |
| state | enum('可编辑', '送审', '回退修改', '审核通过') | default '可编辑' | 成绩状态 |

#### 6. semesters 表（学期信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 学期ID |
| semester_name | varchar(50) | not null | 学期名称 |
| start_date | varchar(10) | not null | 开始日期 |
| end_date | varchar(10) | not null | 结束日期 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |
| flag | enum('上', '下') |  | 学期标识（上/下学期） |

#### 7. student 表（学生信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 学生ID |
| student_number | varchar(20) | not null, unique | 学号 |
| password | varchar(255) | not null | 密码 |
| name | varchar(100) | not null | 姓名 |
| grade | varchar(50) | not null | 年级 |
| department | varchar(100) | not null | 院系 |
| major | varchar(100) | not null | 专业 |
| email | varchar(100) |  | 邮箱 |
| phone_number | varchar(15) | not null | 电话号码 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |
| role | enum('STUDENT') | default 'STUDENT' | 角色（默认为学生） |
| age | int | not null | 年龄 |
| gender | varchar(2) | check (gender in ('男','女')) | 性别 |

#### 8. student_course 表（学生课程关系）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 关系ID |
| course_name | varchar(255) | not null | 课程名称 |
| course_no | varchar(50) | not null | 课程编号 |
| course_id | int | not null | 课程ID |
| student_id | int | not null | 学生ID |
| semester | varchar(50) | not null | 学期 |
| teacher_name | varchar(255) | not null | 授课教师姓名 |
| teacher_id | int | not null | 授课教师ID |
| state | enum('已打分', '未打分') | default '未打分' | 状态（已打分/未打分） |

#### 9. teacher 表（教师信息）
| 字段名 | 类型 | 约束条件 | 描述 |
| --- | --- | --- | --- |
| id | int | primary key auto_increment | 教师ID |
| teacher_number | int | not null | 教师工号 |
| name | varchar(100) | not null | 姓名 |
| email | varchar(100) |  | 邮箱 |
| phone | varchar(15) |  | 电话 |
| created_time | timestamp | default CURRENT_TIMESTAMP | 创建时间 |
| updated_time | timestamp | default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP | 更新时间 |
| password | varchar(255) | not null | 密码 |
| role | enum('TEACHER') | default 'TEACHER' | 角色（默认为教师） |
| department_id | int | not null | 院系ID |


#### 10. majors 表（专业信息表）
| 字段名 | 数据类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | int | PRIMARY KEY, AUTO_INCREMENT | 专业唯一ID |
| major | varchar(255) | NOT NULL | 专业名称 |
| created_time | timestamp | DEFAULT CURRENT_TIMESTAMP | 记录创建时间 |
| updated_time | timestamp | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 记录更新时间 |


#### 11. log表（登录登出日志表）
| id | int | PRIMARY KEY, AUTO_INCREMENT | 主键ID |
| --- | --- | --- | --- |
| user_id | int | NOT NULL | 操作人ID |
| contest | varchar(255) |  | 操作内容 |
| time | timestamp | DEFAULT CURRENT_TIMESTAMP | 操作时间 |
| operator_name | varchar(50) | NOT NULL | 操作人姓名 |
| ip | varchar(255) |  | 操作人IP |


## 4. 约束和特性

- 数据表中的 id 字段均为自增主键，保证唯一性。
- 使用 unique 约束确保用户名、学号等字段的唯一性。
- 使用 enum 类型来规定角色、成绩状态等字段的取值范围，确保数据完整性和一致性。
- 设计了 created_time 和 updated_time 字段，用于记录数据的创建和更新时间。
## 5. 数据库安全性和访问控制

- 密码字段应存储加密后的值，确保数据安全。
- 可以通过角色管理和权限控制来限制用户对数据的访问和操作。
## 6. 总结
以上数据库设计提供了一个结构化且功能完备的高校成绩管理系统框架，支持学生、教师、课程、成绩等信息的有效管理和查询。设计考虑了数据完整性、安全性和效率，适合实际应用中的使用和扩展需求。
# 系统各模块的设计与实现思路
### （一）学生模块

1. **系统登录与退出**
    - 实现思路：在前端，学生输入用户名和密码，通过 HTTP 请求发送到后端。后端使用 JWT 技术对用户信息进行验证，如果验证通过，生成一个包含用户身份信息的令牌（Token）并返回给前端。前端将令牌存储在本地（如 LocalStorage 或 Cookie 中），后续的请求在请求头中携带该令牌以证明身份。登录和退出时，后端通过 Spring Boot AOP 技术自定义的注解记录用户的登录、退出信息，包括用户名、时间、IP 地址和状态。
    - 示例：学生小明在 2024 年 7 月 2 日 10:00 通过校园网 IP（192.168.1.100）登录系统，系统记录下这些信息。
2. **个人信息管理**
    - 查看个人基本信息：后端从数据库中读取学生的个人信息，如姓名、学号、班级、联系方式等，并将其封装成 JSON 格式的数据返回给前端。前端接收到数据后，通过 Vue3 的组件进行展示。
    - 修改个人基本信息：学生在前端填写修改后的个人信息，点击保存按钮后，前端将修改后的数据发送到后端。后端接收到数据后，使用 MyBatis 执行更新操作，将新的数据写入数据库。
    - 示例：学生小红发现自己的电话号码有误，在前端修改为正确的号码“138xxxx1234”并保存，后端更新数据库中的相应记录。
3. **查询课程成绩**
    - 按学期或课程查询：学生在前端选择学期和课程，前端将这些查询条件发送到后端。后端根据条件在数据库中进行查询，获取相应的成绩数据，并按照一定的格式（如 JSON ）返回给前端。前端接收到数据后，使用 Vue3 的组件进行展示。
    - 列出所有课程成绩：后端从数据库中获取学生的所有课程成绩数据，包括课程名称、学期、成绩等，并进行整理和封装。然后将数据返回给前端，前端通过表格或列表的形式展示给学生。
    - 示例：学生小李想查看自己上学期的数学成绩，在前端选择“上学期”和“数学”，后端从数据库中找到相关成绩并返回给前端展示。
### （二）教师模块

1. **系统登录与退出**
    - 与学生的登录与退出实现方式相同，通过 JWT 进行身份验证，使用 AOP 记录登录退出信息。
    - 示例：教师王老师在 2024 年 7 月 2 日 14:30 通过办公室电脑 IP（192.168.0.200）登录系统，16:00 退出，系统记录全过程。
2. **个人信息管理**
    - 查看和修改个人基本信息的实现方式与学生模块类似，包括姓名、工号、所授课程等信息。
    - 示例：教师张老师修改了自己的办公地点信息。
3. **查询所讲授课程**
    - 按学期查询：教师在前端选择学期，后端根据教师的身份信息和学期条件，在数据库中查询该教师在该学期所讲授的课程，并将结果返回给前端展示。
    - 示例：李老师想查看自己本学期的授课课程，选择“本学期”后，系统返回“高等数学”和“线性代数”两门课程。
4. **成绩管理**
    - 录入成绩：教师在前端输入学生的学号和成绩，点击保存后，前端将数据发送到后端。后端根据送审状态判断是否允许录入，如果允许，则使用 MyBatis 将成绩数据插入到数据库中。
    - 修改成绩：教师选择要修改的成绩记录，在前端进行修改后提交。后端根据送审状态和审核状态判断是否允许修改，如果允许，则更新数据库中的相应数据。
    - 送审成绩：教师完成成绩录入或修改后，点击送审按钮。后端将成绩的状态标记为“送审”，并等待管理员审核。
    - 查看学生成绩、成绩排序：后端根据前端传入的条件（如按学号或成绩排序），从数据库中读取成绩数据，并进行相应的排序处理后返回给前端展示。
    - 示例：刘老师录入了“计算机基础”课程中部分学生的成绩，并送审，之后又发现一个成绩有误，在送审状态下进行了修改。
5. **成绩查询**
    - 按学号、姓名查询：教师在前端输入学号或姓名，后端根据输入条件在数据库中查询相应学生的成绩，并返回给前端展示。
    - 不及格学生名单查询：后端根据设定的及格分数线（如 60 分），在数据库中筛选出成绩低于该分数线的学生名单，并返回给前端展示。
    - 示例：陈老师想查看学号为“2021001”的学生的成绩，输入学号后系统返回该生的所有课程成绩。
6. **数据导入导出**
    - 从 Excel 导入学生成绩：教师在前端选择要导入的 Excel 文件，文件通过 HTTP 请求上传到后端。后端使用 POI 插件读取 Excel 文件中的数据，进行数据校验和处理后，使用 MyBatis 将数据插入到数据库中。
    - 将学生成绩导出到 Excel：教师在前端发起导出请求，后端从数据库中读取成绩数据，使用 POI 插件生成 Excel 文件，并将文件返回给前端供下载。
    - 示例：赵老师将整理好的学生成绩 Excel 文件导入系统，系统成功读取并保存数据。或者赵老师将某门课程的成绩导出为 Excel 文件，用于线下分析。
7. **数据统计**
    - 按班级按课程统计学生的成绩：后端根据教师选择的班级和课程，从数据库中读取相关成绩数据。然后使用相应的算法计算出最高分、最低分、平均分、及格率等统计指标，并将结果返回给前端展示。不同分数段的学生人数及比例通过对成绩进行分段统计得出。
    - 示例：吴老师查看“大学物理”课程中某个班级的成绩统计信息，包括平均分为 75 分，及格率为 80%等。
### （三）管理员模块

1. **系统登录与退出**
    - 与学生和教师的登录退出机制相同，确保安全性和日志记录的完整性。
    - 示例：管理员在 2024 年 7 月 2 日 9:00 登录，17:00 退出。
2. **数据维护**
    - 添加学院、专业、年级、教师、学生、课程等基本信息：管理员在前端填写相关信息，点击添加按钮后，前端将数据发送到后端。后端进行数据校验和处理后，使用 MyBatis 将数据插入到数据库中。
    - 修改基本信息：管理员在前端选择要修改的记录，进行修改后提交。后端根据修改内容更新数据库中的相应数据。
    - 删除基本信息：管理员在前端选择要删除的记录，点击删除按钮。后端确认删除操作后，从数据库中删除相应的数据。
    - 查询基本信息：管理员在前端输入查询条件，后端根据条件在数据库中进行查询，并将结果返回给前端展示。
    - 示例：管理员添加了一个新的专业“人工智能”，或者修改了某个学院的名称。
3. **成绩审核**
    - 查看送审成绩：管理员在前端点击成绩审核菜单，后端从数据库中获取处于送审状态的成绩数据，并返回给前端展示。
    - 审核操作：管理员对送审的成绩进行审核，选择通过或不通过。如果通过，将成绩状态更新为“审核通过”；如果不通过，将成绩状态更新为“审核不通过”，并将成绩退回给任课教师进行修改。
    - 示例：管理员审核了某门课程的送审成绩，其中一部分通过，一部分退回修改。
4. **数据导入导出**
    - 从 Excel 导入各种数据：管理员在前端选择要导入的 Excel 文件（包含学院、专业、年级、教师、学生、课程等数据），文件上传到后端。后端使用 POI 插件读取文件中的数据，进行处理和校验后，使用 MyBatis 将数据插入到数据库中。
    - 将各种数据导出到 Excel：管理员在前端选择要导出的数据类型（如学院数据、学生数据等），后端从数据库中读取相应数据，使用 POI 插件生成 Excel 文件并返回给前端供下载。
    - 示例：管理员将新入学学生的信息通过 Excel 导入系统，或者将某个专业的所有课程信息导出为 Excel 文件。
5. **权限管理**
    - 在后端定义不同用户角色（学生、教师、管理员）的权限范围，通过在数据库中存储用户角色和对应的权限信息来实现。在用户进行操作时，后端根据用户的角色和当前操作判断是否具有相应的权限，如果没有则拒绝访问并返回相应的提示信息。
    - 示例：学生尝试修改其他同学的成绩，系统提示无权限操作。
# 系统部署与运行

1. 系统通过 GitHub Action 工作流实现自动化部署。首先，在 GitHub 仓库中配置好工作流文件（.yml 文件），指定构建、测试和部署的步骤和环境。当有代码更新推送到仓库时，GitHub Action 会自动触发工作流。
2. 首先，在 GitHub 仓库中配置好工作流文件（.yml 文件），指定构建、测试和部署的步骤和环境。当有代码更新推送到仓库时，GitHub Action 会自动触发工作流。
    - 构建阶段：使用 Maven 或 Gradle 等构建工具对项目进行编译，生成可执行的 JAR 包或 WAR 包。
    - 测试阶段：运行单元测试和集成测试，确保代码的质量和功能的正确性。
    - 部署阶段：将构建好的包上传到阿里云服务器，并通过 SSH 连接到服务器，执行相应的部署脚本，如启动应用服务器、配置环境变量等。
3. 在阿里云服务器上，需要提前安装好所需的环境，如 JDK（Java Development Kit）、数据库（如 MySQL）、应用服务器（如 Tomcat）等。
    - JDK 用于运行 Java 应用程序。
    - 数据库用于存储系统的数据，如学生信息、课程信息、成绩信息等。
    - 应用服务器用于部署和运行系统的 Web 应用。
4. 系统部署完成后，启动应用服务器，系统即可对外提供服务。用户可以通过浏览器访问系统的 URL ，输入用户名和密码进行登录，然后根据自己的角色和权限进行相应的操作。

