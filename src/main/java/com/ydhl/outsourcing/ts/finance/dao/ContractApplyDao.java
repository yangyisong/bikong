package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import com.ydhl.outsourcing.ts.finance.model.ContractApply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ContractApplyDao extends BaseMapper<ContractApply> {
    @Select("select * from contract_apply where contract_id = #{contractId}")
    public List<ContractApply> getApplyIdByContractId(@Param("contractId") Long contractId);

    /**
     * 查询需要业务经理审批的审批申请
     *
     * @param id
     * @return
     */
    @Select("select * from contract_apply where id = #{id} and approved_struts in (1,2,3)")
    List<ContractApply> getContractListNeedManager(Long id);

    /**
     * 查询不需要业务经理审批的审批申请
     *
     * @param id
     * @return
     */
    @Select("select * from contract_apply where id = #{id} and approved_struts in (2,3)")
    List<ContractApply> getContractList(Long id);

    /**
     * 查询待审批的申请合同
     *
     * @return
     */
    @Select("select * from contract_apply where id = #{applyId} and audit_struts = 'WAIT'")
    ContractApply getWaitApplyById(Long applyId);

    /**
     * 查询待审批的申请合同
     *
     * @return
     */
    @Select("select * from contract_apply where contract_id = #{contractId} and audit_struts = 'WAIT'")
    ContractApply getWaitByContractId(@Param("contractId") Long contractId);

    /**
     * 根据合同id，类型查询待审批的合同
     *
     * @param contractId
     * @param type
     * @return
     */
    @Select("select * from contract_apply where contract_id = #{contractId} and type = #{type} and audit_struts = 'WAIT'")
    ContractApply getApplyByContractIdAndType(Long contractId, ContractType type);

    @Select("select * from contract_apply where contract_id = #{contractId} and type = #{type} and audit_struts=#{auditStruts}")
    List<ContractApply> getContractApplysByContractIdAndTypeAndAuditStruts(@Param("contractId") Long contractId, @Param("type") ContractType type,@Param("auditStruts") AuditStruts auditStruts);

    @Select("select * from contract_apply where contract_id = #{contractId} and type = #{type}")
    List<ContractApply> getContractApplysByContractIdAndType(@Param("contractId") Long contractId, @Param("type") ContractType type);

    @Select("select * from contract_apply where id = #{id}")
    ContractApply getApplyId(@Param("id") Long id);

    @Select("select * from contract_apply where type = #{contractType} and contract_id = #{id} and (audit_struts = 'WAIT' or apply_struts = 'CTHR') ")
    ContractApply getWaitApplyByContractId(@Param("id") Long id, @Param("contractType") ContractType contractType );

    @Select("select count(id) from contract_apply where user_id = #{id} and type = #{adv} and apply_struts = #{struts}")
    Integer getCountApprovByTypeAndUserId(@Param("id") Long id, @Param("adv") ContractType adv, @Param("struts") ApplicationStruts struts);

    @Select("select count(id) from contract_apply where user_id = #{id} and apply_struts = #{struts}")
    Integer getCountArejByTypeAndUserId(@Param("id") Long id, @Param("struts") ApplicationStruts struts);

    @Select("select count(id) from contract_apply where approved_struts = #{id} and type = #{adv} and apply_struts = 'BREV'")
    Integer getCountWaitApprovByUserId(@Param("id") Integer id, @Param("adv") ContractType adv);
}
