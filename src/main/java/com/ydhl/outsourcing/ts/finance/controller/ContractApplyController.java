package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ContractApplyQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApplicationStruts;
import com.ydhl.outsourcing.ts.finance.enums.AuditStruts;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.service.ContractApplyService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by dell on 2018/2/1.
 */
@Controller
@RequestMapping("/contractApply")
public class ContractApplyController  extends BaseController {
    @Autowired
    private ContractApplyService contractApplyService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/contractApplyList",method = RequestMethod.GET)
    public ModelAndView contractApplyList(PageModel pageModel, ContractApplyQueryDto contractApplyQueryDto) throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        Map<Object,Object> result = contractApplyService.queryContractDtoListPage(pageModel,contractApplyQueryDto);
        //合同总计金额
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(CurrentUserUtils.currentUserId()).get(0));
        mav.addObject("role", role);
        mav.addObject("pageInfo",result.get("pageInfo"));
        mav.addObject("totalAmount",result.get("totalAmount"));
        mav.addObject("contractApplyQueryDto",contractApplyQueryDto);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看签约列表");
        operationService.insertOperation(operationDto);
        mav.setViewName("sign/signList");
        return mav;
    }

    @RequestMapping(value = "/contractApplyList2",method = RequestMethod.GET)
    public ModelAndView contractApplyList2(PageModel pageModel, ContractApplyQueryDto contractApplyQueryDto) throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(CurrentUserUtils.currentUserId()).get(0));
        contractApplyQueryDto.setFlag(1);
        if(contractApplyQueryDto.getApplyStruts()==null){
            contractApplyQueryDto.setApplyStruts(ApplicationStruts.BREV);
        }
        Map<Object,Object> result = contractApplyService.queryContractDtoListPage(pageModel,contractApplyQueryDto);
        //合同总计金额
        mav.addObject("role", role);
        mav.addObject("pageInfo",result.get("pageInfo"));
        mav.addObject("totalAmount",result.get("totalAmount"));
        mav.addObject("contractApplyQueryDto",contractApplyQueryDto);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看签约列表");
        operationService.insertOperation(operationDto);
        mav.setViewName("sign/signList2");
        return mav;
    }

    @RequestMapping(value = "/contractApplyList3",method = RequestMethod.GET)
    public ModelAndView contractApplyList3(PageModel pageModel, ContractApplyQueryDto contractApplyQueryDto) throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(CurrentUserUtils.currentUserId()).get(0));
        contractApplyQueryDto.setFlag(2);
        if(contractApplyQueryDto.getAuditStruts()==null){
            contractApplyQueryDto.setAuditStruts(AuditStruts.WAIT);
        }
        Map<Object,Object> result = contractApplyService.queryContractDtoListPage(pageModel,contractApplyQueryDto);
        //合同总计金额
        mav.addObject("role", role);
        mav.addObject("pageInfo",result.get("pageInfo"));
        mav.addObject("totalAmount",result.get("totalAmount"));
        mav.addObject("contractApplyQueryDto",contractApplyQueryDto);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看签约列表");
        operationService.insertOperation(operationDto);
        mav.setViewName("sign/signList3");
        return mav;
    }
}
