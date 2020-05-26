package com.lit.service.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/5
 */
@Getter
@Setter
@ToString
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = 9117520677523958701L;

    private Boolean success;
    private String code;
    private String message;

    private Object result;





}
