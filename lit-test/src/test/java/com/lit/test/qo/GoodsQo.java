package com.lit.test.qo;

import com.lit.commons.page.Pager;

import java.util.List;

/**
 * User : liulu
 * Date : 2017-1-10 21:23
 * version $Id: GoodsQo.java, v 0.1 Exp $
 */
public class GoodsQo extends Pager {

    private static final long serialVersionUID = -5053198635663636680L;

    private List<String> codes;

    private String name;

    private String code;

    private String aaa;

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }
}
