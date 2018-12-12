package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.base.report.ReportConstant;
import com.ydhl.outsourcing.ts.finance.base.report.UserReportVo;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.custantModule.ConstantModule;
import com.ydhl.outsourcing.ts.finance.dto.DepartmentDto;
import com.ydhl.outsourcing.ts.finance.dto.RoleDto;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.dto.query.UserQueryDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.User;
import com.ydhl.outsourcing.ts.finance.service.DepartmentService;
import com.ydhl.outsourcing.ts.finance.service.RoleService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by huangxinguang on 2017/5/22 下午5:37.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户列表
     * @param userQueryDto
     * @return
     */
    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    public ModelAndView userList(PageModel pageModel, UserQueryDto userQueryDto) throws BusinessException, UnknownHostException {
        Long aLong = CurrentUserUtils.currentUserId();
        ModelAndView mav = getModelAndView();
        PageInfo<UserDto> pageInfo = userService.queryUserListPage(pageModel,userQueryDto);
        pageInfo.setPageNum(pageModel.getPage());
        List<DepartmentDto> departmentDtos = departmentService.getAllDepartmentDtoList();
        List<RoleDto> roleDtos = roleService.getAllRoleList();
        mav.addObject("departmentDtos", departmentDtos);
        mav.addObject("roleDtos", roleDtos);
        mav.addObject("pageInfo",pageInfo);
        mav.addObject("queryDto",userQueryDto);
        mav.setViewName("user/userList");
        return mav;
    }

    /**
     * 添加用户页面
     *
     * @return
     */
    @RequestMapping(value = "/addUser.html",method = RequestMethod.GET)
    public ModelAndView addUserPage() {
        ModelAndView mav = new ModelAndView();
        List<DepartmentDto> departmentDtos = departmentService.getAllDepartmentDtoList();
        List<RoleDto> roleDtos = roleService.getAllRoleList();
        mav.addObject("departmentList", departmentDtos);
        mav.addObject("roleList", roleDtos);
        return mav;
    }

    /**
     * 编辑用户页面
     * @param userId
     * @return
     */
    @RequestMapping(value = "/editUser.html",method = RequestMethod.GET)
    public ModelAndView editUserPage(Long userId) {
        ModelAndView mav = getModelAndView();
        UserDto user = userService.queryUser(userId);
        List<DepartmentDto> departmentDtos = departmentService.getAllDepartmentDtoList();
        List<RoleDto> roleDtos = roleService.getAllRoleList();
        mav.addObject("departmentList", departmentDtos);
        mav.addObject("roleList", roleDtos);
        mav.addObject("user",user);
        mav.setViewName("user/editUser");
        return mav;
    }

    /**
     * 个人资料信息编辑页面
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/editData.html")
    public ModelAndView editeData(Long userId) {
        ModelAndView mav = new ModelAndView();
        UserDto user = userService.queryUser(userId);
        mav.addObject("user",user);
        mav.setViewName("user/editData");
        return mav;
    }

    /**
     * 修改员工资料
     *
     * @param userDto
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/updateUser.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<Object,Object> updateUser(UserDto userDto) throws BusinessException, UnknownHostException {
        return userService.updateUser(userDto);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/saveUser.do",method = RequestMethod.POST)
    @ResponseBody
    public Object saveUser(@RequestBody @Validated(UserDto.Add.class) UserDto user,BindingResult result) throws BusinessException, UnknownHostException {
        Map<Object,Object> map = new HashMap<>();
        if (userService.existsJobNumber(user.getJobNumber())) {
            map.put("msg", "工号已存在");
            return map;
        }
        boolean exists = userService.exists(user.getUsername());
        if(exists) {
            map.put("msg","用户名已存在");
        }else{
            userService.saveUser(user);
            map.put("msg","Success");
        }
        return map;
    }

    /**
     * 停用用户
     *
     * @param
     * @return
     */
    @PostMapping(value = "/closeUser.do")
    @ResponseBody
    public Map<String, Object> closeUser(@RequestBody List<Long> userIdList) throws BusinessException, UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        map = userService.closeUser(userIdList);
        if (map == null || map.size() == 0) {
            map.put("msg", "success");
        }
        return map;
    }

    /**
     * 启用用户
     *
     * @param
     * @return
     */
    @PostMapping(value = "/openUser.do")
    @ResponseBody
    public Object openUser(@RequestBody List<Long> userIdList) throws BusinessException, UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        userService.openUser(userIdList);
        map.put("msg", "success");
        return map;
    }

    /**
     * 根据用户姓名或工号查询用户
     *
     * @param content
     * @return
     */
    @GetMapping("/getUserListByRealnameOrJobNumber")
    public ModelAndView getUserListByRealnameOrJobNumber(String content) {
        ModelAndView mav = new ModelAndView();
        List<UserDto> dtoList = userService.getUserByRealnameOrJobNumber(content);
        mav.addObject("userList", dtoList);
        return mav;
    }

    /**
     * 解锁
     *
     * @param userName
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unLock.do",method = RequestMethod.POST)
    public Object unLock(String userName,String password) {
        UserDto userDto = userService.check(userName,password);
        Map<String,Object> result  = new HashMap<String,Object>();
        if (userDto != null) {
            result.put("success", true);
        }else {
            result.put("success", false);
        }
        return result;
    }



    /**
     * 导出会员列表
     *
     * @return 分页结果
     */
    @RequestMapping(value = "/exports.html", method = RequestMethod.GET)
    public ModelAndView exports( UserQueryDto userQueryDto) throws Exception {
        PageInfo<UserDto> pageInfo = userService.queryUserPage(userQueryDto);

        UserReportVo reportDto = new UserReportVo();
        int sheetNum = reportDto.addOneSheet(UserReportVo.F_S_COL_HD);
        for (UserDto user : pageInfo.getList()) {
            reportDto.fillOneRow(
                    sheetNum
                    , user.getId()
                    , user.getRealname()
                    , user.getPhone()
                    , user.getRole()
                    , user.getDepartment()
                    , user.getCustomTotal()
                    , user.getJobNumber()
                    , user.getLastLogin()
                    , user.getCreateTime()
            );
        }
        reportDto.setFileName(ReportConstant.USER_REPORT);
        return new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, reportDto);
    }

    /**
     * 导入积分excel
     *
     * @param multipartFile 上传文件
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public void importScoreExcel(@RequestParam("multipartFile") MultipartFile multipartFile) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        List<User> mbMemberDtoList = null;
        try {
            mbMemberDtoList = importMemberExcel(inputStream, 1L);
        } catch (BusinessException e) {
//            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
//                LOGGER.error(e.getMessage(), e);
            }
        }
//        memberService.inserMemberList(mbMemberDtoList);
    }


    /**
     * 读取excel数据
     *
     * @param inputStream 输入流
     * @return 返回结果list
     */
    public static List<User> importMemberExcel(InputStream inputStream, Long merchantsId) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();
        List<List<Object>> resultList = new ArrayList<>();
        rows.next();
        Integer index = 2;
        try {
            while (rows.hasNext()) {
                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                List<Object> objectList = new ArrayList<>();
                Integer cellIndex = 1;
                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();
                    //因为模板固定第三列是数值，所以固定判断此值必不为字符串
/*                    if ((cellIndex == 1 || cellIndex == 4) && cell.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
                        throw new BusinessException("",index.toString());
                    }*/
                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        String stringCellValue = cell.getStringCellValue();
                        //防止备注超过数据库限制
/*                        if (cellIndex == 5 && stringCellValue.length() > 255) {
                            throw new BusinessException("", index.toString());
                        }*/
                        objectList.add(stringCellValue);
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        //数字类型不可转化为字符串
                        objectList.add(cell.getNumericCellValue());
                    }
                    cellIndex++;
                }
                //如果list长度没有5，代表该行有空数据
/*                if (objectList.size() != 6) {
                    throw new BusinessException("", index.toString());
                }*/
                resultList.add(objectList);
                index++;
            }
        } catch (Exception e) {
            throw new BusinessException("", index.toString());
        }

        List<User> memberDtoList = new ArrayList<>();
        for (List<Object> objectList : resultList) {
            User mbMemberDto = new User();
/*            mbMemberDto.setNickname(String.valueOf(objectList.get(0)));
            mbMemberDto.setPhone(String.valueOf(objectList.get(1)));
            mbMemberDto.setAge((Integer) objectList.get(2));
            mbMemberDto.setMerchantsId(merchantsId);*/
            memberDtoList.add(mbMemberDto);
        }
        return memberDtoList;
    }
}
