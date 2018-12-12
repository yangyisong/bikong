package com.ydhl.outsourcing.ts.finance.base;


import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.exception.InterfaceDeprecatedException;
import com.ydhl.outsourcing.ts.finance.exception.MaxSessionException;
import com.ydhl.outsourcing.ts.finance.exception.PageNotFoundException;
import com.ydhl.outsourcing.ts.finance.exception.RequestLimitException;
import com.ydhl.outsourcing.ts.finance.exception.SessionTimeoutException;
import com.ydhl.outsourcing.ts.finance.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;


public abstract class ExceptionController {

    public static final String PATH_ERROR_MAX_SESSION = "/error/max_sessions";
    public static final String PATH_TIME_OUT = "/error/time_out";
    private static final String PATH_ERROR_ALL = "/error/all";

    private static final String MAX_SESSION_LIMIT = "max_session_limit";
    private static final String TIME_OUT = "time_out";
    private static final String UNAUTHORIZED = "unauthorized";
    private static final String INVALID_PARAM = "invalid_param";
    private static final String PAGE_NOT_FOUND = "page_not_found";
    private static final String METHOD_NOT_ALLOWED = "method_not_allowed";
    private static final String INTERFACE_DEPRECATED = "interface_deprecated";
    private static final String GATEWAY_TIMEOUT = "gateway_timeout";
    private static final String TOO_MANY_REQUESTS = "too_many_requests";
    private static final String INTERNAL_SERVER_ERROR = "internal_server_error";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @Resource
    private MessageSource messageSource;

    private boolean showRealError = false;

    public void setShowRealError(boolean showRealError) {
        this.showRealError = showRealError;
    }

    /**
     * session超过最大限制异常
     *
     * @return 异常页面
     */
    @RequestMapping(PATH_ERROR_MAX_SESSION)
    public String maxSessions() throws MaxSessionException {
        throw new MaxSessionException();
    }

    /**
     * 超时异常
     *
     * @return 异常页面
     */
    @RequestMapping(PATH_TIME_OUT)
    public String timeout() throws SessionTimeoutException {
        throw new SessionTimeoutException();
    }

    /**
     * 其他异常
     *
     * @param request HttpServletRequest
     * @return 异常页面
     * @throws Throwable
     */
    @RequestMapping(PATH_ERROR_ALL)
    public String error(HttpServletRequest request) throws Throwable {
        Integer code = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        Throwable exception = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
        String simpleMsg = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();

        LOGGER.error("*****Spring can't catch this error code[{}], message[{}], currUserId[{}]*****", code, simpleMsg, currUserId, exception);
        if (exception != null) {
            throw getRootCause(exception);
        } else {
            switch (code) {
                case 404:
                    throw new PageNotFoundException();
                case 403:
                    throw new AccessDeniedException(simpleMsg);
                default:
                    throw new Exception(simpleMsg);
            }
        }
    }

