package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.TimeType;

import javax.persistence.Table;

/**
 * @author Martins
 * @create 2018/1/12 10:59.
 * @description 客户购买信息中间表
 */
@Table(name = "buy")
public class Buy extends BaseDomain<Long> {

    private static final long serialVersionUID = 8594570087196783368L;

    /**
     * 产品
     */
    private Long productId;

    /**
     * 客户
     */
    private Long customerId;

    /**
     * 时间类型
     */
    private TimeType timePeriod;

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

    public TimeType getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimeType timePeriod) {
        this.timePeriod = timePeriod;
    }
}
