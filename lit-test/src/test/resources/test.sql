CREATE TABLE lit_goods (
        goods_Id bigint NOT NULL AUTO_INCREMENT COMMENT '商品Id',
        code VARCHAR(32) NOT NULL COMMENT '商品编码',
        name VARCHAR(32) COMMENT '商品名称',
        price DECIMAL(12,2) COMMENT '价格',
        is_delete SMALLINT COMMENT '是否删除',
        inventory bigint COMMENT '库存',
        create_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        PRIMARY KEY (goods_Id),
        INDEX idx_code (code)
    )ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='商品表';