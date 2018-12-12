package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dto.query.OperationQueryDto;
import com.ydhl.outsourcing.ts.finance.model.Operation;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Martins
 * @create 2018/1/15 11:40.
 * @description
 */
public class OperationExample {

    public static Example getOperationExample(OperationQueryDto operationQueryDto, PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        Example example = new Example(Operation.class);
        Example.Criteria criteria = example.createCriteria();
        if (operationQueryDto.getStartTime() != null) {
            criteria.andGreaterThanOrEqualTo("operateTime", DateUtilPlus.setDayStart(operationQueryDto.getStartTime()));
        }
        if (operationQueryDto.getEndTime() != null) {
            criteria.andLessThanOrEqualTo("operateTime", DateUtilPlus.setDayEnd(operationQueryDto.getEndTime()));
        }
        if (StringUtilPlus.isNotEmpty(operationQueryDto.getOperatorName())) {
            criteria.andLike("operatorName", "%" + operationQueryDto.getOperatorName() + "%");
        }
        example.orderBy("id").desc();
        return example;
    }

    public static Example getOperationExample(OperationQueryDto operationQueryDto) {
        Example example = new Example(Operation.class);
        Example.Criteria criteria = example.createCriteria();
        if (operationQueryDto.getStartTime() != null) {
            criteria.andGreaterThanOrEqualTo("operateTime", DateUtilPlus.setDayStart(operationQueryDto.getStartTime()));
        }
        if (operationQueryDto.getEndTime() != null) {
            criteria.andLessThanOrEqualTo("operateTime", DateUtilPlus.setDayEnd(operationQueryDto.getEndTime()));
        }
        if (StringUtilPlus.isNotEmpty(operationQueryDto.getOperatorName())) {
            criteria.andLike("operatorName", "%" + operationQueryDto.getOperatorName() + "%");
        }
        example.orderBy("id").desc();
        return example;
    }
}
