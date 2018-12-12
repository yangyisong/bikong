package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.dto.query.UserQueryDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/5/22 下午5:48.
 */
public interface UserService {

    /**
     * 查询用户/员工分页
     *
     * @param pageModel 分页模型
     * @param userQueryDto 查询条件
     * @return 用户/员工分页
     */
    PageInfo<UserDto> queryUserListPage(PageModel pageModel, UserQueryDto userQueryDto) throws UnknownHostException, BusinessException;

    /**
     * 导出列表
     *
     * @param userQueryDto
     * @return
     */
    PageInfo<UserDto> queryUserPage(UserQueryDto userQueryDto) throws UnknownHostException, BusinessException;

    /**
     * 新增用户
     *
     * @param userDto 用户对象
     */
    void saveUser(UserDto userDto) throws BusinessException, UnknownHostException;

    UserDto queryUser(Long userId);

    UserDto  check(String userName,String password);

    boolean exists(String userName);

    boolean existsJobNumber(String jobNumber);

    Map<String, Object> closeUser(List<Long> userIdList) throws BusinessException, UnknownHostException;

    /**
     * 编辑用户
     *
     * @param userDto 用户对象
     */
    Map<Object,Object> updateUser(UserDto userDto) throws BusinessException, UnknownHostException;

    /**
     * 通过用户名获取用户Id
     */
    List<Long> getUserIdByName(String realname);

    /**
     * 根据用户姓名或工号查询用户
     * @return
     */
    List<UserDto> getUserByRealnameOrJobNumber(String content);

    /**
     * 获取所有业务人员
     *
     * @return
     */
    List<UserDto> getAllSalesman();

    UserDto userLogin(String username, String password) throws BusinessException;

    /**
     * 通过Id获取用户
     */
    UserDto getUserDtoById(Long id);

    /**
     * 通过用户名获取用户Id列表
     */
    List<Long> getUserIdsByName(String name);

    /**
     * 员工启用
     *
     * @param userIdList
     */
    void openUser(List<Long> userIdList) throws UnknownHostException, BusinessException;

    /**
     * 查询部门列表下是否有员工
     */
    public Long getUserCountByDepartmentIds(List<Long> ids);

    List<Long> getAllUserId();
}
