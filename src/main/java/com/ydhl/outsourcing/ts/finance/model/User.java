package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseUser;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author Martins
 * @create 2018/1/12 10:32.
 * @description 用户/员工表
 */
@Table(name = "user")
public class User extends BaseUser {

    private static final long serialVersionUID = 605951941121048495L;

    /**
     * 姓名
     */
    private String realname;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 部门
     */
    private Long departmentId;

    /**
     * 手机
     */
    private String phone;

    /**
     * 最后登录时间
     */
    private Date lastLogin;

    /**
     * 是否为系统管理员
     */
    private Boolean systemer;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getSystemer() {
        return systemer;
    }

    public void setSystemer(Boolean systemer) {
        this.systemer = systemer;
    }
}
