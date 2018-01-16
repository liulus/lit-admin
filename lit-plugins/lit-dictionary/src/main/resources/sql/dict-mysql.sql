
CREATE TABLE lit_dictionary
(
    dict_id INT unsigned PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '字典Id',
    dict_key VARCHAR(128) NOT NULL DEFAULT '' COMMENT '字典key',
    dict_value VARCHAR(128) NOT NULL DEFAULT '' COMMENT '字典值',
    order_num SMALLINT unsigned COMMENT '顺序号',
    dict_level TINYINT unsigned COMMENT '字典层级',
    memo VARCHAR(512) DEFAULT '' COMMENT '备注',
    is_system TINYINT COMMENT '是否系统字典数据',
    parent_id INT unsigned COMMENT '上级字典Id',
    sys_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

CREATE INDEX fk_pid_did ON lit_dictionary (parent_id);