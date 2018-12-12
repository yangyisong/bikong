package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.DateHelperPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dao.OperationDao;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.query.OperationQueryDto;
import com.ydhl.outsourcing.ts.finance.example.OperationExample;
import com.ydhl.outsourcing.ts.finance.model.Operation;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 11:26.
 * @description
 */
@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationDao operationDao;

    @Override
    public PageInfo<OperationDto> findOperationPage(OperationQueryDto operationQueryDto, PageModel pageModel) {
        Example example = OperationExample.getOperationExample(operationQueryDto, pageModel);
        Page<Operation> operationPage = (Page<Operation>) operationDao.selectByExample(example);
        List<OperationDto> operationDtoList = new ArrayList<>();
        for (Operation operation : operationPage) {
            OperationDto operationDto = new OperationDto();
            operationDto.setId(operation.getId());
            operationDto.setOperateTime(operation.getOperateTime());
            operationDto.setOperatorName(operation.getOperatorName());
            operationDto.setOperateIp(operation.getOperateIp());
            operationDto.setContent(operation.getContent());
            operationDto.setRemark(operation.getRemark());
            operationDtoList.add(operationDto);
        }
        return new PageInfo<>(operationDtoList, operationPage.getPages());
    }

    @Override
    public PageInfo<OperationDto> findOperationPage(OperationQueryDto operationQueryDto) {
        Example example = OperationExample.getOperationExample(operationQueryDto);
        List<Operation> operationPage = operationDao.selectByExample(example);
        List<OperationDto> operationDtoList = new ArrayList<>();
        for (Operation operation : operationPage) {
            OperationDto operationDto = new OperationDto();
            operationDto.setId(operation.getId());
            operationDto.setOperateTime(operation.getOperateTime());
            operationDto.setOperatorName(operation.getOperatorName());
            operationDto.setOperateIp(operation.getOperateIp());
            operationDto.setContent(operation.getContent());
            operationDto.setRemark(operation.getRemark());
            operationDtoList.add(operationDto);
        }
        return new PageInfo<>(operationDtoList);
    }


    @Override
    public void insertOperation(OperationDto operationDto) {
        Operation operation = new Operation();
        operation.setOperatorName(operationDto.getOperatorName());
        operation.setOperateTime(DateHelperPlus.getNow().getTime());
        operation.setOperateIp(operationDto.getOperateIp());
        operation.setContent(operationDto.getContent());
        operation.setRemark(operationDto.getRemark());
        operationDao.insert(operation);
    }
}
