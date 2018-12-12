package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.ContractSignInfoDto;

import java.util.List;

/**
 * Created by dell on 2018/1/28.
 */
public interface ContractSignInfoService {
    /**
     * 插入合同签约信息列表
     */
    public void insertContractSignInfoList(List<ContractSignInfoDto> contractSignInfoDtos, Long productId);

    /**
     * 获取合同签约信息列表
     */
    public List<ContractSignInfoDto> getContractSignInfoList(Long contractId);

    /**
     * 删除合同签约列表
     */
    public void deleteContractSignInfoList(Long contractId);

    void updateSignInfoList(List<ContractSignInfoDto> contractSignInfoDtos, Long id);
}
