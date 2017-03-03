package com.lit.commons.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * User : liulu
 * Date : 2017-2-21 22:09
 * version $Id: ExceptionHandlerController.java, v 0.1 Exp $
 */

@ControllerAdvice
public class ExceptionHandlerController {

    @Value("${error.page:error}")
    private String errorPage;

    @ExceptionHandler(Exception.class)
    public String exceptionHandler() {
        return "";
    }
}
