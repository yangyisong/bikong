package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dto.query.CustomerQueryDto;
import com.ydhl.outsourcing.ts.finance.model.Customer;
import tk.mybatis.mapper.entity.Example;


/**
 * 客户查询对象
 * Created by dell on 2018/1/12.
 */
public class CustomerExample {

    public static Example getCustomerExample(CustomerQueryDto customerQueryDto, PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtilPlus.isNoneEmpty(customerQueryDto.getName())) {
            criteria.andLike("name", "%"+customerQueryDto.getName()+"%");
        }
        if (StringUtilPlus.isNoneEmpty(customerQueryDto.getPhone())) {
            criteria.andLike("phone", "%"+customerQueryDto.getPhone()+"%");
        }
        if (StringUtilPlus.isNoneEmpty(customerQueryDto.getIdcard())) {
            criteria.andLike("idcard", "%"+customerQueryDto.getIdcard()+"%");
        }
        if (customerQueryDto.getBeginTime()!=null) {
            criteria.andGreaterThanOrEqualTo("createTime", DateUtilPlus.setDayStart(customerQueryDto.getBeginTime()));
        }
        if (customerQueryDto.getEndTime()!=null) {
            criteria.andLessThanOrEqualTo("createTime", DateUtilPlus.setDayEnd(customerQueryDto.getEndTime()));
        }
        if(customerQueryDto.getBusinessIds()!=null){
            criteria.andIn("userId",customerQueryDto.getBusinessIds());
        }
        example.orderBy("createTime").desc();
        return example;
    }

    public static Example getCustomerExample(CustomerQueryDto customerQueryDto) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtilPlus.isNoneEmpty(customerQueryDto.getName())) {
            criteria.andLike("name", "%"+customerQueryDto.getName()+"%");
        }
        if (StringUtilPlus.isNoneEmpty(customerQueryDto.getPhone())) {
            criteria.andLike("phone", "%"+customerQueryDto.getPhone()+"%");
        }
        if (StringUtilPlus.isNoneEmpty(customerQueryDto.getIdcard())) {
            criteria.andLike("idcard", "%"+customerQueryDto.getIdcard()+"%");
        }
        if (customerQueryDto.getBeginTime()!=null) {
            criteria.andGreaterThanOrEqualTo("createTime", customerQueryDto.getBeginTime());
        }
        if (customerQueryDto.getEndTime()!=null) {
            criteria.andLessThanOrEqualTo("createTime", customerQueryDto.getEndTime());
        }
        if(customerQueryDto.getBusinessIds()!=null){
            criteria.andIn("userId",customerQueryDto.getBusinessIds());
        }
        return example;
    }
}
