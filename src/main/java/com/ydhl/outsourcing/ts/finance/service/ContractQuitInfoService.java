package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.model.ContractQuitInfo;

/**
 * @author Martins
 * @create 2018/1/31 13:40.
 */
public interface ContractQuitInfoService {

    /**
     * 新增退款信息
     * @param quitInfo
     */
    void insertQuitInfo(ContractQuitInfo quitInfo);

    /**
     * 通过合同id查询退款信息
     *
     * @param contractId
     * @return
     */
    ContractQuitInfo getInfoByContractId(Long contractId);
}
