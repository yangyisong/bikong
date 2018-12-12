package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/12 10:48.
 */
public enum SettleType {

    MON("月付"),

    QUAR("季付"),

    YEAR("年付");

    private String desc;

    SettleType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
