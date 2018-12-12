package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.ProductSignInfoType;

import javax.persistence.Table;

/**
 * Created by dell on 2018/1/25.
 */
@Table(name = "product_sign_info")
public class ProductSignInfo  extends BaseDomain<Long> {
    private static final long serialVersionUID = 4150630008358021111L;

    private Long productId;

    private ProductSignInfoType type;

    private String code;

    private String defaultValue;

    private String value;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductSignInfoType getType() {
        return type;
    }

    public void setType(ProductSignInfoType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
