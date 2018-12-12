package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.ApprovedApplyDto;

/**
 * Created by dell on 2018/2/5.
 */
public interface ApprovedApplyservice {

    /**
     * 通过结算Id获取申请
     */
    public ApprovedApplyDto getApprovedApplyDtoById(Long id);

    /**
     * 通过申请Id修改申请状态
     */
    public void updateApplyStruts(Long id,Boolean struts,String reason);
}
