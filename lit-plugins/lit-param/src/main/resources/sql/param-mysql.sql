CREATE TABLE lit_param
(
  id  INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '参数Id',
  code      VARCHAR(32)              NOT NULL DEFAULT ''
  COMMENT '参数编码',
  value     VARCHAR(64)              NOT NULL DEFAULT ''
  COMMENT '参数值',
  remark      VARCHAR(256)             NOT NULL DEFAULT ''
  COMMENT '备注',
  is_system TINYINT(1)               NOT NULL DEFAULT 0
  COMMENT '是否系统级',
  sys_time  TIMESTAMP                NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  COMMENT = '参数表';

CREATE UNIQUE INDEX uk_code
  ON lit_param (code);