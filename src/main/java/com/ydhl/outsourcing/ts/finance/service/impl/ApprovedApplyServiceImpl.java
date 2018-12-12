package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ApprovedApplyDao;
import com.ydhl.outsourcing.ts.finance.dto.ApprovedApplyDto;
import com.ydhl.outsourcing.ts.finance.model.ApprovedApply;
import com.ydhl.outsourcing.ts.finance.service.ApprovedApplyservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2018/2/5.
 */
@Service
public class ApprovedApplyServiceImpl implements ApprovedApplyservice {
    @Autowired
    private ApprovedApplyDao approvedApplyDao;

    @Override
    public ApprovedApplyDto getApprovedApplyDtoById(Long id) {
        ApprovedApply approvedApply = approvedApplyDao.selectByPrimaryKey(id);
        return toDto(approvedApply);
    }

    public ApprovedApplyDto toDto(ApprovedApply approvedApply){
        ApprovedApplyDto approvedApplyDto = new ApprovedApplyDto();
        approvedApplyDto.setId(approvedApply.getId());
        approvedApplyDto.setApprovedId(approvedApply.getApprovedId());
        approvedApplyDto.setReason(approvedApply.getReason());
        approvedApplyDto.setStruts(approvedApply.getStruts());
        approvedApplyDto.setUserId(approvedApply.getUserId());
        return approvedApplyDto;
    }

    @Override
    public void updateApplyStruts(Long id, Boolean struts,String reason) {
        approvedApplyDao.updateApplyStruts(id,struts,reason);
    }
}
