package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.model.ContractConfirmInfo;

/**
 * Created by dell on 2018/2/6.
 */
public interface ContractConfirmInfoService {
    /**
     * 通过合同Id和角色Id获取合同确认信息列表
     */
    public ContractConfirmInfo getContractConfirmInfoListByContractIdAndRoleId(Long contractId,Long roleId);

    /**
     * 修改合同信息对应状态
     */
    public void updateContractConfirmInfo(Long id,Boolean infoConfirm,Boolean receiveConfirm,Boolean amountConfirm,Boolean ifPrint);

    void updateInfoAmountConfirm( Boolean infoConfirm,Boolean amountConfirm, Long contractId,Long roleId);
}
