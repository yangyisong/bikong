package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/16 14:23.
 */
public interface PersonalIncomeDao extends BaseMapper<PersonalIncome> {

    /**
     * 已付合计
     *
     * @return
     */
    @Select("select sum(income) from personal_income where user_id = #{userId}")
    BigDecimal getPrepaidTotal(Long userId);

    /**
     * 查询合同是否有临退信息 处于未通过状态
     * @param contractId
     * @return
     */
    @Select("select * from personal_income where contract_id = #{contractId} and quit = true and struts = 'A_I'")
    List<PersonalIncome> getQuitListByContractId(Long contractId);

    /**
     * 获取临退之后的未申请的个人收益
     * @param contractId
     * @param quitDate
     * @return
     */
    @Select("select * from personal_income where contract_id = #{contractId} " +
            "and  (settle_start_time <= #{quitDate} and settle_end_time >= #{quitDate} or settle_end_time >= #{quitDate}) and struts = 'N_A'")
    List<PersonalIncome> getAfterQuitDateIncomeList(@Param("contractId") Long contractId, @Param("quitDate") Date quitDate);

    @Update("update personal_income set struts = #{struts} where id = #{id}")
    void updateIncomeStruts(@Param("id") Long id, @Param("struts") ApprovalType struts);

    /*@Select("select * from personal_income where product_id = #{productId} and user_id = #{userId} and approved_id is null " +
            "and settle_end_time < #{todays} and struts='N_A'")*/
    @Select("select * from personal_income where product_id = #{productId} and user_id = #{userId} and approved_id is null " +
            " and struts='N_A' and freeze!=true")
    List<PersonalIncome> getPersonalIncomeListByProductId(@Param("productId") Long productId,@Param("userId") Long userId,@Param("todays") Date todays);

    @Select("select distinct product_id from personal_income")
    List<Long> getIncomeProductIdList();

    @Select("select distinct product_id from personal_income where user_id=#{userId}")
    List<Long> getIncomeProductIdListByUserId(Long userId);

    @Select("select * from personal_income where contract_id = #{contractId} ")
    List<PersonalIncome> getIncomeListByContractId(Long contractId);

    @Update("update personal_income set approved_id = null,struts = 'N_A' where approved_id = #{approvedId}")
    void updateApprovedIdbyApprovedId(Long approvedId);

    @Update("<script> "
            + "update personal_income set approved_id = #{approvedId} where id in "
            + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    void updateApprovedIdByApprovedIdAndIdList(@Param("approvedId") Long approvedId,@Param("ids") List<Long> ids);

    @Select("<script> "
            +"select * from personal_income where id in "
            + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    List<PersonalIncome> getIncomeListByIdList(@Param("ids") List<Long> ids);

    @Update("update personal_income set struts = #{struts} where approved_id = #{approvedId}")
    void updateIncomeStrutsByApprovedIdAndStruts(@Param("approvedId")Long approvedId,@Param("struts") ApprovalType struts);

    @Select("select * from personal_income where contract_id = #{contractId} and struts = 'N_A' and approved_id != null")
    List<PersonalIncome> getPersonIncomeListByContractIdAndApprovedId(@Param("contractId") Long contractId);

    @Select("select count(id) from personal_income where struts = 'W_P'")
    Integer getCountPayWaitIncome();

    @Select("select * from personal_income where contract_id = #{contractId} and settle_end_time > #{quitDate} and struts != 'N_A'")
    List<PersonalIncome> getIncomeListByApplyIdAndQuitDate(@Param("contractId") Long contractId,@Param("quitDate") Date quitDate);

    @Select("select * from personal_income where contract_id = #{contractId} and settle_end_time > #{quitDate} and struts = 'N_A'")
    List<PersonalIncome> getAfterQuitDateNAIncomeList(@Param("contractId") Long contractId,@Param("quitDate") Date quitDate);

    @Select("select * from personal_income where contract_id = #{contractId} and freeze = true")
    List<PersonalIncome> getIncomeListByFreezeByContractId(Long contractId);

    @Select("select distinct apply_id from personal_income where approved_id = #{approvedId}")
    Long getApplyIdByApprovedId(Long approvedId);
}
