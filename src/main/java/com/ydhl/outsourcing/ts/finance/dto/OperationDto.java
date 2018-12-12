package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;

import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/15 11:17.
 * @description
 */
public class OperationDto extends BaseDto<Long> {

    private static final long serialVersionUID = -2669790882483844198L;

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
