package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ContractQuitInfo;
import org.apache.ibatis.annotations.Select;

/**
 * @author Martins
 * @create 2018/1/30 16:28.
 */
public interface ContractQuitInfoDao extends BaseMapper<ContractQuitInfo> {

    /**
     * 查询合同退款信息
     * @return
     */
    @Select("select * from contract_quit_info where apply_id = #{applyId}")
    ContractQuitInfo getInfoByContractId(Long applyId);
}
