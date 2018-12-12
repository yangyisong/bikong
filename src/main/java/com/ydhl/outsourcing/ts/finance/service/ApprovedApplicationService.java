package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.ApprovedApplicationDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ApprovedApplicationQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author Martins
 * @create 2018/1/16 20:35.
 */
public interface ApprovedApplicationService {

    /**
     * 查询结算列表
     *
     * @param approvedApplicationQueryDto 查询条件
     * @param pageModel 分页模型
     * @return
     */
    PageInfo<ApprovedApplicationDto> getApprovedApplicationPage(ApprovedApplicationQueryDto approvedApplicationQueryDto, PageModel pageModel);

    /**
     * 获取所有审批状态
     * @return
     */
    Map<Object, ApplicationStruts> getAllStruts();

    /**
     * 通过审批人获取合同Id列表
     */
    List<Long> getContractIdByUserId(Long userId);

    /**
     * 通过申请
     */
    public void passApply(Long approveId) throws UnknownHostException, BusinessException;

    /**
     * 拒绝申请
     */
    public void refuseApply(Long approveId,String content) throws UnknownHostException, BusinessException;

    ApprovedApplicationDto getApprovedApplicationById(Long id);

    /**
     * 新增申请
     */
    void insertApprovedApplication(ApprovedApplicationDto approvedApplicationDto,List<Long> incomeIds) throws UnknownHostException, BusinessException;

    /**
     * 通过申请Id获取收益Id列表
     */
    List<Long> getIncomeIdListByApprovedId(Long approvedId);

    /**
     * 删除申请
     */
    void deleteApprovedById(Long approvedId);

    /**
     * 再次申请
     */
    void retryApprovedById(Long approvedId) throws UnknownHostException, BusinessException;
}
