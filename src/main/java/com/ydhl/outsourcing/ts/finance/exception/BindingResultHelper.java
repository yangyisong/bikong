package com.ydhl.outsourcing.ts.finance.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindingResultHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BindingResultHelper.class);

    public static void log(BindingResult bindingResult) {
        for (ObjectError oe : bindingResult.getGlobalErrors()) {
            LOGGER.error("*****校验对象【{}】全局错误：{}", oe.getObjectName(), oe.getDefaultMessage());
        }
        for (FieldError fe : bindingResult.getFieldErrors()) {
            LOGGER.error("*****校验对象【{}】的属性【{}】出现错误：{}，输入值为【{}】", fe.getObjectName(), fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue());
        }
    }

    public static void checkAndThrowErrors(BindingResult bindingResult) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            List<String> codeList = new ArrayList<>();
            List<String> messageList = new ArrayList<>();
            Map<String, Object[]> argumentsMap = new HashMap<>();

            String code;
            for (ObjectError oe : bindingResult.getGlobalErrors()) {
                code = oe.getCodes() != null && oe.getCodes().length > 0 ? oe.getCodes()[0] : null;
                if (code != null) {
                    codeList.add(code);
                    argumentsMap.put(code, oe.getArguments());
                }
                LOGGER.error("*****校验对象【{}】全局错误：{}", oe.getObjectName(), oe.getCodes()[0]);
            }
            //TODO 临时解决message 报null的问题
            String message = null;
            for (FieldError fe : bindingResult.getFieldErrors()) {
                code = fe.getCodes() != null && fe.getCodes().length > 0 ? fe.getCodes()[0] : null;
                if (code != null) {
                    codeList.add(code);
                    messageList.add(fe.getDefaultMessage());
                    argumentsMap.put(code, fe.getArguments());
                    message = fe.getDefaultMessage();
                }
                LOGGER.error("*****校验对象【{}】的属性【{}】出现错误：{}，输入值为【{}】", fe.getObjectName(), fe.getField(), fe.getCodes()[0], fe.getRejectedValue());
            }
            throw new ValidateException(codeList, messageList, argumentsMap, message);
        }
    }
}
