package net.skeyurt.lit.test.bean;

import lombok.Data;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;

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
