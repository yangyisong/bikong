package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.dto.query.UserQueryDto;
import com.ydhl.outsourcing.ts.finance.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-12-18 上午 11:30
 * @description
 */
public interface UserDao extends BaseMapper<User> {

    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
    @Select("select * from user where username = #{userName} ")
    User findUserByName(String userName);

    @Select("select * from user where username = #{userName} and enabled = 1")
    User findEnableUserByName(String userName);


    /**
     * 查询用户列表
     * @param
     * @return
     */
    @Select("select * from user where username like CONCAT('%', #{username}, '%') ")
    List<UserDto> findUserListPage(@Param("userQueryDto") UserQueryDto userQueryDto);

    /**
     * 通过用户名查询用户id
     * @param
     * @return
     */
    @Select("select id from user where realname like CONCAT('%', #{realname}, '%') ")
    List<Long> getUserIdByName(@Param("realname") String realname);

    /**
     * 通过部门id 获取员工集合
     *
     * @param departmentId 部门id
     * @return 员工集合
     */
    @Select("select * from user where department_id = #{departmentId}")
    List<User> getUserByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * 通过用户姓名和用户工号搜索用户
     *
     * @param
     * @return
     */
    @Select("select * from user where realname like CONCAT('%', #{content}, '%') or job_number like CONCAT('%', #{content}, '%')")
    List<User> getUserByRealnameOrJobNumber(@Param("content") String content);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

    /**
     * 获取所有业务人员列表
     *
     * @return
     */
    @Select("select * from user where id in (select user_id from user_role where role_id in (2,3))")
    List<User> getAllSalesman();

    @Select("select id from user where realname like CONCAT('%', #{name}, '%')")
    List<Long> getUserIdsByName(@Param("name") String name);

    /**
     * 查询部门列表下是否有员工
     */
    @Select("<script> "
            + "SELECT count(id) from user where department_id in "
            + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    public Long getUserCountByDepartmentIds(@Param(value = "ids") List<Long> ids);

    @Select("select * from user where job_number = #{jobNumber}")
    User findUserByJobNumber(String jobNumber);

    @Select("select id from user")
    List<Long> getAllUserId();
}
