package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.common.utils.PatternUtils;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.enums.IncomeType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/16 14:31.
 * @description
 */
public class PersonalIncomeDto extends BaseDto<Long> {

    private static final long serialVersionUID = 7270792653978970989L;

    /**
     * 编号
     */
    private String incomeNumber;

    /**
     * 客户
     */
    private String customerName;

    /**
     * 产品
     */
    private String productName;

    /**
     * 业务员
     */
    private String userRealname;

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
     * 收益
     */
    private BigDecimal income;

    /**
     * 计息开始时间
     */
    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
    private Date settleStartTime;

    /**
     * 计息结束时间
     */
    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
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
     * 排序
     */
    private Integer seq;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 状态值
     */
    private String strutsValue;

    /**
     * 利息合计
     */
    private BigDecimal incomeTotal;

    /**
     * 未付合计
     */
    private BigDecimal unpaidTotal;

    /**
     * 已付合计
     */
    private BigDecimal prepaidTotal;

    /**
     * 是否为本金
     */
    private Boolean principal;

    /**
     * 结算时间段
     */
    private String settleTime;

    /**
     * 结息类型值
     */
    private String incomeTypeValue;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 是否为临退
     */
    private Boolean quit;

    /**
     * 到期时间
     */
    private String endTime;

    private Long approvedId;

    private Boolean approvedStruts;

    /**
     * 手续费
     */
    private BigDecimal sxf;

    private Boolean freeze;

    public String getIncomeNumber() {
        return incomeNumber;
    }

    public void setIncomeNumber(String incomeNumber) {
        this.incomeNumber = incomeNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStrutsValue() {
        return strutsValue;
    }

    public void setStrutsValue(String strutsValue) {
        this.strutsValue = strutsValue;
    }

    public BigDecimal getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(BigDecimal incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public BigDecimal getUnpaidTotal() {
        return unpaidTotal;
    }

    public void setUnpaidTotal(BigDecimal unpaidTotal) {
        this.unpaidTotal = unpaidTotal;
    }

    public BigDecimal getPrepaidTotal() {
        return prepaidTotal;
    }

    public void setPrepaidTotal(BigDecimal prepaidTotal) {
        this.prepaidTotal = prepaidTotal;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getIncomeTypeValue() {
        return incomeTypeValue;
    }

    public void setIncomeTypeValue(String incomeTypeValue) {
        this.incomeTypeValue = incomeTypeValue;
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

    public Boolean getApprovedStruts() {
        return approvedStruts;
    }

    public void setApprovedStruts(Boolean approvedStruts) {
        this.approvedStruts = approvedStruts;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getSxf() {
        return sxf;
    }

    public void setSxf(BigDecimal sxf) {
        this.sxf = sxf;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }
}
