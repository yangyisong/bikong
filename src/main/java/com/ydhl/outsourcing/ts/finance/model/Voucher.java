package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;

/**
 * 凭证
 * Created by dell on 2018/1/15.
 */
@Table(name = "voucher")
public class Voucher extends BaseDomain<Long> {
    private static final long serialVersionUID = 605951941121048495L;
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
}
