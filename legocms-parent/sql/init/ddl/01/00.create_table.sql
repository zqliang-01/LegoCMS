/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/5 22:00:53                            */
/*==============================================================*/

/*==============================================================*/
/* Table: CMS_CATEGORY                                          */
/*==============================================================*/
create table CMS_CATEGORY
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   SITE_ID              bigint(15) not null comment '站点',
   PARENT_ID            bigint(15) comment '父分类',
   TYPE_ID              bigint(15) not null comment '分类类型',
   PATH                 varchar(255) not null comment '访问路径',
   CONTENT_PATH         varchar(255) comment '内容访问路径',
   TEMPLATE_ID          bigint(15) not null comment '模板ID',
   PAGE_SIZE            int(5) not null comment '每页数据条数',
   SORT                 int(5) not null comment '序号',
   STATUS_ID            bigint(15) not null comment '状态',
   primary key (ID)
);

alter table CMS_CATEGORY comment '分类';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on CMS_CATEGORY
(
   CODE
);

/*==============================================================*/
/* Table: CMS_CATEGORY_MODEL                                    */
/*==============================================================*/
create table CMS_CATEGORY_MODEL
(
   CATEGORY_ID          bigint(15) not null comment '分类',
   MODEL_ID             bigint(15) not null comment '模型',
   primary key (CATEGORY_ID, MODEL_ID)
);

alter table CMS_CATEGORY_MODEL comment '用户角色';

/*==============================================================*/
/* Table: CMS_FILE                                              */
/*==============================================================*/
create table CMS_FILE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(255) comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   UPDATE_TIME          datetime not null comment '更新时间',
   TYPE_ID              bigint(15) not null comment '类型：file/dir',
   PARENT_ID            bigint(15) comment '父ID',
   SITE_ID              bigint(15) not null comment '站点ID',
   PATH                 varchar(255) not null comment '路径',
   SIZE                 bigint not null comment '大小',
   primary key (ID)
);

alter table CMS_FILE comment '文件';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on CMS_FILE
(
   CODE
);

/*==============================================================*/
/* Index: PATH_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index PATH_UNIQUE_INDEX on CMS_FILE
(
   SITE_ID,
   PATH
);

/*==============================================================*/
/* Table: CMS_MODEL                                             */
/*==============================================================*/
create table CMS_MODEL
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   TEMPLATE_ID          bigint(15) comment '模板',
   HAS_IMAGES           tinyint(1) comment '拥有图片列表',
   HAS_FILES            tinyint(1) comment '拥有附件列表',
   PARENT_ID            bigint(15) comment '上级模型',
   SITE_ID              bigint(15) not null comment '站点',
   primary key (ID)
);

alter table CMS_MODEL comment '内容模型';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on CMS_MODEL
(
   CODE
);

/*==============================================================*/
/* Table: CMS_MODEL_ATTRIBUTE                                   */
/*==============================================================*/
create table CMS_MODEL_ATTRIBUTE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   MODEL_ID             bigint(15) not null comment '模型ID',
   REQUIRED             tinyint(1) not null default 0 comment '是否必须',
   TYPE                 bigint(15) not null comment '编辑类型',
   SORT                 int(5) not null comment '序号',
   primary key (ID)
);

alter table CMS_MODEL_ATTRIBUTE comment '模型属性';

/*==============================================================*/
/* Index: CODE_MODEL_UNIQUE_INDEX                               */
/*==============================================================*/
create unique index CODE_MODEL_UNIQUE_INDEX on CMS_MODEL_ATTRIBUTE
(
   CODE,
   MODEL_ID
);

/*==============================================================*/
/* Table: CMS_PLACE                                             */
/*==============================================================*/
create table CMS_PLACE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(255) comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   UPDATE_TIME          datetime not null comment '更新时间',
   TYPE_ID              bigint(15) not null comment '类型：file/dir',
   PARENT_ID            bigint(15) comment '父ID',
   SITE_ID              bigint(15) not null comment '站点ID',
   CONTENT              text comment '内容',
   primary key (ID),
   unique key AK_UNIQUE_CODE (CODE)
);

alter table CMS_PLACE comment '模板片段';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on CMS_PLACE
(
   CODE
);

/*==============================================================*/
/* Table: CMS_SIMPLE_TYPE                                       */
/*==============================================================*/
create table CMS_SIMPLE_TYPE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   CLASS_TYPE           varchar(50) not null comment '类型',
   SEQUENCE             int(10) not null comment '序号',
   primary key (ID)
);

