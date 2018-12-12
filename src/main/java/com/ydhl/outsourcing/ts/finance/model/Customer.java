package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.CustomSource;
import com.ydhl.outsourcing.ts.finance.enums.CustomType;
import com.ydhl.outsourcing.ts.finance.enums.Gender;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/11 22:05.
 * @description 客户表
 */
@Table(name = "customer")
public class Customer extends BaseDomain<Long> {

    private static final long serialVersionUID = 2233085658575406578L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 座机
     */
    private String tel;

    /**
     * 手机
     */
    private String phone;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 邮箱
     */
    private String email;

    /**
     * qq
     */
    private String qq;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 来源
     */
    private CustomSource source;

    /**
     * 交易次数
     */
    private Integer buyTimes;

    /**
     * 业务员id
     */
    private Long userId;

    /**
     * 紧急联系人
     */
    private String crashName;

    /**
     * 紧急联系人电话
     */
    private String crashPhone;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 是否员工
     */
    private CustomType type;

    /**
     * 录入人员
     */
    private String entryUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public CustomSource getSource() {
        return source;
    }

    public void setSource(CustomSource source) {
        this.source = source;
    }

    public Integer getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(Integer buyTimes) {
        this.buyTimes = buyTimes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCrashName() {
        return crashName;
    }

    public void setCrashName(String crashName) {
        this.crashName = crashName;
    }

    public String getCrashPhone() {
        return crashPhone;
    }

    public void setCrashPhone(String crashPhone) {
        this.crashPhone = crashPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomType getType() {
        return type;
    }

    public void setType(CustomType type) {
        this.type = type;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }
}
