package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dell on 2018/1/30.
 */
@Table(name="contract_apply_audit_recording")
public class ContractApplyAuditRecording  extends BaseDomain<Long> {
    private Long applyId;

    private Long userId;

    private Boolean struts;

    private String remark;

    private Date auditTime;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getStruts() {
        return struts;
    }

    public void setStruts(Boolean struts) {
        this.struts = struts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}
