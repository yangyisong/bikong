package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.common.utils.BankAcountAes;
import com.ydhl.outsourcing.ts.finance.common.utils.NumberToChinese;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.dto.*;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 凭证控制器
 * Created by dell on 2018/1/10.
 */
@Controller
@RequestMapping("/credential")
public class CredentialController extends BaseController {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private ContractApplyService contractApplyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    /**
     * 凭证列表
     * @return
     */
    @RequestMapping(value = "/credentialList",method = RequestMethod.GET)
    public ModelAndView credentialList() throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        List<VoucherDto> voucherDtos = voucherService.getVoucherList();
        mav.addObject("voucherDtos",voucherDtos);
        mav.setViewName("credential/credentialList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看凭证列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 跳转到收益给付凭证
     * @return
     */
    @RequestMapping(value = "/toIncomeCredential",method = RequestMethod.GET)
    public ModelAndView toIncomeCredential() throws BusinessException {
        ModelAndView mav = getModelAndView();
        mav.addObject("customerName","");
        mav.addObject("idCard","");
        mav.addObject("amount","");
        mav.addObject("chineseAmount", "");
        mav.addObject("number","");
        mav.addObject("nows","");
        mav.setViewName("credential/incomeCredential");
        return mav;
    }

    /**
     * 跳转到收据
     * @return
     */
    @RequestMapping(value = "/toReceipt",method = RequestMethod.GET)
    public ModelAndView toReceipt() throws BusinessException {
        ModelAndView mav = getModelAndView();
        mav.setViewName("credential/receiptCredential");
        return mav;
    }

    /**
     * 跳转到财产份额确认凭证
     * @return
     */
    @RequestMapping(value = "/toProperty",method = RequestMethod.GET)
    public ModelAndView toProperty() throws BusinessException {
        ModelAndView mav = getModelAndView();
        mav.addObject("customerName","");
        mav.addObject("idCard","");
        mav.addObject("amount","");
        mav.addObject("chineseAmount", "");
        mav.addObject("number","");
        mav.setViewName("credential/propertyCredential");
        return mav;
    }

    /**
     * 修改凭证联数
     */
    @RequestMapping(value = "/editUnited",method = RequestMethod.POST)
    @ResponseBody
    public void editUnited(Long id,Integer united) throws BusinessException, UnknownHostException {
        voucherService.editUnited(id,united);
    }

    /**
     * 打印凭证
     */
    @RequestMapping(value = "/prints",method = RequestMethod.GET)
    public ModelAndView prints(Long applyId) throws BusinessException,Exception {
        ModelAndView mav = getModelAndView();
        ContractApplyDto contractApplyDto = contractApplyService.getContractApplyDtoById(applyId);
        ProductDto productDto = productService.getProductDtoById(contractApplyDto.getContractDto().getProductId());
        CustomerDto customerDto = customerService.getCustomerInfoById(contractApplyDto.getContractDto().getCustomerId());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
        String nows = fmt.format(new Date());
        if(productDto.getVoucherDto().getId()==1){
            mav.addObject("customerName",customerDto.getName());
            mav.addObject("idCard",customerDto.getIdcard());
//            mav.addObject("amount",contractApplyDto.getContractDto().getAmount());
            mav.addObject("amount",BankAcountAes.bigDecimalFormat(contractApplyDto.getContractDto().getAmount()));
            mav.addObject("chineseAmount", NumberToChinese.arabNumToChineseRMB(Double.parseDouble(contractApplyDto.getContractDto().getAmount().toString())));
            mav.addObject("number",contractApplyDto.getContractDto().getNumber());
            mav.setViewName("credential/incomeCredential");
        }else{
            mav.addObject("customerName",customerDto.getName());
            mav.addObject("idCard",customerDto.getIdcard());
            mav.addObject("amount",contractApplyDto.getContractDto().getAmount());
            mav.addObject("chineseAmount", NumberToChinese.arabNumToChineseRMB(Double.parseDouble(contractApplyDto.getContractDto().getAmount().toString())));
            mav.addObject("number",contractApplyDto.getContractDto().getNumber());
            mav.setViewName("credential/propertyCredential");
        }
        mav.addObject("nows",nows);
        return mav;
    }

}
