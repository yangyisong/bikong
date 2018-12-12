package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/12 10:07.
 */
public enum CustomSource {

    /**
     * 新增
     */
    ADD("新增"),

    /**
     * 转移
     */
    TRAN("转移"),

    /**
     * 录入
     */
    IMPO("录入"),;

    private String desc;

    CustomSource(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
