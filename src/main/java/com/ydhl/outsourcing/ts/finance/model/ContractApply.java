package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dell on 2018/1/29.
 */
@Table(name = "contract_apply")
public class ContractApply  extends BaseDomain<Long> {
    private static final long serialVersionUID = 3636016923227049293L;

    /**
     * 申请人
     */
    private Long userId;

    /**
     * 合同Id
     */
    private Long contractId;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 审批状态 审批中,已通过,未通过
     */
    private AuditStruts auditStruts;

    /**
     * 审批状态 -1:已拒绝,0:待业务经理审批,1:业务经理审批通过,2:出纳审批通过,3:财务审批通过
     */
    private Integer approvedStruts;

    /**
     * 类型
     */
    private ContractType type;

    /**
     * 是否有效
     */
    private Boolean effective;

    /**
     * 是否已操作
     */
    private Boolean operation;

    /**
     * 修改次数
     */
    private Integer num;

    /**
     * 申请状态
     */
    private ApplicationStruts applyStruts;

    /**
     * 是否需要业务经理审批
     */
    private Boolean supportManagerAudit;

    /**
     * 来源id
     */
    private Long sourceId;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public AuditStruts getAuditStruts() {
        return auditStruts;
    }

    public void setAuditStruts(AuditStruts auditStruts) {
        this.auditStruts = auditStruts;
    }

    public Integer getApprovedStruts() {
        return approvedStruts;
    }

    public void setApprovedStruts(Integer approvedStruts) {
        this.approvedStruts = approvedStruts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public ApplicationStruts getApplyStruts() {
        return applyStruts;
    }

    public void setApplyStruts(ApplicationStruts applyStruts) {
        this.applyStruts = applyStruts;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }

    public Boolean getOperation() {
        return operation;
    }

    public void setOperation(Boolean operation) {
        this.operation = operation;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
