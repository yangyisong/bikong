package com.ydhl.outsourcing.ts.finance.model;


import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;

/**
 * @author Junpeng.Su
 * @create 2017-08-17 下午 1:51
 * @description 权限表
 */
@Table(name = "privilege")
public class Privilege extends BaseDomain<Long> {

    private static final long serialVersionUID = -8627125548084559585L;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
