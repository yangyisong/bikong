package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/16 13:57.
 */
public enum EarningsType {

    LADDER("阶梯"),

    FIXED("固定"),;

    private String desc;

    EarningsType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
