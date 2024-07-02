create table if not exists student.admin
(
    id           int auto_increment
        primary key,
    username     varchar(255)                             not null,
    password     varchar(255)                             not null,
    name         varchar(255)                             not null,
    created_time timestamp      default CURRENT_TIMESTAMP null,
    updated_time timestamp      default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    role         enum ('ADMIN') default 'ADMIN'           null,
    constraint username
        unique (username)
);

create table if not exists student.courses
(
    id           int auto_increment
        primary key,
    course_no    varchar(100)                        not null,
    course_name  varchar(100)                        not null,
    credits      int                                 not null,
    semester     varchar(50)                         null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    updated_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    teacher_id   int                                 not null,
    teacher_name varchar(50)                         null
);

create table if not exists student.departments
(
    id           int auto_increment
        primary key,
    department   varchar(255)                        not null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    updated_time timestamp default CURRENT_TIMESTAMP null
);

create table if not exists student.grades
(
    id           int auto_increment
        primary key,
    grade        varchar(50)                         not null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    updated_time timestamp default CURRENT_TIMESTAMP null
);

create table if not exists student.log
(
    id            int auto_increment comment '主键ID'
        primary key,
    user_id       int                                 not null comment '操作人ID',
    contest       varchar(255)                        null comment '操作内容',
    time          timestamp default CURRENT_TIMESTAMP null comment '操作时间',
    operator_name varchar(50)                         not null comment '操作人',
    ip            varchar(255)                        null comment '操作人IP'
)
    comment '操作日志表' collate = utf8mb4_unicode_ci;

create table if not exists student.majors
(
    id           int auto_increment
        primary key,
    major        varchar(255)                        not null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    updated_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists student.score
(
    id         int auto_increment comment 'ID'
        primary key,
    course_id  int                                                              null comment '课程ID',
    student_id int                                                              null comment '学生ID',
    score      double(10, 1)                                                    null comment '分数',
    comment    varchar(255)                                                     null comment '评语',
    feedback   varchar(255)                                                     null comment '学生反馈',
    semester   varchar(50)                                                      not null,
    state      enum ('可编辑', '送审', '回退修改', '审核通过') default '可编辑' not null
)
    comment '成绩' collate = utf8mb4_unicode_ci;

create table if not exists student.semesters
(
    id            int auto_increment
        primary key,
    semester_name varchar(50)                         not null,
    start_date    varchar(10)                         not null,
    end_date      varchar(10)                         not null,
    created_time  timestamp default CURRENT_TIMESTAMP null,
    updated_time  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    flag          enum ('上', '下')                   null
);

create table if not exists student.student
(
    id             int auto_increment
        primary key,
    student_number varchar(20)                                not null,
    password       varchar(255)                               not null,
    name           varchar(100)                               not null,
    grade          varchar(50)                                not null,
    department     varchar(100)                               not null,
    major          varchar(100)                               not null,
    email          varchar(100)                               null,
    phone_number   varchar(15)                                not null,
    updated_time   timestamp        default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    created_time   timestamp        default CURRENT_TIMESTAMP null,
    role           enum ('STUDENT') default 'STUDENT'         null,
    age            int                                        not null,
    gender         varchar(2)                                 null,
    constraint student_number
        unique (student_number),
    check (`gender` in ('男','女'))
);

create table if not exists student.student_course
(
    id           int auto_increment
        primary key,
    course_name  varchar(255)                               not null,
    course_no    varchar(50)                                not null,
    course_id    int                                        not null,
    student_id   int                                        not null,
    semester     varchar(50)                                not null,
    teacher_name varchar(255)                               not null,
    teacher_id   int                                        not null,
    state        enum ('已打分', '未打分') default '未打分' not null
);

create table if not exists student.teacher
(
    id             int auto_increment
        primary key,
    teacher_number int                                        not null,
    name           varchar(100)                               not null,
    email          varchar(100)                               null,
    phone          varchar(15)                                null,
    created_time   timestamp        default CURRENT_TIMESTAMP null,
    updated_time   timestamp        default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    password       varchar(255)                               not null,
    role           enum ('TEACHER') default 'TEACHER'         null,
    department_id  int                                        not null
);

