package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;
import com.ydhl.outsourcing.ts.finance.model.Privilege;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/12 18:16.
 * @description
 */
public class RoleDto extends BaseDto<Long> {

    private static final long serialVersionUID = -6166270437303439457L;

    /**
     * 角色名
     */
    private String name ;

    /**
     * 权限
     */
    private List<Privilege> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}
