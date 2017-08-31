
-- 字典表
CREATE TABLE lit_dictionary
(
    dict_id INT unsigned PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '字典Id',
    dict_key VARCHAR(128) NOT NULL COMMENT '字典key',
    dict_value VARCHAR(128) NOT NULL COMMENT '字典值',
    order_num SMALLINT unsigned COMMENT '顺序号',
    dict_level TINYINT unsigned COMMENT '字典层级',
    memo VARCHAR(512) COMMENT '备注',
    is_system TINYINT COMMENT '是否系统字典数据',
    parent_id INT unsigned COMMENT '上级字典Id',
    CONSTRAINT fk_pid_did FOREIGN KEY (parent_id) REFERENCES lit_dictionary (dict_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

CREATE INDEX fk_pid_did ON lit_dictionary (parent_id);


-- 菜单表
CREATE TABLE lit_menu
(
    menu_id INT unsigned PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '菜单Id',
    menu_code VARCHAR(128) NOT NULL COMMENT '菜单编码',
    menu_name VARCHAR(256) NOT NULL COMMENT '菜单名称',
    menu_icon VARCHAR(128) COMMENT '菜单图标',
    menu_url VARCHAR (256) COMMENT '链接',
    order_num SMALLINT unsigned COMMENT '顺序号',
    memo VARCHAR(512) COMMENT '备注',
    menu_type VARCHAR (128) COMMENT '菜单类型',
    module VARCHAR(128) COMMENT '所属模块',
    is_enable TINYINT COMMENT '是否启用',
    parent_id INT unsigned COMMENT '上级菜单Id',
    CONSTRAINT fk_pid_mid FOREIGN KEY (parent_id) REFERENCES lit_menu (menu_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE INDEX fk_pid_mid ON lit_menu (parent_id);

INSERT INTO lit.lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('menu_type', '菜单类型', 1, 1, '菜单类型', 0, null);
INSERT INTO lit.lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('menu_type_top', '顶部导航菜单', 1, 2, '顶部导航菜单', 0, 1);
INSERT INTO lit.lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('menu_type_left', '左侧菜单', 2, 2, '左侧菜单', 0, 1);

INSERT INTO lit.lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system', '系统管理', '１', null, 1, '系统管理', 'menu_type_left', null, 1, null);
INSERT INTO lit.lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_menu', '菜单管理', '12', '/plugin/menu', 1, '菜单管理', 'menu_type_left', null, 1, 1);
INSERT INTO lit.lit_menu (menu_code, menu_name, menu_icon, menu_url, order_num, memo, menu_type, module, is_enable, parent_id) VALUES ('system_dictionary', '字典管理', '123', '/plugin/dictionary', 2, '字典管理', 'menu_type_left', null, 1, 1);