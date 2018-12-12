package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractQueryDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by dell on 2018/1/22.
 */
@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {
    @Autowired
    private ContractService contractService;

    /**
     * 合同列表
     * @return
     */
    @RequestMapping(value = "/contractList",method = RequestMethod.GET)
    public ModelAndView contractList(PageModel pageModel, ContractQueryDto contractQueryDto) throws BusinessException {
        ModelAndView mav = getModelAndView();
        Map<Object,Object> result = contractService.queryContractDtoListPage(pageModel,contractQueryDto);
        //合同总计金额
        mav.addObject("pageInfo",result.get("pageInfo"));
        mav.addObject("totalAmount",result.get("totalAmount"));
        mav.addObject("contractQueryDto",contractQueryDto);
        mav.setViewName("contract/contractList");
        return mav;
    }
}
