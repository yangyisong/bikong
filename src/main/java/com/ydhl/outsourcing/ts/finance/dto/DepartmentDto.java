package com.ydhl.outsourcing.ts.finance.dto;

import com.ydhl.outsourcing.ts.finance.base.BaseDto;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/2 11:46.
 */
public class DepartmentDto extends BaseDto<Long> {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean struts;

    /**
     * 上级部门
     */
    private Long parentId;

    /**
     * 部门员工数量
     */
    private Integer employeeNum;

    /**
     * 部门领导
     */
    private Long headId;

    /**
     * 是否为总部
     */
    private Boolean head;

    private List<DepartmentDto> children;

   /* public List<DepartmentDto> getDepartmentDtos() {
        return departmentDtos;
    }

    public void setDepartmentDtos(List<DepartmentDto> departmentDtos) {
        this.departmentDtos = departmentDtos;
    }*/

    public List<DepartmentDto> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentDto> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStruts() {
        return struts;
    }

    public void setStruts(Boolean struts) {
        this.struts = struts;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public Boolean getHead() {
        return head;
    }

    public void setHead(Boolean head) {
        this.head = head;
    }
}
