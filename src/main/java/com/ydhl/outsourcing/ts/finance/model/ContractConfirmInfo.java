package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;

/**
 * @author Martins
 * @create 2018/2/6 11:06.
 * @description
 */
@Table(name = "contract_cofirm_info")
public class ContractConfirmInfo extends BaseDomain<Long> {

    private static final long serialVersionUID = 7031736267602569069L;

    /**
     * 角色
     */
    private Long roleId;

    /**
     * 合同
     */
    private Long contractId;

    /**
     * 信息审批
     */
    private Boolean infoConfirm;

    /**
     * 收款确认
     */
    private Boolean receiveConfirm;

    /**
     * 金额确认
     */
    private Boolean amountConfirm;

    /**
     * 是否打印凭证
     */
    private Boolean ifPrint;

    /**
     * 备注
     */
    private String remark;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Boolean getInfoConfirm() {
        return infoConfirm;
    }

    public void setInfoConfirm(Boolean infoConfirm) {
        this.infoConfirm = infoConfirm;
    }

    public Boolean getReceiveConfirm() {
        return receiveConfirm;
    }

    public void setReceiveConfirm(Boolean receiveConfirm) {
        this.receiveConfirm = receiveConfirm;
    }

    public Boolean getAmountConfirm() {
        return amountConfirm;
    }

    public void setAmountConfirm(Boolean amountConfirm) {
        this.amountConfirm = amountConfirm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIfPrint() {
        return ifPrint;
    }

    public void setIfPrint(Boolean ifPrint) {
        this.ifPrint = ifPrint;
    }
}
