package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dao.ContractApplyDao;
import com.ydhl.outsourcing.ts.finance.dto.ContractApplyAuditRecordingDto;
import com.ydhl.outsourcing.ts.finance.dto.ContractApplyDto;
import com.ydhl.outsourcing.ts.finance.dto.ContractDto;
import com.ydhl.outsourcing.ts.finance.dto.LoginSecurityDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractApplyQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.ContractType;
import com.ydhl.outsourcing.ts.finance.example.ContractApplyExample;
import com.ydhl.outsourcing.ts.finance.model.ContractApply;
import com.ydhl.outsourcing.ts.finance.service.ContractApplyAuditRecordingService;
import com.ydhl.outsourcing.ts.finance.service.ContractApplyService;
import com.ydhl.outsourcing.ts.finance.service.ContractService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/30.
 */
@Service
public class ContractApplyServiceImpl implements ContractApplyService {
    @Autowired
    private ContractApplyDao contractApplyDao;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractApplyAuditRecordingService contractApplyAuditRecordingService;

    @Autowired
    private UserService userService;

    @Override
    public List<Long> getContractIdListByApplyId(ContractApplyQueryDto contractApplyQueryDto) {
        Example example = ContractApplyExample.getContractApplyExample(contractApplyQueryDto,null);
        List<ContractApply> contractApplies = contractApplyDao.selectByExample(example);
        List<Long> contractIds = new ArrayList<>();
        for(ContractApply contractApply : contractApplies){
            contractIds.add(contractApply.getContractId());
        }
        return contractIds;
    }

    @Override
    public List<ContractApply> getApplyIdByContractId(Long contractId) {
        return contractApplyDao.getApplyIdByContractId(contractId);
    }

    @Override
    public Long addContractApply(ContractApply contractApply) {
        contractApplyDao.insert(contractApply);
        return contractApply.getId();
    }

    @Override
    public List<ContractApply> getApplyListByContractId(Long id, Boolean struts) {
        List<ContractApply> applyList ;
        if (struts) {
            applyList = contractApplyDao.getContractListNeedManager(id);
        }else {
            applyList = contractApplyDao.getContractList(id);
        }
        return applyList;
    }

    @Override
    public ContractApply getWaitApplyById(Long applyId) {
        return contractApplyDao.getWaitApplyById(applyId);
    }

    @Override
    public void updateApply(ContractApply contractApply) {
        contractApplyDao.updateByPrimaryKey(contractApply);
    }

    @Override
    public ContractApply getApplyByContractIdAndType(Long contractId, ContractType type) {
        return contractApplyDao.getApplyByContractIdAndType(contractId, type);
    }

