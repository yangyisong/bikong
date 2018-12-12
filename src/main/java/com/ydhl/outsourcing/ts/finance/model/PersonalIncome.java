package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.enums.IncomeType;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/16 14:24.
 * @description
 */
@Table(name = "personal_income")
public class PersonalIncome extends BaseDomain<Long> {

    private static final long serialVersionUID = -3343875040606926366L;

    /**
     * 编号
     */
    private String incomeNumber;

    /**
     * 收益
     */
    private BigDecimal income;

    /**
     * 到期时间
     */
    private String endTime;

    /**
     * 计息开始时间
     */
    private Date settleStartTime;

    /**
     * 计息结束时间
     */
    private Date settleEndTime;

    /**
     * 操作人
     */
    private Long userId;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 计息天数
     */
    private Integer dayNums;

    /**
     * 结息类型
     */
    private IncomeType incomeType;

    /**
     * 状态
     */
    private ApprovalType struts;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 客户名
     */
    private String customerName;

    /**
     * 业务员
     */
    private String userRealname;

    /**
     * 是否为本金
     */
    private Boolean principal;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 是否为临退
     */
    private Boolean quit;

    private Long applyId;

    private Long approvedId;

    /**
     * 是否被冻结
     */
    private Boolean freeze;

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Date getSettleStartTime() {
        return settleStartTime;
    }

    public void setSettleStartTime(Date settleStartTime) {
        this.settleStartTime = settleStartTime;
    }

    public Date getSettleEndTime() {
        return settleEndTime;
    }

    public void setSettleEndTime(Date settleEndTime) {
        this.settleEndTime = settleEndTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getIncomeNumber() {
        return incomeNumber;
    }

    public void setIncomeNumber(String incomeNumber) {
        this.incomeNumber = incomeNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getDayNums() {
        return dayNums;
    }

    public void setDayNums(Integer dayNums) {
        this.dayNums = dayNums;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public ApprovalType getStruts() {
        return struts;
    }

    public void setStruts(ApprovalType struts) {
        this.struts = struts;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Boolean getQuit() {
        return quit;
    }

    public void setQuit(Boolean quit) {
        this.quit = quit;
    }

    public Long getApprovedId() {
        return approvedId;
    }

    public void setApprovedId(Long approvedId) {
        this.approvedId = approvedId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    @Override
    public String toString() {
        return "PersonalIncome{" +
                "incomeNumber='" + incomeNumber + '\'' +
                ", income=" + income +
                ", endTime='" + endTime + '\'' +
                ", settleStartTime=" + settleStartTime +
                ", settleEndTime=" + settleEndTime +
                ", userId=" + userId +
                ", contractNumber='" + contractNumber + '\'' +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", dayNums=" + dayNums +
                ", incomeType=" + incomeType +
                ", struts=" + struts +
                ", seq=" + seq +
                ", customerName='" + customerName + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", principal=" + principal +
                '}';
    }
}
