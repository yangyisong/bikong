package com.ydhl.outsourcing.ts.finance.dto.query;

import com.ydhl.outsourcing.ts.finance.dto.PersonalIncomeDto;

/**
 * @author Martins
 * @create 2018/1/16 15:27.
 * @description
 */
public class PersonalIncomeQueryDto extends PersonalIncomeDto {

    private static final long serialVersionUID = 4794712412752576907L;

    private Integer searchTab=0;

    private String content;

    public Integer getSearchTab() {
        return searchTab;
    }

    public void setSearchTab(Integer searchTab) {
        this.searchTab = searchTab;
        if(searchTab==0){
            setIncomeNumber(getContent());
        }else if(searchTab==1){
            setContractNumber(getContent());
        }else if(searchTab==2){
            setCustomerName(getContent());
        }else if (searchTab == 3){
            setUserRealname(getContent());
        }else if (searchTab == -1){
            this.searchTab = null;
        }else {}
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
