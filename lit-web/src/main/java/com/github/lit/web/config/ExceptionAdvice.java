package com.github.lit.web.config;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.commons.exception.BizException;
import com.github.lit.web.annotation.ViewName;
import com.github.lit.web.util.WebUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * User : liulu
 * Date : 2017/6/14 19:25
 * version $Id: ExceptionAdvice.java, v 0.1 Exp $
 */

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @Value("${unchecked.error.message:系统错误}")
    private String errorMsg;


    @ExceptionHandler(Exception.class)
    public String exception(HandlerMethod handlerMethod, Model model, Exception ex) {
        model.addAttribute(ResultConst.SUCCESS, false);

        BizException bizException = findBizException(ex);
        if (bizException != null) {
            if (!Strings.isNullOrEmpty(bizException.getCode())) {
                model.addAttribute(ResultConst.CODE, bizException.getCode());
            }
            model.addAttribute(ResultConst.MASSAGE, bizException.getMessage());

            StackTraceElement traceElement = ex.getStackTrace()[0];
            log.warn("\n biz exception --> class: [{}], method: [{}], line: [{}], code: [{}],  message: [{}]",
                    traceElement.getClassName(), traceElement.getMethodName(), traceElement.getLineNumber(),
                    bizException.getCode(), bizException.getMessage());
            // 处理自定义视图名称
            ViewName viewName = handlerMethod.getMethodAnnotation(ViewName.class);
            if (viewName != null && !viewName.spel()) {
                return viewName.value();
            }
        } else {
            model.addAttribute(ResultConst.CODE, "9999");
            model.addAttribute(ResultConst.MASSAGE, errorMsg);
            model.addAttribute(ResultConst.ERROR, ex.getMessage());
            log.error("unchecked exception", ex);
        }

        // json 请求直接返回空
        HttpServletRequest request = WebUtils.getRequest();
        boolean isJsonContentType = Optional.ofNullable(request.getContentType())
                .map(contentType -> contentType.contains(MediaType.APPLICATION_JSON_VALUE))
                .orElse(false);
        if (request.getRequestURI().endsWith(".json") || isJsonContentType) {
            return "";
        }
        return "error/error";
    }

    private BizException findBizException(Throwable ex) {
        while (ex != null) {
            if (ex instanceof BizException) {
                return (BizException) ex;
            }
            ex = ex.getCause();
        }
        return null;
    }

    private void handlerResponseStatus(HttpServletResponse response, Exception ex) {
        if (ex instanceof BizException) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            String[] supportedMethods = ((HttpRequestMethodNotSupportedException) ex).getSupportedMethods();
            if (supportedMethods != null) {
                log.warn("Allow", StringUtils.arrayToDelimitedString(supportedMethods, ", "));
            }
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            List<MediaType> mediaTypes = ((HttpMediaTypeNotSupportedException) ex).getSupportedMediaTypes();
            if (!CollectionUtils.isEmpty(mediaTypes)) {
                log.warn("Accept", MediaType.toString(mediaTypes));
            }
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        } else if (ex instanceof MissingPathVariableException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else if (ex instanceof MissingServletRequestParameterException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof ServletRequestBindingException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof ConversionNotSupportedException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else if (ex instanceof TypeMismatchException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof HttpMessageNotReadableException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof HttpMessageNotWritableException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else if (ex instanceof MethodArgumentNotValidException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof MissingServletRequestPartException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof BindException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof NoHandlerFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (ex instanceof AsyncRequestTimeoutException) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

}
