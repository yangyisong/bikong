package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.query.OperationQueryDto;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 11:24.
 */
public interface OperationService {

    /**
     * 查询操作日志分页数据
     *
     * @param operationQueryDto 查询条件封装
     * @param pageModel 分页模型
     * @return 分页数据
     */
    PageInfo<OperationDto> findOperationPage(OperationQueryDto operationQueryDto, PageModel pageModel);

    /**
     * 查询操作日志分页数据
     *
     * @param operationQueryDto 查询条件封装
     * @return 分页数据
     */
    PageInfo<OperationDto> findOperationPage(OperationQueryDto operationQueryDto);

    /**
     * 操作日志新增
     *
     * @param operationDto
     */
    void insertOperation(OperationDto operationDto);
}
