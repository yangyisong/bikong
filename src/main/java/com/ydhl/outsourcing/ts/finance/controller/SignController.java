package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.base.report.ContractReportVo;
import com.ydhl.outsourcing.ts.finance.base.report.ReportConstant;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.PersonalIncomeUtil;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dto.*;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractApplyQueryDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto2;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.enums.CustomType;
import com.ydhl.outsourcing.ts.finance.enums.FeeType;
import com.ydhl.outsourcing.ts.finance.enums.SettleType;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.ContractConfirmInfo;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签约控制器
 * Created by dell on 2018/1/10.
 */
@Controller
@RequestMapping("/sign")
public class SignController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ProductSignInfoService productSignInfoService;

    @Autowired
    private ContractApplyAuditRecordingService contractApplyAuditRecordingService;

    @Autowired
    private ContractApplyService contractApplyService;

    @Autowired
    private ContractConfirmInfoService contractConfirmInfoService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private EarningsService earningsService;

    @Autowired
    private ContractSignInfoService contractSignInfoService;

    @Autowired
    private RoleDao roleDao;

    /**
     * 签约列表
     *
     * @return
     */
    @RequestMapping(value = "/contractList", method = RequestMethod.GET)
    public ModelAndView contractList(PageModel pageModel, ContractQueryDto2 contractQueryDto) throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        Long userId = CurrentUserUtils.currentUserId();
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(userId).get(0));
        if(role.getId()==2){
            contractQueryDto.setUserId(userId);
        }
        //合同总计金额
        Map<Object, Object> result = contractService.queryContractDtoListPage2(pageModel,contractQueryDto);
        mav.addObject("role", role);
        mav.addObject("pageInfo", result.get("pageInfo"));
        mav.addObject("totalAmount", result.get("totalAmount"));
        mav.addObject("contractQueryDto", contractQueryDto);
        mav.setViewName("sign/contractList");
        return mav;
    }

    /**
     * 签约列表
     *
     * @return
     */
    @RequestMapping(value = "/signList", method = RequestMethod.GET)
    public ModelAndView signList(PageModel pageModel, ContractQueryDto contractQueryDto) throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        Map<Object, Object> result = contractService.queryContractDtoListPage(pageModel, contractQueryDto);
        //合同总计金额
        mav.addObject("pageInfo", result.get("pageInfo"));
        mav.addObject("totalAmount", result.get("totalAmount"));
        mav.addObject("contractQueryDto", contractQueryDto);
        mav.setViewName("sign/signList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看签约列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 新增签约页面跳转
     *
     * @return
     * @throws BusinessException
     */
    @GetMapping("/signAdd.html")
    public ModelAndView toSignAdd() throws BusinessException {
        ModelAndView mav = new ModelAndView();
        Long userId = CurrentUserUtils.currentUserId();
        List<CustomerDto> dtoList = customerService.getAllCustomreByUserId(userId);
        List<ProductDto> productList = productService.getAllShelveProduct();
        mav.addObject("customerList", dtoList);
        mav.addObject("productList", productList);
        mav.setViewName("sign/signAdd");
        return mav;
    }

    /**
     * 新增签约
     *
     * @return
     */
    @PostMapping(value = "/addSign.do")
    @ResponseBody
    public Map<String, Object> addSign(ContractDto contractDto) throws Exception {
        Long userId = CurrentUserUtils.currentUserId();
        contractDto.setUserId(userId);
        Map<String, Object> map = contractService.insertContract(contractDto);
        return map;
    }

    /**
     * 签约编辑
     *
     * @param contractDto
     * @return
     */
    @PostMapping(value = "/editSign.do")
    @ResponseBody
    public Map<String, Object> editSign(ContractDto contractDto) throws Exception {
        Map<String, Object> map = contractService.updateContract(contractDto);
        return map;
    }

    /**
     * 临退
     *
     * @param contractDto
     * @return
     */
    @PostMapping(value = "/quitSign.do")
    @ResponseBody
    public Map<String, Object> quitSign(ContractDto contractDto) throws Exception {
        Map<String, Object> map = contractService.quitContract(contractDto);
        return map;
    }

    /**
     * 转换签约
     * @param contractDto
     * @return
     */
    @PostMapping(value = "/changeSign.do")
    @ResponseBody
    public Map<String, Object> changeSign(ContractDto contractDto) throws Exception {
        //手续费设置为0
        contractDto.setQuitPoundage(new BigDecimal(0));
        contractDto.setQuitDate(contractDto.getStartTime());
        Map<String, Object> map = contractService.changeContract(contractDto);
        return map;
    }

    /**
     * 续签
     *
     * @param contractDto
     * @return
     */
    @PostMapping(value = "/continueSign.do")
    @ResponseBody
    public Map<String, Object> continueSign(ContractDto contractDto) throws Exception {
        Map<String, Object> map = contractService.continueSign(contractDto);
        return map;
    }

    /**
     * 签约编辑页面
     *
     * @return
     */
    @GetMapping("/signEdit.html")
    public ModelAndView toSignEdit(Long applyId) throws BusinessException {
        ModelAndView mav = new ModelAndView();
        Long userId = CurrentUserUtils.currentUserId();
        ContractDto contractDto = contractService.getContractById(applyId);
        List<ProductDto> productList = productService.getAllShelveProduct();
        List<ProductSignInfoDto> productSignInfoDtos = productSignInfoService.getProductSignInfoList(contractDto.getProductId());
        List<CustomerDto> dtoList = customerService.getAllCustomreByUserId(userId);
        mav.addObject("applyId", applyId);
        mav.addObject("contract", contractDto);
        mav.addObject("customerList", dtoList);
        mav.addObject("productSignInfoDtos", productSignInfoDtos);
        mav.addObject("productList", productList);
        mav.setViewName("sign/signEdit");
        return mav;
    }

    /**
     * 签约续签页面
     *
     * @return
     */
    @GetMapping("/signContinue.html")
    public ModelAndView toSignContinue(Long contractId) throws BusinessException {
        ModelAndView mav = new ModelAndView();
        Long userId = CurrentUserUtils.currentUserId();
        ContractDto contractDto = contractService.getContractById(contractId);
        List<ProductDto> productList = productService.getAllShelveProduct();
        List<CustomerDto> dtoList = customerService.getAllCustomreByUserId(userId);
        List<ProductSignInfoDto> productSignInfoDtos = productSignInfoService.getProductSignInfoList(contractDto.getProductId());
        mav.addObject("productSignInfoDtos", productSignInfoDtos);
        mav.addObject("contractId", contractId);
        mav.addObject("contract", contractDto);
        mav.addObject("customerList", dtoList);
        mav.addObject("productList", productList);
        mav.setViewName("sign/signContinue");
        return mav;
    }

    /**
     * 临退签约页面
     *
     * @return
     */
    @GetMapping("/signQui.html")
    public ModelAndView toSignQui(Long contractId) {
        ModelAndView mav = new ModelAndView();
        ContractDto contractDto = contractService.getContractById(contractId);
        ProductDto productDto = productService.getProductDtoById(contractDto.getProductId());
        List<ProductSignInfoDto> productSignInfoDtos = productSignInfoService.getProductSignInfoList(contractDto.getProductId());
        mav.addObject("productSignInfoDtos", productSignInfoDtos);
        mav.addObject("contractId", contractId);
        mav.addObject("contract", contractDto);
        mav.addObject("productDto", productDto);
        mav.setViewName("sign/signQui");
        return mav;
    }

    /**
     * 签约详情页面
     */
    @GetMapping("/signDetail.html")
    public ModelAndView tosignDetail(Long applyId) {
        ModelAndView mav = new ModelAndView();
        //根据申请Id获取当前有效的申请及审批记录
        ContractApplyDto contractApplyDto = contractApplyService.getContractApplyDtoById(applyId);
        contractApplyDto.getContractDto().setEarningRatio(contractApplyDto.getContractDto().getEarningRatio().setScale(2, BigDecimal.ROUND_HALF_UP));

        //根据申请Id获取该类申请的所有审批记录
        List<ContractApplyDto> contractApplyDtos = contractApplyService.getContractApplysByContractIdAndTypeAndAuditStruts(contractApplyDto.getContractDto().getId(),contractApplyDto.getType(),AuditStruts.AREJ);

        //根据申请Id获取审批记录
        List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos = contractApplyAuditRecordingService.getContractApplyAuditRecordingDtoListByApplyId(applyId);

        List<ContractSignInfoDto> contractSignInfoDtos = contractSignInfoService.getContractSignInfoList(contractApplyDto.getContractDto().getId());

        String speed = "";
        if(contractApplyDto.getSupportManagerAudit()){
            speed = new BigDecimal(16.66).multiply(new BigDecimal(contractApplyAuditRecordingDtos.size()*2)).setScale(2,BigDecimal.ROUND_HALF_UP)+"%";
        }else{
            speed = new BigDecimal(16.66).multiply(new BigDecimal((contractApplyAuditRecordingDtos.size()+1)*2)).setScale(2,BigDecimal.ROUND_HALF_UP)+"%";
        }
        if(contractApplyAuditRecordingDtos.size()==0){
            speed = "16.66%";
        }
        try{
            Long roleId = CurrentUserUtils.currentRoleId();
            mav.addObject("roleId", roleId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        mav.addObject("speed", speed);
        mav.addObject("contractApplyDto", contractApplyDto);
        mav.addObject("contractSignInfoDtos", contractSignInfoDtos);
        mav.addObject("contractApplyAuditRecordingDtos", contractApplyAuditRecordingDtos);
        mav.addObject("allContractApplyAuditRecordingDtos", contractApplyDtos);
        mav.setViewName("sign/signDetail");
        return mav;
    }

    /**
     * 转换签约页面
     *
     * @return
     */
    @GetMapping(value = "/signChange.html")
    public ModelAndView toSignChang(Long contractId) {
        ModelAndView mav = new ModelAndView();
        ContractDto contractDto = contractService.getContractById(contractId);
        ProductDto oldProductDto = productService.getProductDtoById(contractDto.getProductId());
        ProductDto productDto= productService.getProductDtoById(oldProductDto.getConversionProductDto().getId());
        //List<ProductDto> productList = productService.getAllShelveProduct();
        List<ProductSignInfoDto> productSignInfoDtos = productSignInfoService.getProductSignInfoList(productDto.getId());
        mav.addObject("contractId", contractId);
        mav.addObject("productSignInfoDtos", productSignInfoDtos);
        mav.addObject("contract", contractDto);
        mav.addObject("oldProductDto", oldProductDto);
        mav.addObject("productDto", productDto);
        //mav.addObject("productList", productList);
        mav.setViewName("sign/signChange");
        return mav;
    }

    /**
     * 信息审批通过
     * @param applyId
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/signInfoConfirm.do")
    @ResponseBody
    public Map<String, Object> signInfoConfirm(Long applyId) throws BusinessException {
        Long userId = CurrentUserUtils.currentUserId();

        return contractService.signInfoConfirm(applyId, userId);
    }

    /**
     * 收款确认
     * @param applyId
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/signReceiveConfirm.do")
    @ResponseBody
    public Map<String, Object> signReceiveConfirm(Long applyId) throws BusinessException {
        Long userId = CurrentUserUtils.currentUserId();

        return contractService.signReceiveConfirm(applyId, userId);
    }

    /**
     * 金额确认
     * @param applyId
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/signAmountConfirm.do")
    @ResponseBody
    public Map<String, Object> signAmountConfirm(Long applyId) throws BusinessException {
        Long userId = CurrentUserUtils.currentUserId();

        return contractService.signAmountConfirm(applyId, userId);
    }

    /**
     * 签约审批通过
     * @return
     */
    @PostMapping(value = "/approvedAgreeSign.do")
    @ResponseBody
    public Map<String, Object> approvedAgreeSign(@RequestParam("applyId") Long applyId) throws Exception {
        Long userId = CurrentUserUtils.currentUserId();
        Map<String, Object> map = contractService.approvedAgreeSign(applyId, userId);
        return map;
    }

    /**
     * 签约审批拒绝
     * @return
     */
    @PostMapping(value = "/approvedRejectSign.do")
    @ResponseBody
    public Map<String, Object> approvedRejectSign(Long applyId, String remark) throws Exception {
        Long userId = CurrentUserUtils.currentUserId();
        Map<String, Object> map = contractService.approvedRejectSign(applyId, userId, remark);
        return map;
    }

    /**
     * 签约详情页面
     */
    @GetMapping("/auditSign.html")
    public ModelAndView auditSign(Long applyId) {
        ModelAndView mav = new ModelAndView();
        //根据申请Id获取当前有效的申请及审批记录
        ContractApplyDto contractApplyDto = contractApplyService.getContractApplyDtoById(applyId);

        ProductDto productDto = productService.getProductDtoById(contractApplyDto.getContractDto().getProductId());

        //根据申请Id获取该类申请的所有审批记录
        List<ContractApplyDto> contractApplyDtos = contractApplyService.getContractApplysByContractIdAndTypeAndAuditStruts(contractApplyDto.getContractDto().getId(),contractApplyDto.getType(), AuditStruts.AREJ);

        //根据申请Id获取审批记录
        List<ContractApplyAuditRecordingDto> contractApplyAuditRecordingDtos = contractApplyAuditRecordingService.getContractApplyAuditRecordingDtoListByApplyId(applyId);

        //获取合同确认信息
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        ContractConfirmInfo contractConfirmInfo = contractConfirmInfoService.getContractConfirmInfoListByContractIdAndRoleId(contractApplyDto.getContractDto().getId(),roleId);

        String speed = "";
        if(contractApplyDto.getSupportManagerAudit()){
            speed = new BigDecimal(16.66).multiply(new BigDecimal(contractApplyAuditRecordingDtos.size()*2)).setScale(2,BigDecimal.ROUND_HALF_UP)+"%";
        }else{
            speed = new BigDecimal(16.66).multiply(new BigDecimal((contractApplyAuditRecordingDtos.size()+1)*2)).setScale(2,BigDecimal.ROUND_HALF_UP)+"%";
        }
        if(contractApplyAuditRecordingDtos.size()==0){
            speed = "16.66%";
        }

        mav.addObject("speed", speed);
        mav.addObject("productDto", productDto);
        mav.addObject("contractApplyDto", contractApplyDto);
        mav.addObject("contractConfirmInfo", contractConfirmInfo);
        mav.addObject("roleId", roleId);
        mav.addObject("contractApplyAuditRecordingDtos", contractApplyAuditRecordingDtos);
        mav.addObject("allContractApplyAuditRecordingDtos", contractApplyDtos);
        mav.setViewName("sign/auditSign");
        return mav;
    }

    @PostMapping("/updateContractConfirmInfoStruts")
    @ResponseBody
    public Map<String,String> updateContractConfirmInfoStruts(Long id,Boolean infoConfirm,Boolean receiveConfirm,Boolean amountConfirm,Boolean ifPrint) {
        Map<String,String> map = new HashMap<>();
        try{
            contractConfirmInfoService.updateContractConfirmInfo(id, infoConfirm, receiveConfirm, amountConfirm, ifPrint);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","修改信息错误");
        }
        return map;
    }

    /**
     * 临退金额计算
     */
    @PostMapping("/quiSum")
    @ResponseBody
    public Map<String,Object> quiSum(Long contractId, Date endDate) {
        Map<String,Object> map = new HashMap<>();
        try{
            ContractDto contractDto = contractService.getContractById(contractId);
            ProductDto productDto = productService.getProductDtoById(contractDto.getProductId());
            contractDto.setIntOrB(productDto.getIntOrB());
            List<PersonalIncome> personalIncomes = incomeService.getIncomeListByContractId(contractDto.getId());
            CustomerDto customerDto = customerService.getCustomerInfoById(contractDto.getCustomerId());
            Integer settlementCycle = 0;
            if(productDto.getBillingCycle().equals(SettleType.MON)){
                settlementCycle = 1;
            }else if(productDto.getBillingCycle().equals(SettleType.QUAR)){
                settlementCycle = 3;
            }
            BigDecimal sxf = new BigDecimal(0);//内部员工手续费为0
            String v = productDto.getQuiteFeeValue();
            BigDecimal bj = new BigDecimal(0);
            BigDecimal lx = new BigDecimal(0);
            if(customerDto.getType().equals(CustomType.OUT_CUS)){//外部员工
                if(productDto.getQuiteFeeType().equals(FeeType.AMOUNT)){//按照金额计算
                    sxf = contractDto.getAmount().multiply(new BigDecimal(Double.parseDouble(v)/100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }else{//按照利息计算
                    //获取购买金额对应的阶梯收益比
                    //BigDecimal rate = earningsService.getRateByProductIdAndAmount(productDto.getId(),contractDto.getAmount()).divide(new BigDecimal(100));
                    BigDecimal rate = contractDto.getEarningRatio().divide(new BigDecimal(100));
                    sxf = contractDto.getAmount().multiply(rate).multiply(new BigDecimal(Double.parseDouble(v))).divide(new BigDecimal(360),2, BigDecimal.ROUND_HALF_UP);
                }
            }
            Map<String, Object> map2 = PersonalIncomeUtil.getRandom(contractDto,personalIncomes,endDate,settlementCycle);
            List<PersonalIncome>  incomes = (List<PersonalIncome> )map2.get("list");
            for(PersonalIncome personalIncome:incomes){
                if(personalIncome.getSeq()==-1){
                    bj = personalIncome.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP);
                }else{
                    lx = personalIncome.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }
            bj = bj.add(lx).subtract(sxf).setScale(2, BigDecimal.ROUND_HALF_UP);
            map.put("msg","success");
            map.put("sxf",sxf);
            map.put("lx",lx);
            map.put("bj",bj);
            map.put("type",customerDto.getType());
        }catch (Exception e){
            map.put("msg","修改信息错误");
        }
        return map;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/exports", method = RequestMethod.GET)
    public ModelAndView exports(ContractApplyQueryDto contractApplyQueryDto) throws Exception {
        PageInfo<ContractApplyDto> contractDtos = contractApplyService.queryContractPage(contractApplyQueryDto);
        ContractReportVo contractReportVo = new ContractReportVo();
        int sheetNum = contractReportVo.addOneSheet(ContractReportVo.F_S_COL_HD);
        for (ContractApplyDto contractApplyDto : contractDtos.getList()) {
            ContractDto contractDto = contractApplyDto.getContractDto();
            contractReportVo.fillOneRow(
                    sheetNum
                    , contractDto.getNumber()
                    , contractDto.getCustomerName()
                    , contractDto.getAmount()
                    , contractDto.getProductName()
                    , contractDto.getStartTime()
                    , contractDto.getEndTime()
                    , contractDto.getOpenName()
                    , contractDto.getOpenBank()
                    , contractDto.getBankAccount()
                    , contractDto.getType().getDesc()
                    , contractDto.getUser().getRealname()
                    , contractDto.getStruts().getDesc()
                    , contractDto.getRemark()
            );
        }
        contractReportVo.setFileName(ReportConstant.CONTRACT_EXPORT);
        ModelAndView mav =  new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, contractReportVo);
        mav.addObject("contractApplyQueryDto",contractApplyQueryDto);
        return mav;
    }

    /**
     * 合同详情页面
     */
    @GetMapping("/contractDetail.html")
    public ModelAndView toContractDetail(Long contractId) {
        ModelAndView mav = getModelAndView();
        ContractDto contractDto = contractService.getContractById(contractId);
        mav.addObject("contractDto",contractDto);
        mav.setViewName("sign/contractDetail");
        return mav;
    }

    @RequestMapping(value = "/contractExports", method = RequestMethod.GET)
    public ModelAndView contractExports(PageModel pageModel, ContractQueryDto2 contractQueryDto) throws Exception {
        pageModel.setPageSize(Integer.MAX_VALUE);
        Map<Object, Object> result = contractService.queryContractDtoListPage2(pageModel,contractQueryDto);
        PageInfo<ContractDto> contractDtos = (PageInfo<ContractDto>) result.get("pageInfo");
        ContractReportVo contractReportVo = new ContractReportVo();
        int sheetNum = contractReportVo.addOneSheet(ContractReportVo.F_S_COL_HD);
        for (ContractDto contractDto : contractDtos.getList()) {
            contractReportVo.fillOneRow(
                    sheetNum
                    , contractDto.getNumber()
                    , contractDto.getCustomerName()
                    , contractDto.getAmount()
                    , contractDto.getProductName()
                    , contractDto.getStartTime()
                    , contractDto.getEndTime()
                    , contractDto.getOpenName()
                    , contractDto.getOpenBank()
                    , contractDto.getBankAccount()
                    , contractDto.getType().getDesc()
                    , contractDto.getUser().getRealname()
                    , contractDto.getStruts().getDesc()
                    , contractDto.getRemark()
            );
        }
        contractReportVo.setFileName(ReportConstant.CONTRACT_EXPORT);
        ModelAndView mav =  new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, contractReportVo);
        mav.addObject("contractQueryDto",contractQueryDto);
        return mav;
    }
}
