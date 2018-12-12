package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.*;
import com.ydhl.outsourcing.ts.finance.dto.query.ApprovedApplicationQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 结算控制器
 * Created by dell on 2018/1/10.
 */
@Controller
@RequestMapping("/settlement")
public class SettlementController  extends BaseController {

    @Autowired
    private ApprovedApplicationService approvedApplicationService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApprovedRecordingService approvedRecordingService;

    @Autowired
    private ContractApplyService contractApplyService;

    @Autowired
    private ContractSignInfoService contractSignInfoService;

    /**
     * 结算列表
     * @return
     */
    @RequestMapping(value = "/settlementList",method = RequestMethod.GET)
    public ModelAndView settlementList(ApprovedApplicationQueryDto approvedApplicationQueryDto, PageModel pageModel) throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        PageInfo<ApprovedApplicationDto> pageInfo = approvedApplicationService.getApprovedApplicationPage(approvedApplicationQueryDto, pageModel);
        Map<Object, ApplicationStruts> strutsMap = approvedApplicationService.getAllStruts();
        mav.addObject("queryDto", approvedApplicationQueryDto);
        mav.addObject("strutsMap", strutsMap);
        mav.addObject("pageInfo", pageInfo);
        mav.setViewName("settlement/settlementList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看结算列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 跳转到结算审批详情
     * @return
     */
    @RequestMapping(value = "/toSettlementApplyDetail",method = RequestMethod.GET)
    public ModelAndView toSettlementApplyDetail(Long approvedId) throws BusinessException {
        ModelAndView mav = getModelAndView();
        ApprovedApplicationDto approvedApplicationDto = approvedApplicationService.getApprovedApplicationById(approvedId);
        //获取当前正在审批的记录
        Map<String,Object> map =  approvedRecordingService.getEffectiveApprovedRecordingDtoListByApprovedId(approvedId,true);
        List<ApprovedRecordingDto> approvedRecordings = (List<ApprovedRecordingDto>)map.get("approvedRecordingDtos");
        String speed = ((BigDecimal)map.get("speed")).toString()+"%";
        //获取过往被拒绝的审批记录
        List<List<ApprovedRecordingDto>> ars = approvedRecordingService.getNotEffectiveApprovedRecordingDtoListByApprovedId(approvedId,false);
        mav.addObject("approvedApplicationDto",approvedApplicationDto);
        mav.addObject("approvedRecordings",approvedRecordings);
        mav.addObject("speed",speed);
        mav.addObject("ars",ars);
        mav.setViewName("settlement/settlementApplyDetail");
        return mav;
    }

    /**
     * 跳转到新增结算
     * @return
     *//*
    @RequestMapping(value = "/toAddSettlement",method = RequestMethod.GET)
    public ModelAndView toAddSettlement(Long productId) throws BusinessException {
        ModelAndView mav = getModelAndView();
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        List<Long> productIds = null;
        if(roleId==2){
            productIds = incomeService.getIncomeProductIdList(userId);
        }else{
            productIds = incomeService.getIncomeProductIdList(null);
        }
        List<ProductDto> productDtos = null;
        if(productIds!=null && productIds.size()!=0){
            productDtos = productService.getProductDtosByIds(productIds);
        }
        if(productId==null){
            if(productDtos!=null && productDtos.size()!=0){
                productId = productDtos.get(0).getId();
            }
        }
        Map<String,Object> map = incomeService.getPersonalIncomeListByProductId(productId,userId);
        List<PersonalIncomeDto> personalIncomeDtoList = (List<PersonalIncomeDto>)map.get("personalIncomeDtoList");
        BigDecimal total = (BigDecimal)map.get("total");
        mav.addObject("personalIncomeDtoList",personalIncomeDtoList);
        mav.addObject("today",new Date());
        mav.addObject("productDtos",productDtos);
        mav.addObject("productId",productId);
        mav.addObject("total",total);
        mav.setViewName("settlement/addSettlement");
        return mav;
    }*/

    @RequestMapping(value = "/addSettlement",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addSettlement(Integer penNumber,Long productId,@RequestParam(value = "incomeIds[]") Long[] incomeIds) throws BusinessException {
        Map<String,String> map = new HashMap<>();
        //新增结算申请
        //修改收益列表的申请Id
        try{
            ApprovedApplicationDto approvedApplicationDto = new ApprovedApplicationDto();
            approvedApplicationDto.setPenNumber(penNumber);
            approvedApplicationDto.setProductId(productId);
            List<Long> ids = Arrays.asList(incomeIds);
            approvedApplicationService.insertApprovedApplication(approvedApplicationDto,ids);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","新增申请失败");
        }
        return map;
    }

    /**
     * 跳转到再次申请结算
     * @return
     */
    /*@RequestMapping(value = "/toRetrySettlement",method = RequestMethod.GET)
    public ModelAndView toRetrySettlement(Long productId,Long approvedId) throws BusinessException {
        ModelAndView mav = getModelAndView();
        List<Long> productIds = incomeService.getIncomeProductIdList();
        List<ProductDto> productDtos = productService.getProductDtosByIds(productIds);
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginSecurityDto.getId();
        Map<String,Object> map = incomeService.getPersonalIncomeListByProductId(productId,userId);
        List<PersonalIncomeDto> personalIncomeDtoList = (List<PersonalIncomeDto>)map.get("personalIncomeDtoList");
        BigDecimal total = (BigDecimal)map.get("total");
        mav.addObject("personalIncomeDtoList",personalIncomeDtoList);
        mav.addObject("today",new Date());
        mav.addObject("productDtos",productDtos);
        mav.addObject("productId",productId);
        mav.addObject("approvedId",approvedId);
        mav.addObject("total",total);
        mav.setViewName("settlement/retrySettlemen");
        return mav;
    }*/

    /**
     * 通过
     */
    @RequestMapping(value = "/pass",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> pass(Long approvedId) throws BusinessException {
        Map<String,String> map = new HashMap<>();
       try{
            approvedApplicationService.passApply(approvedId);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","通过操作失败");
        }
        return map;
    }

    /**
     * 拒绝
     */
    @RequestMapping(value = "/refuse",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> refuse(Long approvedId,String content) throws BusinessException {
        Map<String,String> map = new HashMap<>();
        try{
            approvedApplicationService.refuseApply(approvedId,content);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","拒绝操作失败");
        }
        return map;
    }

    /**
     * 撤回
     */
    @RequestMapping(value = "/delsSettlement",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delsSettlement(Long approvedId) throws BusinessException {
        Map<String,String> map = new HashMap<>();
        try{
            approvedApplicationService.deleteApprovedById(approvedId);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","拒绝操作失败");
        }
        return map;
    }

    /**
     * 再次申请
     */
    @RequestMapping(value = "/retryApply",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> retryApply(Long approvedId) throws BusinessException {
        Map<String,String> map = new HashMap<>();
        try{
            approvedApplicationService.retryApprovedById(approvedId);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","再次申请失败");
        }
        return map;
    }

    @RequestMapping(value = "/toHaveALookSettlement",method = RequestMethod.GET)
    public ModelAndView toHaveALookSettlement(Long approvedId){
        ModelAndView mav = getModelAndView();
        List<Long> incomeIds = approvedApplicationService.getIncomeIdListByApprovedId(approvedId);
        ApprovedApplicationDto approvedApplicationDto = approvedApplicationService.getApprovedApplicationById(approvedId);
        Long productId = approvedApplicationDto.getProductId();
        ProductDto productDto = productService.getProductDtoById(productId);
        Map<String,Object> map = incomeService.getIncomeListByIdList(incomeIds);
        mav.addObject("approvedApplicationDto",approvedApplicationDto);
        mav.addObject("productDto",productDto);
        mav.addObject("map",map);
        mav.setViewName("settlement/haveALookSettlement");
        return mav;
    }

    @RequestMapping(value = "/toContractDetail",method = RequestMethod.GET)
    public ModelAndView toContractDetail(Long approvedId){
        ModelAndView mav = getModelAndView();
        Long applyId = incomeService.getApplyIdByApprovedId(approvedId);
        ContractApplyDto contractApplyDto = contractApplyService.getContractApplyDtoById(applyId);
        List<ContractSignInfoDto> contractSignInfoDtos = contractSignInfoService.getContractSignInfoList(contractApplyDto.getContractDto().getId());
        mav.addObject("contractApplyDto",contractApplyDto);
        mav.addObject("contractSignInfoDtos",contractSignInfoDtos);
        mav.setViewName("settlement/settlementSignDetail");
        return mav;
    }

    /**
     * 跳转到新增结算
     * @return
     */
    @RequestMapping(value = "/toAddSettlement",method = RequestMethod.GET)
    public ModelAndView toAddSettlement(Long productId, PageModel pageModel) throws BusinessException {
        ModelAndView mav = getModelAndView();
        LoginSecurityDto loginSecurityDto = (LoginSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long roleId = loginSecurityDto.getRoleId();
        Long userId = loginSecurityDto.getId();
        List<Long> productIds = null;
        if(roleId==2){
            productIds = incomeService.getIncomeProductIdList(userId);
        }else{
            productIds = incomeService.getIncomeProductIdList(null);
        }
        List<ProductDto> productDtos = null;
        if(productIds!=null && productIds.size()!=0){
            productDtos = productService.getProductDtosByIds(productIds);
        }
        if(productId==null){
            if(productDtos!=null && productDtos.size()!=0){
                productId = productDtos.get(0).getId();
            }
        }
        Map<String,Object> map = incomeService.getPersonalIncomePageByProductId(productId,userId,pageModel);
        PageInfo<PersonalIncomeDto> pageInfo = (PageInfo<PersonalIncomeDto>)map.get("pageInfo");
        pageInfo.setPageNum(pageModel.getPage());
        BigDecimal total = (BigDecimal)map.get("total");
        mav.addObject("pageInfo",pageInfo);
        mav.addObject("today",new Date());
        mav.addObject("productDtos",productDtos);
        mav.addObject("productId",productId);
        mav.addObject("total",total);
        mav.setViewName("settlement/addSettlement");
        return mav;
    }
}
