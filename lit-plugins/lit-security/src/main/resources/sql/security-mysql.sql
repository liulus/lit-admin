CREATE TABLE lit_authority
(
    id       INT UNSIGNED PRIMARY KEY            NOT NULL AUTO_INCREMENT
    COMMENT 'id',
    code     VARCHAR(32) DEFAULT ''              NOT NULL
    COMMENT '权限码',
    name     VARCHAR(128) DEFAULT ''             NOT NULL
    COMMENT '权限码名称',
    module   VARCHAR(64) DEFAULT ''              NOT NULL
    COMMENT '权限类型',
    remark   VARCHAR(512) DEFAULT ''             NOT NULL
    COMMENT '备注',
    sys_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    ON UPDATE CURRENT_TIMESTAMP
)
    ENGINE = InnoDB
    COMMENT ='权限码';

CREATE UNIQUE INDEX uk_code
    ON lit_authority (code);

CREATE TABLE lit_role (
    id       INT UNSIGNED PRIMARY KEY                     NOT NULL AUTO_INCREMENT
    COMMENT '角色Id',
    code     VARCHAR(32) DEFAULT ''                       NOT NULL
    COMMENT '角色编号',
    name     VARCHAR(64) DEFAULT ''                       NOT NULL
    COMMENT '角色名',
    remark   VARCHAR(512) DEFAULT ''                      NOT NULL
    COMMENT '备注',
    sys_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP          NOT NULL
    ON UPDATE CURRENT_TIMESTAMP
)
    ENGINE = InnoDB
    COMMENT ='角色';

CREATE UNIQUE INDEX uk_code
    ON lit_role (code);


CREATE TABLE lit_role_authority (
    id           INT UNSIGNED PRIMARY KEY                        NOT NULL AUTO_INCREMENT,
    role_id      INT UNSIGNED DEFAULT 0                          NOT NULL
    COMMENT '角色Id',
    authority_id INT UNSIGNED DEFAULT 0                          NOT NULL
    COMMENT '权限码Id',
    sys_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP             NOT NULL
    ON UPDATE CURRENT_TIMESTAMP
)
    ENGINE = InnoDB
    COMMENT ='角色权限关联表';

CREATE UNIQUE INDEX uk_role_auth
    ON lit_role_authority (role_id, authority_id);

CREATE TABLE lit_user_role (
    id       INT UNSIGNED PRIMARY KEY                     NOT NULL AUTO_INCREMENT,
    user_id  INT UNSIGNED DEFAULT 0                       NOT NULL
    COMMENT '用户Id',
    role_id  INT UNSIGNED DEFAULT 0                       NOT NULL
    COMMENT '角色Id',
    sys_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP          NOT NULL
    ON UPDATE CURRENT_TIMESTAMP
)
    ENGINE = InnoDB
    COMMENT ='用户角色关联表';
CREATE UNIQUE INDEX uk_user_role
    ON lit_user_role (user_id, role_id);
