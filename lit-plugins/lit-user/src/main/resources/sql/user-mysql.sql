CREATE TABLE lit_organization (
  org_id      INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '机构id',
  parent_id   INT UNSIGNED COMMENT '父机构Id',
  org_code    VARCHAR(32) DEFAULT '' COMMENT '机构号',
  org_name    VARCHAR(512) DEFAULT '' COMMENT '机构名',
  short_name  VARCHAR(128) DEFAULT '' COMMENT '机构简称',
  org_type    VARCHAR(64) DEFAULT '' COMMENT '机构类型',
  org_level   TINYINT COMMENT '机构层级',
  serial_num  VARCHAR(128) DEFAULT '' COMMENT '特殊编号, 用于查询',
  org_address VARCHAR(512) DEFAULT '' COMMENT '地址',
  memo        VARCHAR(512) DEFAULT '' COMMENT '备注',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '机构表';


CREATE TABLE lit_user
(
  user_id        INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '用户Id',
  org_id         INT UNSIGNED COMMENT '机构Id',
  user_code      VARCHAR(32) DEFAULT '' COMMENT '用户编号',
  user_name      VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户名',
  nick_name      VARCHAR(64) DEFAULT '' COMMENT '昵称',
  real_name      VARCHAR(16) DEFAULT '' COMMENT '真实姓名',
  avatar         VARCHAR(512) DEFAULT '' COMMENT '头像',
  password       VARCHAR(64) DEFAULT '' COMMENT '密码',
  gender         TINYINT COMMENT '性别',
  email          VARCHAR(64) DEFAULT '' COMMENT '邮箱',
  mobile_phone   VARCHAR(16) DEFAULT '' COMMENT '手机号',
  telephone      VARCHAR(16) DEFAULT '' COMMENT '电话',
  id_card_num    VARCHAR(32) DEFAULT '' COMMENT '身份证号',
  user_type      VARCHAR(32) DEFAULT '' COMMENT '用户类型',
  user_status    VARCHAR(32) DEFAULT '' COMMENT '用户状态',
  is_lock        TINYINT COMMENT '是否锁定',
  creator        VARCHAR(64) COMMENT '创建人',
  gmt_create     DATETIME                          DEFAULT current_timestamp
  COMMENT '创建时间',
  gmt_last_login DATETIME COMMENT '最后登录时间',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户表';


