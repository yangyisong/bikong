package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.ContractApplyDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractApplyQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import com.ydhl.outsourcing.ts.finance.model.ContractApply;

import java.util.List;
import java.util.Map;

/**
 * 合同申请Service
 */
public interface ContractApplyService {
    /**
     * 获取分页合同申请列表
     */
    Map<Object,Object> queryContractDtoListPage(PageModel pageModel, ContractApplyQueryDto contractApplyQueryDto);

    PageInfo<ContractApplyDto> queryContractPage(ContractApplyQueryDto contractApplyQueryDto);

    /**
     * 通过申请Id获取合同Id列表
     */
    public List<Long> getContractIdListByApplyId(ContractApplyQueryDto contractApplyQueryDto);

    /**
     * 通过合同Id获取申请Id
     */
    public List<ContractApply> getApplyIdByContractId(Long contractId);

    /**
     * 新增合同申请记录
     *
     * @param contractApply
     */
    Long addContractApply(ContractApply contractApply);

    /**
     * 通过合同id 查询审批
     *
     * @param id
     * @return
     */
    List<ContractApply> getApplyListByContractId(Long id, Boolean struts);

    /**
     * 根据合同id查询待审批合同
     *
     * @param contractId
     * @return
     */
    ContractApply getWaitApplyById(Long contractId);

    /**
     * 更改状态
     * @param contractApply
     */
    void updateApply(ContractApply contractApply);

    /**
     * 根据合同id，类型查询待审批的合同
     *
     * @param contractId
     * @param type
     * @return
     */
    ContractApply getApplyByContractIdAndType(Long contractId, ContractType type);

    /**
     * 根据申请Id获取申请
     */
    ContractApplyDto getContractApplyDtoById(Long applyId);

    /**
     * 根据合同Id和类型获取所有申请Id
     */
    List<ContractApplyDto> getContractApplysByContractIdAndTypeAndAuditStruts(Long contractId,ContractType type,AuditStruts auditStruts);

    ContractApply getWaitApplyByContractId(Long id, ContractType contractType);

    ContractApply getApplyById(Long applyId);

    ContractApply getWaitByContractId(Long contractId);

}
