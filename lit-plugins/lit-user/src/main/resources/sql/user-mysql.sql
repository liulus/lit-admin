CREATE TABLE lit_user
(
    user_id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '用户Id',
    user_code VARCHAR(32) COMMENT '用户编号',
    user_name VARCHAR(64) NOT NULL COMMENT '用户名',
    password VARCHAR(64) COMMENT '密码',
    sex TINYINT COMMENT '性别',
    email VARCHAR(64) COMMENT '邮箱',
    mobile_phone VARCHAR(16) COMMENT '手机号',
    telephone VARCHAR(16) COMMENT '电话',
    nick_name VARCHAR(64) COMMENT '昵称',
    real_name VARCHAR(16) COMMENT '真实姓名',
    id_card_num VARCHAR(32) COMMENT '身份证号',
    user_type VARCHAR(32) COMMENT '用户类型',
    user_status TINYINT COMMENT '用户状态'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE UNIQUE INDEX lit_user_user_name_uindex ON imooc.lit_user (user_name);
CREATE UNIQUE INDEX lit_user_email_uindex ON imooc.lit_user (email);
CREATE UNIQUE INDEX lit_user_mobile_phone_uindex ON imooc.lit_user (mobile_phone);