package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.*;
import com.ydhl.outsourcing.ts.finance.dto.query.PersonalIncomeQueryDto;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Martins
 * @create 2018/1/16 15:58.
 * @description
 */
public class PersonalIncomeExample {

    public static Example getPersonalIncomeExample(PersonalIncomeQueryDto personalIncomeQueryDto, PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        Example example = new Example(PersonalIncome.class);
        Example.Criteria criteria = example.createCriteria();
        if (personalIncomeQueryDto.getProductId() != null) {
            criteria.andEqualTo("productId", personalIncomeQueryDto.getProductId());
        }
        if (personalIncomeQueryDto.getUserId() != null) {
            criteria.andEqualTo("userId", personalIncomeQueryDto.getUserId());
        }
        if (personalIncomeQueryDto.getDayNums() != null) {
            criteria.andEqualTo("dayNums", personalIncomeQueryDto.getDayNums());
        }
        if (personalIncomeQueryDto.getStruts() != null) {
            criteria.andEqualTo("struts", personalIncomeQueryDto.getStruts());
        }
        if (personalIncomeQueryDto.getIncomeType() != null) {
            criteria.andEqualTo("incomeType", personalIncomeQueryDto.getIncomeType());
        }
        if (personalIncomeQueryDto.getSettleStartTime() != null) {
            criteria.andGreaterThanOrEqualTo("settleStartTime", DateUtilPlus.setDayStart(personalIncomeQueryDto.getSettleStartTime()));
        }
        if (personalIncomeQueryDto.getSettleEndTime() != null) {
            criteria.andLessThanOrEqualTo("settleEndTime", DateUtilPlus.setDayEnd(personalIncomeQueryDto.getSettleEndTime()));
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getIncomeNumber())) {
            criteria.andLike("incomeNumber", "%"+ personalIncomeQueryDto.getIncomeNumber().toUpperCase() + "%");
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getContractNumber())) {
            criteria.andLike("contractNumber", "%"+ personalIncomeQueryDto.getContractNumber().toUpperCase() + "%");
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getCustomerName())) {
            criteria.andLike("customerName", "%"+personalIncomeQueryDto.getCustomerName() + "%");
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getUserRealname())) {
            criteria.andLike("userRealname", "%" + personalIncomeQueryDto.getUserRealname() + "%");
        }
        if(personalIncomeQueryDto.getFreeze()!=null){
            criteria.andEqualTo(personalIncomeQueryDto.getFreeze());
        }
        example.orderBy("createTime");
        return example;
    }

    public static Example getIncomeExampleForExport(PersonalIncomeQueryDto personalIncomeQueryDto) {
        Example example = new Example(PersonalIncome.class);
        Example.Criteria criteria = example.createCriteria();
        if (personalIncomeQueryDto.getProductId() != null) {
            criteria.andEqualTo("productId", personalIncomeQueryDto.getProductId());
        }
        if (personalIncomeQueryDto.getUserId() != null) {
            criteria.andEqualTo("userId", personalIncomeQueryDto.getUserId());
        }
        if (personalIncomeQueryDto.getDayNums() != null) {
            criteria.andEqualTo("dayNums", personalIncomeQueryDto.getDayNums());
        }
        if (personalIncomeQueryDto.getStruts() != null) {
            criteria.andEqualTo("struts", personalIncomeQueryDto.getStruts());
        }
        if (personalIncomeQueryDto.getIncomeType() != null) {
            criteria.andEqualTo("incomeType", personalIncomeQueryDto.getIncomeType());
        }
        if (personalIncomeQueryDto.getSettleStartTime() != null) {
            criteria.andGreaterThanOrEqualTo("settleStartTime", DateUtilPlus.setMonthStart(personalIncomeQueryDto.getSettleStartTime()));
        }
        if (personalIncomeQueryDto.getSettleEndTime() != null) {
            criteria.andLessThanOrEqualTo("settleEndTime", DateUtilPlus.setMonthEnd(personalIncomeQueryDto.getSettleEndTime()));
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getIncomeNumber())) {
            criteria.andLike("incomeNumber", "%"+ personalIncomeQueryDto.getIncomeNumber().toUpperCase() + "%");
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getContractNumber())) {
            criteria.andLike("contractNumber", "%"+ personalIncomeQueryDto.getContractNumber().toUpperCase() + "%");
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getCustomerName())) {
            criteria.andLike("customerName", "%"+personalIncomeQueryDto.getCustomerName() + "%");
        }
        if (StringUtilPlus.isNotEmpty(personalIncomeQueryDto.getUserRealname())) {
            criteria.andLike("userRealname", "%" + personalIncomeQueryDto.getUserRealname() + "%");
        }
        return example;
    }
}
