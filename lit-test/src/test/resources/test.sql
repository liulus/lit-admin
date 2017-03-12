-- MYSQL 建表语句
CREATE TABLE lit_goods (
        goods_Id bigint NOT NULL AUTO_INCREMENT COMMENT '商品Id',
        code VARCHAR(32) NOT NULL COMMENT '商品编码',
        name VARCHAR(256) COMMENT '商品名称',
        price DECIMAL(12,2) COMMENT '价格',
        is_delete SMALLINT COMMENT '是否删除',
        inventory bigint COMMENT '库存',
        create_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        PRIMARY KEY (goods_Id),
        INDEX idx_code (code)
    )ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='商品表';


-- ORACLE 建表语句
CREATE TABLE lit_goods(
    goods_Id INT NOT NULL,
    code VARCHAR2(32) NOT NULL,
    name VARCHAR2(256),
    price DECIMAL(12,2),
    is_delete SMALLINT,
    inventory INTEGER,
    gmt_Create TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (goods_Id),
);
CREATE UNIQUE INDEX "lit_goods_code_uindex" ON lit_goods (code);
COMMENT ON TABLE lit_goods IS '商品表';
COMMENT ON COLUMN lit_goods.goods_Id IS '商品Id';
COMMENT ON COLUMN lit_goods.code IS '商品编码';
COMMENT ON COLUMN lit_goods.name IS '商品名称';
COMMENT ON COLUMN lit_goods.price IS '价格';
COMMENT ON COLUMN lit_goods.is_delete IS '删除标志: 0删除, 1未删除';
COMMENT ON COLUMN lit_goods.inventory IS '库存';
COMMENT ON COLUMN lit_goods.gmt_Create IS '创建时间';

