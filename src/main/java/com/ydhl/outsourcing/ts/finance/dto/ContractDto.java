package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.common.utils.PatternUtils;
import com.ydhl.outsourcing.ts.finance.enums.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2018/1/22.
 */
public class ContractDto  extends BaseDto<Long> {
    private static final long serialVersionUID = 3636016923227049293L;

    /**
     * 原始产品
     */
    private Long oldProductId;

    /**
     * 产品
     */
    private Long productId;

    /**
     * 客户
     */
    private String customerName;

    /**
     * 业务员
     */
    private UserDto user;

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
     * 转换产品
     */
    private ProductDto changeProduct;

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
     * 是否支持临退
     */
    private Boolean supportQuit;

    /**
     * 客户角色
     */
    private CustomType customType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 合同签约信息列表
     * @return
     */
    private List<ContractSignInfoDto> contractSignInfoDtos;

    /**
     * 是否有效
     */
    private Boolean validity;

    /**
     * 临退时间
     */
    private Date quitDate;

    /**
     * 临退结息
     */
    private BigDecimal quitInterest;

    /**
     * 临退手续费
     */
    private BigDecimal quitPoundage;

    /**
     * 应退总额
     */
    private BigDecimal quitAmount;

    /**
     * 结算周期
     */
    private SettleType cycle;

    /**
     * 审批状态值 用于确定页面权限控制
     */
    private Integer approvedStruts;

    /**
     * 是否需要业务经理审批
     */
    private Boolean supportManagerAudit;

    private Long applyId;

    private Boolean intOrB;

    private Boolean changeAble;

    private StateChangeType stateChangeType;

    private Long sourceId;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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

    public ProductDto getChangeProduct() {
        return changeProduct;
    }

    public void setChangeProduct(ProductDto changeProduct) {
        this.changeProduct = changeProduct;
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

    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
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

    public Boolean getSupportQuit() {
        return supportQuit;
    }

    public void setSupportQuit(Boolean supportQuit) {
        this.supportQuit = supportQuit;
    }

    public CustomType getCustomType() {
        return customType;
    }

    public void setCustomType(CustomType customType) {
        this.customType = customType;
    }

    public Long getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Long oldProductId) {
        this.oldProductId = oldProductId;
    }

    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    public List<ContractSignInfoDto> getContractSignInfoDtos() {
        return contractSignInfoDtos;
    }

    public void setContractSignInfoDtos(List<ContractSignInfoDto> contractSignInfoDtos) {
        this.contractSignInfoDtos = contractSignInfoDtos;
    }

    public Boolean getTemporary() {
        return temporary;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    @DateTimeFormat(pattern = PatternUtils.FORMAT_DATE_TIME)
    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public BigDecimal getQuitInterest() {
        return quitInterest;
    }

    public void setQuitInterest(BigDecimal quitInterest) {
        this.quitInterest = quitInterest;
    }

    public BigDecimal getQuitPoundage() {
        return quitPoundage;
    }

    public void setQuitPoundage(BigDecimal quitPoundage) {
        this.quitPoundage = quitPoundage;
    }

    public BigDecimal getQuitAmount() {
        return quitAmount;
    }

    public void setQuitAmount(BigDecimal quitAmount) {
        this.quitAmount = quitAmount;
    }

    public SettleType getCycle() {
        return cycle;
    }

    public void setCycle(SettleType cycle) {
        this.cycle = cycle;
    }

    public Integer getApprovedStruts() {
        return approvedStruts;
    }

    public void setApprovedStruts(Integer approvedStruts) {
        this.approvedStruts = approvedStruts;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Boolean getIntOrB() {
        return intOrB;
    }

    public void setIntOrB(Boolean intOrB) {
        this.intOrB = intOrB;
    }

    public Boolean getChangeAble() {
        return changeAble;
    }

    public void setChangeAble(Boolean changeAble) {
        this.changeAble = changeAble;
    }

    public StateChangeType getStateChangeType() {
        return stateChangeType;
    }

    public void setStateChangeType(StateChangeType stateChangeType) {
        this.stateChangeType = stateChangeType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
