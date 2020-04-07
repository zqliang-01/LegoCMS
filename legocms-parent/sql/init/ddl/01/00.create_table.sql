/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/4/22 15:03:42                           */
/*==============================================================*/


/*==============================================================*/
/* Table: SYS_ORGANIZATION                                      */
/*==============================================================*/
create table SYS_ORGANIZATION
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_DATE          datetime not null comment '创建时间',
   STATUS               bigint(15) not null comment '状态',
   PARENT_ID            bigint(15) comment '上级部门',
   primary key (ID)
);

alter table SYS_ORGANIZATION comment '部门（用户一对一）';

/*==============================================================*/
/* Table: SYS_PERMISSION                                        */
/*==============================================================*/
create table SYS_PERMISSION
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(255) comment '名称',
   CREATE_DATE          datetime not null comment '创建时间',
   URL                  varchar(255) comment '链接地址',
   AUTHORIZED_URL       text comment '授权地址',
   ICON                 varchar(50) comment '图标',
   PARENT_ID            bigint(15) comment '父模块',
   MENU                 tinyint(1) not null default 1 comment '是否菜单',
   SORT                 int(10) not null comment '排序',
   primary key (ID),
   unique key AK_UNIQUE_CODE (CODE)
);

alter table SYS_PERMISSION comment '授权模块';

/*==============================================================*/
/* Table: SYS_PERMISSION_LANG                                   */
/*==============================================================*/
create table SYS_PERMISSION_LANG
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) comment '值',
   CREATE_DATE          datetime not null comment '创建时间',
   PERMISSION_ID        bigint(15) not null comment '授权模块ID',
   primary key (ID, PERMISSION_ID),
   unique key AK_unique_type (PERMISSION_ID, CODE)
);

alter table SYS_PERMISSION_LANG comment '模块语言';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
create table SYS_ROLE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   CREATE_DATE          datetime not null comment '创建时间',
   NAME                 varchar(100) not null comment '名称',
   primary key (ID)
);

alter table SYS_ROLE comment '角色';

/*==============================================================*/
/* Table: SYS_ROLE_PERMISSION                                   */
/*==============================================================*/
create table SYS_ROLE_PERMISSION
(
   ROLE_ID              bigint(15) not null comment 'USER_ID',
   PERMISSION_ID        bigint(15) not null comment 'VERSION',
   primary key (ROLE_ID, PERMISSION_ID)
);

alter table SYS_ROLE_PERMISSION comment '角色授权';

/*==============================================================*/
/* Table: SYS_SIMPLE_TYPE                                       */
/*==============================================================*/
create table SYS_SIMPLE_TYPE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_DATE          datetime not null comment '创建时间',
   CLASS_TYPE           varchar(50) not null comment '类型',
   SEQUENCE             int(10) not null comment '序号',
   primary key (ID)
);

alter table SYS_SIMPLE_TYPE comment '系统主题';

/*==============================================================*/
/* Table: SYS_THEME                                             */
/*==============================================================*/
create table SYS_THEME
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_DATE          datetime not null comment '创建时间',
   TYPE                 bigint(15) not null comment '类型',
   primary key (ID)
);

alter table SYS_THEME comment '系统主题';

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_DATE          datetime not null comment '创建时间',
   PASSWORD             varchar(100) not null comment '密码',
   ORGANIZATION_ID      bigint(15) not null comment '部门',
   STATUS               bigint(15) not null comment '状态',
   primary key (ID)
);

alter table SYS_USER comment '用户';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
create table SYS_USER_ROLE
(
   USER_ID              bigint(15) not null comment 'USER_ID',
   ROLE_ID              bigint(15) not null comment 'VERSION',
   primary key (USER_ID, ROLE_ID)
);

alter table SYS_USER_ROLE comment '用户角色';

alter table SYS_ORGANIZATION add constraint FK_ORGANIZATION_STATUS foreign key (STATUS)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_PERMISSION_LANG add constraint FK_PERMISSION_LANG foreign key (PERMISSION_ID)
      references SYS_PERMISSION (ID) on delete restrict on update restrict;

alter table SYS_ROLE_PERMISSION add constraint FK_PERMISSION_ROLE foreign key (PERMISSION_ID)
      references SYS_PERMISSION (ID) on delete restrict on update restrict;

alter table SYS_ROLE_PERMISSION add constraint FK_ROLE_PERMISSION foreign key (ROLE_ID)
      references SYS_ROLE (ID) on delete restrict on update restrict;

alter table SYS_THEME add constraint FK_THEME_TYPE foreign key (TYPE)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_USER add constraint FK_USER_ORGANIZATION foreign key (ORGANIZATION_ID)
      references SYS_ORGANIZATION (ID) on delete restrict on update restrict;

alter table SYS_USER add constraint FK_USER_STATUS foreign key (STATUS)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_USER_ROLE add constraint FK_ROLE_USER foreign key (USER_ID)
      references SYS_USER (ID) on delete restrict on update restrict;

alter table SYS_USER_ROLE add constraint FK_USER_ROLE foreign key (ROLE_ID)
      references SYS_ROLE (ID) on delete restrict on update restrict;

