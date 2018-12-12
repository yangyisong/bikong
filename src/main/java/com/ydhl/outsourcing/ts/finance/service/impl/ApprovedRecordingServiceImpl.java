package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ApprovedRecordingDao;
import com.ydhl.outsourcing.ts.finance.dto.ApprovedRecordingDto;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.model.ApprovedRecording;
import com.ydhl.outsourcing.ts.finance.service.ApprovedRecordingService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/28.
 */
@Service
public class ApprovedRecordingServiceImpl implements ApprovedRecordingService {
    @Autowired
    private ApprovedRecordingDao approvedRecordingDao;

    @Autowired
    private UserService userService;

    @Override
    public List<ApprovedRecordingDto> getApprovedRecordingDtoList() {
        List<ApprovedRecording> approvedRecordings = approvedRecordingDao.selectAll();
        List<ApprovedRecordingDto> approvedRecordingDtos = new ArrayList<>();
        for(ApprovedRecording approvedRecording : approvedRecordings){
            ApprovedRecordingDto approvedRecordingDto = approvedRecordingToApprovedRecordingDto(approvedRecording);
            approvedRecordingDtos.add(approvedRecordingDto);
        }
        return approvedRecordingDtos;
    }

    @Override
    public void insertApprovedRecordingDto(ApprovedRecordingDto approvedRecordingDto) {
        approvedRecordingDao.insert(approvedRecordingDtoToApprovedRecording(approvedRecordingDto));
    }

    @Override
    public Integer getNumByApprovedId(Long approvedId) {
        return approvedRecordingDao.getNumByApprovedId(approvedId);
    }

    @Override
    public List<Long> getEffectiveApprovedIdListByUserId(Long userId) {
        return approvedRecordingDao.getEffectiveApprovedIdListByUserId(userId);
    }

    @Override
    public Map<String,Object> getEffectiveApprovedRecordingDtoListByApprovedId(Long approvedId, Boolean effective) {
        Map<String,Object> map = new HashMap<>();
        List<ApprovedRecording> approvedRecordings = approvedRecordingDao.getApprovedRecordingDtoListByApprovedIdAndEffective(approvedId,true);
        BigDecimal speed = new BigDecimal(16.66).multiply(new BigDecimal(approvedRecordings.size()-1));
        //List<ApprovedRecordingDto> approvedRecordingDtos = new ArrayList<>();
        List<ApprovedRecordingDto> approvedRecordingDtos = getAllRecords();
        for(ApprovedRecording approvedRecording:approvedRecordings){
            ApprovedRecordingDto approvedRecordingDto = approvedRecordingToApprovedRecordingDto(approvedRecording);
            for(int i=0;i<approvedRecordingDtos.size();i++){
                if(approvedRecordingDtos.get(i).getRoleId()==approvedRecordingDto.getRoleId()){
                    if(approvedRecordingDto.getRoleId()==2){
                        approvedRecordingDto.setTitle("提交申请");
                    }else{
                        if(approvedRecordingDto.getStruts()){
                            approvedRecordingDto.setTitle("审批通过");
                        }else{
                            approvedRecordingDto.setTitle("审批未通过");
                        }
                    }
                    approvedRecordingDtos.set(i,approvedRecordingDto);
                }
            }
            //approvedRecordingDtos.add(approvedRecordingDto);
        }
        map.put("speed",speed.setScale(0,BigDecimal.ROUND_HALF_UP));
        map.put("approvedRecordingDtos",approvedRecordingDtos);
        return map;
    }

    @Override
    public List<List<ApprovedRecordingDto>> getNotEffectiveApprovedRecordingDtoListByApprovedId(Long approvedId, Boolean effective) {
        List<ApprovedRecording> approvedRecordings =  approvedRecordingDao.getApprovedRecordingDtoListByApprovedIdAndEffective(approvedId,false);
        List<List<ApprovedRecordingDto>> ars = new ArrayList<>();
        List<ApprovedRecordingDto> ar0 = new ArrayList<>();
        Integer num = 0;
        if(approvedRecordings!=null && approvedRecordings.size()!=0){
            num = approvedRecordings.get(0).getNum();
        }else{
            return null;
        }
        for(int i=0;i<approvedRecordings.size();i++){
            ApprovedRecordingDto approvedRecordingDto = approvedRecordingToApprovedRecordingDto(approvedRecordings.get(i));
            if(approvedRecordings.get(i).getNum()!=num){
                if(i==approvedRecordings.size()-1){
                    ars.add(ar0);
                    ar0 = new ArrayList<>();
                    ar0.add(approvedRecordingDto);
                    ars.add(ar0);
                    return ars;
                }
                ars.add(ar0);
                num = approvedRecordingDto.getNum();
                ar0 = new ArrayList<>();
                ar0.add(approvedRecordingDto);
            }else{
                ar0.add(approvedRecordingDto);
                if(i==approvedRecordings.size()-1){
                    ars.add(ar0);
                    return ars;
                }
            }
        }
        return ars;
    }

