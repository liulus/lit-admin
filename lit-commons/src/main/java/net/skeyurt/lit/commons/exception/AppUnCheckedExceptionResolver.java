package net.skeyurt.lit.commons.exception;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.condition.ConditionalOnClass;
import net.skeyurt.lit.commons.context.ResultConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * User : liulu
 * Date : 2017/3/21 20:50
 * version $Id: AppExceptionResolver.java, v 0.1 Exp $
 */
@Component
@ConditionalOnClass(HandlerExceptionResolver.class)
@Slf4j
public class AppUnCheckedExceptionResolver implements HandlerExceptionResolver {

    @Value("${error.page:error}")
    private String errorPage;

    @Value("${error.message:未知异常}")
    private String errorMsg;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

        HashMap<String, Object> map = new HashMap<>();

        if (e instanceof AppCheckedException) {
            String errorCode = ((AppCheckedException) e).getErrorCode();
            if (StringUtils.isNotEmpty(errorCode)) {
                map.put(ResultConst.CODE, errorCode);
            }
            errorMsg = StringUtils.isEmpty(e.getMessage()) ? errorMsg : e.getMessage();
        } else if (e instanceof AppUnCheckedException) {
            errorMsg = StringUtils.isEmpty(e.getMessage()) ? errorMsg : e.getMessage();
        }
        HttpStatus status = getStatus(request);
        map.put(ResultConst.SUCCESS, false);
        map.put(ResultConst.STATUS, status.value());
        map.put(ResultConst.ERROR, status.getReasonPhrase());
        map.put(ResultConst.MASSAGE, errorMsg);
        map.put("path", request.getRequestURI());

        HandlerMethod method = (HandlerMethod) o;
        log.error(String.format("exception: [method=%s], [url=%s]", method.getShortLogMessage(), request.getRequestURI()), e);

        return new ModelAndView(errorPage, map);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
