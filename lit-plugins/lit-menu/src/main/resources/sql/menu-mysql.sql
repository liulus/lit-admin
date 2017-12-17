
CREATE TABLE lit_menu
(
    menu_id INT unsigned PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '菜单Id',
    menu_code VARCHAR(128) NOT NULL COMMENT '菜单编码',
    menu_name VARCHAR(256) NOT NULL COMMENT '菜单名称',
    menu_icon VARCHAR(128) DEFAULT '' COMMENT '菜单图标',
    menu_url VARCHAR (256) DEFAULT '' COMMENT '链接',
    order_num SMALLINT unsigned COMMENT '顺序号',
    memo VARCHAR(512)  DEFAULT '' COMMENT '备注',
    menu_type VARCHAR(128) DEFAULT ''  COMMENT '菜单类型',
    module VARCHAR(128)  DEFAULT '' COMMENT '所属模块',
    is_enable TINYINT COMMENT '是否启用',
    parent_id INT unsigned COMMENT '上级菜单Id',
    CONSTRAINT fk_pid_mid FOREIGN KEY (parent_id) REFERENCES lit_menu (menu_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE INDEX fk_pid_mid ON lit_menu (parent_id);