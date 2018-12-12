package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.PersonalIncomeDto;
import com.ydhl.outsourcing.ts.finance.dto.query.PersonalIncomeQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.enums.IncomeType;
import com.ydhl.outsourcing.ts.finance.model.Contract;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Martins
 * @create 2018/1/16 14:23.
 * @description
 */
public interface IncomeService {

    /**
     * 收益列表
     *
     * @param personalIncomeQueryDto 条件dto
     * @param pageModel              分页模型
     * @return 收益列表
     */
     Map<String,Object> getPersonalIncomePage(PersonalIncomeQueryDto personalIncomeQueryDto, PageModel pageModel);

    /**
     * 获取所有收益合计
     *
     * @return
     */
    BigDecimal getAllIncomeTotal(Long userId);

    /**
     * 获取所有结息类型
     *
     * @return
     */
    Map<Object, IncomeType> getAllIncomeType();

    /**
     * 获取所有状态
     *
     * @return
     */
    Map<Object, ApprovalType> getAllApprovalType();

    /**
     * 新增收益
     *
     * @param incomeDto
     */
    void insertIncomeDtoList(List<PersonalIncomeDto> incomeDto, Long applyId, String userName);

    /**
     * 查询合同是否有临退信息 处于未通过状态
     * @return
     */
    List<PersonalIncome> getQuitListByContractId(Long id);

    void insertIncome(List<PersonalIncome> incomeList, Contract contract, Long applyId);

    /**
     * 查询退出时间之后的个人收益
     * @param contractId
     * @param quitDate
     * @return
     */
    List<PersonalIncome> getAfterQuitDateIncomeList(Long contractId, Date quitDate);

    /**
     * 修改收益状态
     * @param income
     */
    void updateIncomeStruts(PersonalIncome income);

    /**
     * 修改收益状态
     */
    void updateIncomeStruts(Long id,ApprovalType struts);

    PageInfo<PersonalIncomeDto> getIncomePage(PersonalIncomeQueryDto personalIncomeQueryDto);

    /**
     * 获取某个产品未申请的收益列表
     */
    public Map<String,Object> getPersonalIncomeListByProductId(Long productId,Long userId);

    /**
     * 获取所有的产品ID
     */
    List<Long> getIncomeProductIdList(Long userId);

    /**
     * 根据合同Id获取该合同所有收益列表
     */
    List<PersonalIncome> getIncomeListByContractId(Long contractId);

    /**
     * 通过申请Id修改收益的申请Id为空
     */
    void updateApprovedIdbyApprovedId(Long approvedId);

    /**
     * 设置收益列表的申请Id
     */
    void updateApprovedIdByApprovedIdAndIdList(Long approvedId,List<Long> ids);

    /**
     * 通过收益Id列表获取收益
     */
    Map<String,Object> getIncomeListByIdList(List<Long> ids);

    /**
     * 通过申请Id修改收益的状态
     */
    void updateIncomeStrutsByApprovedIdAndStruts(Long approvedId,ApprovalType struts);

    List<PersonalIncome> getPersonIncomeListByContractIdAndApprovedId(Long id);

    List<PersonalIncome> getIncomeListByApplyIdAndQuitDate(Long contractId, Date quitDate);

    List<PersonalIncome> getAfterQuitDateNAIncomeList(Long contractId, Date quitDate);

    List<PersonalIncome> getIncomeListByFreezeByContractId(Long contractId);

    void updateIncomeList(List<PersonalIncome> incomes, Boolean boo);

    /**
     * 通过结算申请Id获取签约申请Id
     */
    Long getApplyIdByApprovedId(Long approvedId);

    Map<String,Object> getPersonalIncomePageByProductId(Long productId,Long userId, PageModel pageModel);
}
