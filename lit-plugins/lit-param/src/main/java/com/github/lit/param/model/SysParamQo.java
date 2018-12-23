package com.github.lit.param.model;

import com.github.lit.support.page.PageRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User : liulu
 * Date : 17-9-17 下午3:03
 * version $Id: ParamVo.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
public class SysParamQo extends PageRequest {

    private static final long serialVersionUID = -117622963182937153L;

    private String keyword;

    private String code;



}
