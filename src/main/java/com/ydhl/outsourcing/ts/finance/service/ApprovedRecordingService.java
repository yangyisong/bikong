package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.ApprovedRecordingDto;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/28.
 */
public interface ApprovedRecordingService {
    /**
     * 获取所有申请的审批记录列表
     */
    public List<ApprovedRecordingDto> getApprovedRecordingDtoList();

    /**
     * 插入审批记录
     */
    public void insertApprovedRecordingDto(ApprovedRecordingDto approvedRecordingDto);

    /**
     * 通过申请Id获取有效记录中的num
     */
    public Integer getNumByApprovedId(Long approvedId);

    /**
     * 通过审批人Id获取他已审批的有效的申请的id列表
     */
    public List<Long> getEffectiveApprovedIdListByUserId(Long userId);

    /**
     * 获取有效的审批的记录
     */
    public Map<String,Object> getEffectiveApprovedRecordingDtoListByApprovedId(Long approvedId, Boolean effective);

    /**
     * 获取无效的审批的记录
     */
    public List<List<ApprovedRecordingDto>> getNotEffectiveApprovedRecordingDtoListByApprovedId(Long approvedId, Boolean effective);

    /**
     * 设置所有的审批记录为无效
     */
    void updateApprovedRecordEffectiveByApprovedId(Long approvedId);

    /**
     * 删除审批记录
     */
    void deleteApprovedRecordingByApprovedId(Long approvedId);

}
