package net.skeyurt.lit.commons.exception;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.context.ResultConst;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/6/14 19:25
 * version $Id: ExceptionAdvice.java, v 0.1 Exp $
 */

@Slf4j
@ControllerAdvice
@Conditional(EnableExceptionHandlerCondition.class)
public class ExceptionAdvice {

    @Value("${unchecked.error.message:系统错误}")
    private String errorMsg;


    @ModelAttribute
    public void success(Model model) {
        model.addAttribute(ResultConst.SUCCESS, true);
    }

    @ExceptionHandler(Exception.class)
    public String exception(Model model, HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        model.addAttribute(ResultConst.SUCCESS, false);
        if (ex instanceof AppException) {
            AppException checkedException = (AppException) ex;
            model.addAttribute(ResultConst.CODE, checkedException.getErrorCode());
            model.addAttribute(ResultConst.MASSAGE, checkedException.getErrorMsg());

            StackTraceElement traceElement = ex.getStackTrace()[0];
            log.warn("\n checked exception --> class: [{}], method: [{}], line: [{}], code: [{}],  message: [{}]",
                    traceElement.getClassName(), traceElement.getMethodName(), traceElement.getLineNumber(),
                    checkedException.getErrorCode(), checkedException.getErrorMsg());
        } else {
            model.addAttribute(ResultConst.MASSAGE, errorMsg);
            model.addAttribute(ResultConst.ERROR, ex.getMessage());
            model.addAttribute(ResultConst.URL, request.getRequestURI());
            log.error("unchecked exception", ex);
        }
        handlerResponseStatus(response, ex);

        model.addAttribute(ResultConst.STATUS, response.getStatus());

        return "error";
    }

    private void handlerResponseStatus(HttpServletResponse response, Exception ex) {
        if (ex instanceof AppException) {
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
