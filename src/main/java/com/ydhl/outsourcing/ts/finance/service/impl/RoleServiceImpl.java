package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.PrivilegeDao;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dto.RoleDto;
import com.ydhl.outsourcing.ts.finance.model.Privilege;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/12 18:19.
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public List<RoleDto> getAllRoleList() {
        List<Role> roles = roleDao.selectAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }

    @Override
    public List<RoleDto> getAllRoleListAndPrivileges() {
        List<Role> roles = roleDao.selectAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            List<Long> ids = roleDao.findPrivilegeIdListByRoleId(role.getId());
            if(ids!=null && ids.size()!=0){
                List<Privilege> privilegeDbs = privilegeDao.findPrivilegeListByIdList(ids);
                roleDto.setPrivileges(privilegeDbs);
            }
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}
