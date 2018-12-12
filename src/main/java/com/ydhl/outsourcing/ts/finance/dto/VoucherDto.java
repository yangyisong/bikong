package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;

import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */
public class VoucherDto extends BaseDto<Long> {
    private static final long serialVersionUID = -4083358532586103102L;
    /**
     * 内容
     */
    private String content;

    /**
     * 名称
     */
    private String name;

    /**
     * 编号
     */
    private String number;

    /**
     * 联数
     */
    private Integer united;

    /**
     * 关联产品集合
     */
    private List<String> productNameList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getUnited() {
        return united;
    }

    public void setUnited(Integer united) {
        this.united = united;
    }

    public List<String> getProductNameList() {
        return productNameList;
    }

    public void setProductNameList(List<String> productNameList) {
        this.productNameList = productNameList;
    }
}
