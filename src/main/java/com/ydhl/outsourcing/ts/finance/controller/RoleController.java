package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.dto.RoleDto;
import com.ydhl.outsourcing.ts.finance.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/12 18:15.
 * @description
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有职位
     *
     * @return 所有职位
     */
    @GetMapping(value = "/getAllRoleList")
    public ModelAndView getAllRoleList() {
        ModelAndView mav = new ModelAndView();
        List<RoleDto> roleDtoList = roleService.getAllRoleList();
        mav.addObject("roleList", roleDtoList);
        return mav;
    }

    @GetMapping(value = "/roleList")
    public ModelAndView roleList() {
        ModelAndView mav = new ModelAndView();
        List<RoleDto> roleDtoList = roleService.getAllRoleListAndPrivileges();
        mav.addObject("roleList", roleDtoList);
        mav.setViewName("permission/permissionList");
        return mav;
    }
}
