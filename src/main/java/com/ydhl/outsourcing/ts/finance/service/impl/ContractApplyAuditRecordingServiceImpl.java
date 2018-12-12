package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ContractApplyAuditRecordingDao;
import com.ydhl.outsourcing.ts.finance.dto.ContractApplyAuditRecordingDto;
import com.ydhl.outsourcing.ts.finance.model.ContractApplyAuditRecording;
import com.ydhl.outsourcing.ts.finance.service.ContractApplyAuditRecordingService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/30.
 */
@Service
public class ContractApplyAuditRecordingServiceImpl implements ContractApplyAuditRecordingService{
    @Autowired
    private ContractApplyAuditRecordingDao contractApplyAuditRecordingDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Long> getApplyIdListByUserId(Long userId, Boolean struts) {
        return contractApplyAuditRecordingDao.getApplyIdListByUserId(userId,struts);
    }

    @Override
    public List<ContractApplyAuditRecordingDto> getContractApplyAuditRecordingDtoListByApplyId(Long applyId) {
        List<ContractApplyAuditRecording> contractApplyAuditRecordings =  contractApplyAuditRecordingDao.getContractApplyAuditRecordingDtoListByApplyId2(applyId);
        List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos = new ArrayList<>();
        for(int i=0;i<contractApplyAuditRecordings.size();i++){
            ContractApplyAuditRecordingDto contractApplyAuditRecordingDto = toDto(contractApplyAuditRecordings.get(i));
            contractApplyAuditRecordingDtos.add(contractApplyAuditRecordingDto);
        }
        return contractApplyAuditRecordingDtos;
    }

    @Override
    public Map<String, Object> getContractApplyAuditRecordingDtoListByApplyId2(Long applyId,Boolean effective) {
        List<ContractApplyAuditRecording> contractApplyAuditRecordings =  contractApplyAuditRecordingDao.getContractApplyAuditRecordingDtoListByApplyId(applyId);
        List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        Boolean flag = false;
        for(int i=0;i<contractApplyAuditRecordings.size();i++){
            ContractApplyAuditRecordingDto contractApplyAuditRecordingDto = toDto(contractApplyAuditRecordings.get(i));
            if(i!=0 && contractApplyAuditRecordingDto.getUserDto().getRoleId()==3){
                flag = true;
            }
            contractApplyAuditRecordingDtos.add(contractApplyAuditRecordingDto);
        }
        map.put("hasManager",flag);
        map.put("contractApplyAuditRecordingDtos",contractApplyAuditRecordingDtos);
        return map;
    }

    @Override
    public Map<String, Object> getContractApplyAuditRecordingDtosByApplyIdList(List<Long> ids) {
        Map<String,Object> map = new HashMap<>();
        List<ContractApplyAuditRecording> contractApplyAuditRecordings =  contractApplyAuditRecordingDao.getContractApplyAuditRecordingDtoListByApplyIdList(ids);
        List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos = new ArrayList<>();
        Boolean flag = false;
        for(int i=0;i<contractApplyAuditRecordings.size();i++){
            ContractApplyAuditRecordingDto contractApplyAuditRecordingDto = toDto(contractApplyAuditRecordings.get(i));
            contractApplyAuditRecordingDtos.add(contractApplyAuditRecordingDto);
            if(i!=0 && contractApplyAuditRecordingDto.getUserDto().getRoleId()==3){
                flag = true;
            }
        }
        map.put("hasManager",flag);
        map.put("contractApplyAuditRecordingDtos",contractApplyAuditRecordingDtos);
        return map;
    }

    /*@Override
    public List<List<ContractApplyAuditRecordingDto>> getContractApplyAuditRecordingDtoListByApplyId3(Long applyId) {
        List<ContractApplyAuditRecording> contractApplyAuditRecordings =  contractApplyAuditRecordingDao.getContractApplyAuditRecordingDtoListByApplyId2(applyId);
        Integer f = contractApplyAuditRecordings.get(0).getNum();
        List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos = new ArrayList<>();
        List<List<ContractApplyAuditRecordingDto>> cARD = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for(ContractApplyAuditRecording contractApplyAuditRecording : contractApplyAuditRecordings){
            ContractApplyAuditRecordingDto contractApplyAuditRecordingDto = toDto(contractApplyAuditRecording);
            if(f==contractApplyAuditRecordingDto.getNum()){
                contractApplyAuditRecordingDtos.add(contractApplyAuditRecordingDto);
            }else{
                cARD.add(contractApplyAuditRecordingDtos);
                f = contractApplyAuditRecordingDto.getNum();
                contractApplyAuditRecordingDtos = new ArrayList<>();
                contractApplyAuditRecordingDtos.add(contractApplyAuditRecordingDto);
            }
        }
        cARD.add(contractApplyAuditRecordingDtos);
        return null;
    }*/

    @Override
    public void addRecording(ContractApplyAuditRecording auditRecording) {
        contractApplyAuditRecordingDao.insert(auditRecording);
    }

    public ContractApplyAuditRecordingDto toDto(ContractApplyAuditRecording contractApplyAuditRecording){
        ContractApplyAuditRecordingDto contractApplyAuditRecordingDto = new ContractApplyAuditRecordingDto();
        contractApplyAuditRecordingDto.setId(contractApplyAuditRecording.getId());
        contractApplyAuditRecordingDto.setApplyId(contractApplyAuditRecording.getApplyId());
        contractApplyAuditRecordingDto.setRemark(contractApplyAuditRecording.getRemark());
        contractApplyAuditRecordingDto.setStruts(contractApplyAuditRecording.getStruts());
        contractApplyAuditRecordingDto.setUserDto(userService.getUserDtoById(contractApplyAuditRecording.getUserId()));
        contractApplyAuditRecordingDto.setCreateTime(contractApplyAuditRecording.getCreateTime());
        contractApplyAuditRecordingDto.setAuditTime(contractApplyAuditRecording.getAuditTime());
        return contractApplyAuditRecordingDto;
    }

    @Override
    public List<Long> getContractApplyAuditRecordingIdListByUserId(Long userId,Boolean struts) {
        List<Long> ids = contractApplyAuditRecordingDao.getContractApplyAuditRecordingIdListByUserId(userId,struts);
        return ids;
    }
}
