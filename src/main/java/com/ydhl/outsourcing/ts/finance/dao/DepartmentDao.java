package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.dto.DepartmentDto;
import com.ydhl.outsourcing.ts.finance.model.Department;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/2 11:54.
 */
public interface DepartmentDao extends BaseMapper<Department> {

    @Select("select * from department where name like CONCAT('%', #{name}, '%')")
    List<DepartmentDto> queryDepartmentPage(@Param("name") String name);

    /**
     * 通过名称和上级查询部门信息
     *
     * @param name 名称
     * @param parentId 上级部门
     * @return
     */
    @Select("select * from department where name = #{name} and parent_id = #{parentId}")
    Department getDepartmentByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);

    /**
     * 通过父Id查询子部门
     * @param parentId
     * @return
     */
    @Select("select * from department where parent_id = #{parentId}")
    List<Department> getDepartmentByParentId(@Param("parentId") Long parentId);

    /**
     * 编辑部门
     */
    @Update("update department set name = #{name} where id = #{id}")
    void editDepartment(@Param("id") Long id,@Param("name") String name);

    /**
     * 通过部门名称和父Id判断部门是否存在
     */
    /*@Select("select id from department where name = #{name} and parent_id = #{parentId}")
    Long getDepartmentByNameAndParentId(@Param("parentId") Long parentId,@Param("name") String name);*/

    /**
     * 通过部门名称和Id判断部门是否存在
     */
    @Select("select count(id) from department where parent_id = (select parent_id from department where id = #{id}) and name = #{name} and id!=#{id}")
    Long getDepartmentByNameAndId(@Param("id") Long id,@Param("name") String name);

    /**
     * 查询部门列表下是否有员工
     */
    @Select("<script> "
            + "delete from department where id in "
            + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    public void deleteDepartmentsByIds(@Param(value = "ids") List<Long> ids);
}
