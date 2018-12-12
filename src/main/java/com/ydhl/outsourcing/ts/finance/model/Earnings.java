package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.EarningsType;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Martins
 * @create 2018/1/12 11:25.
 * @description 阶梯收益表
 */
@Table(name = "earnings")
public class Earnings extends BaseDomain<Long> {

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
