package com.ydhl.outsourcing.ts.finance.enums;

/**
 * @author Martins
 * @create 2018/1/12 11:18.
 */
public enum StateChangeType {

    /**
     * 未操作
     */
    ND("未操作"),

    /**
     * 有转换申请
     */
    HCON("有转换"),

    /**
     * 有续签签约申请
     */
    HREN("有续签"),

    /**
     * 有临退签约申请
     */
    HQUI("有临退"),;

    private String desc;

    StateChangeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
