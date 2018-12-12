package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/16 19:54.
 * 审批状态 待审批,已通过,已拒绝
 */
public enum AuditStruts {

    /**
     * 待审批
     */
    WAIT("待审批"),

    /**
     * 已通过
     */
    CTHR("已通过"),

    /**
     * 已拒绝
     */
    AREJ("已拒绝"),;

    private String desc;

    AuditStruts(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