    @Override
    public void updateApprovedRecordEffectiveByApprovedId(Long approvedId) {
        approvedRecordingDao.updateApprovedRecordEffectiveByApprovedId(approvedId);
    }

    @Override
    public void deleteApprovedRecordingByApprovedId(Long approvedId) {
        approvedRecordingDao.deleteApprovedRecordingByApprovedId(approvedId);
    }

    public ApprovedRecording approvedRecordingDtoToApprovedRecording(ApprovedRecordingDto approvedRecordingDto){
        ApprovedRecording approvedRecording = new ApprovedRecording();
        approvedRecording.setApprovedId(approvedRecordingDto.getApprovedId());
        approvedRecording.setId(approvedRecordingDto.getId());
        approvedRecording.setRemark(approvedRecordingDto.getRemark());
        approvedRecording.setStruts(approvedRecordingDto.getStruts());
        approvedRecording.setUserId(approvedRecordingDto.getUserId());
        approvedRecording.setEffective(approvedRecordingDto.getEffective());
        approvedRecording.setNum(approvedRecordingDto.getNum());
        approvedRecording.setCreateTime(approvedRecordingDto.getCreateTime());
        return approvedRecording;
    }

    public ApprovedRecordingDto approvedRecordingToApprovedRecordingDto(ApprovedRecording approvedRecording){
        ApprovedRecordingDto approvedRecordingDto = new ApprovedRecordingDto();
        approvedRecording.setApprovedId(approvedRecordingDto.getApprovedId());
        approvedRecordingDto.setId(approvedRecording.getId());
        approvedRecordingDto.setRemark(approvedRecording.getRemark());
        approvedRecordingDto.setStruts(approvedRecording.getStruts());
        approvedRecordingDto.setUserId(approvedRecording.getUserId());
        UserDto userDto = userService.getUserDtoById(approvedRecording.getUserId());
        approvedRecordingDto.setUserName(userDto.getRealname());
        approvedRecordingDto.setRoleId(userDto.getRoleId());
        approvedRecordingDto.setRoleName(userDto.getRole());
        approvedRecordingDto.setEffective(approvedRecording.getEffective());
        approvedRecordingDto.setNum(approvedRecording.getNum());
        approvedRecordingDto.setCreateTime(approvedRecording.getCreateTime());
        approvedRecordingDto.setCreateTime(approvedRecording.getCreateTime());
        return approvedRecordingDto;
    }

    public List<ApprovedRecordingDto> getAllRecords(){
        List<ApprovedRecordingDto> approvedRecordingDtos = new ArrayList<>();
        Long roleId = 2l;
        while(roleId<=8){
            ApprovedRecordingDto approvedRecordingDto = new ApprovedRecordingDto();
            approvedRecordingDto.setRoleId(roleId);
            if(roleId==2){
                approvedRecordingDto.setTitle("业务员");
            }else if(roleId==3){
                approvedRecordingDto.setTitle("业务经理审批");
            }else if(roleId==4){
                approvedRecordingDto.setTitle("出纳审批");
            }else if(roleId==5){
                approvedRecordingDto.setTitle("财务审批");
            }else if(roleId==6){
                approvedRecordingDto.setTitle("子公司BOSS审批");
            }else if(roleId==7){
                approvedRecordingDto.setTitle("集团财务审批");
            }else if(roleId==8){
                approvedRecordingDto.setTitle("集团总监审批");
            }
            approvedRecordingDtos.add(approvedRecordingDto);
            roleId++;
        }
        return approvedRecordingDtos;
    }
}
