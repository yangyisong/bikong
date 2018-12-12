package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ContractQuitInfoDao;
import com.ydhl.outsourcing.ts.finance.model.ContractQuitInfo;
import com.ydhl.outsourcing.ts.finance.service.ContractQuitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Martins
 * @create 2018/1/31 13:53.
 * @description
 */
@Service
public class ContractQuitInfoServiceImpl implements ContractQuitInfoService{

    @Autowired
    private ContractQuitInfoDao quitInfoDao;

    @Override
    public void insertQuitInfo(ContractQuitInfo quitInfo) {
        quitInfoDao.insert(quitInfo);
    }

    @Override
    public ContractQuitInfo getInfoByContractId(Long applyId) {
        return quitInfoDao.getInfoByContractId(applyId);
    }
}
