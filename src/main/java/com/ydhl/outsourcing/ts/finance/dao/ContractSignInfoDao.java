package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ContractSignInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @create 2017-12-18 上午 11:30
 * @description
 */
public interface ContractSignInfoDao extends BaseMapper<ContractSignInfo> {

    @Select("select * from contract_sign_info where contract_id = #{contractId}")
    public List<ContractSignInfo> getContractSignInfoListByContractId(@Param("contractId") Long contractId);
}
