package com.ydhl.outsourcing.ts.finance.model;



import com.ydhl.outsourcing.ts.finance.base.BaseDomain;
import com.ydhl.outsourcing.ts.finance.enums.AuthType;
import com.ydhl.outsourcing.ts.finance.enums.ResourceType;

import javax.persistence.Table;

/**
 * @author Junpeng.Su
 * @create 2017-08-17 下午 1:50
 * @description 资源表
 */
@Table(name = "resource")
public class Resource extends BaseDomain<Long> {

    private static final long serialVersionUID = 8903208959911474142L;

    /**
     * 菜单的url
     */
    private String url;

    /**
     * 名称
     */
    private String name;

    /**
     * 资源类型
     */
    private ResourceType type;

    /**
     * 是否是根菜单
     */
    private Boolean root;

    /**
     * 是不是叶子菜单
     */
    private Boolean leaf;

    /**
     * 次序
     */
    private Integer orders;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 可访问类型
     */
    private AuthType authType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

}