alter table CMS_SIMPLE_TYPE comment 'cms枚举类型';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on CMS_SIMPLE_TYPE
(
   CODE,
   CLASS_TYPE
);

/*==============================================================*/
/* Table: CMS_TEMPLATE                                          */
/*==============================================================*/
create table CMS_TEMPLATE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(255) comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   UPDATE_TIME          datetime not null comment '更新时间',
   TYPE_ID              bigint(15) not null comment '类型：file/dir',
   PARENT_ID            bigint(15) comment '父ID',
   SITE_ID              bigint(15) not null comment '站点ID',
   CONTENT              text comment '内容',
   primary key (ID)
);

alter table CMS_TEMPLATE comment '页面模板';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on CMS_TEMPLATE
(
   CODE
);

/*==============================================================*/
/* Table: SYS_DOMAIN                                            */
/*==============================================================*/
create table SYS_DOMAIN
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   PATH                 varchar(255) not null comment '地址',
   SITE_ID              bigint(15) not null comment '站点',
   primary key (ID)
);

alter table SYS_DOMAIN comment '域名';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on SYS_DOMAIN
(
   CODE,
   SITE_ID
);

/*==============================================================*/
/* Table: SYS_OPERATION_LOG                                     */
/*==============================================================*/
create table SYS_OPERATION_LOG
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   USER_ID              bigint(15) not null comment '操作人',
   PERMISSION_ID        bigint(15) comment '功能',
   DESCRIPTION          text comment '备注',
   LOCATION             varchar(100) comment '操作地址',
   ACTION_TYPE          bigint(15) comment '操作类型',
   primary key (ID),
   unique key AK_UNIQUE_CODE (CODE)
);

alter table SYS_OPERATION_LOG comment '操作日志';

/*==============================================================*/
/* Table: SYS_ORGANIZATION                                      */
/*==============================================================*/
create table SYS_ORGANIZATION
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   STATUS_ID            bigint(15) not null comment '状态',
   PARENT_ID            bigint(15) comment '上级部门',
   primary key (ID),
   unique key AK_UNIQUE_CODE (CODE)
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
   CREATE_TIME          datetime not null comment '创建时间',
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
   CREATE_TIME          datetime not null comment '创建时间',
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
   CREATE_TIME          datetime not null comment '创建时间',
   NAME                 varchar(100) not null comment '名称',
   primary key (ID),
   unique key AK_UNIQUE_CODE (CODE)
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
   CREATE_TIME          datetime not null comment '创建时间',
   CLASS_TYPE           varchar(50) not null comment '类型',
   SEQUENCE             int(10) not null comment '序号',
   primary key (ID)
);

alter table SYS_SIMPLE_TYPE comment '系统枚举';

/*==============================================================*/
/* Table: SYS_SITE                                              */
/*==============================================================*/
create table SYS_SITE
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
   PATH                 varchar(255) not null comment '地址',
   DYNAMIC_PATH         varchar(255) comment '动态地址',
   ORGANIZATION_ID      bigint(15) not null comment '部门',
   primary key (ID)
);

alter table SYS_SITE comment '站点配置';

/*==============================================================*/
/* Index: CODE_UNIQUE_INDEX                                     */
/*==============================================================*/
create unique index CODE_UNIQUE_INDEX on SYS_SITE
(
   CODE
);

