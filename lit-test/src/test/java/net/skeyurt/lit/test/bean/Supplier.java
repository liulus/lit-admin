package net.skeyurt.lit.test.bean;

import lombok.Builder;
import lombok.Data;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017/6/11 21:00
 * version $Id: Supplier.java, v 0.1 Exp $
 */
@Table(name = "lit_supplier")
@Data
@Builder
public class Supplier implements Serializable {

    private static final long serialVersionUID = 548793140920612818L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String code;
    private String name;
    private String address;
    private String contact;
    private String telephone;
    private String mobile;

}
