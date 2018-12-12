package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.EnumConvertUtilplus;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dao.ApprovedApplicationDao;
import com.ydhl.outsourcing.ts.finance.dto.*;
import com.ydhl.outsourcing.ts.finance.dto.query.ApprovedApplicationQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.example.ApplicationExample;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.ApprovedApplication;
import com.ydhl.outsourcing.ts.finance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @author Martins
 * @create 2018/1/16 20:37.
 * @description
 */
@Service
public class ApprovedApplicationServiceImpl implements ApprovedApplicationService {


    @Autowired
    private ApprovedApplicationDao approvedApplicationDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ApprovedRecordingService approvedRecordingService;

    @Autowired
    private ApprovedApplyservice approvedApplyservice;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OperationService operationService;

    @Override
    public PageInfo<ApprovedApplicationDto> getApprovedApplicationPage(ApprovedApplicationQueryDto approvedApplicationQueryDto, PageModel pageModel) {
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        List<Long> userIds = new ArrayList<>();
        List<Long> approvedIds = approvedRecordingService.getEffectiveApprovedIdListByUserId(userId);
        if(roleId==2){//业务员 他提交的结算
            userIds = new ArrayList<>();
            userIds.add(userId);
            approvedApplicationQueryDto.setUserIds(userIds);
        }else if(roleId==3){//业务经理 待他审批的结算 他提交的结算 他已审批的结算 是需要业务经理审批
            approvedApplicationQueryDto.setApprovedStruts(0);//待他审批的结算
            approvedApplicationQueryDto.setApprovedIds(approvedIds);//他已审批的结算
            userIds = new ArrayList<>();
            userIds.add(userId);
            approvedApplicationQueryDto.setUserIds(userIds);//他提交的结算
            approvedApplicationQueryDto.setFlag(1);
            approvedApplicationQueryDto.setSupportManagerAudit(true);//是需要业务经理审批
        }else if(roleId==4){
            approvedApplicationQueryDto.setApprovedStruts(1);//待他审批的结算
            approvedApplicationQueryDto.setApprovedIds(approvedIds);//他已审批的结算
        }else if(roleId==5){
            approvedApplicationQueryDto.setApprovedStruts(2);//待他审批的结算
            approvedApplicationQueryDto.setApprovedIds(approvedIds);//他已审批的结算
        }else if(roleId==6){
            approvedApplicationQueryDto.setApprovedStruts(3);//待他审批的结算
            approvedApplicationQueryDto.setApprovedIds(approvedIds);//他已审批的结算
        }else if(roleId==7){
            approvedApplicationQueryDto.setApprovedStruts(4);//待他审批的结算
            approvedApplicationQueryDto.setApprovedIds(approvedIds);//他已审批的结算
        }else if(roleId==8){
            approvedApplicationQueryDto.setApprovedStruts(5);//待他审批的结算
            approvedApplicationQueryDto.setApprovedIds(approvedIds);//他已审批的结算
        }

        /*if(StringUtilPlus.isNotEmpty(approvedApplicationQueryDto.getUserRealname()) && roleId!=2){
            List<UserDto> userDtos = userService.getUserByRealnameOrJobNumber(approvedApplicationQueryDto.getUserRealname());
            List<Long> users = new ArrayList<>();
            if(userDtos!=null && userDtos.size()!=0){
                for(UserDto userDto:userDtos){
                    if(userDto.getRoleId()==2){
                        users.add(userDto.getId());
                    }else if(userDto.getRoleId()==3){
                        if(roleId==3){
                            if(userId==userDto.getId()){
                                users.add(userDto.getId());
                            }
                        }else if(roleId>3 || roleId==1){
                            users.add(userDto.getId());
                        }
                    }
                }
                approvedApplicationQueryDto.setUserIds(users);
            }else{
                approvedApplicationQueryDto.setUserIds(null);
            }
        }*/

        if(StringUtilPlus.isNoneEmpty(approvedApplicationQueryDto.getUserRealname())){
            List<UserDto> userDtos = userService.getUserByRealnameOrJobNumber(approvedApplicationQueryDto.getUserRealname());
            userIds = new ArrayList<>();
            if(roleId==2){//业务员
                userIds.add(userId);
            }else{
                for(UserDto userDto:userDtos){
                    if(roleId==3){//业务经理
                        if(userDto.getRoleId()==2){
                            userIds.add(userDto.getId());
                        }else if(userDto.getRoleId()==3){
                            if(userDto.getId()==userId){
                                userIds.add(userDto.getId());
                            }
                        }
                    }else{
                        userIds.add(userDto.getId());
                    }
                }
            }
            //approvedApplicationQueryDto.setUserIds(userIds);
        }

        Example example = ApplicationExample.getApplicationExample(approvedApplicationQueryDto, pageModel);
        Page<ApprovedApplication> applicationPage = (Page<ApprovedApplication>)approvedApplicationDao.selectByExample(example);
        List<ApprovedApplicationDto> applicationDtoList = new ArrayList<>();
        for (ApprovedApplication approvedApplication : applicationPage) {
            if(!checkApprovedApplication(approvedApplicationQueryDto,approvedApplication)){
                continue;
            }
            ApprovedApplicationDto approvedApplicationDto = new ApprovedApplicationDto();
            approvedApplicationDto.setId(approvedApplication.getId());
            approvedApplicationDto.setSettleNumber(approvedApplication.getSettleNumber());
            approvedApplicationDto.setUserRealname(userService.queryUser(approvedApplication.getUserId()).getRealname());
            approvedApplicationDto.setPenNumber(approvedApplication.getPenNumber());
            approvedApplicationDto.setApplyTime(approvedApplication.getApplyTime());
            approvedApplicationDto.setApprovedStruts(approvedApplication.getApprovedStruts());
            approvedApplicationDto.setStruts(approvedApplication.getStruts());
            approvedApplicationDto.setReason(approvedApplication.getReason());
            approvedApplicationDto.setProductId(approvedApplication.getProductId());
            approvedApplicationDto.setUserId(approvedApplication.getUserId());
            if(StringUtilPlus.isNoneEmpty(approvedApplicationQueryDto.getUserRealname())){
                for(Long uId:userIds){
                    if(approvedApplication.getUserId()==uId){
                        applicationDtoList.add(approvedApplicationDto);
                    }
                }
            }else{
                applicationDtoList.add(approvedApplicationDto);
            }
        }
        return new PageInfo<>(applicationDtoList, applicationPage.getPages());
    }

