package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;

import javax.persistence.Table;

/**
 * Created by dell on 2018/2/5.
 */
@Table(name = "approved_apply")
public class ApprovedApply  extends BaseDomain<Long> {
    private static final long serialVersionUID = 7120396888109052919L;

    private Long approvedId;

    private Long userId;

    private String reason;
    /**
     * 审批状态
     */
    private Integer approvedStruts;

    /**
     * 状态
     */
    private ApplicationStruts struts;

    private Boolean supportManagerAudit;

    /**
     * 结算编号
     */
    private String settleNumber;

    /**
     * 笔数
     */
    private Integer penNumber;

    public Long getApprovedId() {
        return approvedId;
    }

    public void setApprovedId(Long approvedId) {
        this.approvedId = approvedId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getApprovedStruts() {
        return approvedStruts;
    }

    public void setApprovedStruts(Integer approvedStruts) {
        this.approvedStruts = approvedStruts;
    }

    public ApplicationStruts getStruts() {
        return struts;
    }

    public void setStruts(ApplicationStruts struts) {
        this.struts = struts;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSettleNumber() {
        return settleNumber;
    }

    public void setSettleNumber(String settleNumber) {
        this.settleNumber = settleNumber;
    }

    public Integer getPenNumber() {
        return penNumber;
    }

    public void setPenNumber(Integer penNumber) {
        this.penNumber = penNumber;
    }
}
