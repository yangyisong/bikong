package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto2;
import com.ydhl.outsourcing.ts.finance.model.Contract;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 产品查询对象
 * Created by dell on 2018/1/12.
 */
public class ContractExample2 {

    public static Example getContractExample(ContractQueryDto2 contractQueryDto, PageModel pageModel) {
        if(pageModel!=null) {
            PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        }
        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtilPlus.isNoneEmpty(contractQueryDto.getNumber())){
            criteria.andLike("number","%"+contractQueryDto.getNumber()+"%");
        }
        if(StringUtilPlus.isNoneEmpty(contractQueryDto.getProductName())){
            criteria.andLike("productName","%"+contractQueryDto.getProductName()+"%");
        }
        if(contractQueryDto.getUserIds()!=null && contractQueryDto.getUserIds().size()!=0){
            criteria.andIn("userId",contractQueryDto.getUserIds());
        }
        if(contractQueryDto.getUserId()!=null){
            criteria.andEqualTo("userId",contractQueryDto.getUserId());
        }
        if(contractQueryDto.getCustomIds()!=null && contractQueryDto.getCustomIds().size()!=0){
            criteria.andIn("customerId",contractQueryDto.getCustomIds());
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
        if(contractQueryDto.getFlag()==0){
            criteria.andEqualTo("struts", contractQueryDto.getStruts());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d = sdf.format(new Date());
            Date nows = DateUtilHelper.fomatDate(d);
            criteria.andGreaterThanOrEqualTo("endTime", nows);
        }else{
            criteria.andLessThanOrEqualTo("endTime", new Date());
        }
        return example;
    }
}
