package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @create dell 2018/1/25 19:54.
 */
public enum ProductSignInfoType {

    /**
     * 输入框
     */
    INPUT("input"),

    /**
     * 单选框
     */
    RADIO("radio"),

    /**
     * 多选框
     */
    CHECKBOX("checkbox"),;

    private String desc;

    ProductSignInfoType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
