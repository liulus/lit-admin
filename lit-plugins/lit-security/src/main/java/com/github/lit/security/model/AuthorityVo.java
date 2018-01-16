package com.github.lit.security.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/11/19 16:40
 * version $Id: AuthorityVo.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
public class AuthorityVo extends Authority {

    private static final long serialVersionUID = -5010568354608479443L;

    // zTree 属性 隐藏选择框
    private Boolean nocheck;

    private Boolean isParent;

    private List<AuthorityVo> children;
}
