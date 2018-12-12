package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ContractConfirmInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Martins
 * @create 2018/2/6 11:14.
 */
public interface ContractConfirmInfoDao extends BaseMapper<ContractConfirmInfo> {

    @Select("select * from contract_cofirm_info where contract_id = #{contractId} and role_id = #{roleId}")
    ContractConfirmInfo getConfirmInfoByContractIdAndRoleId(@Param("contractId") Long contractId, @Param("roleId") Long roleId);

    @Update("update contract_cofirm_info set info_confirm=#{infoConfirm},receive_confirm=#{receiveConfirm},amount_confirm=#{amountConfirm},if_print=#{ifPrint} where id=#{id}")
    void updateContractConfirmInfo(@Param("id") Long id,@Param("infoConfirm") Boolean infoConfirm,@Param("receiveConfirm") Boolean receiveConfirm,@Param("amountConfirm") Boolean amountConfirm,@Param("ifPrint") Boolean ifPrint);

    @Select("select * from contract_cofirm_info where contract_id = #{contractId}")
    List<ContractConfirmInfo> getConfirmInfoByContractId(Long contractId);

    @Update("update contract_cofirm_info set info_confirm=#{infoConfirm},amount_confirm=#{amountConfirm} where contract_id = #{contractId} and role_id = #{roleId}")
    void updateInfoAmountConfirm(@Param("infoConfirm") Boolean infoConfirm, @Param("amountConfirm") Boolean amountConfirm,@Param("contractId") Long contractId, @Param("roleId") Long roleId);
}
