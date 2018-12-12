package com.ydhl.outsourcing.ts.finance.model;

import com.ydhl.outsourcing.ts.finance.base.BaseDomain;

import javax.persistence.Table;

/**
 * @author Martins
 * @create 2018/1/2 11:47.
 * @description 部门表
 */
@Table(name = "department")
public class Department extends BaseDomain<Long> {

    private static final long serialVersionUID = 9037803278752840766L;

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
