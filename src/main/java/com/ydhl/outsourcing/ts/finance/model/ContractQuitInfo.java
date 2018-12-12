package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.base.BaseDomainNoVersion;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/30 16:18.
 * @description
 */
@Table(name = "contract_quit_info")
public class ContractQuitInfo extends BaseDomainNoVersion<Long> {

    private static final long serialVersionUID = 259964959819186713L;

    /**
     * 合同
     */
    private Long applyId;

    /**
     * 临退时间
     */
    private Date quitDate;

    /**
     * 临退结息
     */
    private BigDecimal quitInterest;

    /**
     * 临退手续费
     */
    private BigDecimal quitPoundage;

    /**
     * 应退总额
     */
    private BigDecimal quitAmount;

    /**
     * 本金
     */
    private BigDecimal principalAmount;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public BigDecimal getQuitInterest() {
        return quitInterest;
    }

    public void setQuitInterest(BigDecimal quitInterest) {
        this.quitInterest = quitInterest;
    }

    public BigDecimal getQuitPoundage() {
        return quitPoundage;
    }

    public void setQuitPoundage(BigDecimal quitPoundage) {
        this.quitPoundage = quitPoundage;
    }

    public BigDecimal getQuitAmount() {
        return quitAmount;
    }

    public void setQuitAmount(BigDecimal quitAmount) {
        this.quitAmount = quitAmount;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }
}
