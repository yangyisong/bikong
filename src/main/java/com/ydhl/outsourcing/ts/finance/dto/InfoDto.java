package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;

/**
 * Created by dell on 2018/3/6.
 */
public class InfoDto extends BaseDto<Long> {
    /**
     * 签约审批中
     */
    private Integer sApprovIng;

    /**
     * 临退
     */
    private Integer qApprovIng;

    /**
     * 转换审批中
     */
    private Integer cApprovIng;

    /**
     * 续签审批总
     */
    private Integer rApprovIng;

    /**
     * 结算审批中
     */
    private Integer jApprovIng;

    /**
     * 签约被驳回
     */
    private Integer sApprovAre;

    /**
     * 结算被驳回
     */
    private Integer jApprovAre;

    /**
     * 签约待审批
     */
    private Integer sApprovW;

    /**
     * 临退待审批
     */
    private Integer qApprovW;

    /**
     * 转换待审批
     */
    private Integer cApprovW;

    /**
     * 续签待审批
     */
    private Integer rApprovW;

    /**
     * 收益待付款
     */
    private Integer iPayW;

    /**
     * 结算待审批
     */
    private Integer jApprovW;

    public Integer getsApprovIng() {
        return sApprovIng;
    }

    public void setsApprovIng(Integer sApprovIng) {
        this.sApprovIng = sApprovIng;
    }

    public Integer getqApprovIng() {
        return qApprovIng;
    }

    public void setqApprovIng(Integer qApprovIng) {
        this.qApprovIng = qApprovIng;
    }

    public Integer getcApprovIng() {
        return cApprovIng;
    }

    public void setcApprovIng(Integer cApprovIng) {
        this.cApprovIng = cApprovIng;
    }

    public Integer getrApprovIng() {
        return rApprovIng;
    }

    public void setrApprovIng(Integer rApprovIng) {
        this.rApprovIng = rApprovIng;
    }

    public Integer getjApprovIng() {
        return jApprovIng;
    }

    public void setjApprovIng(Integer jApprovIng) {
        this.jApprovIng = jApprovIng;
    }

    public Integer getsApprovAre() {
        return sApprovAre;
    }

    public void setsApprovAre(Integer sApprovAre) {
        this.sApprovAre = sApprovAre;
    }

    public Integer getjApprovAre() {
        return jApprovAre;
    }

    public void setjApprovAre(Integer jApprovAre) {
        this.jApprovAre = jApprovAre;
    }

    public Integer getsApprovW() {
        return sApprovW;
    }

    public void setsApprovW(Integer sApprovW) {
        this.sApprovW = sApprovW;
    }

    public Integer getqApprovW() {
        return qApprovW;
    }

    public void setqApprovW(Integer qApprovW) {
        this.qApprovW = qApprovW;
    }

    public Integer getcApprovW() {
        return cApprovW;
    }

    public void setcApprovW(Integer cApprovW) {
        this.cApprovW = cApprovW;
    }

    public Integer getrApprovW() {
        return rApprovW;
    }

    public void setrApprovW(Integer rApprovW) {
        this.rApprovW = rApprovW;
    }

    public Integer getiPayW() {
        return iPayW;
    }

    public void setiPayW(Integer iPayW) {
        this.iPayW = iPayW;
    }

    public Integer getjApprovW() {
        return jApprovW;
    }

    public void setjApprovW(Integer jApprovW) {
        this.jApprovW = jApprovW;
    }
}
