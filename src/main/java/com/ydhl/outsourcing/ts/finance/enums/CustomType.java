package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/12 10:03.
 */
public enum CustomType {

    /**
     * 内部员工
     */
    IN_CUS("内部员工"),

    /**
     * 外部人员
     */
    OUT_CUS("外部客户"),;

    private String desc;

    CustomType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
