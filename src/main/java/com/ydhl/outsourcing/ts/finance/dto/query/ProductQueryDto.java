package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.dto.ProductDto;

/**
 * 产品查询对象
 */
public class ProductQueryDto extends ProductDto {

    private static final long serialVersionUID = -8968086084037499095L;

    /**
     * 状态
     */
    private Boolean struts;

    public Boolean getStruts() {
        return struts;
    }

    public void setStruts(Boolean struts) {
        this.struts = struts;
    }

}