    /**
     * 接口过时异常 302
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = InterfaceDeprecatedException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ModelAndView interfaceDeprecated(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(INTERFACE_DEPRECATED, null, "您的App版本过低，请升级", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch InterfaceDeprecatedException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.FOUND, INTERFACE_DEPRECATED, errorMessage, exception);
    }

    /**
     * 同时登陆数量超过最大异常 401
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = MaxSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView maxSessionException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(MAX_SESSION_LIMIT, null, "同时登陆的会话已达最大阀值", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch MaxSessionException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.UNAUTHORIZED, MAX_SESSION_LIMIT, errorMessage, exception);
    }

    /**
     * 会话超时异常 401
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = SessionTimeoutException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView sessionTimeoutException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(TIME_OUT, null, "会话超时", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch SessionTimeoutException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.UNAUTHORIZED, TIME_OUT, errorMessage, exception);
    }

    /**
     * 无权访问指定页面异常 401
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView accessDeniedException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(UNAUTHORIZED, null, "无权访问", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch AccessDeniedException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.UNAUTHORIZED, UNAUTHORIZED, errorMessage, exception);
    }

    /**
     * 系统实体校验，丢失参数，参数类型转换异常 400
     *
     * @param request   页面请求
     * @param exception 异常
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = {ValidateException.class, ServletRequestBindingException.class, TypeMismatchException.class, BindException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView validateException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(INVALID_PARAM, null, "请求数据无法通过验证", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch ValidateException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        if (showRealError) {
            if (exception instanceof ConstraintViolationException) {
                StringBuilder sb = new StringBuilder();
                Set<ConstraintViolation<?>> constraintViolationSet = ((ConstraintViolationException) exception).getConstraintViolations();
                for (ConstraintViolation<?> constraintViolation : constraintViolationSet) {
                    sb.append("[");
                    sb.append(constraintViolation.getPropertyPath());
                    sb.append(constraintViolation.getMessage());
                    sb.append("]");
                }
                errorMessage = sb.toString();
            } else if (exception instanceof ValidateException) {
                StringBuilder sb = new StringBuilder();
                List<String> codeList = ((ValidateException) exception).getCodeList();
                for (String code : codeList) {
                    sb.append("[");
                    sb.append(code);
                    sb.append("]");
                }
                errorMessage = sb.toString();
            } else {
                errorMessage = exception.getMessage();
            }
        }
        return prepareExceptionInfo(request, response, HttpStatus.BAD_REQUEST, INVALID_PARAM, errorMessage, exception);
    }

    /**
     * 系统业务异常 403
     *
     * @param request   页面请求
     * @param exception 异常
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView businessException(HttpServletRequest request, HttpServletResponse response, BusinessException exception) {
        String errorMessage = messageSource.getMessage(exception.getCode(), exception.getArguments(), LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch BusinessException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), errorMessage, currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.FORBIDDEN, exception.getCode(), errorMessage, exception);
    }

    /**
     * 找不到指定页面异常 404
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView pageNotFoundException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(PAGE_NOT_FOUND, null, "找不到指定页面", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch PageNotFoundException: errorUrl[{}],errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.NOT_FOUND, PAGE_NOT_FOUND, errorMessage, exception);
    }

    /**
     * 请求方法错误 405
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String requestMethod = request.getMethod();
        String errorMessage = messageSource.getMessage(METHOD_NOT_ALLOWED, new String[]{requestMethod}, "该接口不支持{0}请求", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch HttpRequestMethodNotSupportedException: errorUrl[{}], errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED, errorMessage, exception);
    }

    /**
     * 太多请求 429
     *
     * @param request 页面请求
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler(value = RequestLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ModelAndView requestLimitException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String errorMessage = messageSource.getMessage(TOO_MANY_REQUESTS, null, "接口繁忙，请稍后再试", LocaleContextHolder.getLocale());
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();
        LOGGER.error("Error catch RequestLimitException: errorUrl[{}], errorMessage[{}], currUserId[{}]", request.getRequestURI(), exception.getMessage(), currUserId);
        return prepareExceptionInfo(request, response, HttpStatus.TOO_MANY_REQUESTS, TOO_MANY_REQUESTS, errorMessage, exception);
    }


    /**
     * 其他异常 500
     *
     * @param request   页面请求
     * @param exception 异常
     * @return ExceptionDto 异常JSON
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView springErrorHand(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String simpleMsg = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
        String errorCode = exception == null ? simpleMsg : exception.getClass().getSimpleName();
        String errorMessage = exception == null ? simpleMsg : exception.getMessage();
        Long currUserId = CurrentUserUtils.currentUserIdWithNull();

        LOGGER.error("Error catch: statusCode[{}], errorCode[{}], errorMessage[{}], errorUrl[{}], currUserId[{}]"
                , HttpStatus.INTERNAL_SERVER_ERROR.value()
                , errorCode
                , errorMessage
                , request.getRequestURI()
                , currUserId
                , exception);
        String realMessage = messageSource.getMessage(INTERNAL_SERVER_ERROR, null, errorMessage, LocaleContextHolder.getLocale());
        if (showRealError) {
            realMessage = errorMessage;
        }
        return prepareExceptionInfo(request, response, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR, realMessage, exception);
    }

    abstract protected ModelAndView prepareExceptionInfo(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String errorCode, String errorMessage, Exception exception);

    private Throwable getRootCause(Throwable exception) {
        if (exception.getCause() != null) {
            return getRootCause(exception.getCause());
        }
        return exception;
    }
}
