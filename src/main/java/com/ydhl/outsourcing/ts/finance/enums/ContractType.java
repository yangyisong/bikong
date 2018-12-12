package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/12 11:18.
 */
public enum ContractType {

    /**
     * 现金签约
     */
    ADV("现金"),

    /**
     * 转换签约
     */
    CON("转换"),

    /**
     * 续签签约
     */
    REN("续签"),

    /**
     * 临退签约
     */
    QUI("临退"),;

    private String desc;

    ContractType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
