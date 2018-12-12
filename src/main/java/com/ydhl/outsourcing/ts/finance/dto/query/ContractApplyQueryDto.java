package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.dto.ContractApplyDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 申请查询Dto
 * Created by dell on 2018/1/12.
 */
public class ContractApplyQueryDto extends ContractApplyDto implements Serializable{

    private static final long serialVersionUID = -8968086084037499095L;

    /**
     * 审批状态
     */
    private AuditStruts auditStruts;

    /**
     * 申请状态
     */
    private ApplicationStruts applyStruts;

    /**
     * 合同编号
     * @return
     */
    private String number;

    /**
     * 客户姓名
     * @return
     */
    private String customName;

    /**
     * 员工姓名
     * @return
     */
    private String userName;
    private List<Long> userIds;

    /**
     * 产品名称
     * @return
     */
    private String productName;

    /**
     * 产品生效日期(min)
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minStartTime;

    /**
     * 产品生效日期(max)
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxStartTime;

    /**
     * 产品结束日期(min)
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minEndTime;

    /**
     * 产品结束日期(max)
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxEndTime;

    /**
     * 标志 0:全部 1:我的申请 2:我的审批 3:我的签约
     * @return
     */
    private Integer flag=0;

    private List<Long> applyIds;

    /**
     * 最大审批状态
     * @return
     */
    private Integer approvedStruts;

    /**
     * 是否有效
     */
    private Boolean effective;

    /**
     * 是否需要业务经理审批
     */
    private Boolean supportManagerAudit;

    public List<Long> getApplyIds() {
        return applyIds;
    }

    public void setApplyIds(List<Long> applyIds) {
        this.applyIds = applyIds;
    }

    @Override
    public Integer getApprovedStruts() {
        return approvedStruts;
    }

    @Override
    public void setApprovedStruts(Integer approvedStruts) {
        this.approvedStruts = approvedStruts;
    }

    @Override
    public AuditStruts getAuditStruts() {
        return auditStruts;
    }

    @Override
    public void setAuditStruts(AuditStruts auditStruts) {
        this.auditStruts = auditStruts;
    }

    @Override
    public ApplicationStruts getApplyStruts() {
        return applyStruts;
    }

    @Override
    public void setApplyStruts(ApplicationStruts applyStruts) {
        this.applyStruts = applyStruts;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getMinStartTime() {
        return minStartTime;
    }

    public void setMinStartTime(Date minStartTime) {
        this.minStartTime = minStartTime;
    }

    public Date getMaxStartTime() {
        return maxStartTime;
    }

    public void setMaxStartTime(Date maxStartTime) {
        this.maxStartTime = maxStartTime;
    }

    public Date getMinEndTime() {
        return minEndTime;
    }

    public void setMinEndTime(Date minEndTime) {
        this.minEndTime = minEndTime;
    }

    public Date getMaxEndTime() {
        return maxEndTime;
    }

    public void setMaxEndTime(Date maxEndTime) {
        this.maxEndTime = maxEndTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
    }

    @Override
    public Boolean getSupportManagerAudit() {
        return supportManagerAudit;
    }

    @Override
    public void setSupportManagerAudit(Boolean supportManagerAudit) {
        this.supportManagerAudit = supportManagerAudit;
    }
}
