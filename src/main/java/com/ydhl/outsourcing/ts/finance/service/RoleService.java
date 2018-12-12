package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.RoleDto;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/12 18:15.
 */
public interface RoleService {

    /**
     * 获取角色/员工列表
     *
     * @return 角色/员工列表
     */
    List<RoleDto> getAllRoleList();

    /**
     * 获取所有角色及权限
     */
    List<RoleDto> getAllRoleListAndPrivileges();
}
