package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseUserDto;
import com.ydhl.outsourcing.ts.finance.common.utils.PatternUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author Junpeng.Su
 * @create 2017-12-19 下午 5:21
 * @description
 */
public class UserDto extends BaseUserDto{

    private static final long serialVersionUID = -5911530046878284279L;

    public interface Add{}

    public interface Edit{}

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
     * 职位
     */
    private String role;

    /**
     * 部门
     */
    private String department;

    /**
     * 客户数量
     */
    private Integer customTotal;

    private Long roleId;

    /**
     * 是否为系统管理员
     */
    private Boolean systemer;

    private String roleName;

    @Override
    @NotBlank(message="用户名不能为空")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @NotBlank(message="密码不能为空")
    @Length(min=6,message="密码长度不能小于6位")
    public String getPassword() {
        return super.getPassword();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @NotBlank(message="工号不能为空")
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    @NotNull(message = "部门不为空")
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotBlank(message = "手机号不为空", groups = {Add.class, Edit.class})
    @Pattern(regexp = PatternUtils.FORMAT_PHONE, message = "不是正确的手机号", groups = {Add.class, Edit.class})
    public String getPhone() {
        return phone;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getCustomTotal() {
        return customTotal;
    }

    public void setCustomTotal(Integer customTotal) {
        this.customTotal = customTotal;
    }

    @NotNull(message = "职位不为空")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Boolean getSystemer() {
        return systemer;
    }

    public void setSystemer(Boolean systemer) {
        this.systemer = systemer;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
