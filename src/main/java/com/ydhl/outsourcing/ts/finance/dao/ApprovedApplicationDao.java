package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.model.ApprovedApplication;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/16 20:38.
 */
public interface ApprovedApplicationDao extends BaseMapper<ApprovedApplication> {

    /**
     * 通过合同id用户id查询审核中的申请
     *
     * @param userId
     * @return
     */
    @Select("select * from approved_application where user_id = #{userId} and struts = 'BREV'")
    List<ApprovedApplication> getApprovedApplicationByContractIdAndUserId( @Param("userId") Long userId);

    /**
     * 查询审核通过的申请
     * @param contractId
     * @return
     */
    @Select("select * from approved_application where contract_id = #{contractId} and struts = 'CTHR'")
    List<ApprovedApplication> getCtherApplicationByContractId(@Param("contractId") Long contractId);

    /**
     * 通过审批人获取合同Id列表
     */
    @Select("select distinct contract_id from approved_application where user_id = #{userId}")
    List<Long> getContractIdByUserId(@Param("userId") Long userId);

    /**
     * 修改结算状态值
     */
    @Update("update approved_application set approved_struts=#{approvedStruts} where id=#{approvedId}")
    public void updateApprovedApplicationApprovedStruts(@Param("approvedId") Long approvedId,@Param("approvedStruts") Integer approvedStruts);

    /**
     * 修改结算状态和状态值
     */
    @Update("update approved_application set struts=#{struts},approved_struts=#{approvedStruts},reason=#{reason} where id=#{approvedId}")
    public void updateApprovedApplicationStrutsAndApprovedStruts(@Param("approvedId") Long approvedId, @Param("struts") ApplicationStruts struts, @Param("approvedStruts") Integer approvedStruts, @Param("reason") String reason);

    @Select("select * from approved_application where id = #{approvedId}")
    ApprovedApplication getApprovedApplicationById(@Param("approvedId") Long approvedId);

    @Insert("insert into approved_income(approved_id,income_id) values(#{approvedId},#{incomeId})")
    void insertAprrovedIdAndIncomeId(@Param("approvedId") Long approvedId,@Param("incomeId") Long incomeId);

    @Select("select income_id from approved_income where approved_id=#{approvedId}")
    List<Long> getIncomeIdListByApprovedId(Long approvedId);

    /**
     * 删除申请
     */
    @Delete("delete from approved_application where id=#{id}")
    void deleteApprovedById(Long id);

    /**
     * 删除中间表信息
     */
    @Delete("delete from approved_income where approved_id=#{approvedId}")
    void deleteApprovedIncomeInfo(Long approvedId);

    @Select("select count(id) from approved_application where approved_struts = #{id} and struts = #{brev}")
    Integer getCountApprovByUserId(@Param("id") Long id, @Param("brev") ApplicationStruts brev);
}
