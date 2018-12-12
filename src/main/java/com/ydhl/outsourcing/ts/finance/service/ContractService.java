package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.ContractDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto2;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * 合同Service
 */
public interface ContractService {
    /**
     * 获取合同分页列表
     */
    Map<Object,Object> queryContractDtoListPage(PageModel pageModel, ContractQueryDto contractQueryDto);

    /**
     * 通过id 获取合同
     * @return
     */
    ContractDto getContractById(Long contractId);

    /**
     * 新增签约
     *
     * @param contractDto
     */
    Map<String, Object> insertContract(ContractDto contractDto) throws UnknownHostException, BusinessException, Exception;

    /**
     * 根据合同编号查询合同
     *
     * @param number
     * @return
     */
    ContractDto getContractByNumber(String number);

    /**
     * 合同编辑
     *
     * @param contractDto
     */
    Map<String, Object> updateContract(ContractDto contractDto) throws Exception;

    /**
     * 转换签约
     *
     * @param contractDto
     * @return
     */
    Map<String,Object> changeContract(ContractDto contractDto) throws Exception;

    /**
     * 续签
     *
     * @param contractDto
     * @return
     */
    Map<String,Object> continueSign(ContractDto contractDto) throws Exception;

    /**
     * 通过产品Id获取合同数量
     */
    public Long getContractNumberByProductId(Long productId);

    /**
     * 临退
     * @param contractDto
     * @return
     */
    Map<String,Object> quitContract(ContractDto contractDto) throws UnknownHostException, BusinessException, Exception;

    /**
     * 签约审批通过
     *
     * @return
     */
    Map<String,Object> approvedAgreeSign(Long applyId, Long userId) throws UnknownHostException, BusinessException, Exception;

    /**
     * 签约审批拒绝
     *
     * @return
     */
    Map<String,Object> approvedRejectSign(Long applyId, Long userId, String remark) throws UnknownHostException, BusinessException, Exception;

    /**
     * 信息确认
     * @param applyId
     * @param userId
     * @return
     */
    Map<String, Object> signInfoConfirm(Long applyId, Long userId);

    /**
     * 收款确认
     * @param applyId
     * @param userId
     * @return
     */
    Map<String, Object> signReceiveConfirm(Long applyId, Long userId);

    /**
     * 金额确认
     * @param applyId
     * @param userId
     * @return
     */
    Map<String, Object> signAmountConfirm(Long applyId, Long userId);

    /**
     * 获取合同分页列表
     */
    Map<Object,Object> queryContractDtoListPage2(PageModel pageModel, ContractQueryDto2 contractQueryDto);
}
