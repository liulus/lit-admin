CREATE TABLE lit_param
(
  param_id    INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '参数Id',
  param_code  VARCHAR(32) NOT NULL DEFAULT '' COMMENT '参数编码',
  param_value VARCHAR(64) NOT NULL DEFAULT '' COMMENT '参数值',
  memo        VARCHAR(256) DEFAULT '' COMMENT '备注',
  is_system   TINYINT COMMENT '是否系统级',
  is_load     TINYINT COMMENT '是否启动加载',
  sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数表';

CREATE UNIQUE INDEX lit_param_param_code_uindex
  ON lit_param (param_code);