    @Override
    public Map<Object, Object> queryContractDtoListPage(PageModel pageModel, ContractApplyQueryDto contractApplyQueryDto) {
        Map<Object, Object> result = new HashMap<>();
        contractApplyQueryDto.setEffective(true);
        AuditStruts auditStruts = contractApplyQueryDto.getAuditStruts();
        //全部
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        if(contractApplyQueryDto.getFlag()==0){
            if(roleId.equals(2L)){//业务员 所有他提交的审批通过的
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractApplyQueryDto.setUserIds(userIds);
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(3L)){//业务经理 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(4L)){//出纳 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(5L)){//财务 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(6L)){//子公司Boss 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }

        }else if(contractApplyQueryDto.getFlag()==1){//我的申请
            if(roleId.equals(2L)){//业务员 业务员自己提交的申请
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractApplyQueryDto.setUserIds(userIds);
            }else if(roleId.equals(3L)){//业务经理 业务经理提交的申请
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractApplyQueryDto.setUserIds(userIds);
            }else{
                result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                result.put("totalAmount", new BigDecimal(0));
                return result;
            }
        }else if(contractApplyQueryDto.getFlag()==2){//我的审批
            if(roleId.equals(3L)){//业务经理：需要业务经理审批的和业务经理已经审批的
                contractApplyQueryDto.setSupportManagerAudit(true);
                //查询审批记录表审批人为业务经理的申请Id列表
                if(contractApplyQueryDto.getAuditStruts()!=null){
                    if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.WAIT)){
                        //待审批
                        contractApplyQueryDto.setApprovedStruts(0);
                    }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.CTHR)){
                        //已通过
                        List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,true);
                        if(applyIds==null || applyIds.size()==0){
                            result.put("pageInfo", new PageInfo<>(new ArrayList<ContractApplyDto>(), 1));
                            result.put("totalAmount", 0);
                            return result;
                        }
                        contractApplyQueryDto.setApplyIds(applyIds);
                        //contractApplyQueryDto.setApprovedStruts(1);
                    }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.AREJ)){
                        //未通过
                        List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,false);
                        if(applyIds==null || applyIds.size()==0){
                            result.put("pageInfo", new PageInfo<>(new ArrayList<ContractApplyDto>(), 1));
                            result.put("totalAmount", 0);
                            return result;
                        }
                        contractApplyQueryDto.setApplyIds(applyIds);
                        contractApplyQueryDto.setApprovedStruts(-1);
                    }
                }

            } else if(roleId.equals(4L)){//出纳: 需要出纳审批的和出纳已经审批的
                if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.WAIT)){
                    //待审批
                    contractApplyQueryDto.setApprovedStruts(1);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.CTHR)){
                    //已通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,true);
                    if(applyIds==null || applyIds.size()==0){
                        result.put("pageInfo", new PageInfo<>(new ArrayList<ContractApplyDto>(), 1));
                        result.put("totalAmount", 0);
                        return result;
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    //contractApplyQueryDto.setApprovedStruts(2);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.AREJ)){
                    //未通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,false);
                    if(applyIds==null || applyIds.size()==0){
                        result.put("pageInfo", new PageInfo<>(new ArrayList<ContractApplyDto>(), 1));
                        result.put("totalAmount", 0);
                        return result;
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    contractApplyQueryDto.setApprovedStruts(-1);
                }
            } else if(roleId.equals(5L)){//财务经理： 需要财务经理审批的和财务经理已经审批的
                if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.WAIT)){
                    contractApplyQueryDto.setApprovedStruts(2);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.CTHR)){
                    //已通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,true);
                    if(applyIds==null || applyIds.size()==0){
                        result.put("pageInfo", new PageInfo<>(new ArrayList<ContractApplyDto>(), 1));
                        result.put("totalAmount", 0);
                        return result;
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    //contractApplyQueryDto.setApprovedStruts(3);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.AREJ)){
                    //未通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,false);
                    if(applyIds==null || applyIds.size()==0){
                        result.put("pageInfo", new PageInfo<>(new ArrayList<ContractApplyDto>(), 1));
                        result.put("totalAmount", 0);
                        return result;
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    contractApplyQueryDto.setApprovedStruts(-1);
                }
            }
            contractApplyQueryDto.setAuditStruts(null);
        }

        Example example = ContractApplyExample.getContractApplyExample(contractApplyQueryDto,pageModel);
        Page<ContractApply> contractApplies =  (Page<ContractApply>)contractApplyDao.selectByExample(example);
        contractApplyQueryDto.setAuditStruts(auditStruts);
        List<ContractApplyDto> contractApplyDtos = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        for(ContractApply contractApply:contractApplies){
            ContractApplyDto contractApplyDto = contractApplyToContractApplyDto(contractApply,contractApplyQueryDto);
            if(contractApplyDto!=null){
                totalAmount = totalAmount.add(contractApplyDto.getContractDto().getAmount());
                //排除掉不需要业务经理审批的
               /* if(roleId==3 && !contractApplyDto.getContractDto().getSupportManagerAudit()){
                    continue;
                }*/
                contractApplyDtos.add(contractApplyDto);
            }
        }
        result.put("pageInfo", new PageInfo<>(contractApplyDtos, contractApplies.getPages()));
        result.put("totalAmount", totalAmount);
        return result;
    }

    @Override
    public PageInfo<ContractApplyDto> queryContractPage(ContractApplyQueryDto contractApplyQueryDto) {
        contractApplyQueryDto.setEffective(true);
        AuditStruts auditStruts = contractApplyQueryDto.getAuditStruts();
        //全部
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        if(contractApplyQueryDto.getFlag()==0){
            if(roleId.equals(2L)){//业务员 所有他提交的审批通过的
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractApplyQueryDto.setUserIds(userIds);
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(3L)){//业务经理 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(4L)){//出纳 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(5L)){//财务 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }else if(roleId.equals(6L)){//子公司Boss 所有审批通过的
                contractApplyQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }

        }else if(contractApplyQueryDto.getFlag()==1){//我的申请
            if(roleId.equals(2L)){//业务员 业务员自己提交的申请
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractApplyQueryDto.setUserIds(userIds);
            }else if(roleId.equals(3L)){//业务经理 业务经理提交的申请
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractApplyQueryDto.setUserIds(userIds);
            }else{
                return new PageInfo<>();
            }
        }else if(contractApplyQueryDto.getFlag()==2){//我的审批
            if(roleId.equals(3L)){//业务经理：需要业务经理审批的和业务经理已经审批的
                contractApplyQueryDto.setSupportManagerAudit(true);
                //查询审批记录表审批人为业务经理的申请Id列表
                if(contractApplyQueryDto.getAuditStruts()!=null){
                    if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.WAIT)){
                        //待审批
                        contractApplyQueryDto.setApprovedStruts(0);
                    }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.CTHR)){
                        //已通过
                        List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,true);
                        if(applyIds==null || applyIds.size()==0){
                            return new PageInfo<>();
                        }
                        contractApplyQueryDto.setApplyIds(applyIds);
                        //contractApplyQueryDto.setApprovedStruts(1);
                    }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.AREJ)){
                        //未通过
                        List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,false);
                        if(applyIds==null || applyIds.size()==0){
                            return new PageInfo<>();
                        }
                        contractApplyQueryDto.setApplyIds(applyIds);
                        contractApplyQueryDto.setApprovedStruts(-1);
                    }
                }

            } else if(roleId.equals(4L)){//出纳: 需要出纳审批的和出纳已经审批的
                if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.WAIT)){
                    //待审批
                    contractApplyQueryDto.setApprovedStruts(1);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.CTHR)){
                    //已通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,true);
                    if(applyIds==null || applyIds.size()==0){
                        return new PageInfo<>();
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    //contractApplyQueryDto.setApprovedStruts(2);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.AREJ)){
                    //未通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,false);
                    if(applyIds==null || applyIds.size()==0){
                        return new PageInfo<>();
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    contractApplyQueryDto.setApprovedStruts(-1);
                }
            } else if(roleId.equals(5L)){//财务经理： 需要财务经理审批的和财务经理已经审批的
                if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.WAIT)){
                    contractApplyQueryDto.setApprovedStruts(2);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.CTHR)){
                    //已通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,true);
                    if(applyIds==null || applyIds.size()==0){
                        return new PageInfo<>();
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    //contractApplyQueryDto.setApprovedStruts(3);
                }else if(contractApplyQueryDto.getAuditStruts().equals(AuditStruts.AREJ)){
                    //未通过
                    List<Long> applyIds = contractApplyAuditRecordingService.getContractApplyAuditRecordingIdListByUserId(userId,false);
                    if(applyIds==null || applyIds.size()==0){
                        return new PageInfo<>();
                    }
                    contractApplyQueryDto.setApplyIds(applyIds);
                    contractApplyQueryDto.setApprovedStruts(-1);
                }
            }
            contractApplyQueryDto.setAuditStruts(null);
        }

        Example example = ContractApplyExample.getContractApplyExample(contractApplyQueryDto, null);
        List<ContractApply> contractApplies =  contractApplyDao.selectByExample(example);
        contractApplyQueryDto.setAuditStruts(auditStruts);
        List<ContractApplyDto> contractApplyDtos = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        for(ContractApply contractApply:contractApplies){
            ContractApplyDto contractApplyDto = contractApplyToContractApplyDto(contractApply,contractApplyQueryDto);
            if(contractApplyDto!=null){
                totalAmount = totalAmount.add(contractApplyDto.getContractDto().getAmount());
                //排除掉不需要业务经理审批的
               /* if(roleId==3 && !contractApplyDto.getContractDto().getSupportManagerAudit()){
                    continue;
                }*/
                contractApplyDtos.add(contractApplyDto);
            }
        }
        return new PageInfo<>(contractApplyDtos);
    }

    @Override
    public ContractApplyDto getContractApplyDtoById(Long applyId) {
        ContractApply contractApply =  contractApplyDao.getApplyId(applyId);
        ContractApplyDto contractApplyDto = new ContractApplyDto();
        contractApplyDto.setId(contractApply.getId());
        contractApplyDto.setApplyTime(contractApply.getApplyTime());
        contractApplyDto.setApprovedStruts(contractApply.getApprovedStruts());
        contractApplyDto.setAuditStruts(contractApply.getAuditStruts());
        contractApplyDto.setUserDto(userService.getUserDtoById(contractApply.getUserId()));
        ContractDto contractDto = contractService.getContractById(contractApply.getContractId());
        contractApplyDto.setContractDto(contractDto);
        contractApplyDto.setApplyStruts(contractApply.getApplyStruts());
        contractApplyDto.setType(contractApply.getType());
        contractApplyDto.setNum(contractApply.getNum());
        contractApplyDto.setCreateTime(contractApply.getCreateTime());
        contractApplyDto.setSupportManagerAudit(contractDto.getSupportManagerAudit());
        return contractApplyDto;
    }

    @Override
    public List<ContractApplyDto> getContractApplysByContractIdAndTypeAndAuditStruts(Long contractId, ContractType type,AuditStruts auditStruts) {
        List<ContractApply> contractApplies = new ArrayList<>();
        if(auditStruts==null){
            contractApplies = contractApplyDao.getContractApplysByContractIdAndType(contractId,type);
        }else{
            contractApplies =contractApplyDao.getContractApplysByContractIdAndTypeAndAuditStruts(contractId,type,auditStruts);
        }
        List<ContractApplyDto> contractApplyDtos = new ArrayList<>();
        for(ContractApply contractApply:contractApplies){
            ContractApplyDto contractApplyDto = new ContractApplyDto();
            contractApplyDto.setId(contractApply.getId());
            contractApplyDto.setApplyTime(contractApply.getApplyTime());
            contractApplyDto.setApprovedStruts(contractApply.getApprovedStruts());
            contractApplyDto.setAuditStruts(contractApply.getAuditStruts());
            contractApplyDto.setUserDto(userService.getUserDtoById(contractApply.getUserId()));
            ContractDto contractDto = contractService.getContractById(contractApply.getContractId());
            contractApplyDto.setContractDto(contractDto);
            contractApplyDto.setApplyStruts(contractApply.getApplyStruts());
            contractApplyDto.setContractApplyAuditRecordingDtos(contractApplyAuditRecordingService.getContractApplyAuditRecordingDtoListByApplyId(contractApply.getId()));
            contractApplyDto.setNum(contractApply.getNum());
            contractApplyDto.setCreateTime(contractApply.getCreateTime());
            contractApplyDto.setSupportManagerAudit(contractApply.getSupportManagerAudit());
            contractApplyDtos.add(contractApplyDto);
        }
        return contractApplyDtos;
    }

    @Override
    public ContractApply getWaitApplyByContractId(Long id, ContractType contractType) {
        return contractApplyDao.getWaitApplyByContractId(id, contractType);
    }

    @Override
    public ContractApply getApplyById(Long applyId) {
        return contractApplyDao.selectByPrimaryKey(applyId);
    }

    @Override
    public ContractApply getWaitByContractId(Long contractId) {
        return contractApplyDao.getWaitByContractId(contractId);
    }

    public ContractApplyDto contractApplyToContractApplyDto(ContractApply contractApply, ContractApplyQueryDto contractApplyQueryDto){
        ContractDto contractDto = contractService.getContractById(contractApply.getContractId());
        if(!checkContract(contractDto,contractApplyQueryDto)){
            return null;
        }
        ContractApplyDto contractApplyDto = new ContractApplyDto();
        contractApplyDto.setId(contractApply.getId());
        contractApplyDto.setApplyTime(contractApply.getApplyTime());
        contractApplyDto.setApprovedStruts(contractApply.getApprovedStruts());
        contractApplyDto.setAuditStruts(contractApply.getAuditStruts());
        contractApplyDto.setUserDto(userService.getUserDtoById(contractApply.getUserId()));
        contractApplyDto.setType(contractApply.getType());
        contractApplyDto.setContractDto(contractDto);
        contractApplyDto.setApplyStruts(contractApply.getApplyStruts());
        contractApplyDto.setCreateTime(contractApply.getCreateTime());
        contractApplyDto.setSupportManagerAudit(contractApply.getSupportManagerAudit());
        return contractApplyDto;
    }

    public Boolean checkContract(ContractDto contractDto,ContractApplyQueryDto contractApplyQueryDto){
        Boolean flag = true;
        if(StringUtilPlus.isNoneEmpty(contractApplyQueryDto.getNumber())){
            if(contractDto.getNumber().indexOf(contractApplyQueryDto.getNumber())<0){
                flag = false;
            }
        }
        if(StringUtilPlus.isNoneEmpty(contractApplyQueryDto.getCustomName())){
            if(contractDto.getCustomerName().indexOf(contractApplyQueryDto.getCustomName())<0){
                flag = false;
            }
        }
        if(StringUtilPlus.isNoneEmpty(contractApplyQueryDto.getUserName())){
            if(contractDto.getUser().getRealname().indexOf(contractApplyQueryDto.getUserName())<0){
                flag = false;
            }
        }
        if(StringUtilPlus.isNoneEmpty(contractApplyQueryDto.getProductName())){
            if(contractDto.getProductName().indexOf(contractApplyQueryDto.getProductName())<0){
                flag = false;
            }
        }
        if(contractApplyQueryDto.getMinStartTime()!=null){
            if(contractDto.getStartTime().getTime()<contractApplyQueryDto.getMinStartTime().getTime()){
                flag = false;
            }
        }
        if(contractApplyQueryDto.getMaxStartTime()!=null){
            if(contractDto.getStartTime().getTime()>contractApplyQueryDto.getMaxStartTime().getTime()){
                flag = false;
            }
        }
        if(contractApplyQueryDto.getMinEndTime()!=null){
            if(contractDto.getStartTime().getTime()<contractApplyQueryDto.getMinEndTime().getTime()){
                flag = false;
            }
        }
        if(contractApplyQueryDto.getMaxEndTime()!=null){
            if(contractDto.getStartTime().getTime()>contractApplyQueryDto.getMaxEndTime().getTime()){
                flag = false;
            }
        }
        //身份证号
        //签约状态
        return flag;
    }
}
