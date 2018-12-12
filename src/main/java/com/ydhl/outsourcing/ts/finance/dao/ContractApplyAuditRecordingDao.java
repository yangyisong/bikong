package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ContractApplyAuditRecording;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2018/1/30.
 */
public interface ContractApplyAuditRecordingDao  extends BaseMapper<ContractApplyAuditRecording> {
    @Select("select apply_id from contract_apply_audit_recording where user_id = #{userId} and struts = #{struts}")
    public List<Long> getApplyIdListByUserId(@Param("userId") Long userId,@Param("struts") Boolean struts);

    @Select("select * from contract_apply_audit_recording where apply_id = #{applyId}")
    public List<ContractApplyAuditRecording> getContractApplyAuditRecordingDtoListByApplyId(@Param("applyId") Long applyId);

    @Select("select * from contract_apply_audit_recording where apply_id = #{applyId}")
    public List<ContractApplyAuditRecording> getContractApplyAuditRecordingDtoListByApplyId2(@Param("applyId") Long applyId);

    @Select("select distinct apply_id from contract_apply_audit_recording where user_id = #{userId} and struts=#{struts}")
    List<Long> getContractApplyAuditRecordingIdListByUserId(@Param("userId") Long userId,@Param("struts") Boolean struts);

    /**
     * 通过申请Id列表查询审批记录列表
     */
    @Select("<script> "
            + "SELECT * from contract_apply_audit_recording where apply_id in "
            + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    public List<ContractApplyAuditRecording> getContractApplyAuditRecordingDtoListByApplyIdList(@Param(value = "ids") List<Long> ids);

}
