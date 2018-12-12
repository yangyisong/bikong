package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ContractConfirmInfoDao;
import com.ydhl.outsourcing.ts.finance.model.ContractConfirmInfo;
import com.ydhl.outsourcing.ts.finance.service.ContractConfirmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell on 2018/2/6.
 */
@Service
public class ContractConfirmInfoServiceImpl implements ContractConfirmInfoService {
    @Autowired
    private ContractConfirmInfoDao contractConfirmInfoDao;

    @Override
    public ContractConfirmInfo getContractConfirmInfoListByContractIdAndRoleId(Long contractId, Long roleId) {
        return contractConfirmInfoDao.getConfirmInfoByContractIdAndRoleId(contractId,roleId);
    }

    @Override
    public void updateContractConfirmInfo(Long id,Boolean infoConfirm,Boolean receiveConfirm,Boolean amountConfirm,Boolean ifPrint) {
        contractConfirmInfoDao.updateContractConfirmInfo(id, infoConfirm, receiveConfirm, amountConfirm, ifPrint);
    }

    @Override
    public void updateInfoAmountConfirm(Boolean infoConfirm, Boolean amountConfirm, Long contractId, Long roleId) {
        contractConfirmInfoDao.updateInfoAmountConfirm(infoConfirm,amountConfirm,contractId,roleId);
    }
}
