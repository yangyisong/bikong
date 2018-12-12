package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractApplyQueryDto;
import com.ydhl.outsourcing.ts.finance.model.ContractApply;
import tk.mybatis.mapper.entity.Example;


/**
 * 申请查询对象
 * Created by dell on 2018/1/12.
 */
public class ContractApplyExample {

    public static Example getContractApplyExample(ContractApplyQueryDto contractApplyQueryDto,PageModel pageModel) {
        if(pageModel!=null) {
            PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        }
        Example example = new Example(ContractApply.class);
        Example.Criteria criteria = example.createCriteria();
        if(contractApplyQueryDto.getApplyIds()!=null && contractApplyQueryDto.getApplyIds().size()!=0){
            criteria.orIn("id",contractApplyQueryDto.getApplyIds());
        }
        if (contractApplyQueryDto.getApprovedStruts()!=null) {
            criteria.andEqualTo("approvedStruts", contractApplyQueryDto.getApprovedStruts());
        }
        if(contractApplyQueryDto.getApplyStruts()!=null){
            criteria.andEqualTo("applyStruts",contractApplyQueryDto.getApplyStruts());
        }
        if(contractApplyQueryDto.getAuditStruts()!=null){
            criteria.andEqualTo("auditStruts",contractApplyQueryDto.getAuditStruts());
        }
        if(contractApplyQueryDto.getUserIds()!=null && contractApplyQueryDto.getUserIds().size()!=0){
            criteria.andIn("userId",contractApplyQueryDto.getUserIds());
        }
        if (contractApplyQueryDto.getEffective() != null) {
            criteria.andEqualTo("effective", contractApplyQueryDto.getEffective());
        }
        if (contractApplyQueryDto.getSupportManagerAudit() != null) {
            criteria.andEqualTo("supportManagerAudit", contractApplyQueryDto.getSupportManagerAudit());
        }
        example.orderBy("createTime").desc();
        return example;
    }
}
