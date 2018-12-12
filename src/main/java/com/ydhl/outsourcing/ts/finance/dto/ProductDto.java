package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.enums.EarningsType;
import com.ydhl.outsourcing.ts.finance.enums.FeeType;
import com.ydhl.outsourcing.ts.finance.enums.SettleType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2018/1/16.
 */
public class ProductDto extends BaseDto<Long>{
    private static final long serialVersionUID = 4150630008358024018L;

    /**
     * 产品编号
     */
    private String number;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 销售总额度
     */
    private BigDecimal salesAmount;

    /**
     * 剩余额度
     */
    private BigDecimal remainingAmount;

    /**
     * 外部起购额度
     */
    private BigDecimal outStartAmount;

    /**
     * 购买次数
     */
    private Integer buyCount;

    /**
     * 介绍
     */
    private String content;

    /**
     * 是否打印凭证
     */
    private Boolean supportVoucher;

    /**
     * 凭证
     */
    private VoucherDto voucherDto;

    /**
     * 是否业务经理审批
     */
    private Boolean supportManagerAudit;

    /**
     * 是否支持内部购买
     */
    private Boolean supportInter;

    /**
     * 是否支持转换
     */
    private Boolean supportConversion=false;

    /**
     * 转换产品
     */
    private ProductDto conversionProductDto;

    /**
     * 是否支持临退
     */
    private Boolean supportQuit = true;

    /**
     * 结算周期
     */
    private SettleType billingCycle;

    /**
     * 产品周期
     */
    private BigDecimal cycle;

    /**
     * 退出手续费类型
     */
    private FeeType quiteFeeType;

    /**
     * 退出手续费计算值
     */
    private String quiteFeeValue;

    /**
     * 起购时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date openTime;

    /**
     * 退出时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date closeTime;

    /**
     * 内部额度
     */
    private BigDecimal interQuota;

    /**
     * 是否支持内部临退
     */
    private Boolean interQuit;

    /**
     * 内部收益比
     */
    private BigDecimal interEarningsRatio;

    /**
     * 内部结算周期
     */
    private SettleType interCycle;

    /**
     * 状态
     */
    private Boolean struts = true;

    /**
     * 小数精确位数
     */
    private Boolean intOrB;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收益比类型
     */
    private EarningsType earningsType;

    /**
     * 外部收益比的值
     */
    private BigDecimal outRateValue;

    /**
     * 阶梯收益列表
     */
    private List<EarningsDto> earningsDtos;

    /**
     * 签约信息列表
     */
    private List<ProductSignInfoDto> productSignInfoDtos;

    /**
     * 内部剩余额度
     */
    private BigDecimal interRemainingAmount=new BigDecimal(0);

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public BigDecimal getOutStartAmount() {
        return outStartAmount;
    }

    public void setOutStartAmount(BigDecimal outStartAmount) {
        this.outStartAmount = outStartAmount;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSupportVoucher() {
        return supportVoucher;
    }

    public void setSupportVoucher(Boolean supportVoucher) {
        this.supportVoucher = supportVoucher;
    }

    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }

    public Boolean getSupportInter() {
        return supportInter;
    }

    public void setSupportInter(Boolean supportInter) {
        this.supportInter = supportInter;
    }

    public Boolean getSupportConversion() {
        return supportConversion;
    }

    public void setSupportConversion(Boolean supportConversion) {
        this.supportConversion = supportConversion;
    }

    public Boolean getSupportQuit() {
        return supportQuit;
    }

    public void setSupportQuit(Boolean supportQuit) {
        this.supportQuit = supportQuit;
    }

    public SettleType getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(SettleType billingCycle) {
        this.billingCycle = billingCycle;
    }

    public BigDecimal getCycle() {
        return cycle;
    }

    public void setCycle(BigDecimal cycle) {
        this.cycle = cycle;
    }

    public FeeType getQuiteFeeType() {
        return quiteFeeType;
    }

    public void setQuiteFeeType(FeeType quiteFeeType) {
        this.quiteFeeType = quiteFeeType;
    }

    public String getQuiteFeeValue() {
        return quiteFeeValue;
    }

    public void setQuiteFeeValue(String quiteFeeValue) {
        this.quiteFeeValue = quiteFeeValue;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public BigDecimal getInterQuota() {
        return interQuota;
    }

    public void setInterQuota(BigDecimal interQuota) {
        if(interQuota==null){
            this.interQuota = new BigDecimal(0.00);
        }else{
            this.interQuota = interQuota;
        }
    }

    public Boolean getInterQuit() {
        return interQuit;
    }

    public void setInterQuit(Boolean interQuit) {
        this.interQuit = interQuit;
    }

    public BigDecimal getInterEarningsRatio() {
        return interEarningsRatio;
    }

    public void setInterEarningsRatio(BigDecimal interEarningsRatio) {
        this.interEarningsRatio = interEarningsRatio;
    }

    public SettleType getInterCycle() {
        return interCycle;
    }

    public void setInterCycle(SettleType interCycle) {
        this.interCycle = interCycle;
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

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public List<EarningsDto> getEarningsDtos() {
        return earningsDtos;
    }

    public void setEarningsDtos(List<EarningsDto> earningsDtos) {
        this.earningsDtos = earningsDtos;
    }

    public VoucherDto getVoucherDto() {
        return voucherDto;
    }

    public void setVoucherDto(VoucherDto voucherDto) {
        this.voucherDto = voucherDto;
    }

    public ProductDto getConversionProductDto() {
        return conversionProductDto;
    }

    public void setConversionProductDto(ProductDto conversionProductDto) {
        this.conversionProductDto = conversionProductDto;
    }

    public EarningsType getEarningsType() {
        return earningsType;
    }

    public void setEarningsType(EarningsType earningsType) {
        this.earningsType = earningsType;
    }

    public BigDecimal getOutRateValue() {
        return outRateValue;
    }

    public void setOutRateValue(BigDecimal outRateValue) {
        this.outRateValue = outRateValue;
    }

    public List<ProductSignInfoDto> getProductSignInfoDtos() {
        return productSignInfoDtos;
    }

    public void setProductSignInfoDtos(List<ProductSignInfoDto> productSignInfoDtos) {
        this.productSignInfoDtos = productSignInfoDtos;
    }

    public BigDecimal getInterRemainingAmount() {
        return interRemainingAmount;
    }

    public void setInterRemainingAmount(BigDecimal interRemainingAmount) {
        if(interRemainingAmount==null){
            this.interRemainingAmount = new BigDecimal(0.00);
        }else {
            this.interRemainingAmount = interRemainingAmount;
        }
    }

    public Boolean getIntOrB() {
        return intOrB;
    }

    public void setIntOrB(Boolean intOrB) {
        this.intOrB = intOrB;
    }
}
