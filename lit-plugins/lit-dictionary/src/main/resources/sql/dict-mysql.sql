-- dictionary
CREATE TABLE lit_dictionary
(
  id    INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '字典Id',
  parent_id  INT UNSIGNED             NOT NULL DEFAULT 0
  COMMENT '上级字典Id',
  dict_key   VARCHAR(32)              NOT NULL DEFAULT ''
  COMMENT '字典key',
  dict_value VARCHAR(32)              NOT NULL DEFAULT ''
  COMMENT '字典值',
  order_num  SMALLINT UNSIGNED        NOT NULL DEFAULT 0
  COMMENT '顺序号',
  remark       VARCHAR(256)             NOT NULL DEFAULT ''
  COMMENT '备注',
  is_system  TINYINT(1)               NOT NULL DEFAULT 0
  COMMENT '是否系统字典数据',
  sys_time   TIMESTAMP                NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  COMMENT = '字典表';

create UNIQUE INDEX uk_pid_key
  ON lit_dictionary (parent_id, dict_key);
