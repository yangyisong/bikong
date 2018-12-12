package com.ydhl.outsourcing.ts.finance.model;


import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;

/**
 * @author Junpeng.Su
 * @create 2017-08-17 下午 1:50
 * @description 角色/职位表
 */
@Table(name = "role")
public class Role extends BaseDomain<Long> {

    private static final long serialVersionUID = -8627125548084559585L;

    /**
     * 角色名
     */
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
