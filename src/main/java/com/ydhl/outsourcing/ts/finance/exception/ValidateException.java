package com.ydhl.outsourcing.ts.finance.exception;


import java.util.List;
import java.util.Map;

/**
 * <p>类简述：数据对象验证异常</p>
 * <p>
 * <p>描述：使用{@link org.springframework.validation.annotation.Validated}注解校验的错误，需要使用该异常包装后抛出</p>
 * <p>
 * <p>补充：数据对象的国际化格式为attr.数据对象名.属性</p>
 *
 * @author Junpeng.Su
 */
public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 7450363518285869297L;

    private final List<String> codeList;

    private final List<String> messageList;

    private final Map<String, Object[]> argumentsMap;

    //TODO 临时解决message 报null的问题
    private final String message;


    public ValidateException() {
        this.codeList = null;
        this.argumentsMap = null;
        this.messageList = null ;
        this.message = null ;
    }

    public ValidateException(List<String> codeList, List<String> messageList, Map<String, Object[]> argumentsMap, String message) {
        this.codeList = codeList;
        this.messageList = messageList ;
        this.argumentsMap = argumentsMap;
        this.message = message ;

    }

    public List<String> getCodeList() {
        return codeList;
    }

    public Map<String, Object[]> getArgumentsMap() {
        return argumentsMap;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
