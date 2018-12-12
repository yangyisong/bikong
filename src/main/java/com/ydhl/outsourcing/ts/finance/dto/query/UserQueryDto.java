package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.dto.UserDto;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/12 13:57.
 */
public class UserQueryDto extends UserDto {

    private static final long serialVersionUID = -8968086084037499095L;

    /**
     * 标示
     */
    private Integer searchTab = 0;

    /**
     * 内容
     */
    private String content;

    /**
     * 职位
     */
    private Long roleId;

    /**
     * 职位id集合
     */
    private List<Long> userIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getSearchTab() {
        return searchTab;
    }

    public void setSearchTab(Integer searchTab) {
        this.searchTab = searchTab;
        if(searchTab==0){
            setRealname(getContent());
        }else if(searchTab==1){
            setJobNumber(getContent());
        }else if(searchTab==2){
            setPhone(getContent());
        }else{}
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
