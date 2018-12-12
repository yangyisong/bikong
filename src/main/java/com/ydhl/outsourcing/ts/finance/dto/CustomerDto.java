package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.common.utils.PatternUtils;
import com.ydhl.outsourcing.ts.finance.enums.CustomSource;
import com.ydhl.outsourcing.ts.finance.enums.CustomType;
import com.ydhl.outsourcing.ts.finance.enums.Gender;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 客户Dto
 * Created by dell on 2018/1/12.
 */
public class CustomerDto extends BaseDto<Long> {

    private static final long serialVersionUID = -5911530046878284279L;

    public interface Add{};

    public interface Edit{};

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 职业值
     */
    private String occupationValue;

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
     * 客户角色
     */
    private CustomType type;

    /**
     * 来源值
     */
    private String sourceValue;

    /**
     * 录入人员
     */
    private String entryUser;

    /**
     * 类型值
     */
    private String typeValue;

    /**
     * 合同数
     */
    private Integer contractCount;

    /**
     * 性别值
     */
    private String genderValue;

    /**
     * 业务员姓名
     */
    private String salesmanName;

    @NotBlank(message = "客户姓名不为空", groups = {Add.class, Edit.class})
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "性别不为空", groups = {Add.class, Edit.class})
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

    @NotBlank(message = "手机号不为空", groups = {Add.class, Edit.class})
    @Pattern(regexp = PatternUtils.FORMAT_PHONE, message = "不是正确的手机号", groups = {Add.class, Edit.class})
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

    @NotBlank(message = "身份证号码不为空", groups = {Add.class, Edit.class})
//    @Pattern(regexp = PatternUtils.FORMAT_IDCARD, message = "不是正确的身份证号码格式", groups = {Add.class, Edit.class})
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

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getOccupationValue() {
        return occupationValue;
    }

    public void setOccupationValue(String occupationValue) {
        this.occupationValue = occupationValue;
    }
}
