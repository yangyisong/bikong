package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/12 11:01.
 */
public enum TimeType {

    ONCE("曾经购买"),

    EXPE("预计购买"),;

    private String desc;

    TimeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
