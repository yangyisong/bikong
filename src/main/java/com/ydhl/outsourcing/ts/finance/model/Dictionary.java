package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;

/**
 * @author Martins
 * @create 2018/1/11 22:03.
 * @description 字典表
 */
@Table(name = "dictionary")
public class Dictionary extends BaseDomain<Long> {

    private static final long serialVersionUID = -553033659782553479L;

    /**
     * 类型
     */
    private String type;

    /**
     * 编码
     */
    private String code;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer seq;

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