    public Boolean checkApprovedApplication(ApprovedApplicationQueryDto approvedApplicationQueryDto,ApprovedApplication approvedApplication){
        Boolean flag = true;
        if(approvedApplicationQueryDto.getStruts()!=null){
            if(approvedApplication.getStruts()!=approvedApplicationQueryDto.getStruts()){
                flag = false;
            }
        }
        if(approvedApplicationQueryDto.getStartTime()!=null){
            if(approvedApplication.getApplyTime().getTime()<approvedApplicationQueryDto.getStartTime().getTime()){
                flag = false;
            }
        }
        if(approvedApplicationQueryDto.getEndTime()!=null){
            Date endTime = approvedApplicationQueryDto.getEndTime();
            if(approvedApplication.getApplyTime().getTime()>(approvedApplicationQueryDto.getEndTime().getTime()+24*60*60*1000)){
                flag = false;
            }
        }
        if(StringUtilPlus.isNoneEmpty(approvedApplicationQueryDto.getSettleNumber())){
            if(approvedApplication.getSettleNumber().indexOf(approvedApplicationQueryDto.getSettleNumber())<0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public Map<Object, ApplicationStruts> getAllStruts() {
        return EnumConvertUtilplus.toMap(ApplicationStruts.class, "getDesc");
    }

    @Override
    public List<Long> getContractIdByUserId(Long userId) {
        return approvedApplicationDao.getContractIdByUserId(userId);
    }

    @Override
    @Transactional
    public void passApply(Long approveId) throws UnknownHostException, BusinessException {
        Integer num = approvedRecordingService.getNumByApprovedId(approveId);
        if(num==null || num==0){
            num = 1;
        }else{
            num = num + 1;
        }
        //通过申请 1.在审批记录中插入数据并修改结算状态
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        ApprovedRecordingDto approvedRecordingDto = new ApprovedRecordingDto();
        approvedRecordingDto.setUserId(userId);
        approvedRecordingDto.setApprovedId(approveId);
        approvedRecordingDto.setStruts(true);
        approvedRecordingDto.setEffective(true);
        approvedRecordingDto.setNum(num);
        approvedRecordingDto.setCreateTime(new Date());
        approvedRecordingService.insertApprovedRecordingDto(approvedRecordingDto);
        if(roleId==3){//业务经理
            approvedApplicationDao.updateApprovedApplicationApprovedStruts(approveId,1);
        }else if(roleId==4){
            approvedApplicationDao.updateApprovedApplicationApprovedStruts(approveId,2);
        }else if(roleId==5){
            approvedApplicationDao.updateApprovedApplicationApprovedStruts(approveId,3);
        }else if(roleId==6){
            approvedApplicationDao.updateApprovedApplicationApprovedStruts(approveId,4);
        }else if(roleId==7){
            approvedApplicationDao.updateApprovedApplicationApprovedStruts(approveId,5);
        }else if(roleId==8){
            approvedApplicationDao.updateApprovedApplicationStrutsAndApprovedStruts(approveId, ApplicationStruts.CTHR,6,"");
            //修改收益列表的状态为待打款
            incomeService.updateIncomeStrutsByApprovedIdAndStruts(approveId, ApprovalType.W_P);
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("通过结算审批");
        operationService.insertOperation(operationDto);
    }

    @Override
    @Transactional
    public void refuseApply(Long approveId, String content) throws UnknownHostException, BusinessException {
        Integer num = approvedRecordingService.getNumByApprovedId(approveId);
        if(num==null || num==0){
            num = 1;
        } else{
            num = num + 1;
        }
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginSecurityDto.getId();
        ApprovedRecordingDto approvedRecordingDto = new ApprovedRecordingDto();
        approvedRecordingDto.setUserId(userId);
        approvedRecordingDto.setStruts(false);
        approvedRecordingDto.setApprovedId(approveId);
        approvedRecordingDto.setRemark(content);
        approvedRecordingDto.setEffective(true);
        approvedRecordingDto.setNum(num);
        approvedRecordingDto.setCreateTime(new Date());
        //插入审批记录
        approvedRecordingService.insertApprovedRecordingDto(approvedRecordingDto);

        //修改结算状态和状态值
        approvedApplicationDao.updateApprovedApplicationStrutsAndApprovedStruts(approveId, ApplicationStruts.AREJ,-1,content);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("拒绝结算审批");
        operationService.insertOperation(operationDto);
    }

    @Override
    public ApprovedApplicationDto getApprovedApplicationById(Long id) {
        ApprovedApplication approvedApplication =  approvedApplicationDao.getApprovedApplicationById(id);
        ApprovedApplicationDto approvedApplicationDto = new ApprovedApplicationDto();
        approvedApplicationDto.setId(approvedApplication.getId());
        approvedApplicationDto.setSettleNumber(approvedApplication.getSettleNumber());
        approvedApplicationDto.setUserRealname(userService.queryUser(approvedApplication.getUserId()).getRealname());
        approvedApplicationDto.setPenNumber(approvedApplication.getPenNumber());
        approvedApplicationDto.setApplyTime(approvedApplication.getApplyTime());
        approvedApplicationDto.setApprovedStruts(approvedApplication.getApprovedStruts());
        approvedApplicationDto.setStruts(approvedApplication.getStruts());
        approvedApplicationDto.setReason(approvedApplication.getReason());
        approvedApplicationDto.setProductId(approvedApplication.getProductId());
        approvedApplicationDto.setApplyTime(approvedApplication.getApplyTime());
        return approvedApplicationDto;
    }

    @Override
    @Transactional
    public void insertApprovedApplication(ApprovedApplicationDto approvedApplicationDto, List<Long> incomeIds) throws UnknownHostException, BusinessException {
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginSecurityDto.getId();
        Long roleId = loginSecurityDto.getRoleId();
        ApprovedApplication approvedApplication = new ApprovedApplication();
        approvedApplication.setProductId(approvedApplicationDto.getProductId());
        approvedApplication.setPenNumber(approvedApplicationDto.getPenNumber());
        approvedApplication.setUserId(userId);
        approvedApplication.setStruts(ApplicationStruts.BREV);
        ProductDto productDto = productService.getProductDtoById(approvedApplicationDto.getProductId());
        if(productDto.getSupportManagerAudit() && roleId!=3){
            approvedApplication.setApprovedStruts(0);
        }else{
            approvedApplication.setApprovedStruts(1);
        }
        approvedApplication.setSupportManagerAudit(productDto.getSupportManagerAudit());
        approvedApplication.setApplyTime(new Date());
        approvedApplication.setSettleNumber(UUID.randomUUID().toString());
        //插入申请
        approvedApplicationDao.insert(approvedApplication);
        Long approvedId = approvedApplication.getId();
        //修改收益的申请Id
        incomeService.updateApprovedIdByApprovedIdAndIdList(approvedId,incomeIds);
        //像收益申请中间表中插入数据
        for(Long incomeId:incomeIds){
            approvedApplicationDao.insertAprrovedIdAndIncomeId(approvedId,incomeId);
        }
        //修改收益列表的状态为待打款
        incomeService.updateIncomeStrutsByApprovedIdAndStruts(approvedId, ApprovalType.A_I);
        //插入审批记录
        ApprovedRecordingDto approvedRecordingDto = new ApprovedRecordingDto();
        approvedRecordingDto.setUserId(userId);
        approvedRecordingDto.setApprovedId(approvedId);
        approvedRecordingDto.setStruts(true);
        approvedRecordingDto.setEffective(true);
        approvedRecordingDto.setNum(1);
        approvedRecordingDto.setCreateTime(new Date());
        approvedRecordingService.insertApprovedRecordingDto(approvedRecordingDto);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("新增结算申请("+ approvedApplication.getSettleNumber() +")");
        operationService.insertOperation(operationDto);
    }

    @Override
    public List<Long> getIncomeIdListByApprovedId(Long approvedId) {
        return approvedApplicationDao.getIncomeIdListByApprovedId(approvedId);
    }

    @Override
    @Transactional
    public void deleteApprovedById(Long approvedId) {
        //修改所有收益的申请Id为空,状态为未申请
        incomeService.updateApprovedIdbyApprovedId(approvedId);
        //删除中间表信息
        approvedApplicationDao.deleteApprovedIncomeInfo(approvedId);
        //删除审批记录
        approvedRecordingService.deleteApprovedRecordingByApprovedId(approvedId);
        //删除申请
        approvedApplicationDao.deleteApprovedById(approvedId);
    }

    @Override
    @Transactional
    public void retryApprovedById(Long approvedId) throws UnknownHostException, BusinessException {
        ApprovedApplication approvedApplication = approvedApplicationDao.getApprovedApplicationById(approvedId);
        if(approvedApplication.getSupportManagerAudit()){
            approvedApplicationDao.updateApprovedApplicationStrutsAndApprovedStruts(approvedId, ApplicationStruts.BREV,0,"");
        }else{
            approvedApplicationDao.updateApprovedApplicationStrutsAndApprovedStruts(approvedId, ApplicationStruts.BREV,1,"");
        }
        //修改所有审批记录为无效
        approvedRecordingService.updateApprovedRecordEffectiveByApprovedId(approvedId);
        //插入审批记录
        Integer num = approvedRecordingService.getNumByApprovedId(approvedId);
        if(num==null || num==0){
            num = 1;
        } else{
            num = num + 1;
        }
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginSecurityDto.getId();
        ApprovedRecordingDto approvedRecordingDto = new ApprovedRecordingDto();
        approvedRecordingDto.setUserId(userId);
        approvedRecordingDto.setApprovedId(approvedId);
        approvedRecordingDto.setStruts(true);
        approvedRecordingDto.setEffective(true);
        approvedRecordingDto.setNum(num);
        approvedRecordingDto.setCreateTime(new Date());
        approvedRecordingService.insertApprovedRecordingDto(approvedRecordingDto);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("再次申请结算("+ approvedApplication.getSettleNumber() +")");
        operationService.insertOperation(operationDto);
    }
}
