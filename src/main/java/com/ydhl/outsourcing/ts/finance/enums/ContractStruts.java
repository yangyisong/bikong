package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @create 2018/1/16 19:54.
 * 合同状态
 */
public enum ContractStruts {
    /**
     * 待激活
     */
    DJH("待激活"),

    /**
     * 待激活
     */
    YJH("已激活"),

    /**
     * 未到期
     */
    WDQ("未到期"),

    /**
     * 已到期
     */
    YDQ("已到期"),

    /**
     * 已关闭
     */
    YGB("已关闭"),;

    private String desc;

    ContractStruts(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
