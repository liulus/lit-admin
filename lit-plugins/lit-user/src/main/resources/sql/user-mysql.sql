DROP TABLE IF EXISTS lit_organization;
DROP TABLE IF EXISTS lit_user;

CREATE TABLE lit_organization (
    org_id      INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
    COMMENT '机构id',
    parent_id   INT UNSIGNED DEFAULT 0   NOT NULL
    COMMENT '父机构Id',
    code        VARCHAR(32) DEFAULT ''   NOT NULL
    COMMENT '机构号',
    full_name   VARCHAR(256) DEFAULT ''  NOT NULL
    COMMENT '机构名',
    short_name  VARCHAR(128) DEFAULT ''  NOT NULL
    COMMENT '机构简称',
    type        VARCHAR(64) DEFAULT ''   NOT NULL
    COMMENT '机构类型',
    level_index VARCHAR(32) DEFAULT ''   NOT NULL
    COMMENT '特殊编号, 用于查询',
    address     VARCHAR(512) DEFAULT ''  NOT NULL
    COMMENT '地址',
    memo        VARCHAR(512) DEFAULT ''  NOT NULL
    COMMENT '备注',
    sys_time    TIMESTAMP                NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT = '机构表';

CREATE INDEX uk_code
    ON lit_organization (code);
CREATE UNIQUE INDEX uk_name
    ON lit_organization (full_name);
CREATE INDEX idx_pid_level
    ON lit_organization (parent_id, level_index);


CREATE TABLE lit_user
(
    user_id     INT UNSIGNED PRIMARY KEY           NOT NULL AUTO_INCREMENT
    COMMENT '用户Id',
    org_id      INT UNSIGNED DEFAULT 0             NOT NULL
    COMMENT '机构Id',
    code        VARCHAR(32) DEFAULT ''             NOT NULL
    COMMENT '用户编号',
    job_num     VARCHAR(32) DEFAULT ''             NOT NULL
    COMMENT '工号',
    user_name   VARCHAR(64) DEFAULT ''             NOT NULL
    COMMENT '用户名',
    nick_name   VARCHAR(64) DEFAULT ''             NOT NULL
    COMMENT '昵称',
    real_name   VARCHAR(16) DEFAULT ''             NOT NULL
    COMMENT '真实姓名',
    mobile_num  VARCHAR(16) DEFAULT ''             NOT NULL
    COMMENT '手机号',
    avatar      VARCHAR(256) DEFAULT ''            NOT NULL
    COMMENT '头像',
    password    VARCHAR(64) DEFAULT ''             NOT NULL
    COMMENT '密码',
    gender      TINYINT(1) DEFAULT 1               NOT NULL
    COMMENT '性别',
    email       VARCHAR(64) DEFAULT ''             NOT NULL
    COMMENT '邮箱',
    id_card_num VARCHAR(32) DEFAULT ''             NOT NULL
    COMMENT '身份证号',
    telephone   VARCHAR(16) DEFAULT ''             NOT NULL
    COMMENT '电话',
    type        VARCHAR(32) DEFAULT ''             NOT NULL
    COMMENT '用户类型',
    status      VARCHAR(32) DEFAULT ''             NOT NULL
    COMMENT '用户状态',
    is_lock     TINYINT(1) DEFAULT 0               NOT NULL
    COMMENT '是否锁定',
    creator     VARCHAR(64) DEFAULT ''             NOT NULL
    COMMENT '创建人',
    gmt_create  DATETIME DEFAULT current_timestamp NOT NULL
    COMMENT '创建时间',
    sys_time    TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT = '用户表';
CREATE UNIQUE INDEX uk_name
    ON lit_user (user_name);
CREATE INDEX uk_code_num
    ON lit_user (code, job_num, mobile_num, email, id_card_num);



