package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.enums.ProductSignInfoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/25.
 */
public class ProductSignInfoDto  extends BaseDto<Long> {
    private static final long serialVersionUID = 4150630008358024018L;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品类型
     */
    private ProductSignInfoType type;

    /**
     * 产品编码
     */
    private String code;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 值
     */
    private String value;

    /**
     * 值的列表
     */
    private List<Map<String,String>> valueList;

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
        setValueList();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<Map<String,String>> getValueList() {
        return valueList;
    }

    public void setValueList() {
        if(getType().equals(ProductSignInfoType.RADIO)){
            String v = getValue();
            String dv = getDefaultValue();
            List<Map<String,String>> list = new ArrayList<>();
            String[] vs = v.split(",");
            String[] dvs = dv.split(",");
            if(StringUtilPlus.isNoneEmpty(v)){
                for(int i=0;i<vs.length;i++){
                    Map<String,String> map = new HashMap<>();
                    map.put("value",vs[i]);
                    map.put("defaultValue",dvs[i]);
                    list.add(map);
                }
            }
            this.valueList = list;
        }
    }

}
