package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/16 15:22.
 */
public enum IncomeType {

    /**
     * 正常结息
     */
    N_S("正常结息"),

    /**
     * 退还本金
     */
    R_P("退还本金"),

    /**
     * 续约本金
     */
    C_P("续约本金"),

    /**
     * 转换本金
     */
    C_V_C("转换本金"),

    /**
     * 临退结息
     */
    T_I("临退结息");

    private String desc;

    IncomeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
