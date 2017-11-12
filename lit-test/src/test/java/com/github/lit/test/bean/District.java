package com.github.lit.test.bean;

import com.github.lit.jdbc.annotation.GeneratedValue;
import com.github.lit.jdbc.annotation.Id;
import com.github.lit.jdbc.annotation.Table;
import com.github.lit.jdbc.enums.GenerationType;
import lombok.Data;

/**
 * User : liulu
 * Date : 2017/11/4 11:40
 * version $Id: District.java, v 0.1 Exp $
 */
@Data
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String type;
    private Integer orderNum;
    private Long parentId;



}