/*==============================================================*/
/* Table: SYS_THEME                                             */
/*==============================================================*/
create table SYS_THEME
(
   ID                   bigint(15) not null comment 'ID',
   VERSION              int(5) not null comment 'VERSION',
   CODE                 varchar(50) not null comment 'CODE',
   NAME                 varchar(100) not null comment '名称',
   CREATE_TIME          datetime not null comment '创建时间',
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
   CREATE_TIME          datetime not null comment '创建时间',
   PASSWORD             varchar(100) not null comment '密码',
   ORGANIZATION_ID      bigint(15) not null comment '部门',
   STATUS_ID            bigint(15) not null comment '状态',
   SITE_ID              bigint(15) comment '当前管理站点',
   primary key (ID),
   unique key AK_UNIQUE_CODE (CODE)
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

alter table CMS_CATEGORY add constraint FK_CATEGORY_SITE foreign key (SITE_ID)
      references SYS_SITE (ID) on delete restrict on update restrict;

alter table CMS_CATEGORY add constraint FK_CATEGORY_STATUS foreign key (STATUS_ID)
      references CMS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table CMS_CATEGORY add constraint FK_CATEGORY_TEMPLATE foreign key (TEMPLATE_ID)
      references CMS_TEMPLATE (ID) on delete restrict on update restrict;

alter table CMS_CATEGORY add constraint FK_CATEGORY_TYPE foreign key (TYPE_ID)
      references CMS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table CMS_CATEGORY_MODEL add constraint FK_CATEGORY_MODEL foreign key (MODEL_ID)
      references CMS_MODEL (ID) on delete restrict on update restrict;

alter table CMS_CATEGORY_MODEL add constraint FK_MODEL_CATEGORY foreign key (CATEGORY_ID)
      references CMS_CATEGORY (ID) on delete restrict on update restrict;

alter table CMS_FILE add constraint FK_FILE_TYPE foreign key (TYPE_ID)
      references CMS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table CMS_MODEL add constraint FK_MODEL_TEMPLATE foreign key (TEMPLATE_ID)
      references CMS_TEMPLATE (ID) on delete restrict on update restrict;

alter table CMS_MODEL_ATTRIBUTE add constraint FK_INPUT_TYPE foreign key (TYPE)
      references CMS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table CMS_MODEL_ATTRIBUTE add constraint FK_MODEL_ATTRIBUTE foreign key (MODEL_ID)
      references CMS_MODEL (ID) on delete restrict on update restrict;

alter table CMS_PLACE add constraint FK_PLACE_TYPE foreign key (TYPE_ID)
      references CMS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table CMS_TEMPLATE add constraint FK_TEMPLATE_SITE foreign key (SITE_ID)
      references SYS_SITE (ID) on delete restrict on update restrict;

alter table CMS_TEMPLATE add constraint FK_TEMPLATE_TYPE foreign key (TYPE_ID)
      references CMS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_DOMAIN add constraint FK_DOMAIN_SITE foreign key (SITE_ID)
      references SYS_SITE (ID) on delete restrict on update restrict;

alter table SYS_OPERATION_LOG add constraint FK_OPERATION_PERMISSION foreign key (PERMISSION_ID)
      references SYS_PERMISSION (ID) on delete restrict on update restrict;

alter table SYS_OPERATION_LOG add constraint FK_OPERATION_TYPE foreign key (ACTION_TYPE)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_OPERATION_LOG add constraint FK_OPERATION_USER foreign key (USER_ID)
      references SYS_USER (ID) on delete restrict on update restrict;

alter table SYS_ORGANIZATION add constraint FK_ORGANIZATION_STATUS foreign key (STATUS_ID)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_PERMISSION_LANG add constraint FK_PERMISSION_LANG foreign key (PERMISSION_ID)
      references SYS_PERMISSION (ID) on delete restrict on update restrict;

alter table SYS_ROLE_PERMISSION add constraint FK_PERMISSION_ROLE foreign key (PERMISSION_ID)
      references SYS_PERMISSION (ID) on delete restrict on update restrict;

alter table SYS_ROLE_PERMISSION add constraint FK_ROLE_PERMISSION foreign key (ROLE_ID)
      references SYS_ROLE (ID) on delete restrict on update restrict;

alter table SYS_SITE add constraint FK_ORGANIZATION_SITE foreign key (ORGANIZATION_ID)
      references SYS_ORGANIZATION (ID) on delete restrict on update restrict;

alter table SYS_THEME add constraint FK_THEME_TYPE foreign key (TYPE)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_USER add constraint FK_SITE_USER foreign key (SITE_ID)
      references SYS_SITE (ID) on delete restrict on update restrict;

alter table SYS_USER add constraint FK_USER_ORGANIZATION foreign key (ORGANIZATION_ID)
      references SYS_ORGANIZATION (ID) on delete restrict on update restrict;

alter table SYS_USER add constraint FK_USER_STATUS foreign key (STATUS_ID)
      references SYS_SIMPLE_TYPE (ID) on delete restrict on update restrict;

alter table SYS_USER_ROLE add constraint FK_ROLE_USER foreign key (USER_ID)
      references SYS_USER (ID) on delete restrict on update restrict;

alter table SYS_USER_ROLE add constraint FK_USER_ROLE foreign key (ROLE_ID)
      references SYS_ROLE (ID) on delete restrict on update restrict;

