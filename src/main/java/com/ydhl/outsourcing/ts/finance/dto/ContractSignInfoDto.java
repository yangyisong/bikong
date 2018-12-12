package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.enums.ProductSignInfoType;

/**
 * Created by dell on 2018/1/25.
 */
public class ContractSignInfoDto extends BaseDto<Long> {
    private static final long serialVersionUID = 4150630008358024018L;

    /**
     * 产品ID
     */
    private Long contractId;

    /**
     * 产品类型
     */
    private ProductSignInfoType type;

    /**
     * 产品编码
     */
    private String code;

    /**
     * 值
     */
    private String value;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
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
}
