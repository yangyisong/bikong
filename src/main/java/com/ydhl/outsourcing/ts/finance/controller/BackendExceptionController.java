package com.ydhl.outsourcing.ts.finance.controller;


import com.ydhl.outsourcing.ts.finance.base.ExceptionController;
import com.ydhl.outsourcing.ts.finance.common.utils.NetworkUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>类简述：全局异常控制器</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 */
@ControllerAdvice
@Controller
public class BackendExceptionController extends ExceptionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendExceptionController.class);

    private static final MappingJackson2JsonView DEFAULT_JSON_VIEW = new MappingJackson2JsonView();

    @Override
    protected ModelAndView prepareExceptionInfo(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String errorCode, String errorMessage, Exception exception) {
        LOGGER.error("client ip: [{}]", NetworkUtils.getIpAddress(request));

        Map<String, Object> models = new LinkedHashMap<>();
        models.put("errorCode", errorCode);
        models.put("errorMessage", errorMessage);
        ModelAndView modelAndView = new ModelAndView();
        if (noNeedWrapper(request)) {
            modelAndView.setView(DEFAULT_JSON_VIEW);
            modelAndView.addAllObjects(models);
            return modelAndView;
        } else {
            modelAndView.setViewName("error");
            modelAndView.addAllObjects(models);
            return modelAndView;
        }
    }

    private boolean noNeedWrapper(HttpServletRequest request) {
        String xmlHttpRequest = request.getHeader("X-Requested-With");
        String noWrapperParameter = StringUtils.defaultString(request.getParameter("noSiteMeshWapper"));
        return "XMLHttpRequest".equalsIgnoreCase(xmlHttpRequest)
                || noWrapperParameter.equalsIgnoreCase("true")
                || noWrapperParameter.equalsIgnoreCase("yes")
                || noWrapperParameter.equals("1");
    }

}
