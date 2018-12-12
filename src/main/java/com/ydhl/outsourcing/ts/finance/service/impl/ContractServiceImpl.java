package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.*;
import com.ydhl.outsourcing.ts.finance.dao.ContractConfirmInfoDao;
import com.ydhl.outsourcing.ts.finance.dao.ContractDao;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dto.*;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractApplyQueryDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto2;
import com.ydhl.outsourcing.ts.finance.enums.*;
import com.ydhl.outsourcing.ts.finance.example.ContractExample;
import com.ydhl.outsourcing.ts.finance.example.ContractExample2;
import com.ydhl.outsourcing.ts.finance.model.*;
import com.ydhl.outsourcing.ts.finance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2018/1/22.
 */
@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EarningsService earningsService;

    @Autowired
    private ContractSignInfoService contractSignInfoService;

    @Autowired
    private ApprovedApplicationService approvedApplicationService;

    @Autowired
    private ContractApplyService contractApplyService;

    @Autowired
    private ContractApplyAuditRecordingService contractApplyAuditRecordingService;

    @Autowired
    private ContractQuitInfoService quitInfoService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ContractConfirmInfoDao confirmInfoDao;

    @Autowired
    private OperationService operationService;

    @Autowired
    private ContractConfirmInfoService contractConfirmInfoService;


    @Override
    public Map<Object, Object> queryContractDtoListPage2(PageModel pageModel, ContractQueryDto2 contractQueryDto) {
        Map<Object, Object> result = new HashMap<>();
        List<Long> ids = null;
        if(StringUtilPlus.isNoneEmpty(contractQueryDto.getCustomName())){
            ids = customerService.getCustomIdsByName(contractQueryDto.getCustomName());
            if(ids==null || ids.size()==0){
                result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                result.put("totalAmount", 0);
                return result;
            }
            contractQueryDto.setCustomIds(ids);
        }
        if(StringUtilPlus.isNoneEmpty(contractQueryDto.getUserName())){
            ids = userService.getUserIdsByName(contractQueryDto.getUserName());
            if(ids==null || ids.size()==0){
                result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                result.put("totalAmount", 0);
                return result;
            }
            contractQueryDto.setUserIds(ids);
        }
        Example example = ContractExample2.getContractExample(contractQueryDto, pageModel);
        Page<Contract> contracts = (Page<Contract>) contractDao.selectByExample(example);
        List<ContractDto> contractDtos = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        for (Contract contract : contracts) {
            ContractDto contractDto = contractToContractDto(contract);
            contractDto.setBankAccount(BankAcountAes.getInstance().decrypt(contractDto.getBankAccount()));
            totalAmount = totalAmount.add(contractDto.getAmount());
            contractDtos.add(contractDto);
        }
        result.put("pageInfo", new PageInfo<>(contractDtos, contracts.getPages()));
        result.put("totalAmount", totalAmount);
        return result;
    }

    @Override
    public Map<Object, Object> queryContractDtoListPage(PageModel pageModel, ContractQueryDto contractQueryDto) {
        Map<Object, Object> result = new HashMap<>();
        //封装查询对象
        if (StringUtilPlus.isNoneEmpty(contractQueryDto.getCustomName())) {
            List<Long> customIds = customerService.getCustomIdsByName(contractQueryDto.getCustomName());
            contractQueryDto.setCustomIds(customIds);
        }
        if (StringUtilPlus.isNoneEmpty(contractQueryDto.getUserName())) {
            List<Long> userIds = userService.getUserIdsByName(contractQueryDto.getUserName());
            contractQueryDto.setUserIds(userIds);
        }

        //设置不同角色人员看到不同的数据
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();

        //设置
        if (contractQueryDto.getFlag() == 0) {//全部签约
            if (roleId.equals(2L)) {//业务员 所有他提交的审批通过的
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractQueryDto.setUserIds(userIds);
                contractQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            } else if (roleId.equals(3L)) {//业务经理 所有审批通过的
                contractQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            } else if (roleId.equals(4L)) {//出纳 所有审批通过的
                contractQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            } else if (roleId.equals(5L)) {//财务 所有审批通过的
                contractQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            } else if (roleId.equals(6L)) {//子公司Boss 所有审批通过的
                contractQueryDto.setApplyStruts(ApplicationStruts.CTHR);
            }
        } else if (contractQueryDto.getFlag() == 1) {//我的申请
            if (roleId.equals(2L)) {//业务员 业务员自己提交的申请
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractQueryDto.setUserIds(userIds);
            } else if (roleId.equals(3L)) {//业务经理 业务经理提交的申请
                List<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                contractQueryDto.setUserIds(userIds);
            } else {
                result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                result.put("totalAmount", new BigDecimal(0));
                return result;
            }
        } else if (contractQueryDto.getFlag() == 2) {
            //我的审批
            //待我审批的 根据角色不同,设置不同的approved_struts
            //我已审批的 审批记录中我审批的
            //业务经理: 审批记录中我审批的, 0=approved_struts申请表中待我审批的,合同中不需要业务经理审批的
            //出纳: 审批记录中我审批的,1=approved_struts申请表中待我审批的
            //财务: 审批记录中我审批的,2=approved_struts申请表中待我审批的
            if (roleId.equals(3L)) {
                contractQueryDto.setSupportManagerAudit(true);//设置排除不需要业务经理审批的合同
            }
            List<Long> contractIds = new ArrayList<>();
            ContractApplyQueryDto contractApplyQueryDto = new ContractApplyQueryDto();
            List<Long> applyIds = new ArrayList<>();
            if (contractQueryDto.getAuditStruts().equals(AuditStruts.WAIT)) {//待审批的
                if (roleId.equals(3L)) {
                    contractApplyQueryDto.setApprovedStruts(0);
                } else if (roleId.equals(4L)) {
                    contractApplyQueryDto.setApprovedStruts(1);
                } else if (roleId.equals(5L)) {
                    contractApplyQueryDto.setApprovedStruts(2);
                }
                contractIds = contractApplyService.getContractIdListByApplyId(contractApplyQueryDto);
            } else if (contractQueryDto.getAuditStruts().equals(AuditStruts.CTHR)) {//审批通过
                applyIds = contractApplyAuditRecordingService.getApplyIdListByUserId(userId, true);
                if (applyIds.size() == 0) {
                    result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                    result.put("totalAmount", new BigDecimal(0));
                    return result;
                }
                contractApplyQueryDto.setApplyIds(applyIds);
                contractIds = contractApplyService.getContractIdListByApplyId(contractApplyQueryDto);
            } else if (contractQueryDto.getAuditStruts().equals(AuditStruts.AREJ)) {//审批未通过
                applyIds = contractApplyAuditRecordingService.getApplyIdListByUserId(userId, false);
                if (applyIds.size() == 0) {
                    result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                    result.put("totalAmount", new BigDecimal(0));
                    return result;
                }
                contractApplyQueryDto.setApplyIds(applyIds);
                contractIds = contractApplyService.getContractIdListByApplyId(contractApplyQueryDto);
            }
            if (contractIds.size() == 0) {
                result.put("pageInfo", new PageInfo<>(new ArrayList<>(), 1));
                result.put("totalAmount", new BigDecimal(0));
                return result;
            } else {
                contractQueryDto.setContractIdList(contractIds);
            }
        }

        Example example = ContractExample.getContractExample(contractQueryDto, pageModel);
        if (pageModel != null) {
            Page<Contract> contracts = (Page<Contract>) contractDao.selectByExample(example);
            List<ContractDto> contractDtos = new ArrayList<>();
            BigDecimal totalAmount = new BigDecimal(0);
            for (Contract contract : contracts) {
                ContractDto contractDto = contractToContractDto(contract);

                //-----------
                contractDto.setBankAccount(BankAcountAes.getInstance().decrypt(contractDto.getBankAccount()));

                totalAmount = totalAmount.add(contractDto.getAmount());
                contractDtos.add(contractDto);
            }
            result.put("pageInfo", new PageInfo<>(contractDtos, contracts.getPages()));
            result.put("totalAmount", totalAmount);
        } else {
            List<Contract> contracts = contractDao.selectByExample(example);
            List<ContractDto> contractDtos = new ArrayList<>();
            BigDecimal totalAmount = new BigDecimal(0);
            for (Contract contract : contracts) {
                ContractDto contractDto = contractToContractDto(contract);

                //-----------
                contractDto.setBankAccount(BankAcountAes.getInstance().decrypt(contractDto.getBankAccount()));

                totalAmount = totalAmount.add(contractDto.getAmount());
                contractDtos.add(contractDto);
            }
            result.put("contractDtos", contractDtos);
            result.put("totalAmount", totalAmount);
        }
        return result;
    }

    @Override
    public ContractDto getContractById(Long contractId) {
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        ContractDto contractDto = contractToContractDto(contract);

        //-----------
        contractDto.setBankAccount(BankAcountAes.getInstance().decrypt(contractDto.getBankAccount()));

        contractDto.setCreateTime(contract.getCreateTime());
        contractDto.setStartTime(contract.getStartTime());
        contractDto.setProductName(contract.getProductName());
        contractDto.setEndTime(contract.getEndTime());
        return contractDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insertContract(ContractDto contractDto) throws Exception {

        //------------------------
        if(contractDto.getBankAccount()!=null && contractDto.getBankAccount()!=""){
            contractDto.setBankAccount(BankAcountAes.getInstance().encrypt(contractDto.getBankAccount()));
        }

        Map<String, Object> map = new HashMap<>();
        Contract contract = new Contract();
        ProductDto productDto = productService.getProductDtoById(contractDto.getProductId());
        contract.setUserId(contractDto.getUserId());
        contract.setCustomerId(contractDto.getCustomerId());
        if (contractDao.getContractByNumber(contractDto.getNumber()) != null) {
            map.put("msg", "合同编号已存在");
            return map;
        }
        contract.setNumber(contractDto.getNumber());
        CustomerDto customerDto = customerService.getCustomerInfoById(contractDto.getCustomerId());
        BigDecimal remaingAmount = new BigDecimal(0);
        if (contractDto.getAmount().compareTo(productDto.getRemainingAmount()) == 1) {
            map.put("msg", "购买金额高于产品剩余金额");
            return map;
        }
        if (customerDto.getType() == null) {
            map.put("msg", "客户类型错误");
            return map;
        }
        Integer butTimes = productDto.getBuyCount() + 1;
        if (customerDto.getType().equals(CustomType.IN_CUS)) {
            if (!productDto.getSupportInter()) {
                map.put("msg", "该产品不支持内部员工购买");
                return map;
            }
            if (contractDto.getAmount().compareTo(productDto.getInterRemainingAmount()) >= 0) {
                map.put("msg", "金额超出内部额度限制");
                return map;
            }
            //内部员工
            contract.setEarningRatio(productDto.getInterEarningsRatio());
            contract.setCycle(productDto.getInterCycle());
            //修改内部剩余额度
            productService.updateInterRamainAmount(productDto.getInterRemainingAmount().subtract(contractDto.getAmount()), butTimes, productDto.getId());
            contract.setEarningsDayAmount(EaringsUtil.getEverydayEarning(contractDto.getAmount(), productDto.getInterEarningsRatio()));
            remaingAmount = productDto.getRemainingAmount().subtract(contractDto.getAmount());
        } else {
            if (contractDto.getAmount().compareTo(productDto.getOutStartAmount()) == -1) {
                map.put("msg", "购买金额低于起购金额");
                return map;
            }
            //外部客户
            List<EarningsDto> earningsDtoList = earningsService.getEarningsList(contractDto.getProductId());
            if (productDto.getEarningsType().equals(EarningsType.FIXED)) {
                //固定收益比
                BigDecimal earningRatio = earningsDtoList.get(0).getOutEarningsRatio();
                contract.setEarningRatio(earningRatio);
                //固定收益比每天收益
                contract.setEarningsDayAmount(EaringsUtil.getEverydayEarning(contractDto.getAmount(), earningRatio));
                remaingAmount = productDto.getRemainingAmount().subtract(contractDto.getAmount());
            } else {
                //阶梯收益
                BigDecimal earningRatio = new BigDecimal(0);
                BigDecimal dayEarning = new BigDecimal(0);
                //获取最高阶段
                EarningsDto earningLastDto = earningsDtoList.get(earningsDtoList.size() - 1);
                //判断是否是最高阶段
                if (contractDto.getAmount().compareTo(earningLastDto.getMinRange()) >= 0 && contractDto.getAmount().compareTo(earningLastDto.getMaxRange()) <= 0) {
                    earningRatio = earningLastDto.getOutEarningsRatio();
                    dayEarning = EaringsUtil.getEverydayEarning(contractDto.getAmount(), earningRatio);
                    remaingAmount = productDto.getRemainingAmount().subtract(contractDto.getAmount());
                }
                for (EarningsDto earningDto : earningsDtoList) {
                    //如果合同金额大于等于最小值，小于最大值
                    if (contractDto.getAmount().compareTo(earningDto.getMinRange()) >= 0 && contractDto.getAmount().compareTo(earningDto.getMaxRange()) == -1) {
                        earningRatio = earningDto.getOutEarningsRatio();
                        dayEarning = EaringsUtil.getEverydayEarning(contractDto.getAmount(), earningRatio);
                        remaingAmount = productDto.getRemainingAmount().subtract(contractDto.getAmount());
                    }
                }
                contract.setEarningRatio(earningRatio);
                contract.setEarningsDayAmount(dayEarning);
            }
            contract.setCycle(productDto.getBillingCycle());

            productService.updateRemainAmount(remaingAmount, butTimes, productDto.getId());
        }
        // contract.setApprovedStruts(0);
        contract.setDuration(productDto.getCycle().intValue());
        contract.setEndTime(DateUtilPlus.getDateAddYear(contractDto.getStartTime(), productDto.getCycle().intValue()));
        contract.setProductName(productDto.getName());
        contract.setAmount(contractDto.getAmount());
        contract.setProductId(contractDto.getProductId());
        contract.setCreateTime(DateHelperPlus.getNow().getTime());
        contract.setStartTime(contractDto.getStartTime());
        contract.setOpenName(contractDto.getOpenName());
        contract.setOpenBank(contractDto.getOpenBank());
        contract.setBankAccount(contractDto.getBankAccount());
        contract.setType(ContractType.ADV);
        contract.setStruts(ContractStruts.DJH);
        contract.setSupportManagerAudit(productDto.getSupportManagerAudit());
        //contract.setStruts(ApplicationStruts.BREV);
        contract.setRemark(contractDto.getRemark());
        contract.setValidity(true);
        contract.setStateChangeType(StateChangeType.ND);
        contractDao.insert(contract);
        insertConfirmInfo(contract.getId());
        //插入合同签约信息列表
        if (contractDto.getContractSignInfoDtos() != null) {
            contractSignInfoService.insertContractSignInfoList(contractDto.getContractSignInfoDtos(), contract.getId());
        }

        ContractApply apply = addApply(contractDto, contract, productDto);
        apply.setNum(1);
        contractApplyService.addContractApply(apply);

        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("新增签约(" + contractDto.getNumber() + "相关信息");
        operationService.insertOperation(operationDto);
        map.put("msg", "success");
        return map;
    }

    @Override
    public ContractDto getContractByNumber(String number) {
        Contract contract = contractDao.getContractByNumber(number);
        ContractDto contractDto = new ContractDto();
        if (contract != null) {
            contractDto.setId(contract.getId());
            contractDto.setNumber(contract.getNumber());
        }
        return contractDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateContract(ContractDto contractDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
        BigDecimal remaingAmount = new BigDecimal(0);
//        try {
            ContractApply apply = contractApplyService.getApplyById(contractDto.getApplyId());
            Contract oldContract = contractDao.selectByPrimaryKey(apply.getContractId());
            BigDecimal oldContractAmount = oldContract.getAmount();
            ProductDto oldProduct = productService.getProductDtoById(contractDto.getOldProductId());
            ProductDto newProduct = productService.getProductDtoById(contractDto.getProductId());
            CustomerDto customerDto = customerService.getCustomerInfoById(contractDto.getCustomerId());
            List<ContractApply> applyList;
            if (oldProduct.getSupportManagerAudit()) {
                applyList = contractApplyService.getApplyListByContractId(contractDto.getApplyId(), true);
            } else {
                applyList = contractApplyService.getApplyListByContractId(contractDto.getApplyId(), false);
            }
            if (applyList.size() != 0) {
                map.put("msg", "已有层级审批通过，不允许被编辑");
                return map;
            }
            //剩余金额
            Integer buyCount = 0;
            Integer buyTimes;
            Contract con = contractDao.getContractByNumber(contractDto.getNumber());
            if (con != null) {
                if (!contractDao.getContractByNumber(contractDto.getNumber()).getId().equals(contractDto.getId())) {
                    map.put("msg", "合同编号已存在");
                    return map;
                }
            }
            oldContract.setNumber(contractDto.getNumber());
            if (contractDto.getOldProductId().equals(contractDto.getProductId())) {
                //产品未修改
                if (!contractDto.getCustomerId().equals(oldContract.getCustomerId())) {
                    //客户修改
                    if (customerDto.getType().equals(CustomType.IN_CUS)) {
                        //内部员工
                        if (!oldProduct.getSupportInter()) {
                            map.put("msg", "该产品不支持内部员工购买");
                            return map;
                        }
                        if (contractDto.getAmount().compareTo(oldProduct.getInterRemainingAmount()) >= 0) {
                            map.put("msg", "金额超出内部额度限制");
                            return map;
                        }
                        buyTimes = oldProduct.getBuyCount() +1;
                        productService.updateInterRamainAmount(oldProduct.getInterRemainingAmount().subtract(contractDto.getAmount()), buyTimes, oldContract.getId());
                    }else {
                        productService.updateRemainAmount(oldContract.getAmount().subtract(contractDto.getAmount()).add(oldProduct.getRemainingAmount()).subtract(contractDto.getAmount()), oldProduct.getBuyCount()+ 1, contractDto.getProductId());
                    }
                    if (contractDto.getAmount().compareTo(oldProduct.getOutStartAmount()) == -1) {
                        map.put("msg", "购买金额低于起购金额");
                        return map;
                    }
                    if (contractDto.getAmount().compareTo(oldProduct.getRemainingAmount()) == 1) {
                        map.put("msg", "购买金额高于产品剩余金额");
                        return map;
                    }
                    oldContract.setCustomerId(contractDto.getCustomerId());
                }else {
                    if (customerDto.getType() == CustomType.IN_CUS) {
                        productService.updateInterRamainAmount(oldProduct.getInterRemainingAmount().subtract(contractDto.getAmount()), oldProduct.getBuyCount() + 1, oldProduct.getId());
                    }else {
                        productService.updateRemainAmount(oldContract.getAmount().subtract(contractDto.getAmount()).add(oldProduct.getRemainingAmount()).subtract(contractDto.getAmount()), oldProduct.getBuyCount()+ 1, contractDto.getProductId());
                    }
                }
                oldContract.setProductId(contractDto.getProductId());
                oldContract.setAmount(contractDto.getAmount());
                oldContract.setCycle(oldProduct.getBillingCycle());
                //修改合同签约信息列表
                //删除该合同的签约列表
                if (contractDto.getContractSignInfoDtos() != null) {
                    contractSignInfoService.updateSignInfoList(contractDto.getContractSignInfoDtos(), contractDto.getId());
                }
            } else {
                //产品修改,修改产品
                if (contractDto.getAmount().compareTo(newProduct.getOutStartAmount()) == -1) {
                    map.put("msg", "购买金额低于起购金额");
                    return map;
                }
                if (contractDto.getAmount().compareTo(newProduct.getRemainingAmount()) == 1) {
                    map.put("msg", "购买金额高于产品剩余金额");
                    return map;
                }
                if (!contractDto.getCustomerId().equals(oldContract.getCustomerId())) {
                    //修改了客户
                    if (customerDto.getType().equals(CustomType.IN_CUS)) {
                        //内部员工
                        if (!oldProduct.getSupportInter()) {
                            map.put("msg", "该产品不支持内部员工购买");
                            return map;
                        }
                        if (contractDto.getAmount().compareTo(newProduct.getInterRemainingAmount()) >= 0) {
                            map.put("msg", "金额超出内部额度限制");
                            return map;
                        }
                        //内部收益比和每日收益
                        oldContract.setEarningRatio(newProduct.getInterEarningsRatio());
                        oldContract.setCycle(newProduct.getInterCycle());
                        oldContract.setEarningsDayAmount(EaringsUtil.getEverydayEarning(contractDto.getAmount(), newProduct.getInterEarningsRatio()));
                        remaingAmount = newProduct.getRemainingAmount().subtract(contractDto.getAmount());
                        //修改新产品内部剩余额度
                        productService.updateInterRamainAmount(newProduct.getInterRemainingAmount().subtract(contractDto.getAmount()),newProduct.getBuyCount() + 1, newProduct.getId());
                        //修改之前产品的内部额度
                        productService.updateInterRamainAmount(oldProduct.getInterRemainingAmount().add(oldContract.getAmount()), oldProduct.getBuyCount() -1, oldProduct.getId());
                    } else {
                        //外部客户
                        List<EarningsDto> earningsDtoList = earningsService.getEarningsList(contractDto.getProductId());
                        if (newProduct.getEarningsType().equals(EarningsType.FIXED)) {
                            //固定收益比
                            BigDecimal earningRatio = earningsDtoList.get(0).getOutEarningsRatio();
                            oldContract.setEarningRatio(earningRatio);
                            //固定收益比每天收益
                            oldContract.setEarningsDayAmount(EaringsUtil.getEverydayEarning(contractDto.getAmount(), earningRatio));
                            remaingAmount = newProduct.getRemainingAmount().subtract(contractDto.getAmount());
                        } else {
                            //阶梯收益
                            BigDecimal earningRatio = new BigDecimal(0);
                            BigDecimal dayEarning = new BigDecimal(0);
                            //获取最高阶段
                            EarningsDto earningLastDto = earningsDtoList.get(earningsDtoList.size() - 1);
                            //判断是否是最高阶段
                            if (contractDto.getAmount().compareTo(earningLastDto.getMinRange()) >= 0 && contractDto.getAmount().compareTo(earningLastDto.getMaxRange()) <= 0) {
                                earningRatio = earningLastDto.getOutEarningsRatio();
                                dayEarning = EaringsUtil.getEverydayEarning(contractDto.getAmount(), earningRatio);
                                remaingAmount = newProduct.getRemainingAmount().subtract(contractDto.getAmount());
                            }
                            for (EarningsDto earningDto : earningsDtoList) {
                                //如果合同金额大于等于最小值，小于最大值
                                if (contractDto.getAmount().compareTo(earningDto.getMinRange()) >= 0 && contractDto.getAmount().compareTo(earningDto.getMaxRange()) == -1) {
                                    earningRatio = earningDto.getOutEarningsRatio();
                                    dayEarning = EaringsUtil.getEverydayEarning(contractDto.getAmount(), earningRatio);
                                    remaingAmount = newProduct.getRemainingAmount().subtract(contractDto.getAmount());
                                }
                            }
                            oldContract.setEarningRatio(earningRatio);
                            oldContract.setEarningsDayAmount(dayEarning);
                            oldContract.setCycle(newProduct.getBillingCycle());
                        }
                        // 修改新产品对应金额
                        productService.updateRemainAmount(remaingAmount, newProduct.getBuyCount() + 1, newProduct.getId());
                    }
                    oldContract.setAmount(contractDto.getAmount());
//                    oldContract.setProductId(contractDto.getProductId());
                }else {
                    //没有修改客户
                    oldContract.setAmount(contractDto.getAmount());
                    remaingAmount = newProduct.getRemainingAmount().subtract(contractDto.getAmount());
                    // 修改新产品对应金额
                    if (customerDto.getType() == CustomType.IN_CUS) {
                        productService.updateInterRamainAmount(newProduct.getInterRemainingAmount().subtract(contractDto.getAmount()),newProduct.getBuyCount() + 1, newProduct.getId());
                    }else {
                        productService.updateRemainAmount(remaingAmount, newProduct.getBuyCount() + 1, newProduct.getId());
                    }
                }
                oldContract.setProductId(contractDto.getProductId());
                oldContract.setProductName(productService.getProductDtoById(contractDto.getProductId()).getName());
                //修改合同签约信息列表
                if (contractDto.getContractSignInfoDtos() != null) {
                    contractSignInfoService.deleteContractSignInfoList(contractDto.getId());
                    contractSignInfoService.insertContractSignInfoList(contractDto.getContractSignInfoDtos(), contractDto.getId());
                }
                oldContract.setSupportManagerAudit(newProduct.getSupportManagerAudit());
            }
            oldContract.setStartTime(contractDto.getStartTime());
            oldContract.setOpenName(contractDto.getOpenName());
            oldContract.setOpenBank(contractDto.getOpenBank());

            //---------------------------
            //oldContract.setBankAccount(contractDto.getBankAccount());
            oldContract.setBankAccount(BankAcountAes.getInstance().encrypt(contractDto.getBankAccount()));

            oldContract.setRemark(contractDto.getRemark());
            contractDao.updateByPrimaryKey(oldContract);

            ContractApply oldApply = contractApplyService.getApplyById(contractDto.getApplyId());
            oldApply.setEffective(false);
            contractApplyService.updateApply(oldApply);
            //插入申请数据
            ContractApply contractApply = addApply(contractDto, oldContract, newProduct);
            contractApply.setNum(contractApplyService.getApplyById(contractDto.getApplyId()).getNum() + 1);

            //--------------
            contractApply.setSourceId(contractDto.getSourceId());

            contractApplyService.addContractApply(contractApply);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("修改签约（" + contractDto.getNumber() + ")相关信息");
            if (!oldContractAmount.equals(contractDto.getAmount())) {
                operationDto.setRemark("签约金额：" + oldContractAmount + "到" + contractDto.getAmount());
            }
            operationService.insertOperation(operationDto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("msg", "合同更新失败");
//            return map;
//        }

        map.put("msg", "success");
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> changeContract(ContractDto contractDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Long contractId = contractDto.getId();
        Contract confirm = contractDao.getWaitApprovedContrctBySourceIdAndType(contractId, ContractType.CON);
        if (confirm != null) {
            map.put("msg", "该合同已处于转换申请中，请勿重复操作");
            return map;
        }
        List<PersonalIncome> incomeDto = incomeService.getIncomeListByApplyIdAndQuitDate(contractDto.getId(), contractDto.getStartTime());
        if (incomeDto.size() != 0) {
            map.put("msg", "转换日期后存在已申请结算的收益，不能申请转换");
            return map;
        }

        List<PersonalIncome> incomes = incomeService.getAfterQuitDateNAIncomeList(contractDto.getId(), contractDto.getStartTime());
        //冻结临退日期后的所有收益
        incomeService.updateIncomeList(incomes, true);
        Contract oldContract = contractDao.selectByPrimaryKey(contractId);
        CustomerDto customerDto = customerService.getCustomerInfoById(oldContract.getCustomerId());
        ProductDto newProduct = productService.getProductDtoById(contractDto.getProductId());
        //临时结算
        if (contractDao.getContractByNumber(contractDto.getNumber()) != null) {
            map.put("msg", "合同编号已存在");
            return map;
        }
//        try {
//            incomeService.insertIncome(dtoList);
            Contract newContract = new Contract();
        //因为此处合同金额为原本合同金额，所以合同金额为原合同金额
            if (oldContract.getAmount().compareTo(newProduct.getOutStartAmount()) == -1) {
                map.put("msg", "购买金额低于起购金额");
                return map;
            }
            if (oldContract.getAmount().compareTo(newProduct.getRemainingAmount()) == 1) {
                map.put("msg", "购买金额高于产品剩余金额");
                return map;
            }
            BigDecimal remaingAmount = new BigDecimal(0);
            if (customerDto.getType().equals(CustomType.IN_CUS)) {
                if (!newProduct.getSupportInter()) {
                    map.put("msg", "该产品不支持内部员工购买");
                    return map;
                }
                if (oldContract.getAmount().compareTo(newProduct.getInterRemainingAmount()) >= 0) {
                    map.put("msg", "金额超出内部额度限制");
                    return map;
                }
                //内部员工
                newContract.setEarningRatio(newProduct.getInterEarningsRatio());
                newContract.setCycle(newProduct.getInterCycle());
                //修改内部剩余额度
                productService.updateInterRamainAmount(newProduct.getInterQuota().subtract(oldContract.getAmount()),newProduct.getBuyCount() + 1, newProduct.getId());
                newContract.setEarningsDayAmount(EaringsUtil.getEverydayEarning(oldContract.getAmount(), newProduct.getInterEarningsRatio()));
                remaingAmount = newProduct.getRemainingAmount().subtract(oldContract.getAmount());
            } else {
                //外部客户
                List<EarningsDto> earningsDtoList = earningsService.getEarningsList(contractDto.getProductId());
                if (newProduct.getEarningsType().equals(EarningsType.FIXED)) {
                    //固定收益比
                    BigDecimal earningRatio = earningsDtoList.get(0).getOutEarningsRatio();
                    newContract.setEarningRatio(earningRatio);
                    //固定收益比每天收益
                    newContract.setEarningsDayAmount(EaringsUtil.getEverydayEarning(oldContract.getAmount(), earningRatio));
                    remaingAmount = newProduct.getRemainingAmount().subtract(oldContract.getAmount());
                } else {
                    //阶梯收益
                    BigDecimal earningRatio = new BigDecimal(0);
                    BigDecimal dayEarning = new BigDecimal(0);
                    //获取最高阶段
                    EarningsDto earningLastDto = earningsDtoList.get(earningsDtoList.size() - 1);
                    //判断是否是最高阶段
                    if (oldContract.getAmount().compareTo(earningLastDto.getMinRange()) >= 0 && oldContract.getAmount().compareTo(earningLastDto.getMaxRange()) <= 0) {
                        earningRatio = earningLastDto.getOutEarningsRatio();
                        dayEarning = EaringsUtil.getEverydayEarning(oldContract.getAmount(), earningRatio);
                        remaingAmount = newProduct.getRemainingAmount().subtract(oldContract.getAmount());
                    }
                    for (EarningsDto earningDto : earningsDtoList) {
                        //如果合同金额大于等于最小值，小于最大值
                        if (oldContract.getAmount().compareTo(earningDto.getMinRange()) >= 0 && oldContract.getAmount().compareTo(earningDto.getMaxRange()) == -1) {
                            earningRatio = earningDto.getOutEarningsRatio();
                            dayEarning = EaringsUtil.getEverydayEarning(oldContract.getAmount(), earningRatio);
                            remaingAmount = newProduct.getRemainingAmount().subtract(oldContract.getAmount());
                        }
                    }
                    newContract.setEarningRatio(earningRatio);
                    newContract.setEarningsDayAmount(dayEarning);
                }
                newContract.setCycle(newProduct.getBillingCycle());
                Integer butTimes = newProduct.getBuyCount() + 1;
                productService.updateRemainAmount(remaingAmount, butTimes, newProduct.getId());

            }
            newContract.setStartTime(contractDto.getStartTime());
            newContract.setEndTime(DateUtilPlus.getDateAddYear(contractDto.getStartTime(), newProduct.getCycle().intValue()));
            newContract.setOpenBank(contractDto.getOpenBank());
            newContract.setOpenName(contractDto.getOpenName());

            //-------------------
            //newContract.setBankAccount(contractDto.getBankAccount());
            newContract.setBankAccount(BankAcountAes.getInstance().encrypt(contractDto.getBankAccount()));

            newContract.setDuration(newProduct.getCycle().intValue());
            newContract.setType(ContractType.CON);
            newContract.setStruts(ContractStruts.DJH);
            newContract.setCreateTime(DateHelperPlus.getNow().getTime());
            //newContract.setApplyStruts(ApplicationStruts.BREV);  申请表的申请状态
            newContract.setTemporary(false);
            newContract.setValidity(false);
            newContract.setSourceId(contractId);
            newContract.setRemark(contractDto.getRemark());
            newContract.setProductId(contractDto.getProductId());
            newContract.setCustomerId(oldContract.getCustomerId());
            newContract.setUserId(oldContract.getUserId());
            newContract.setNumber(contractDto.getNumber());
            newContract.setProductName(newProduct.getName());
            newContract.setAmount(oldContract.getAmount());
            newContract.setChanges(true);
            newContract.setChangeProductId(contractDto.getOldProductId());
            newContract.setChangeContractId(contractDto.getId());
            newContract.setStateChangeType(StateChangeType.HCON);
            newContract.setSupportManagerAudit(newProduct.getSupportManagerAudit());
            contractDao.insert(newContract);
            insertConfirmInfo(newContract.getId());
            //插入合同签约信息列表
            if (contractDto.getContractSignInfoDtos() != null) {
                contractSignInfoService.insertContractSignInfoList(contractDto.getContractSignInfoDtos(), newContract.getId());
            }
            //FIXME 转换合同，向临退记录插入数据
            ContractQuitInfo quitInfo = new ContractQuitInfo();
            quitInfo.setQuitDate(contractDto.getStartTime());
            quitInfo.setApplyId(contractDto.getId());
            quitInfo.setQuitPoundage(new BigDecimal(0));
            quitInfo.setQuitAmount(null);
            quitInfo.setCreateTime(DateHelperPlus.getNow().getTime());
            quitInfoService.insertQuitInfo(quitInfo);

            ContractApply contractApply = addApply(contractDto, newContract, newProduct);
            contractApply.setSourceId(contractDto.getId());
            contractApply.setNum(1);
            contractApplyService.addContractApply(contractApply);


            contractDao.updateStateChangeType(contractId,StateChangeType.HCON);

//            //修改产品剩余金额
//
//            Integer butTimes = newProduct.getBuyCount() + 1;
//            productService.updateRemainAmount(remaingAmount, butTimes, newProduct.getId());
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("转换合同(" + oldContract.getNumber() + "到" + contractDto.getNumber());
            operationService.insertOperation(operationDto);
            map.put("msg", "success");

//        } catch (Exception e) {
//            map.put("msg", "合同转换失败");
//            return map;
//        }

        map.put("msg", "success");
        //新增合同
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> continueSign(ContractDto contractDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
//        try {
            Long contractId = contractDto.getId();

            Contract confirm = contractDao.getWaitApprovedContrctBySourceIdAndType(contractId, ContractType.CON);
            if (confirm != null) {
                map.put("msg", "该合同已处于续签申请中，请勿重复操作");
                return map;
            }
            Contract old = contractDao.selectByPrimaryKey(contractId);
            CustomerDto customerDto = customerService.getCustomerInfoById(old.getCustomerId());
            ProductDto productDto = productService.getProductDtoById(old.getProductId());
            if (contractDto.getStartTime().after(productDto.getCloseTime())) {
                map.put("msg", "开始时间超出产品销售时间范围");
                return map;
            }
            if (contractDao.getContractByNumber(contractDto.getNumber()) != null) {
                map.put("msg", "该合同编号已存在，请重新输入");
                return map;
            }
            if (customerDto.getType() == CustomType.IN_CUS) {
                if (productDto.getInterRemainingAmount().compareTo(old.getAmount()) == -1 ) {
                    map.put("msg", "金额超出内部限额，无法续签");
                    return map;
                }
            }else {
                if (productDto.getRemainingAmount().compareTo(old.getAmount()) == -1) {
                    map.put("msg", "金额超出产品限额，无法续签");
                    return map;
                }
            }
            Contract newContract = new Contract();
            newContract.setProductId(old.getProductId());
            newContract.setCustomerId(old.getCustomerId());
            newContract.setUserId(old.getUserId());
            newContract.setNumber(contractDto.getNumber());
            newContract.setProductName(old.getProductName());
            newContract.setAmount(old.getAmount());
            newContract.setChanges(false);
            newContract.setValidity(true);
            newContract.setTemporary(false);
            newContract.setEarningRatio(old.getEarningRatio());
            newContract.setEarningsDayAmount(old.getEarningsDayAmount());
            newContract.setStartTime(contractDto.getStartTime());
            newContract.setEndTime(DateUtilPlus.getDateAddYear(contractDto.getStartTime(), productDto.getCycle().intValue()));
            newContract.setOpenBank(contractDto.getOpenBank());
            newContract.setOpenName(contractDto.getOpenName());
            newContract.setSupportManagerAudit(old.getSupportManagerAudit());
            //---------------------
            //newContract.setBankAccount(contractDto.getBankAccount());
            newContract.setBankAccount(BankAcountAes.getInstance().encrypt(contractDto.getBankAccount()));


            //newContract.setApplyStruts(ApplicationStruts.BREV);
            newContract.setSourceId(contractId);
            newContract.setType(ContractType.REN);
            newContract.setStruts(ContractStruts.DJH);
            newContract.setDuration(productDto.getCycle().intValue());
            newContract.setRemark(contractDto.getRemark());
            newContract.setCreateTime(DateHelperPlus.getNow().getTime());
            newContract.setCycle(old.getCycle());
            newContract.setStateChangeType(StateChangeType.HREN);
            contractDao.insert(newContract);
            insertConfirmInfo(newContract.getId());

            //插入合同签约信息列表
            if (contractDto.getContractSignInfoDtos() != null) {
                contractSignInfoService.insertContractSignInfoList(contractDto.getContractSignInfoDtos(), newContract.getId());
            }

            ContractApply contractApply = addApply(contractDto, newContract, productDto);
            contractApply.setSourceId(contractDto.getApplyId());
            contractApply.setNum(1);
            contractApplyService.addContractApply(contractApply);

            contractDao.updateStateChangeType(contractId,StateChangeType.HREN);


            Integer butTimes = productDto.getBuyCount() + 1;
            if (customerDto.getType() == CustomType.IN_CUS) {
                productService.updateInterRamainAmount(productDto.getInterRemainingAmount().subtract(old.getAmount()), productDto.getBuyCount() + 1, productDto.getId());
            }else {
                //修改产品剩余金额
                BigDecimal remaingAmount = productDto.getRemainingAmount().subtract(old.getAmount());
                productService.updateRemainAmount(remaingAmount, butTimes, productDto.getId());
            }
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("续签合同(" + old.getNumber() + ")");
            operationService.insertOperation(operationDto);
            map.put("msg", "success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("msg", "续签失败");
//        }
        //新增合同信息
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> quitContract(ContractDto contractDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ContractApply contractApply = contractApplyService.getApplyById(contractDto.getApplyId());
        Contract contract = contractDao.selectByPrimaryKey(contractDto.getId());
        ContractApply apply = contractApplyService.getWaitApplyByContractId(contractDto.getId(), ContractType.QUI);
        if (apply != null) {
            map.put("msg", "该合同已处于临退，请勿重复提交");
            return map;
        }
        List<PersonalIncome> incomeDto = incomeService.getIncomeListByApplyIdAndQuitDate(contractDto.getId(), contractDto.getQuitDate());
        if (incomeDto.size() != 0) {
            map.put("msg", "临退日期后存在已申请结算的收益，不能申请临退");
            return map;
        }
        Long applyId = getPersonalIncomeDtos(contractDto, contract.getSupportManagerAudit());
        List<PersonalIncome> incomes = incomeService.getAfterQuitDateNAIncomeList(contractDto.getId(), contractDto.getQuitDate());
        //冻结临退日期后的所有收益
        incomeService.updateIncomeList(incomes, true);
        ContractQuitInfo quitInfo = new ContractQuitInfo();
        quitInfo.setQuitDate(contractDto.getQuitDate());
        quitInfo.setApplyId(applyId);
        quitInfo.setQuitPoundage(contractDto.getQuitPoundage());
        quitInfo.setQuitAmount(contract.getAmount().subtract(contractDto.getQuitPoundage()));
        quitInfo.setCreateTime(DateHelperPlus.getNow().getTime());
        List<ContractConfirmInfo> infoList = confirmInfoDao.getConfirmInfoByContractId(contractDto.getId());
        for (ContractConfirmInfo confirmInfo : infoList) {
            confirmInfo.setInfoConfirm(false);
            confirmInfo.setReceiveConfirm(false);
            confirmInfo.setIfPrint(false);
            confirmInfo.setAmountConfirm(false);
            confirmInfoDao.updateByPrimaryKey(confirmInfo);
        }
        quitInfoService.insertQuitInfo(quitInfo);

        contractDao.updateStateChangeType(contract.getId(),StateChangeType.HQUI);

        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("临退合同(" + contract.getNumber() + ")");
        operationService.insertOperation(operationDto);
        map.put("msg", "success");
        //FIXME 在审批通过之后合同设置为关闭
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> approvedAgreeSign(Long applyId, Long userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //FIXME 审批
//        try {
            ContractApply contractApply = contractApplyService.getWaitApplyById(applyId);
            Long contractId = contractApply.getContractId();
            Contract contract = contractDao.selectByPrimaryKey(contractApply.getContractId());
            ContractApplyAuditRecording auditRecording = new ContractApplyAuditRecording();
            ProductDto productDto = productService.getProductDtoById(contract.getProductId());
            Long roleId = roleDao.findRoleIdListByUserId(userId).get(0);

            if (contractApply.getType().equals(ContractType.QUI)) {
                //临退申请
                if (roleId == 3) {
                    contractApply.setApprovedStruts(1);
                } else if (roleId == 4) {
                    contractApply.setApprovedStruts(2);
                } else if (roleId == 5) {


                    //contract.setApplyStruts(ApplicationStruts.CTHR);

                    ContractQuitInfo quitInfo = quitInfoService.getInfoByContractId(applyId);
                    //合同设置为关闭
                    contract.setTemporary(true);
                    contract.setStruts(ContractStruts.YGB);
                    contractDao.updateByPrimaryKey(contract);
                    //FIXME 合同对应的临退日期后面的个人收益设置为作废
                    List<PersonalIncome> incomes = incomeService.getAfterQuitDateIncomeList(contractId, quitInfo.getQuitDate());
                    for (PersonalIncome income : incomes) {
                        income.setStruts(ApprovalType.H_I);
                        incomeService.updateIncomeStruts(income);
                    }
                    contractApply.setApprovedStruts(3);
                    contractApply.setOperation(true);
                    contractApply.setApplyStruts(ApplicationStruts.CTHR);
                    contractApply.setAuditStruts(AuditStruts.CTHR);
                    List<PersonalIncomeDto> incomeDtoList = getPersonalIncomeDtoList(quitInfo.getQuitPoundage(), contract, productDto, quitInfo.getQuitDate());
                    incomeService.insertIncomeDtoList(incomeDtoList, applyId, userService.getUserDtoById(contract.getUserId()).getRealname());
                }
            } else {
                //其他申请
                if (roleId == 3) {
                    contractApply.setApprovedStruts(1);
                } else if (roleId == 4) {
                    contractApply.setApprovedStruts(2);
                } else if (roleId == 5) {


                    //contract.setApplyStruts(ApplicationStruts.CTHR);


                    if (contractApply.getType().equals(ContractType.CON)) {
                        //如果是转换的话，将之前合同关闭
                        Contract oldContract = contractDao.selectByPrimaryKey(contract.getChangeContractId());
                        oldContract.setTemporary(true);
                        oldContract.setStruts(ContractStruts.YGB);
                        contractDao.updateByPrimaryKey(oldContract);
                        //合同对应的转换日期后面的个人收益设置为作废
                        Contract newContract = contractDao.selectByPrimaryKey(contractId);
                        //激活新合同
                        newContract.setStruts(ContractStruts.YJH);
                        contractDao.updateByPrimaryKey(newContract);
                        ContractQuitInfo quitInfo = quitInfoService.getInfoByContractId(contractApply.getSourceId());
                        List<PersonalIncome> incomes = incomeService.getAfterQuitDateIncomeList(oldContract.getId(), quitInfo.getQuitDate());
                        for (PersonalIncome income : incomes) {
                            income.setStruts(ApprovalType.H_I);
                            incomeService.updateIncomeStruts(income);
                        }
                        //-----------
                        List<PersonalIncomeDto> incomeDtoList = getPersonalIncomeDtoList(quitInfo.getQuitPoundage(), oldContract, productDto, quitInfo.getQuitDate());
                        for(int i=0;i<incomeDtoList.size();i++){
                            if(incomeDtoList.get(i).getSeq()==-1){
                                incomeDtoList.remove(i);
                            }
                        }
                        incomeService.insertIncomeDtoList(incomeDtoList, applyId, userService.getUserDtoById(oldContract.getUserId()).getRealname());

                    }else if (contractApply.getType().equals(ContractType.REN)){
                        Contract newContract = contractDao.selectByPrimaryKey(contractId);
                        //激活新合同
                        newContract.setStruts(ContractStruts.YJH);
                        contractDao.updateByPrimaryKey(contract);
                    }else {
                        Contract contract1 = contractDao.selectByPrimaryKey(contractId);
                        contract1.setStruts(ContractStruts.YJH);
                        contractDao.updateByPrimaryKey(contract1);
                    }
                    contractApply.setApprovedStruts(3);
                    contractApply.setOperation(true);
                    contractApply.setAuditStruts(AuditStruts.CTHR);
                    contractApply.setApplyStruts(ApplicationStruts.CTHR);
                    SettleType settleType = contract.getCycle();
                    ContractDto contractDto = contractToContractDto(contract);
                    contractDto.setUser(userService.queryUser(userId));
                    contractDto.setIntOrB(productDto.getIntOrB());
                    if (settleType.equals(SettleType.MON)) {
                        PersonalIncome[] personalIncomes = PersonalIncomeUtil.sum(contractDto, 12, 1);
                        List<PersonalIncome> incomeList = PersonalIncomeUtil.getCycleMoney(contractDto, personalIncomes, 1);
                        incomeService.insertIncome(incomeList, contract, applyId);
                    } else if (settleType.equals(SettleType.QUAR)) {
                        PersonalIncome[] personalIncomes = PersonalIncomeUtil.sum(contractDto, 12, 3);
                        List<PersonalIncome> incomeList = PersonalIncomeUtil.getCycleMoney(contractDto, personalIncomes, 3);
                        incomeService.insertIncome(incomeList, contract, applyId);
                    }else if (settleType.equals(SettleType.YEAR)) {
                        PersonalIncome[] personalIncomes = PersonalIncomeUtil.sum(contractDto, 12, 12);
                        List<PersonalIncome> incomeList = PersonalIncomeUtil.getCycleMoney(contractDto, personalIncomes, 12);
                        incomeService.insertIncome(incomeList, contract, applyId);
                    }
                }
            }
            auditRecording.setApplyId(contractApply.getId());
            auditRecording.setUserId(userId);
            auditRecording.setStruts(true);
            auditRecording.setAuditTime(DateHelperPlus.getNow().getTime());
            auditRecording.setCreateTime(DateHelperPlus.getNow().getTime());
            contractApplyService.updateApply(contractApply);
            contractApplyAuditRecordingService.addRecording(auditRecording);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("审批合同(" + contract.getNumber() + ")信息通过");
            operationService.insertOperation(operationDto);

//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("msg", "审批确认失败");
//        }
        map.put("msg", "success");
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> approvedRejectSign(Long applyId, Long userId, String remark) throws Exception {
        Map<String, Object> map = new HashMap<>();


        ContractApply contractApply = contractApplyService.getWaitApplyById(applyId);
        Long contractId = contractApply.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        ContractApplyAuditRecording auditRecording = new ContractApplyAuditRecording();
        ProductDto productDto = productService.getProductDtoById(contract.getProductId());
//        Long roleId = roleDao.findRoleIdListByUserId(userId).get(0);
        if (contractApply.getType().equals(ContractType.QUI)){
            contract.setTemporary(false);
            contractDao.updateByPrimaryKey(contract);
            List<PersonalIncome> quitIncome = incomeService.getIncomeListByFreezeByContractId(contractId);
            incomeService.updateIncomeList(quitIncome, false);
        }else if (contractApply.getType().equals(ContractType.CON)) {
            List<PersonalIncome> quitIncome = incomeService.getIncomeListByFreezeByContractId(contractId);
            incomeService.updateIncomeList(quitIncome, false);
        }
        CustomerDto customerDto = customerService.getCustomerInfoById(contract.getCustomerId());
        if (customerDto.getType() == CustomType.IN_CUS) {
            productDto.setInterRemainingAmount(productDto.getInterRemainingAmount().add(contract.getAmount()));
        }else {
            productDto.setRemainingAmount(productDto.getRemainingAmount().add(contract.getAmount()));
        }
        productDto.setBuyCount(productDto.getBuyCount()-1);
        productService.updateProduct(productDto);

        contractDao.updateStateChangeType(contract.getId(),StateChangeType.ND);

    //contract.setApplyStruts(ApplicationStruts.AREJ);

        auditRecording.setAuditTime(DateHelperPlus.getNow().getTime());
        auditRecording.setStruts(false);
        auditRecording.setUserId(userId);
        auditRecording.setApplyId(contractApply.getId());
        auditRecording.setRemark(remark);
        auditRecording.setCreateTime(DateHelperPlus.getNow().getTime());
        contractApplyAuditRecordingService.addRecording(auditRecording);
        contractApply.setAuditStruts(AuditStruts.AREJ);
        contractApply.setApplyStruts(ApplicationStruts.AREJ);
        contractApply.setApprovedStruts(-1);
        contractApplyService.updateApply(contractApply);
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        if(roleId==4 || roleId==5){
            contractConfirmInfoService.updateInfoAmountConfirm(false,false,contractId,4l);
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("审批合同(" + contract.getNumber() + ")信息拒绝");
        operationService.insertOperation(operationDto);

        map.put("msg", "success");
        return map;
    }

    @Override
    public Map<String, Object> signInfoConfirm(Long applyId, Long userId) {
        Map<String, Object> map = new HashMap<>();
        ContractConfirmInfo confirmInfo = getConfirmInfo(applyId, userId);
        confirmInfo.setInfoConfirm(true);
        confirmInfoDao.updateByPrimaryKey(confirmInfo);
        map.put("msg", "success");
        return map;
    }

    @Override
    public Map<String, Object> signReceiveConfirm(Long applyId, Long userId) {
        Map<String, Object> map = new HashMap<>();
        ContractConfirmInfo confirmInfo = getConfirmInfo(applyId, userId);
        confirmInfo.setReceiveConfirm(true);
        confirmInfoDao.updateByPrimaryKey(confirmInfo);
        map.put("msg", "success");
        return map;
    }

    @Override
    public Map<String, Object> signAmountConfirm(Long applyId, Long userId) {
        Map<String, Object> map = new HashMap<>();
        ContractConfirmInfo confirmInfo = getConfirmInfo(applyId, userId);
        confirmInfo.setAmountConfirm(true);
        confirmInfoDao.updateByPrimaryKey(confirmInfo);
        map.put("msg", "success");
        return map;
    }

    private ContractConfirmInfo getConfirmInfo(Long applyId, Long userId) {
        ContractApply apply = contractApplyService.getApplyById(applyId);
        Long contractId = apply.getContractId();
        Long roleId = roleDao.findRoleIdListByUserId(userId).get(0);
        ContractConfirmInfo confirmInfo = confirmInfoDao.getConfirmInfoByContractIdAndRoleId(contractId, roleId);
        return confirmInfo;
    }

    @Override
    public Long getContractNumberByProductId(Long productId) {
        return contractDao.getContractNumberByProductId(productId);
    }

    public ContractDto contractToContractDto(Contract contract) {
        ContractDto contractDto = new ContractDto();
        contractDto.setId(contract.getId());
        contractDto.setProductId(contract.getProductId());
        ProductDto productDto = productService.getProductDtoById(contract.getProductId());
        contractDto.setChangeAble(productDto.getSupportConversion());
        CustomerDto customDto = customerService.getCustomerInfoById(contract.getCustomerId());
        //设置客户对象
        contractDto.setCustomerName(customDto.getName());
        contractDto.setCustomType(customDto.getType());
        contractDto.setCustomerId(customDto.getId());

        //设置是否内部可退
        contractDto.setSupportQuit(productDto.getSupportQuit());

        //设置业务员
        contractDto.setUser(userService.getUserDtoById(contract.getUserId()));

        //设置合同签约列表
        contractDto.setContractSignInfoDtos(contractSignInfoService.getContractSignInfoList(contractDto.getId()));

        contractDto.setNumber(contract.getNumber());
        contractDto.setProductName(contract.getProductName());
        contractDto.setAmount(contract.getAmount());
        contractDto.setChanges(contract.getChanges());

        contractDto.setChangeProduct(productService.getProductDtoById(contract.getChangeProductId()));

        contractDto.setEarningRatio(contract.getEarningRatio());
        contractDto.setEarningsDayAmount(contract.getEarningsDayAmount());
        contractDto.setStartTime(contract.getStartTime());
        contractDto.setEndTime(contract.getEndTime());
        contractDto.setOpenBank(contract.getOpenBank());
        contractDto.setOpenName(contract.getOpenName());
        contractDto.setBankAccount(contract.getBankAccount());
        contractDto.setSource(contract.getSource());
        contractDto.setDuration(contract.getDuration());
        contractDto.setType(contract.getType());
        contractDto.setStruts(contract.getStruts());

        //contractDto.setApplyStruts(contract.getApplyStruts());

        contractDto.setTemporary(contract.getTemporary());
        contractDto.setRemark(contract.getRemark());
        contractDto.setSupportManagerAudit(contract.getSupportManagerAudit());
        contractDto.setStateChangeType(contract.getStateChangeType());
        contractDto.setSourceId(contract.getSourceId());
        contractDto.setCreateTime(contract.getCreateTime());
        return contractDto;
    }

    private ContractApply addApply(ContractDto contractDto, Contract contract, ProductDto productDto) {
        ContractApply contractApply = new ContractApply();
        contractApply.setContractId(contract.getId());
        contractApply.setUserId(contractDto.getUserId());
        contractApply.setApplyTime(DateHelperPlus.getNow().getTime());
        contractApply.setAuditStruts(AuditStruts.WAIT);
        contractApply.setApplyStruts(ApplicationStruts.BREV);
        contractApply.setOperation(false);
        contractApply.setType(contract.getType());
        Long roleId = roleDao.findRoleIdListByUserId(contractDto.getUserId()).get(0);
        contractApply.setEffective(true);
        if (productDto.getSupportManagerAudit()) {
            contractApply.setSupportManagerAudit(true);
        } else {
            contractApply.setSupportManagerAudit(false);
        }
        if (roleId.equals(3)) {
            contractApply.setApprovedStruts(1);
        } else {
            if (productDto.getSupportManagerAudit()) {
                contractApply.setApprovedStruts(0);
            } else {
                contractApply.setApprovedStruts(1);
            }
        }
        contractApply.setCreateTime(DateHelperPlus.getNow().getTime());
        return contractApply;

    }

    /**
     * 获取最后一笔天数
     *
     * @param contract
     * @param productDto
     * @param quitDate
     */
    private Map<String, Object> getLastIncomeDays(Contract contract, ProductDto productDto, Date quitDate) {
        ContractDto contractDto = contractToContractDto(contract);
        Integer productCycle;
        CustomerDto customerDto = customerService.getCustomerInfoById(contract.getCustomerId());
        if (CustomType.IN_CUS.equals(customerDto.getType())) {
            //内部员工
            if (SettleType.MON.equals(productDto.getInterCycle())) {
                productCycle = 1;
            }else if (SettleType.QUAR.equals(productDto.getInterCycle())){
                productCycle = 3;
            }else {
                productCycle = 12;
            }
        } else {
            if (SettleType.MON.equals(productDto.getBillingCycle())) {
                productCycle = 1;
            }else if (SettleType.QUAR.equals(productDto.getInterCycle())){
                productCycle = 3;
            }else {
                productCycle = 12;
            }
        }
        contractDto.setIntOrB(productDto.getIntOrB());
        PersonalIncome[] incomes = PersonalIncomeUtil.sum(contractDto, 12, productCycle);
        List<PersonalIncome> personalIncomeList = PersonalIncomeUtil.getCycleMoney(contractDto, incomes, productCycle);
        Map<String, Object> map = PersonalIncomeUtil.getRandom(contractDto, personalIncomeList, quitDate, productCycle);

        return map;
    }

    private Long getPersonalIncomeDtos(ContractDto contractDto, Boolean boo) {
        Contract contract = contractDao.selectByPrimaryKey(contractDto.getId());
        ProductDto productDto = productService.getProductDtoById(contract.getProductId());
        //获取临退时间
        Date quitDate = contractDto.getQuitDate();
        //TODO 计算临退结息

        /**
         * 1、结算申请合同插入数据
         * 2、临退记录插入数据
         * 3、待审批通过之后修改合同状态为已关闭，个人收益从结算日期开始后面的结算作废
         */
        ContractApply contractApply = new ContractApply();
        contractApply.setNum(1);
        contractApply.setSupportManagerAudit(boo);
        contractApply.setApplyStruts(ApplicationStruts.BREV);
        contractApply.setUserId(contract.getUserId());
        contractApply.setContractId(contract.getId());
        contractApply.setApplyTime(quitDate);
        contractApply.setEffective(true);
        contractApply.setType(ContractType.QUI);
        contractApply.setAuditStruts(AuditStruts.WAIT);
        Long roleId = roleDao.findRoleIdListByUserId(contractDto.getUserId()).get(0);
        if (roleId == 3) {
            contractApply.setApprovedStruts(1);
        } else {
            if (productDto.getSupportManagerAudit()) {
                contractApply.setApprovedStruts(0);
            } else {
                contractApply.setApprovedStruts(1);
            }
        }
        contractApply.setCreateTime(DateHelperPlus.getNow().getTime());
        return contractApplyService.addContractApply(contractApply);
//        return getPersonalIncomeDtoList(contractDto, contract, productDto, quitDate);
    }


    /**
     * 计算临退最后一笔和本金
     *
     * @return
     */
    private List<PersonalIncomeDto> getPersonalIncomeDtoList(BigDecimal quitPoundage, Contract contract, ProductDto productDto, Date quitDate) {
        String userRealname = userService.getUserDtoById(contract.getUserId()).getRealname();
        String customerName = customerService.getCustomerInfoById(contract.getCustomerId()).getName();
        List<PersonalIncomeDto> dtoList = new ArrayList<>();

        PersonalIncomeDto incomeDto2 = new PersonalIncomeDto();
        Map<String, Object> incomeMap = getLastIncomeDays(contract, productDto, quitDate);
        //距离最后一次结息天数
        Integer days = Integer.parseInt(incomeMap.get("days").toString());
        List<PersonalIncome> list = (List<PersonalIncome>) incomeMap.get("list");
        Date startTime = list.get(0).getSettleStartTime();
        Date endTime = list.get(0).getSettleEndTime();
        //最后一笔收益值
        BigDecimal lastIncome = contract.getEarningsDayAmount().multiply(new BigDecimal(days));
        incomeDto2.setUserRealname(userRealname);
        incomeDto2.setCustomerName(customerName);
        incomeDto2.setIncomeNumber(UUID.randomUUID().toString());
        incomeDto2.setIncome(lastIncome);
        incomeDto2.setSettleStartTime(startTime);
        incomeDto2.setSettleEndTime(endTime);
        incomeDto2.setEndTime(list.get(0).getEndTime());
        incomeDto2.setUserId(contract.getUserId());
        incomeDto2.setContractNumber(contract.getNumber());
        incomeDto2.setCustomerId(contract.getCustomerId());
        incomeDto2.setQuit(true);
        incomeDto2.setPrincipal(false);
        incomeDto2.setProductId(contract.getProductId());
        incomeDto2.setDayNums(days);
        incomeDto2.setIncomeType(IncomeType.T_I);
        incomeDto2.setStruts(ApprovalType.N_A);
        incomeDto2.setContractId(contract.getId());
        incomeDto2.setSeq(list.get(0).getSeq());
        dtoList.add(incomeDto2);

        //本金
        PersonalIncomeDto incomeDto1 = new PersonalIncomeDto();
        incomeDto1.setQuit(true);
        incomeDto1.setIncomeNumber(UUID.randomUUID().toString());
        incomeDto1.setUserRealname(userRealname);
        incomeDto1.setCustomerName(customerName);
        incomeDto1.setIncome(contract.getAmount().subtract(quitPoundage));
        incomeDto1.setUserId(contract.getUserId());
        incomeDto1.setSettleStartTime(list.get(1).getSettleStartTime());
        incomeDto1.setSettleEndTime(list.get(1).getSettleEndTime());
        incomeDto1.setEndTime(list.get(1).getEndTime());
        incomeDto1.setContractNumber(contract.getNumber());
        incomeDto1.setCustomerId(contract.getCustomerId());
        incomeDto1.setPrincipal(true);
        incomeDto1.setDayNums(0);
        incomeDto1.setProductId(contract.getProductId());
        incomeDto1.setContractId(contract.getId());
        incomeDto1.setIncomeType(IncomeType.R_P);
        incomeDto1.setStruts(ApprovalType.N_A);
        incomeDto1.setSeq(-1);
        //新增本金，
        dtoList.add(incomeDto1);
        return dtoList;
    }

    /**
     * 新增财务出纳审批信息确认记录
     */
    private void insertConfirmInfo(Long contractId) {
        ContractConfirmInfo confirmInfo1 = new ContractConfirmInfo();
        confirmInfo1.setContractId(contractId);
        confirmInfo1.setRoleId(4L);
        confirmInfo1.setInfoConfirm(false);
        confirmInfo1.setReceiveConfirm(false);
        confirmInfo1.setAmountConfirm(false);
        confirmInfo1.setIfPrint(true);
        confirmInfo1.setCreateTime(DateHelperPlus.getNow().getTime());
        ContractConfirmInfo confirmInfo2 = new ContractConfirmInfo();
        confirmInfo2.setContractId(contractId);
        confirmInfo2.setRoleId(5L);
        confirmInfo2.setInfoConfirm(true);
        confirmInfo2.setReceiveConfirm(true);
        confirmInfo2.setAmountConfirm(false);
        confirmInfo2.setIfPrint(true);
        confirmInfo2.setCreateTime(DateHelperPlus.getNow().getTime());
        confirmInfoDao.insert(confirmInfo1);
        confirmInfoDao.insert(confirmInfo2);
    }
}
