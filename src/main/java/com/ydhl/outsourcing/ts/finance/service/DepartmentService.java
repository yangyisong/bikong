package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.dto.DepartmentDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Department;

import java.util.List;
import java.util.Map;

/**
 * @author Martins
 * @create 2018/1/2 11:45.
 */
public interface DepartmentService {

    PageInfo<DepartmentDto> queryDepartmentPage(Integer pageNo, Integer pageSize, String name);

    /**
     * 通过部门id查询部门信息
     *
     * @param departmentId 部门id
     * @return 部门信息
     */
    DepartmentDto getDepartmentByDepartmentId(Long departmentId);

    /**
     * 查询所有部门
     *
     * @return 部门列表
     */
    List<DepartmentDto> getAllDepartmentDtoList();

    /**
     * 部门新增
     *
     * @param
     */
    void insertDepartment(String name, Long parentId) throws BusinessException;

    /**
     * 修改部门信息
     *
     * @param
     */
    void updateDepartment(Long id, String name, Long parentId) throws BusinessException;

    /**
     * 根据id 删除部门信息
     *
     * @param departmentId
     */
    void deleteDepartmentById(Long departmentId) throws BusinessException;

    List<DepartmentDto> getDepartmentsList(List<Department> departList);

    /**
     * 获取部门Id列表
     */
    List<Long> getDepartmentIdList(List<Department> departList);

    List<Department> getDepartmentListByParentId(Long parentId);

    /**
     * 新增部门
     */
    public Map<String,String> addDepartment(DepartmentDto departmentDto);

    /**
     * 编辑部门
     */
    public Map<String,String> editDepartment(Long id,String name);

    /**
     * 通过部门Id列表删除部门
     */
    public void deleteDepartmentsByIds(List<Long> ids);
}
