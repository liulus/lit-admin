package com.github.lit.plugin.core.configuration;

import com.github.lit.support.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;
import java.util.Map;


/**
 * @author liulu
 * @version v1.0
 * date 2019-05-12
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Map exception(HandlerMethod handlerMethod, Exception ex) {
         Map<String, Object> result = new HashMap<>();
         result.put("success", false);

        BizException bizException = findBizException(ex);
        if (bizException != null) {
            if (StringUtils.hasText(bizException.getCode())) {
                result.put("code", bizException.getCode());
            }
            result.put("message", bizException.getMessage());

            StackTraceElement traceElement = ex.getStackTrace()[0];
            log.warn("\n biz exception --> class: [{}], method: [{}], line: [{}], code: [{}],  message: [{}]",
                    traceElement.getClassName(), traceElement.getMethodName(), traceElement.getLineNumber(),
                    bizException.getCode(), bizException.getMessage());
        } else {
            result.put("code", "-1");
            result.put("message", "系统异常");
            result.put("error", ex.getMessage());
            log.error("unchecked exception", ex);
        }
        return result;

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

}
