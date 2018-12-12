package com.ydhl.outsourcing.ts.finance.custantModule;

/**
 * @author Martins
 * @create 2018/1/15 22:35.
 * @description
 */
public enum ConstantModule {

    ERROR_DEPARTMENT_HAS_EXITS("error.department.hasExists", "该部门下已存在相同部门"),

    /**
     * 总部不能被删除
     */
    ERROR_HEAD_NOT_ALLOW_DELETE("error.head.not.allowDelete", "总部不允许被删除"),

    /**
     * 用户名或密码错误
     */
    ERROR_CERTIFICATE_FAILED("error.user.certificateFailed", "用户名或密码错误"),

    ERROR_ACCOUNT_HAD_LIMIT("error.account.hadLimit", "账号被限制登录，请联系系统管理员"),

    /**
     * 部门下有员工不允许被删除
     */
    ERROR_DEPARTMENT_HAS_USER_NOT_ALLOW_DELETE("error.department.hasUser.not.allowDelete", "该部门下存在员工不允许被删除"),

    /**
     * 用户名已存在
     */
    ERROR_USERNAME_HAS_EXITS("error.username.hasExists","该用户名已存在"),

    /**
     * 有申请在流程中，不允许转换
     */
    ERROR_HAS_APPLICATION_NOT_ALLOW_CONVERSION("error.has.application.notAllow.conversion","目前有申请正在流程中，不允许客户转换"),

    /**
     * 用户名下存在客户
     */
    ERROR_HAS_CUSTOMER_EXITS("error.has.customerExists","选中用户中有部分拥有客户,请转移后停用"),

    /**
     * 用户名下存在客户
     */
    ERROR_IDCAR_OR_PHONE_HAS_EXITS("error.idcard.or.phone.hasExists","列表中的身份证或手机号已存在"),;

    private String code;

    private String message;

    ConstantModule(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
