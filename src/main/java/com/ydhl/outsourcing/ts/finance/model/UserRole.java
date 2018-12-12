package com.ydhl.outsourcing.ts.finance.model;

/**
 * @author Martins
 * @create 2018/1/15 15:15.
 * @description
 */
public class UserRole {

    private Long userId;

    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
