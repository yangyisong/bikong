package com.ydhl.outsourcing.ts.finance.exception;

import java.util.Arrays;

/**
 * <p>类简述：系统业务异常</p>
 * <p>
 * <p>描述：所有和业务相关的异常均由改类包装抛出，可指定错误代码以及参数</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 2485329619448041725L;

    private final Object[] arguments;

    private final String code;

    public BusinessException(String code, Object... arguments) {
        this.arguments = arguments;
        this.code = code;
    }

    public Object[] getArguments() {
        return Arrays.copyOf(arguments, arguments.length);
    }

    public String getCode() {
        return code;
    }

}
