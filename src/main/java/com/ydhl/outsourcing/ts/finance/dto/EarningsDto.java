package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.enums.EarningsType;

import java.math.BigDecimal;

/**
 * Created by dell on 2018/1/16.
 */
public class EarningsDto extends BaseDto<Long> {
    private static final long serialVersionUID = 3908301218467338697L;

    /**
     * 产品
     */
    private Long productId;

    /**
     * 收益比类型
     */
    private EarningsType earningsType;

    /**
     * 其实额度
     */
    private BigDecimal minRange;

    /**
     * 上限额度
     */
    private BigDecimal maxRange;

    /**
     * 收益比
     */
    private BigDecimal outEarningsRatio;

    public EarningsType getEarningsType() {
        return earningsType;
    }

    public void setEarningsType(EarningsType earningsType) {
        this.earningsType = earningsType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getMinRange() {
        return minRange;
    }

    public void setMinRange(BigDecimal minRange) {
        this.minRange = minRange;
    }

    public BigDecimal getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(BigDecimal maxRange) {
        this.maxRange = maxRange;
    }

    public BigDecimal getOutEarningsRatio() {
        return outEarningsRatio;
    }

    public void setOutEarningsRatio(BigDecimal earningsRatio) {
        this.outEarningsRatio = earningsRatio;
    }
}
