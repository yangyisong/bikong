package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.dto.CustomerDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户查询Dto
 * Created by dell on 2018/1/12.
 */
public class CustomerQueryDto extends CustomerDto implements Serializable{

    private static final long serialVersionUID = -8968086084037499095L;

    /**
     * 标示
     */
    private Integer searchTab=0;

    /**
     * 内容
     */
    private String content;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 开始时间
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 结束时间
     * @return
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    /**
     * 业务人员
     */
    private String businessUser;

    /**
     * 业务人员Id
     * @return
     */
    private List<Long> businessIds;

    private Long userId;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Integer getSearchTab() {
        return searchTab;
    }

    public void setSearchTab(Integer searchTab) {
        this.searchTab = searchTab;
        if(searchTab==0){
            setName(getContent());
        }else if(searchTab==1){
            setPhone(getContent());
        }else if(searchTab==2){
            setIdcard(getContent());
        }else{
            setBusinessUser(getContent());
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getIdcard() {
        return idcard;
    }

    @Override
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBusinessUser() {
        return businessUser;
    }

    public void setBusinessUser(String businessUser) {
        this.businessUser = businessUser;
    }

    public List<Long> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(List<Long> businessIds) {
        this.businessIds = businessIds;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
