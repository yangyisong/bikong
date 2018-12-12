package com.ydhl.outsourcing.ts.finance.controller;

import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.dto.DepartmentDto;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.service.DepartmentService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martins
 * @create 2018/1/2 11:43.
 */
@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private UserService userService;

    /**
     * 查询部门列表
     *
     * @param pageNo
     * @param name
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "/departmentList")
    public ModelAndView getDepartmentList(Integer pageNo, String name) throws BusinessException, UnknownHostException {
        Long userId = CurrentUserUtils.currentUserId();
        ModelAndView mav = getModelAndView();
        //PageInfo<DepartmentDto> departmentDtoPageInfo = departmentService.queryDepartmentPage(pageNo, pageSize, name);

        List<DepartmentDto> departments = departmentService.getDepartmentsList(departmentService.getDepartmentListByParentId(0l));
        //mav.addObject("pageInfo", departmentDtoPageInfo);
        mav.addObject("departments", departments);
        mav.setViewName("department/departmentList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看部门列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 获取部门列表
     *
     * @return 部门列表
     */
    @GetMapping(value = "/getAllDepartmentDtoList")
    public ModelAndView getAllDepartmentDtoList() {
        ModelAndView mav = new ModelAndView();
        List<DepartmentDto> departmentDtoList = departmentService.getAllDepartmentDtoList();
        mav.addObject("departmentList", departmentDtoList);
        return mav;
    }

    /**
     * 跳转到新增部门页面
     */
    @GetMapping(value = "/toAddDepartment")
    public ModelAndView toAddDepartment(){
        ModelAndView mav = new ModelAndView();
        List<DepartmentDto> departments = departmentService.getDepartmentsList(departmentService.getDepartmentListByParentId(0l));
        mav.addObject("departments", departments);
        mav.setViewName("department/addDepartment");
        return mav;
    }
    /**
     * 新增部门
     */
    @PostMapping(value = "/addDepartment")
    @ResponseBody
    public Map<String,String> addDepartment(DepartmentDto departmentDto){
        Map<String,String> map = departmentService.addDepartment(departmentDto);
        return map;
    }
    /**
     * 编辑部门
     */
    @PostMapping(value = "/editDepartment")
    @ResponseBody
    public Map<String,String> editDepartment(DepartmentDto departmentDto){
        Map<String,String> map = departmentService.editDepartment(departmentDto.getId(),departmentDto.getName());
        return map;
    }

    /**
     * 删除部门
     */
    @PostMapping(value = "/deleteDepartment")
    @ResponseBody
    public Map<String,String> deleteDepartment(Long id){
        Map<String,String> map = new HashMap<>();
        try{
            //判断部门下是否有员工
            List<Long> ids = departmentService.getDepartmentIdList(departmentService.getDepartmentListByParentId(id));
            ids.add(id);
            //查询这些部门下是否有员工
            Long count = userService.getUserCountByDepartmentIds(ids);
            if(count>0){
                map.put("msg","该部门或者子部门中有员工,无法删除");
            }else{
                departmentService.deleteDepartmentsByIds(ids);
                OperationDto operationDto = OperaionUtil.getOperationDto();
                operationDto.setContent("删除部门信息");
                operationService.insertOperation(operationDto);
                map.put("msg","success");
            }
        }catch (Exception e){
            map.put("msg","编辑部门失败");
        }
        return map;
    }

}
