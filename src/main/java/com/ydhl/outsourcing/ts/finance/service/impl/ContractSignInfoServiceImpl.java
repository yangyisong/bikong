package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ContractSignInfoDao;
import com.ydhl.outsourcing.ts.finance.dto.ContractSignInfoDto;
import com.ydhl.outsourcing.ts.finance.model.ContractSignInfo;
import com.ydhl.outsourcing.ts.finance.service.ContractSignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */
@Service
public class ContractSignInfoServiceImpl implements ContractSignInfoService {
    @Autowired
    private ContractSignInfoDao contractSignInfoDao;

    @Override
    public void insertContractSignInfoList(List<ContractSignInfoDto> contractSignInfoDtos, Long contractId) {
        for (ContractSignInfoDto contractSignInfoDto:contractSignInfoDtos) {
            contractSignInfoDao.insert(contractSignInfoDtoToContractSignInfo(contractSignInfoDto,contractId));
        }
    }

    @Override
    public List<ContractSignInfoDto> getContractSignInfoList(Long contractId) {
        List<ContractSignInfo> contractSignInfos = contractSignInfoDao.getContractSignInfoListByContractId(contractId);
        List<ContractSignInfoDto> contractSignInfoDtos = new ArrayList<>();
        for (ContractSignInfo contractSignInfo2:contractSignInfos) {
            contractSignInfoDtos.add(contractSignInfoToContractSignInfoDto(contractSignInfo2));
        }
        return contractSignInfoDtos;
    }

    @Override
    public void deleteContractSignInfoList(Long contractId) {
        List<ContractSignInfo> infoList = contractSignInfoDao.getContractSignInfoListByContractId(contractId);
        for (ContractSignInfo contractSignInfo : infoList) {
            contractSignInfoDao.deleteByPrimaryKey(contractSignInfo);
        }
    }

    @Override
    public void updateSignInfoList(List<ContractSignInfoDto> contractSignInfoDtos, Long id) {
        for (ContractSignInfoDto contractSignInfoDto:contractSignInfoDtos) {
            ContractSignInfo contractSignInfo = contractSignInfoDtoToContractSignInfo(contractSignInfoDto, id);
            contractSignInfoDao.updateByPrimaryKey(contractSignInfo);
        }
    }

    public ContractSignInfo contractSignInfoDtoToContractSignInfo(ContractSignInfoDto contractSignInfoDto, Long contractId){
        ContractSignInfo contractSignInfo = new ContractSignInfo();
        contractSignInfo.setId(contractSignInfoDto.getId());
        contractSignInfo.setCode(contractSignInfoDto.getCode());
        contractSignInfo.setContractId(contractId);
        contractSignInfo.setType(contractSignInfoDto.getType());
        contractSignInfo.setValue(contractSignInfoDto.getValue());
        return contractSignInfo;
    }

    public ContractSignInfoDto contractSignInfoToContractSignInfoDto(ContractSignInfo contractSignInfo){
        ContractSignInfoDto contractSignInfoDto = new ContractSignInfoDto();
        contractSignInfoDto.setId(contractSignInfo.getId());
        contractSignInfoDto.setCode(contractSignInfo.getCode());
        contractSignInfoDto.setContractId(contractSignInfo.getContractId());
        contractSignInfoDto.setType(contractSignInfo.getType());
        contractSignInfoDto.setValue(contractSignInfo.getValue());
        return contractSignInfoDto;
    }
}
