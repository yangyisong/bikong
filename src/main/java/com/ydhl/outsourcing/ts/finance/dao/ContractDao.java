package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import com.ydhl.outsourcing.ts.finance.enums.StateChangeType;
import com.ydhl.outsourcing.ts.finance.model.Contract;
import com.ydhl.outsourcing.ts.finance.model.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 17:45.
 */
public interface ContractDao extends BaseMapper<Contract> {

    /**
     * 查询商户合同总数
     *
     * @param customerId 客户id
     * @return 客户合同书
     */
    @Select("select count(*) from contract where customer_id = #{customerId}")
    Integer getContractCountByCustomerId(@Param("customerId") Long customerId);

    /**
     * 通过客户id和业务员id获取合同id
     *
     * @param customerId
     * @param userId
     * @return
     */
    @Select("select id from contract where customer_id = #{customerId} and user_id = #{userId}")
    Long getContractIdByCustomerIdAndUserId(@Param("customerId") Long customerId, @Param("userId") Long userId);

    /**
     * 更合同编号查合同
     * @param number
     * @return
     */
    @Select("select * from contract where number = #{number}")
    Contract getContractByNumber(@Param("number") String number);

    /**
     * 判断身份证是否已注册
     *
     * @param idcard
     * @return
     */
    @Select("select * from customer where idcard = #{idcard} or phone = #{phone}")
    List<Customer> getCustomerByIdcardOrPhone(@Param("idcard") String idcard, @Param("phone") String phone);

    /**
     * 通过产品Id获取合同数量
     */
    @Select("select count(id) from contract where product_id=#{productId}")
    public Long getContractNumberByProductId(@Param("productId") Long productId);

    /**
     * 通过来源合同id，合同类型查询待激活的合同
     * @param con
     * @param contractId
     * @return
     */
    @Select("select * from contract where source_id = #{contractId} and type = #{con} and struts = 'DJH'")
    Contract getWaitApprovedContrctBySourceIdAndType(@Param("contractId") Long contractId, @Param("con") ContractType con);

    /**
     * 修改合同的操作状态
     */
    @Update("update contract set state_change_type=#{stateChangeType} where id = #{contractId}")
    public void updateStateChangeType(@Param("contractId") Long contractId,@Param("stateChangeType") StateChangeType stateChangeType);
}
