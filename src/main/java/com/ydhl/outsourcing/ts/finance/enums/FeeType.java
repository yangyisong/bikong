package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/16 10:30.
 */
public enum FeeType {

    /**
     * 利息
     */
    INSERT("利息"),

    /**
     * 金额
     */
    AMOUNT("金额"),;

    private String desc;

    FeeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
