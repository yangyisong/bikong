package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomainNoVersion;

import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/15 11:10.
 * @description 操作日志
 */
public class Operation extends BaseDomainNoVersion<Long> {

    private static final long serialVersionUID = 2950184650952440293L;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 客户端ip
     */
    private String operateIp;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
