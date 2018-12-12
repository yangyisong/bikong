package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.ContractApplyAuditRecordingDto;
import com.ydhl.outsourcing.ts.finance.model.ContractApplyAuditRecording;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/30.
 */
public interface ContractApplyAuditRecordingService {
    /**
     * 通过审批人Id获取审批记录
     */
    public List<Long> getApplyIdListByUserId(Long userId,Boolean struts);

    /**
     * 通过审批Id获取审批列表
     */
    public List<ContractApplyAuditRecordingDto> getContractApplyAuditRecordingDtoListByApplyId(Long applyId);

    /**
     * 通过审批Id获取审批列表并判定是否有业务经理审批并且有效的审批记录
     */
    public Map<String,Object> getContractApplyAuditRecordingDtoListByApplyId2(Long applyId,Boolean effective);

    /**
     * 通过审批Id获取审批列表
     */
    /*public List<List<ContractApplyAuditRecordingDto>> getContractApplyAuditRecordingDtoListByApplyId3(Long applyId);*/

    /**
     * 插入审批记录
     *
     * @param auditRecording
     */
    void addRecording(ContractApplyAuditRecording auditRecording);

    /**
     * 通过审批人Id获取审批记录
     */
    public List<Long> getContractApplyAuditRecordingIdListByUserId(Long userId,Boolean struts);

    /**
     * 通过申请Id列表获取审批列表
     */
    public Map<String, Object> getContractApplyAuditRecordingDtosByApplyIdList(List<Long> ids);
}
