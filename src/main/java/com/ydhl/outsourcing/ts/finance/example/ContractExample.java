package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto;
import com.ydhl.outsourcing.ts.finance.model.Contract;
import tk.mybatis.mapper.entity.Example;


/**
 * 产品查询对象
 * Created by dell on 2018/1/12.
 */
public class ContractExample {

    public static Example getContractExample(ContractQueryDto contractQueryDto, PageModel pageModel) {
        if(pageModel!=null) {
            PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        }
        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtilPlus.isNoneEmpty(contractQueryDto.getNumber())){
            criteria.andLike("number","%"+contractQueryDto.getNumber()+"%");
        }
        if(contractQueryDto.getUserIds()!=null && contractQueryDto.getUserIds().size()!=0){
            criteria.andIn("userId",contractQueryDto.getUserIds());
        }
        if(contractQueryDto.getCustomIds()!=null && contractQueryDto.getCustomIds().size()!=0){
            criteria.andIn("customerId",contractQueryDto.getCustomIds());
        }
        if(StringUtilPlus.isNoneEmpty(contractQueryDto.getProductName())){
            criteria.andLike("productName","%"+contractQueryDto.getProductName()+"%");
        }
        if (contractQueryDto.getMinStartTime()!=null) {
            criteria.andGreaterThanOrEqualTo("startTime", contractQueryDto.getMinStartTime());
        }
        if (contractQueryDto.getMaxStartTime()!=null) {
            criteria.andLessThanOrEqualTo("startTime", contractQueryDto.getMaxStartTime());
        }
        if (contractQueryDto.getMinEndTime()!=null) {
            criteria.andGreaterThanOrEqualTo("endTime", contractQueryDto.getMinEndTime());
        }
        if (contractQueryDto.getMaxEndTime()!=null) {
            criteria.andLessThanOrEqualTo("endTime", contractQueryDto.getMaxEndTime());
        }
        if(contractQueryDto.getContractStruts()!=null){
            criteria.andEqualTo("struts", contractQueryDto.getContractStruts());
        }
        if(contractQueryDto.getContractIdList()!=null && contractQueryDto.getContractIdList().size()!=0){
            criteria.andIn("id",contractQueryDto.getContractIdList());
        }
       // criteria.andEqualTo("validity", true);
        return example;
    }
}
