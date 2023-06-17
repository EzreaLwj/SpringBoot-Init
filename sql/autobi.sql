create database if not exists autobi collate utf8mb4_general_ci;

use autobi;

create table if not exists user
(
    id            bigint auto_increment comment 'id' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    user_name     varchar(256)                           null comment '用户名称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_role     varchar(256) default 'user'            not null comment '用户角色 admin/user',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint      default 0                 not null comment '是否删除'
) comment '用户表' collate = utf8mb4_general_ci;

create table if not exists chart
(
    id          bigint auto_increment comment 'id' primary key,
    goal        text                               null comment '分析目标',
    char_data   text                               null comment '图表数据',
    char_type   varchar(128)                       null comment '图表类型',
    gen_chart   text                               null comment '生成的图表数据',
    gen_result  text                               null comment '生成图表结果',
    user_id     bigint                             null comment '用户id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'

) comment '图表表' collate = utf8mb4_general_ci;