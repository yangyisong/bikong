package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Privilege;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-12-19 上午 10:22
 * @description
 */
public interface PrivilegeDao extends BaseMapper<Privilege> {

    /**
     * 根据资源ID查询权限
     *
     * @param resourceId 资源ID
     * @return 权限列表
     */
    @Select("SELECT privilege_id from resource_privilege where resource_id = #{resourceId} ")
    List<Long> findPrivilegeIdListByResourceId(@Param("resourceId") Long resourceId);

    /**
     * 查询权限编号列表
     *
     * @param privilegeIdList 权限ID列表
     * @return 权限编号列表
     */
    @Select("<script> "
            + "SELECT code from privilege where id in "
            + "<foreach collection='privilegeIdList' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    List<String> findCodeListByIdList(@Param("privilegeIdList") List<Long> privilegeIdList);
    /**
     * 根据权限ID列表查询权限列表
     *
     * @param privilegeIdList 权限ID列表
     * @return 权限列表
     */
    @Select("<script> "
            + "SELECT * from privilege where root = true and id in "
            + "<foreach collection='privilegeIdList' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    List<Privilege> findPrivilegeListByIdList(@Param("privilegeIdList") List<Long> privilegeIdList);
}
