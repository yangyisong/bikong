package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.enums.TimeType;

/**
 * @author Martins
 * @create 2018/1/15 17:21.
 * @description
 */
public class BuyDto extends BaseDto<Long> {

    private static final long serialVersionUID = 536791720616053494L;

    /**
     * 产品
     */
    private Long productId;

    /**
     * 客户
     */
    private Long customerId;

    /**
     * 时间段
     */
    private TimeType timePeriod;

    /**
     * 产品名
     */
    private String productName;

    /**
     * 时间值
     */
    private String timeValue;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }
}
