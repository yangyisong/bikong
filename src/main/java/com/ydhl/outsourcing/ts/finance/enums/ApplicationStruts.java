package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/16 19:54.
 * 申请状态
 */
public enum ApplicationStruts {

    /**
     * 审核中
     */
    BREV("审核中"),

    /**
     * 通过
     */
    CTHR("已通过"),

    /**
     * 驳回
     */
    AREJ("未通过"),;

    private String desc;

    ApplicationStruts(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
