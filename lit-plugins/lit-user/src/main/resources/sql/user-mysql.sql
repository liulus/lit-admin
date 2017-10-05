CREATE TABLE lit_organization (
  org_id     INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '机构id',
  org_code   VARCHAR(32) COMMENT '机构号',
  org_name   VARCHAR(512) COMMENT '机构名',
  short_name VARCHAR(128) COMMENT '机构简称',
  org_type   VARCHAR(64) COMMENT '机构类型',
  org_level  TINYINT COMMENT '机构层级',
  serial_num VARCHAR(512) COMMENT '特殊编号, 用于查询',
  org_address    VARCHAR(512) COMMENT '地址'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '机构表';

CREATE UNIQUE INDEX lit_org_org_code_uindex
  ON lit_organization (org_code);

CREATE TABLE lit_user
(
  user_id        INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '用户Id',
  org_id         INT UNSIGNED COMMENT '机构Id',
  user_code      VARCHAR(32) COMMENT '用户编号',
  user_name      VARCHAR(64)              NOT NULL
  COMMENT '用户名',
  nick_name      VARCHAR(64) COMMENT '昵称',
  real_name      VARCHAR(16) COMMENT '真实姓名',
  avatar         VARCHAR(512) COMMENT '头像',
  password       VARCHAR(64) COMMENT '密码',
  sex            TINYINT COMMENT '性别',
  email          VARCHAR(64) COMMENT '邮箱',
  mobile_phone   VARCHAR(16) COMMENT '手机号',
  telephone      VARCHAR(16) COMMENT '电话',
  id_card_num    VARCHAR(32) COMMENT '身份证号',
  user_type      VARCHAR(32) COMMENT '用户类型',
  user_status    VARCHAR(32) COMMENT '用户状态',
  is_lock        TINYINT COMMENT '是否锁定',
  creator        VARCHAR(64) COMMENT '创建人',
  gmt_create     DATETIME                          DEFAULT current_timestamp
  COMMENT '创建时间',
  gmt_last_login DATETIME COMMENT '最后登录时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户表';

CREATE UNIQUE INDEX lit_user_user_name_uindex
  ON lit_user (user_name);
CREATE UNIQUE INDEX lit_user_email_uindex
  ON lit_user (email);
CREATE UNIQUE INDEX lit_user_mobile_phone_uindex
  ON lit_user (mobile_phone);

ALTER TABLE lit_user
  ADD CONSTRAINT lit_user_lit_org_org_Id_fk
FOREIGN KEY (org_id) REFERENCES lit_organization (org_id);

