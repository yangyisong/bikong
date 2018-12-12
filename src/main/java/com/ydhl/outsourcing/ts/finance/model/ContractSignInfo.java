package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.ProductSignInfoType;

import javax.persistence.Table;

/**
 * Created by dell on 2018/1/28.
 */
@Table(name = "contract_sign_info")
public class ContractSignInfo extends BaseDomain<Long> {
    private static final long serialVersionUID = 4150630008358024018L;

    private Long contractId;

    private ProductSignInfoType type;

    private String code;

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
