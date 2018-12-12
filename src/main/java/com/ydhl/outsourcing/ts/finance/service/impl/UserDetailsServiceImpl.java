package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.common.utils.CollectionUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.DateHelperPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilPlus;
import com.ydhl.outsourcing.ts.finance.dao.*;
import com.ydhl.outsourcing.ts.finance.dto.AuthorityDto;
import com.ydhl.outsourcing.ts.finance.dto.LoginSecurityDto;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import com.ydhl.outsourcing.ts.finance.model.Privilege;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.model.User;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.UserLoginService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-12-18 下午 3:15
 * @description
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserLoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private OperationService operationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findEnableUserByName(username);

        List<AuthorityDto> privileges = new ArrayList<>();
        List<Long> privilegesId = new ArrayList<>();

        List<Long> roleIdDbs = roleDao.findRoleIdListByUserId(user.getId());//查找用户角色id
        if (CollectionUtilPlus.isNotNullOrEmpty(roleIdDbs)) {
            List<Long> privilegeIdDbs = roleDao.findPrivilegeIdListByRoleIdList(roleIdDbs);//查找角色权限id
            if (CollectionUtilPlus.isNotNullOrEmpty(privilegeIdDbs)) {
                List<Privilege> privilegeDbs = privilegeDao.findPrivilegeListByIdList(privilegeIdDbs);//查找权限
                for (Privilege privilegeDb : privilegeDbs) {
                    AuthorityDto auth = new AuthorityDto();
                    auth.setPrivilege(privilegeDb.getCode());
                    privileges.add(auth);
                    privilegesId.add(privilegeDb.getId());
                }
            }
        }

        LoginSecurityDto loginSecurityDto = new LoginSecurityDto();

        loginSecurityDto.setId(user.getId());
        loginSecurityDto.setAccountNonExpired(true);
        loginSecurityDto.setAccountNonLocked(true);
        loginSecurityDto.setAuthoritieIds(privilegesId);
        loginSecurityDto.setAuthorities(privileges);
        loginSecurityDto.setCredentialsNonExpired(true);
        loginSecurityDto.setEnabled(true);
        loginSecurityDto.setUsername(user.getUsername());
        loginSecurityDto.setPassword(user.getPassword());
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(user.getId()).get(0));

        loginSecurityDto.setRoleId(role!=null?role.getId():null);
        loginSecurityDto.setRoleName(role!=null?role.getName():"");
        loginSecurityDto.setRealname(user.getRealname());
//        loginSecurityDto.setPassword(passwordEncoder.encode(username));

        OperationDto operationDto = new OperationDto();
        operationDto.setOperatorName(user.getRealname());
        try {
            operationDto.setOperateIp(CurrentUserUtils.getLocalhostIp());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        user.setLastLogin(DateHelperPlus.getNow().getTime());
        userDao.updateByPrimaryKey(user);
        operationDto.setContent("登录成功");
        operationDto.setOperateTime(DateHelperPlus.getNow().getTime());
        operationService.insertOperation(operationDto);
        return loginSecurityDto;
    }
}
