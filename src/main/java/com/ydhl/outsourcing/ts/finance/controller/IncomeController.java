package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.base.report.IncomeReportVo;
import com.ydhl.outsourcing.ts.finance.base.report.ReportConstant;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.PersonalIncomeDto;
import com.ydhl.outsourcing.ts.finance.dto.ProductDto;
import com.ydhl.outsourcing.ts.finance.dto.query.PersonalIncomeQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.enums.IncomeType;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;
import com.ydhl.outsourcing.ts.finance.service.IncomeService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收益控制器
 * Created by dell on 2018/1/10.
 */
@Controller
@RequestMapping("/income")
public class IncomeController extends BaseController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OperationService operationService;

    /**
     * 收益列表
     * @return
     */
    @RequestMapping(value = "/incomeList",method = RequestMethod.GET)
    public ModelAndView incomeList(PersonalIncomeQueryDto personalIncomeQueryDto, PageModel pageModel) throws BusinessException, UnknownHostException {
        if(pageModel.getPageSize()==10){
            pageModel.setPageSize(20);
        }
        Long userId = CurrentUserUtils.currentUserId();
        Long roleId = CurrentUserUtils.currentRoleId();
        if(roleId!=4){
            personalIncomeQueryDto.setUserId(userId);
        }
        if(roleId==5 || roleId==6){
            personalIncomeQueryDto.setUserId(null);
        }
        ModelAndView mav = getModelAndView();
        Map<String,Object> map = incomeService.getPersonalIncomePage(personalIncomeQueryDto, pageModel);
        PageInfo<PersonalIncomeDto> pageInfo = (PageInfo<PersonalIncomeDto>)map.get("pageInfo");
        Long totalCount = (Long)map.get("totalCount");
        pageInfo.setPageNum(pageModel.getPage());
        pageInfo.setPageSize(pageModel.getPageSize());
        //获取所有上架的产品
        List<ProductDto> productDtos = productService.getAllShelveProduct();
        BigDecimal allIncomeTatal = (BigDecimal)map.get("allIncomeTatal");
        Map<Object, IncomeType> incomeMap = incomeService.getAllIncomeType();
        Map<Object, ApprovalType> approvalMap = incomeService.getAllApprovalType();
        mav.addObject("productDtos", productDtos);
        mav.addObject("queryDto", personalIncomeQueryDto);
        mav.addObject("incomeMap", incomeMap);
        mav.addObject("approvalMap", approvalMap);
        mav.addObject("allIncomeTatal", allIncomeTatal);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("totalCount", totalCount);
        mav.setViewName("income/incomeList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看收益列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 更新状态
     */
    @PostMapping(value="updateIncomeStruts")
    @ResponseBody
    public Map<String,String> updateIncomeStruts(Long id,ApprovalType struts){
        Map<String,String> map = new HashMap<>();
        try{
            PersonalIncome personalIncome = new PersonalIncome();
            personalIncome.setId(id);
            personalIncome.setStruts(struts);
            incomeService.updateIncomeStruts(id,struts);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("操作收益("+ id + ")");
            operationService.insertOperation(operationDto);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","修改状态失败");
        }
        return map;
    }

    /**
     * 收益导出
     *
     * @param personalIncomeQueryDto
     * @return
     */
    @GetMapping(value = "/export.html")
    public ModelAndView export(PersonalIncomeQueryDto personalIncomeQueryDto) throws BusinessException, UnknownHostException {
        Long userId = CurrentUserUtils.currentUserId();
        personalIncomeQueryDto.setUserId(userId);
        PageInfo<PersonalIncomeDto> pageInfo = incomeService.getIncomePage(personalIncomeQueryDto);
        IncomeReportVo reportVo = new IncomeReportVo();
        int sheetNum = reportVo.addOneSheet(IncomeReportVo.F_S_COL_HD);
        for (PersonalIncomeDto personalIncomeDto : pageInfo.getList()) {
            reportVo.fillOneRow(
                    sheetNum
                    , personalIncomeDto.getIncomeNumber()
                    , personalIncomeDto.getContractNumber()
                    , personalIncomeDto.getCustomerName()
                    , personalIncomeDto.getProductName()
                    , personalIncomeDto.getUserRealname()
                    , personalIncomeDto.getSettleTime()
                    , personalIncomeDto.getIncome()
                    , personalIncomeDto.getDayNums()
                    , personalIncomeDto.getIncomeTypeValue()
                    , personalIncomeDto.getStrutsValue()
            );
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("收益列表导出");
        operationService.insertOperation(operationDto);
        reportVo.setFileName(ReportConstant.INCOME_REPORT);
        return new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, reportVo);
    }
}
