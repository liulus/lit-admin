-- dictionary
CREATE TABLE lit_dictionary
(
    dict_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '字典Id',
    parent_id INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '上级字典Id',
    dict_key VARCHAR(32) NOT NULL DEFAULT '' COMMENT '字典key',
    dict_value VARCHAR(32) NOT NULL DEFAULT '' COMMENT '字典值',
    order_num SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序号',
    memo VARCHAR(256) NOT NULL DEFAULT '' COMMENT '备注',
    is_system TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否系统字典数据',
    sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

CREATE INDEX idx_dict_key ON lit_dictionary (dict_key);

-- param
CREATE TABLE lit_param
(
  param_id    INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '参数Id',
  param_code  VARCHAR(32) NOT NULL DEFAULT '' COMMENT '参数编码',
  param_value VARCHAR(32) NOT NULL DEFAULT '' COMMENT '参数值',
  memo        VARCHAR(256) NOT NULL DEFAULT '' COMMENT '备注',
  is_system   TINYINT COMMENT '是否系统级',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数表';

CREATE UNIQUE INDEX lit_param_param_code_uindex
  ON lit_param (param_code);


-- menu
CREATE TABLE lit_menu
(
    menu_id INT unsigned PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '菜单Id',
    parent_id INT unsigned COMMENT '上级菜单Id',
    code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '菜单编码',
    name VARCHAR(32) NOT NULL DEFAULT '' COMMENT '菜单名称',
    icon VARCHAR(32) NOT NULL DEFAULT '' COMMENT '菜单图标',
    url VARCHAR (256) NOT NULL DEFAULT '' COMMENT '链接',
    order_num SMALLINT unsigned COMMENT '顺序号',
    memo VARCHAR(256) NOT NULL DEFAULT '' COMMENT '备注',
    menu_type VARCHAR(32) NOT NULL DEFAULT ''  COMMENT '菜单类型',
    module VARCHAR(32) NOT NULL DEFAULT '' COMMENT '所属模块',
    is_enable TINYINT COMMENT '是否启用',

    sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';



-- user
CREATE TABLE lit_organization (
  org_id      INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '机构id',
  parent_id   INT UNSIGNED COMMENT '父机构Id',
  code    VARCHAR(32) NOT NULL DEFAULT '' COMMENT '机构号',
  name    VARCHAR(64) NOT NULL DEFAULT '' COMMENT '机构名',
  short_name  VARCHAR(64) NOT NULL DEFAULT '' COMMENT '机构简称',
  org_type    VARCHAR(16) NOT NULL DEFAULT '' COMMENT '机构类型',
  level_index  VARCHAR(16) NOT NULL DEFAULT '' COMMENT '特殊编号, 用于查询',
  address VARCHAR(256) NOT NULL DEFAULT '' COMMENT '地址',
  memo        VARCHAR(256) NOT NULL DEFAULT '' COMMENT '备注',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '机构';


CREATE TABLE lit_user
(
  user_id        INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '用户Id',
  org_id         INT UNSIGNED COMMENT '机构Id',
  code      VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户编号',
  username      VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户名',
  nickname      VARCHAR(32) NOT NULL DEFAULT '' COMMENT '昵称',
  avatar         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '头像',
  password       VARCHAR(64) NOT NULL DEFAULT '' COMMENT '密码',
  gender         TINYINT COMMENT '性别',
  email          VARCHAR(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  mobile_num   VARCHAR(16) NOT NULL DEFAULT '' COMMENT '手机号',
  telephone      VARCHAR(16) NOT NULL DEFAULT '' COMMENT '电话',
  real_name      VARCHAR(16) NOT NULL DEFAULT '' COMMENT '真实姓名',
  id_card_num    VARCHAR(32) NOT NULL DEFAULT '' COMMENT '身份证号',
  user_type      VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户类型',
  user_status    VARCHAR(16) NOT NULL DEFAULT '' COMMENT '用户状态',
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


CREATE TABLE lit_authority
(
  authority_id    INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '权限码ID',
  code  VARCHAR(32) NOT NULL DEFAULT '' COMMENT '权限码',
  name  VARCHAR(64) NOT NULL DEFAULT '' COMMENT '权限码名称',
  authority_type  VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '权限类型',
  memo   VARCHAR(256) NOT NULL DEFAULT '' COMMENT '备注',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限码';

CREATE TABLE lit_role(
  role_id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '角色编号',
  name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '角色名',
  memo VARCHAR(256) NOT NULL DEFAULT '' COMMENT '备注',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

CREATE TABLE lit_role_authority(
  role_authority_id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  role_id INT UNSIGNED NOT NULL COMMENT '角色Id',
  role_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '角色编号',
  authority_id INT UNSIGNED NOT NULL COMMENT '权限码Id',
  authority_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '权限码',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 )ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

CREATE TABLE lit_user_role(
  user_role_id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL COMMENT '用户Id',
  username VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户名',
  role_id INT UNSIGNED NOT NULL COMMENT '角色Id',
  role_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '角色code',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';


INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('menu_type', '菜单类型', 1, 1, '菜单类型', 0, null);
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('menu_type_top', '顶部导航菜单', 1, 2, '顶部导航菜单', 0, last_insert_id());
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('menu_type_left', '左侧菜单', 2, 2, '左侧菜单', 0, last_insert_id()-1);
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('authority_type', '权限类型', 4, 1, '权限类型', 1, null);
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('system_authority', '系统权限', 1, 2, '系统权限', 1, last_insert_id());
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('other_authority', '其他权限', 2, 2, '其他权限', 0, last_insert_id()-1);
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('user_type', '用户类型', 3, 1, '用户类型', 0, NULL);



INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system', '系统管理', '１', null, 1, '系统管理', 'menu_type_left', null, 1, null);
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_menu', '菜单管理', '12', '/plugin/menu', 1, '菜单管理', 'menu_type_left', null, 1, last_insert_id());
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_dictionary', '字典管理', '123', '/plugin/dictionary', 2, '字典管理', 'menu_type_left', null, 1, 1);
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_param', '参数管理', '12', '/plugin/param', 3, '参数管理', 'menu_type_left', null, 1, 1);
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_organization', '机构管理', '2', '/plugin/org', 5, '机构管理', 'menu_type_left', null, 1, 1);
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_user', '用户管理', '2', '/plugin/user', 4, '用户管理', 'menu_type_left', null, 1, 1);
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_role', '角色管理', '12', '/plugin/role/list', 6, '角色管理', 'menu_type_left', null, 1, 1);
INSERT INTO lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_authority', '权限管理', '1', '/plugin/authority/list', 7, '权限管理', 'menu_type_left', null, 1, 1);

INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (1, 'menu_manager', '菜单管理', 'system_authority', '菜单管理', '2018-02-01 11:19:00');
INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (2, 'dictionary_manager', '字典管理', 'system_authority', '字典管理', '2018-02-01 11:19:00');
INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (3, 'param_manager', '参数管理', 'system_authority', '参数管理', '2018-02-01 11:19:00');
INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (4, 'user_manager', '用户管理', 'system_authority', '用户管理', '2018-02-01 11:19:00');
INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (5, 'organization_manager', '机构管理', 'system_authority', '机构管理', '2018-02-01 11:19:00');
INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (6, 'role_manager', '角色管理', 'system_authority', '角色管理', '2018-02-01 11:19:00');
INSERT INTO lit_authority (authority_id, authority_code, authority_name, authority_type, memo, sys_time) VALUES (7, 'authority_manager', '权限管理', 'system_authority', '权限管理', '2018-02-01 11:19:00');

INSERT INTO lit_role (role_id, role_code, role_name, memo, sys_time) VALUES (1, 'role_system', '系统管理员', '系统管理员', '2018-01-16 15:26:36');
INSERT INTO lit_role (role_id, role_code, role_name, memo, sys_time) VALUES (2, 'role_user', '普通用户', '普通用户', '2018-01-16 20:13:41');

INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (1, 1, '', 1, '', '2018-02-01 11:10:23');
INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (2, 1, '', 2, '', '2018-02-01 11:10:23');
INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (3, 1, '', 3, '', '2018-02-01 11:10:23');
INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (4, 1, '', 4, '', '2018-02-01 11:10:23');
INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (5, 1, '', 5, '', '2018-02-01 11:10:23');
INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (6, 1, '', 6, '', '2018-02-01 11:10:23');
INSERT INTO lit_role_authority (role_authority_id, role_id, role_code, authority_id, authority_code, sys_time) VALUES (7, 1, '', 7, '', '2018-02-01 11:10:23');


INSERT INTO lit_user (org_id, user_code, user_name, nick_name, real_name, avatar, password, gender, email, mobile_phone, telephone, id_card_num, user_type, user_status, is_lock, creator, gmt_create, gmt_last_login) VALUES (null, 'liulu', 'liulu', 'liulu', null, null, '$2a$10$7yD3GA3WsqJllGuOCLhyturYJPiWwDP6D1Ix7/5VmOyx3uq.pWw2W', 1, null, '15267548275', null, null, null, null, null, null, '2017-12-17 14:22:02', null);

INSERT INTO lit_user_role (user_role_id, user_id, user_name, role_id, role_code, sys_time) VALUES (1, 1, '', 1, '', '2018-02-01 11:11:22');