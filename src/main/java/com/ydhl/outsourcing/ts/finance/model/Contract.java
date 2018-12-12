package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.ContractStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import com.ydhl.outsourcing.ts.finance.enums.SettleType;
import com.ydhl.outsourcing.ts.finance.enums.StateChangeType;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/12 11:04.
 * @description 合同表
 */
@Table(name = "contract")
public class Contract extends BaseDomain<Long> {

    private static final long serialVersionUID = 3636016923227049293L;

    /**
     * 产品
     */
    private Long productId;

    /**
     * 客户
     */
    private Long customerId;

    /**
     * 业务员
     */
    private Long userId;

    /**
     * 编号
     */
    private String number;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 是否转换
     */
    private Boolean changes;

    /**
     * 转换合同
     */
    private Long changeProductId;

    /**
     * 收益比
     */
    private BigDecimal earningRatio;

    /**
     * 每天收益
     */
    private BigDecimal earningsDayAmount;

    /**
     * 生效时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 开户银行
     */
    private String openBank;

    /**
     * 户名
     */
    private String openName;

    /**
     * 账号
     */
    private String bankAccount;

    /**
     * 来源
     */
    private String source;

    /**
     * 合同时长
     */
    private Integer duration;

    /**
     * 类型
     */
    private ContractType type;

    /**
     * 状态
     */
    private ContractStruts struts;

    /**
     * 是否已关闭(临结)
     */
    private Boolean temporary;


    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效
     */
    private Boolean validity;

    /**
     * 结算周期
     */
    private SettleType cycle;

    /**
     * 转换合同id
     */
    private Long changeContractId;

    /**
     * 是否需要业务经理审批
     */
    private Boolean supportManagerAudit;

    /**
     * 来源合同id
     */
    private Long sourceId;

    private StateChangeType stateChangeType;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getChanges() {
        return changes;
    }

    public void setChanges(Boolean changes) {
        this.changes = changes;
    }

    public Long getChangeProductId() {
        return changeProductId;
    }

    public void setChangeProductId(Long changeProductId) {
        this.changeProductId = changeProductId;
    }

    public BigDecimal getEarningRatio() {
        return earningRatio;
    }

    public void setEarningRatio(BigDecimal earningRatio) {
        this.earningRatio = earningRatio;
    }

    public BigDecimal getEarningsDayAmount() {
        return earningsDayAmount;
    }

    public void setEarningsDayAmount(BigDecimal earningsDayAmount) {
        this.earningsDayAmount = earningsDayAmount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public ContractStruts getStruts() {
        return struts;
    }

    public void setStruts(ContractStruts struts) {
        this.struts = struts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    public Boolean getTemporary() {
        return temporary;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    public SettleType getCycle() {
        return cycle;
    }

    public void setCycle(SettleType cycle) {
        this.cycle = cycle;
    }

    public Long getChangeContractId() {
        return changeContractId;
    }

    public void setChangeContractId(Long changeContractId) {
        this.changeContractId = changeContractId;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public StateChangeType getStateChangeType() {
        return stateChangeType;
    }

    public void setStateChangeType(StateChangeType stateChangeType) {
        this.stateChangeType = stateChangeType;
    }
}
