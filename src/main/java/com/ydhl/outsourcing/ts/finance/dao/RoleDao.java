package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-12-19 上午 10:58
 * @description
 */
public interface RoleDao extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Select("select role_id from user_role where user_id = #{userId} ")
    List<Long> findRoleIdListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID列表查询权限ID列表
     *
     * @param roleIdList 权限ID列表
     * @return 权限ID列表
     */
    @Select("<script> "
            + "SELECT privilege_id from role_privilege where role_id in "
            + "<foreach collection='roleIdList' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    List<Long> findPrivilegeIdListByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    /**
     * 根据角色id查询用户id集合
     *
     * @param roleId 角色id
     * @return 用户id集合
     */
    @Select("select user_id from user_role where role_id = #{roleId}")
    List<Long> findUserIdListByRoleId(@Param("roleId") Long roleId);

    /**
     * 新增中间数据
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Insert("insert into user_role value (#{userId}, #{roleId})")
    void inserUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 新增中间数据
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Insert("insert into user_role value (#{userId}, #{roleId})")
    void updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 通过用户id删除中间数据
     *
     * @param userId 用户id
     */
    @Select("delete from user_role where user_id = #{userId}")
    void deleteUserRoleByUserId(Long userId);

    @Select("select user_id from user_role where role_id in (2,3)")
    List<Long> findAllBussnisUserIdList();

    @Select("SELECT privilege_id from role_privilege where role_id = #{roleId}")
    List<Long> findPrivilegeIdListByRoleId(@Param("roleId") Long roleId);
}
