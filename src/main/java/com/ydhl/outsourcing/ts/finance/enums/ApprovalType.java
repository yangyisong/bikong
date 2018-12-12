package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/16 14:51.
 */
public enum ApprovalType {

    /**
     * 未申请
     */
    N_A("未申请"),

    /**
     * 审批中
     */
    A_I("审批中"),

    /**
     * 待打款
     */
    W_P("待打款"),

    /**
     * 已作废
     */
    H_I("已作废"),

    /**
     * 已续约
     */
    H_C("已续约"),

    /**
     * 已打款
     */
    H_P("已打款"),

    /**
     * 已转换
     */
    H_C_V("已转换"),;

    private String desc;

    ApprovalType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
