package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.report.ContractReportVo;
import com.ydhl.outsourcing.ts.finance.common.utils.DateHelperPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.custantModule.ConstantModule;
import com.ydhl.outsourcing.ts.finance.dao.CustomerDao;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dao.UserDao;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.dto.query.UserQueryDto;
import com.ydhl.outsourcing.ts.finance.example.UserExample;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Customer;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.model.User;
import com.ydhl.outsourcing.ts.finance.service.DepartmentService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/5/22 下午5:49.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OperationService operationService;


    @Override
    public PageInfo<UserDto> queryUserListPage(PageModel pageModel, UserQueryDto userQueryDto) throws UnknownHostException, BusinessException {
        List<Long> userIdList = null;
        if (userQueryDto.getRoleId() != null) {
            userIdList = roleDao.findUserIdListByRoleId(userQueryDto.getRoleId());
            if (userIdList.size() == 0) {
                return new PageInfo<>(new ArrayList<UserDto>(), 0);
            }
        }
        userQueryDto.setUserIdList(userIdList);
        Example example = UserExample.getUserExample(userQueryDto, pageModel);
        Page<User> userPage = (Page<User>)userDao.selectByExample(example);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : userPage) {
            UserDto userDto = new UserDto();
            userDto.setSystemer(user.getSystemer());
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setRealname(user.getRealname());
            userDto.setPhone(user.getPhone());
            List<Long> roleList = roleDao.findRoleIdListByUserId(user.getId());
            userDto.setRole(roleDao.selectByPrimaryKey(roleList.get(0)).getName());
            userDto.setDepartment(departmentService.getDepartmentByDepartmentId(user.getDepartmentId()).getName());
            userDto.setCustomTotal(customerDao.selectTotalCustomByUserId(user.getId()));
            userDto.setJobNumber(user.getJobNumber());
            userDto.setEnabled(user.getEnabled());
            userDto.setLastLogin(user.getLastLogin());
            userDto.setCreateTime(user.getCreateTime());
            userDtos.add(userDto);
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看员工列表");
        operationService.insertOperation(operationDto);
        return new PageInfo<>(userDtos, userPage.getPages());
    }

    @Override
    public PageInfo<UserDto> queryUserPage(UserQueryDto userQueryDto) throws UnknownHostException, BusinessException {
        Example example = UserExample.getUserExample(userQueryDto);
        List<User> userPage = userDao.selectByExample(example);
        List<UserDto> userList = new ArrayList<>();
        for (User user : userPage) {
            UserDto userDto = new UserDto();
            userDto.setSystemer(user.getSystemer());
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setRealname(user.getRealname());
            userDto.setPhone(user.getPhone());
            List<Long> roleList = roleDao.findRoleIdListByUserId(user.getId());
            userDto.setRole(roleDao.selectByPrimaryKey(roleList.get(0)).getName());
            userDto.setDepartment(departmentService.getDepartmentByDepartmentId(user.getDepartmentId()).getName());
            userDto.setCustomTotal(customerDao.selectTotalCustomByUserId(user.getId()));
            userDto.setJobNumber(user.getJobNumber());
            userDto.setLastLogin(user.getLastLogin());
            userDto.setCreateTime(user.getCreateTime());
            userList.add(userDto);
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("导出员工列表");
        operationService.insertOperation(operationDto);
        return new PageInfo<>(userList);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void saveUser(UserDto userDto) throws BusinessException, UnknownHostException {
        User oldUser = userDao.findUserByName(userDto.getUsername());
        if (oldUser != null) {
            throw new BusinessException(ConstantModule.ERROR_USERNAME_HAS_EXITS.getCode(), ConstantModule.ERROR_USERNAME_HAS_EXITS.getMessage());
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setRealname(userDto.getRealname());
        user.setSystemer(false);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setPhone(userDto.getPhone());
        user.setJobNumber(userDto.getJobNumber());
        user.setDepartmentId(userDto.getDepartmentId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreateTime(DateHelperPlus.getNow().getTime());
        //插入用户数据
        userDao.insert(user);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("新增员工("+userDto.getUsername()+")");
        operationService.insertOperation(operationDto);
        //插入用户角色中间数据
        roleDao.inserUserRole(user.getId(), userDto.getRoleId());
    }

    @Override
    public UserDto queryUser(Long userId) {
        User user = userDao.selectByPrimaryKey(userId);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRealname(user.getRealname());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setJobNumber(user.getJobNumber());
        userDto.setRoleId(roleDao.findRoleIdListByUserId(userId).get(0));
        userDto.setDepartmentId(user.getDepartmentId());
        userDto.setCreateTime(user.getCreateTime());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setRoleName(roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(userId).get(0)).getName());
        return userDto;
    }

    @Override
    public UserDto check(String userName, String password) {
        User user = userDao.findUserByName(userName);
        if(user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                user.setLastLogin(DateHelperPlus.getNow().getTime());
                userDao.updateByPrimaryKey(user);
                UserDto userDto = new UserDto();
                userDto.setUsername(userName);
                userDto.setJobNumber(user.getJobNumber());
                userDto.setId(user.getId());
                userDto.setRoleId(roleDao.findRoleIdListByUserId(user.getId()).get(0));
                return userDto;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String userName) {
        User user = userDao.findUserByName(userName);
        if(user != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existsJobNumber(String jobNumber) {
        User user = userDao.findUserByJobNumber(jobNumber);
        if (user != null) {
            return true;
        }
        return false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> closeUser(List<Long> userIdList) throws UnknownHostException, BusinessException {
        Map<String, Object> map = new HashMap<>();
        for (Long id : userIdList) {
            Page<Customer> customers = customerDao.getAllCustomreByUserId(id);
            User user = userDao.selectByPrimaryKey(id);
            if (customers.size()!=0) {
                map.put("msg", user.getRealname()+"：该员工名下拥有客户,请转移后停用");
                continue;
            }
            user.setEnabled(false);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("停用员工("+ user.getUsername()+")");
            operationService.insertOperation(operationDto);
            userDao.updateByPrimaryKey(user);
        }
        return map;
    }


    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Map<Object,Object> updateUser(UserDto userDto) throws BusinessException, UnknownHostException {
        Map<Object,Object> map = new HashMap<>();
        User us = userDao.findUserByJobNumber(userDto.getJobNumber());
        if (us != null) {
            if (!us.getId().equals(userDto.getId())) {
                map.put("msg", "工号已存在");
                return map;
            }
        }
        User u = userDao.getUserByUsername(userDto.getUsername());
        if (u != null) {
            if (!u.getId().equals(userDto.getId())) {
                map.put("msg", "用户名已存在");
                return map;
            }
        }
        User user = userDao.selectByPrimaryKey(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setRealname(userDto.getRealname());
        List<Long> roleIds = roleDao.findRoleIdListByUserId(userDto.getId());
        if (StringUtilPlus.isNotEmpty(userDto.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setDepartmentId(userDto.getDepartmentId());
        user.setJobNumber(userDto.getJobNumber());
        user.setPhone(userDto.getPhone());
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("修改员工("+user.getUsername()+")资料");
        operationService.insertOperation(operationDto);
        //员工资料修改
        userDao.updateByPrimaryKeySelective(user);
        //删除中间表数据
        roleDao.deleteUserRoleByUserId(userDto.getId());
        //新增中间表数据
        roleDao.inserUserRole(userDto.getId(), roleIds.get(0));
        map.put("msg", "success");
        return map;
    }

    @Override
    public List<Long> getUserIdByName(String realname) {
        return userDao.getUserIdByName(realname);
    }

    @Override
    public List<UserDto> getUserByRealnameOrJobNumber(String content) {
        List<User> userList = userDao.getUserByRealnameOrJobNumber(content);
        List<UserDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setRealname(user.getRealname());
            userDto.setJobNumber(user.getJobNumber());
            userDto.setRoleId(roleDao.findRoleIdListByUserId(user.getId()).get(0));
            dtoList.add(userDto);
        }
        return dtoList;
    }

    @Override
    public List<UserDto> getAllSalesman() {
        List<User> userList = userDao.getAllSalesman();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setRealname(user.getRealname());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto userLogin(String username, String password) throws BusinessException {
        User user = userDao.findUserByName(username);
        if (user == null) {
            throw new BusinessException(ConstantModule.ERROR_CERTIFICATE_FAILED.getCode(), ConstantModule.ERROR_CERTIFICATE_FAILED.getMessage());
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ConstantModule.ERROR_CERTIFICATE_FAILED.getCode(), ConstantModule.ERROR_CERTIFICATE_FAILED.getMessage());
        }
        if (!user.getEnabled()) {
            throw new BusinessException(ConstantModule.ERROR_ACCOUNT_HAD_LIMIT.getCode(), ConstantModule.ERROR_ACCOUNT_HAD_LIMIT.getMessage());
        }
        user.setLastLogin(DateHelperPlus.getNow().getTime());
        userDao.updateByPrimaryKey(user);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setRealname(user.getRealname());
        userDto.setJobNumber(user.getJobNumber());
        return userDto;
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        User user = new User();
        user.setId(id);
        user = userDao.selectOne(user);
        return userToUserDto(user);
    }

    @Override
    public List<Long> getUserIdsByName(String name) {
        return userDao.getUserIdsByName(name);
    }

    @Override
    public void openUser(List<Long> userIdList) throws UnknownHostException, BusinessException {
        for (Long id : userIdList) {
            User user = userDao.selectByPrimaryKey(id);
            user.setEnabled(true);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("启用员工("+ user.getUsername()+")");
            operationService.insertOperation(operationDto);
            userDao.updateByPrimaryKey(user);
        }
    }

    @Override
    public Long getUserCountByDepartmentIds(List<Long> ids) {
        return userDao.getUserCountByDepartmentIds(ids);
    }

    @Override
    public List<Long> getAllUserId() {
        return userDao.getAllUserId();
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setRealname(user.getRealname());
        userDto.setPhone(user.getPhone());
        userDto.setJobNumber(user.getJobNumber());
        userDto.setRoleId(roleDao.findRoleIdListByUserId(user.getId()).get(0));
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(user.getId()).get(0));
        userDto.setRole(role!=null?role.getName():"");
        userDto.setSystemer(user.getSystemer());
        return userDto;
    }
}
