package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.RoleDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by dell on 2018/1/8.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private OperationService operationService;

    /**
     * 权限列表
     * @return
     */
    @RequestMapping(value = "/permissionList",method = RequestMethod.GET)
    public ModelAndView permissionList() throws BusinessException, UnknownHostException {
        ModelAndView mav = getModelAndView();
        List<RoleDto> roleDtoList = roleService.getAllRoleListAndPrivileges();
        mav.addObject("roleList", roleDtoList);
        mav.setViewName("permission/permissionList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看权限列表");
        operationService.insertOperation(operationDto);
        return mav;
    }
}
