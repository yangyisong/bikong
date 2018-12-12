package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2018/1/29.
 */
public class ContractApplyDto   extends BaseDto<Long> {
    private static final long serialVersionUID = 3636016923227049293L;

    /**
     * 申请人
     */
    private UserDto userDto;

    /**
     * 合同Id
     */
    private ContractDto contractDto;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 申请状态 审批中,已通过,未通过
     */
    private AuditStruts auditStruts;

    /**
     * 审批状态 -1:已拒绝,0:待业务经理审批,1:业务经理审批通过,2:出纳审批通过,3:财务审批通过
     */
    private Integer approvedStruts;

    /**
     * 申请状态
     */
    private ApplicationStruts applyStruts;

    /**
     * 类型
     */
    private ContractType type;

    /**
     * 第几次
     */
    private Integer num;

    /**
     * 是否需要业务经理审批
     */
    private Boolean supportManagerAudit;

    List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos;

    public ContractDto getContractDto() {
        return contractDto;
    }

    public void setContractDto(ContractDto contractDto) {
        this.contractDto = contractDto;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ApplicationStruts getApplyStruts() {
        return applyStruts;
    }

    public void setApplyStruts(ApplicationStruts applyStruts) {
        this.applyStruts = applyStruts;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public List<ContractApplyAuditRecordingDto> getContractApplyAuditRecordingDtos() {
        return contractApplyAuditRecordingDtos;
    }

    public void setContractApplyAuditRecordingDtos(List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos) {
        this.contractApplyAuditRecordingDtos = contractApplyAuditRecordingDtos;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }
}
