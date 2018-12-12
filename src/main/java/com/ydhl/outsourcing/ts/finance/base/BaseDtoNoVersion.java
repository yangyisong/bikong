package com.ydhl.outsourcing.ts.finance.base;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Junpeng.Su
 * @create 2017-08-15 下午 3:04
 * @description
 */
public class BaseDtoNoVersion<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = -3761675344684204302L;

    private PK id;

    private Date createTime;

    private Date updateTime;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
