package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.dto.ContractDto;
import com.ydhl.outsourcing.ts.finance.enums.ContractStruts;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同查询Dto
 * Created by dell on 2018/1/12.
 */
public class ContractQueryDto2 extends ContractDto implements Serializable{

    private static final long serialVersionUID = -8968086084037499095L;

    private Integer flag = 0;

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

    private List<Long> customIds;

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
     * 状态
     */
    private ContractStruts struts = ContractStruts.YJH;

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
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

    public List<Long> getCustomIds() {
        return customIds;
    }

    public void setCustomIds(List<Long> customIds) {
        this.customIds = customIds;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    public ContractStruts getStruts() {
        return struts;
    }

    @Override
    public void setStruts(ContractStruts struts) {
        this.struts = struts;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
