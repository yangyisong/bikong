package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.base.report.CustomerImportVo;
import com.ydhl.outsourcing.ts.finance.base.report.CustomerReportVo;
import com.ydhl.outsourcing.ts.finance.base.report.ReportConstant;
import com.ydhl.outsourcing.ts.finance.common.report.ExcelDataDto;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.ExcelUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dto.CustomerDto;
import com.ydhl.outsourcing.ts.finance.dto.DictionaryDto;
import com.ydhl.outsourcing.ts.finance.dto.UserDto;
import com.ydhl.outsourcing.ts.finance.dto.query.CustomerQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.CustomSource;
import com.ydhl.outsourcing.ts.finance.enums.CustomType;
import com.ydhl.outsourcing.ts.finance.enums.Gender;
import com.ydhl.outsourcing.ts.finance.exception.BindingResultHelper;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Customer;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.service.CustomerService;
import com.ydhl.outsourcing.ts.finance.service.DictionaryService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 客户控制器
 * Created by dell on 2018/1/9.
 */
@Controller
@RequestMapping("/custom")
public class CustomController  extends BaseController {

    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private RoleDao roleDao;

    /**
     * 客户列表
     * @return
     */
    @RequestMapping(value = "/customList",method = RequestMethod.GET)
    public ModelAndView customList(PageModel pageModel, CustomerQueryDto customerQueryDto) throws BusinessException, UnknownHostException {
        Long userId = CurrentUserUtils.currentUserId();
        customerQueryDto.setUserId(userId);
        ModelAndView mav = getModelAndView();
        PageInfo<CustomerDto> pageInfo = customerService.queryCustomerListPage(pageModel,customerQueryDto);
        pageInfo.setPageNum(pageModel.getPage());
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(userId).get(0));
        mav.addObject("role", role);
        mav.addObject("pageInfo",pageInfo);
        mav.addObject("customerQueryDto",customerQueryDto);
        mav.setViewName("custom/customList");
        return mav;
    }

    /**
     * 客户详情
     *
     * @return
     */
    @GetMapping(value = "/customInfo.html")
    public ModelAndView getCustomInfo(Long customId) {
        ModelAndView mav = new ModelAndView();
//        Customer customer = customDao.queryCustomById(customId);
//        mav.addObject("customer", customer);
        mav.setViewName("/custom/customInfo");
        return mav;
    }

    /**
     * 客户资料编辑
     *
     * @return
     */
    @GetMapping(value = "/editCustom.html")
    public ModelAndView updateCustom(Long customerId) {
        ModelAndView mav = new ModelAndView();
        CustomerDto customerDto = customerService.getCustomerInfoById(customerId);
        List<DictionaryDto> dtoList = dictionaryService.getAllDictionary();
        List<UserDto> userDtoList = userService.getAllSalesman();
        Map<Object, CustomSource> sourceMap = customerService.getAllSoruce();
        Map<Object, CustomType> typeMap = customerService.getAllType();
        mav.addObject("customer", customerDto);
        mav.addObject("dictionaryList",dtoList);
        mav.addObject("userList", userDtoList);
        mav.addObject("sourceMap", sourceMap);
        mav.addObject("typeMap",typeMap);
        mav.setViewName("custom/editCustom");
        return mav;
    }

    /**
     * 添加客户页面
     * @return
     */
    @GetMapping(value = "/addCustom.html" )
    public ModelAndView addCustom(CustomerDto customerDto) {
        ModelAndView mav = new ModelAndView();
        List<DictionaryDto> dtoList = dictionaryService.getAllDictionary();
        //Map<Object, CustomSource> sourceMap = customerService.getAllSoruce();
        Map<Object, CustomType> typeMap = customerService.getAllType();
        //mav.addObject("sourceMap", sourceMap);
        mav.addObject("typeMap",typeMap);
        mav.addObject("dictionaryList",dtoList);
        mav.setViewName("custom/addCustom");
        return mav;
    }

    /**
     * 添加客户
     *
     * @param customerDto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/addCustomer.do")
    @ResponseBody
    public Map<String, Object> adminCustom(@Validated(CustomerDto.Add.class) CustomerDto customerDto, BindingResult bindingResult) throws BusinessException, UnknownHostException {
        BindingResultHelper.checkAndThrowErrors(bindingResult);
        Long userId = CurrentUserUtils.currentUserId();
        customerDto.setUserId(userId);
        Map<String , Object> map = customerService.insertCustomer(customerDto);
        return map;
    }

    /**
     * 更新资料
     *
     * @param customerDto
     * @return
     */
    @PostMapping(value = "/updateCustomer.do")
    @ResponseBody
    public Object updateCustomer(CustomerDto customerDto) {
        Map<String,Object> map = new HashMap<>();
        //ModelAndView mav = new ModelAndView();
        Customer customer = customerService.getCustomByPhone(customerDto.getPhone());
        if (customer != null) {
            if (customer.getId() != customerDto.getId()) {
                map.put("msg", "该手机号已被使用，请重新填写");
                return map;
            }
        }
        try {
            customerService.updateCustomer(customerDto);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","编辑失败");
        }
        return map;
        //mav.setViewName("redirect:customList.html");
        //return mav;
    }

    /**
     * 客户转换页面跳转
     * @return
     */
    @GetMapping(value = "/customChange.html")
    public ModelAndView converCustomerPage(Long customerId) {
        ModelAndView mav = new ModelAndView();
        CustomerDto customerDto = customerService.getCustomerInfoById(customerId);
        List<UserDto> userDtoList = userService.getAllSalesman();
        mav.addObject("userList", userDtoList);
        mav.addObject("customer", customerDto);
        mav.setViewName("custom/customChange");
        return mav;
    }

    /**
     * 客户导入页面跳转
     * @return
     */
    @GetMapping(value = "/customImport.html")
    public ModelAndView customImport() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("custom/customImport");
        return mav;
    }

    /**
     * 客户转换
     *
     * @param customerId 客户id
     * @param userId 业务员id
     * @return
     */
    @PostMapping(value = "/converCustomer.do")
    @ResponseBody
    public Map<String ,Object> converCustomer(Long customerId, Long userId) throws BusinessException, UnknownHostException {
        Map<String, Object> result = customerService.converCustomer(customerId, userId);
        return result;
    }

    /**
     * 下载客户导入模板
     *
     * @return
     */
    @GetMapping(value = "/exportImportTemplate.html")
    public ModelAndView exportImportTemplate() throws BusinessException {
        CustomerImportVo importVo = new CustomerImportVo();
        int sheetNum = importVo.addOneSheet(CustomerImportVo.F_S_COL_HD);
        importVo.setFileName(ReportConstant.CUSTOMER_IMPORT);
        return new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, importVo);
    }

    /**
     * 客户导出
     * @param customerQueryDto
     * @return
     */
    @GetMapping(value = "/export.html")
    public ModelAndView export(CustomerQueryDto customerQueryDto) throws BusinessException, UnknownHostException {
        Long userId = CurrentUserUtils.currentUserId();
        customerQueryDto.setUserId(userId);
        PageInfo<CustomerDto> pageInfo = customerService.queryCustomerPage( customerQueryDto);
        CustomerReportVo reportVo = new CustomerReportVo();
        int sheetNum = reportVo.addOneSheet(CustomerReportVo.F_S_COL_HD);
        for (CustomerDto customerDto : pageInfo.getList()) {
            reportVo.fillOneRow(
                    sheetNum
                    , customerDto.getName()
                    , customerDto.getGenderValue()
                    , customerDto.getIdcard()
                    , customerDto.getPhone()
                    , customerDto.getAge()
                    , customerDto.getBirthday()
                    , customerDto.getTel()
                    , customerDto.getWechat()
                    , customerDto.getEmail()
                    , customerDto.getQq()
                    , customerDto.getContractCount()
                    , customerDto.getCrashName()
                    , customerDto.getCrashPhone()
                    , customerDto.getAddress()
                    , customerDto.getTypeValue()
                    , customerDto.getSalesmanName()
                    , customerDto.getCreateTime()
            );
        }
        reportVo.setFileName(ReportConstant.CUSTOMER_REPORT);
        return new ModelAndView(ExcelDataDto.VIEW_NAME, ExcelDataDto.MODEL_NAME, reportVo);
    }

    /**
     * 读取客户列表excel
     *
     * @param multipartFile 上传文件
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importCustomExcel(@RequestParam("file") MultipartFile multipartFile) throws BusinessException, Exception {
        Long userId = CurrentUserUtils.currentUserId();
        Map<String, Object> map = new HashMap<>();
        if (!(multipartFile.getOriginalFilename().endsWith(EXCEL_XLS) || multipartFile.getOriginalFilename().endsWith(EXCEL_XLSX))) {
            map.put("msg","文件不是Excel表格，请重新选择上传文件");
            return map;
        }
        InputStream inputStream = multipartFile.getInputStream();
        List<CustomerDto> customerList = null;
        try {
            customerList = importCustomerExcel(inputStream);
        } catch (BusinessException e) {
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        map = customerService.insertCustomerList(customerList, userId);
        if (map == null || map.size() == 0) {
            map.put("msg", "success");
        }
        return map;
    }

    /**
     * 读取excel数据
     *
     * @param inputStream 输入流
     * @return 返回结果list
     */
    public static List<CustomerDto> importCustomerExcel(InputStream inputStream) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        Sheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;
        Iterator rows = sheet.rowIterator();
//        rows.next();
        List<List<Object>> resultList = new ArrayList<>();
        if (sheet == null) {
            return null;
        }
        int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();
        List<String[]> list = new ArrayList<String[]>();
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            if (sheet.getRow(rowNum) != null) {
                Row xssfRow = sheet.getRow(rowNum);
                short firstCellNum = xssfRow.getFirstCellNum();
                if (firstCellNum != coloumNum) {
                    String[] values = new String[coloumNum];
                    for (int cellNum = firstCellNum; cellNum < coloumNum; cellNum++) {
                        Cell xssfCell = xssfRow.getCell(cellNum);
                        if (xssfCell == null) {
                            values[cellNum] = "";
                        } else {
                            values[cellNum] = ExcelUtil.parseExcel(xssfCell);
                        }
                    }
                    list.add(values);
                }
            }
        }
//        rows.next();
//        Integer index = 2;
//        try {
//            while (rows.hasNext()) {
//                row = (XSSFRow) rows.next();
//                Iterator cells = row.cellIterator();
//                List<Object> objectList = new ArrayList<>();
//                Integer cellIndex = 1;
//                while (cells.hasNext()) {
//                    cell = (XSSFCell) cells.next();
//
//                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//                    //因为模板固定第三列是数值，所以固定判断此值必不为字符串
///*                    if ((cellIndex == 1 || cellIndex == 4) && cell.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
//                        throw new BusinessException("",index.toString());
//                    }*/
//                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
//                        Integer i = cell.getColumnIndex();
//                        String stringCellValue = cell.getStringCellValue();
//                        //防止备注超过数据库限制
///*                        if (cellIndex == 5 && stringCellValue.length() > 255) {
//                            throw new BusinessException("", index.toString());
//                        }*/
//                        objectList.add(stringCellValue);
//                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
//                        //数字类型不可转化为字符串
//                        objectList.add(cell.getNumericCellValue());
//                    }else{
//                        objectList.add(null);
//                    }
//                    cellIndex++;
//                }
//                //如果list长度没有5，代表该行有空数据
///*                if (objectList.size() != 6) {
//                    throw new BusinessException("", index.toString());
//                }*/
//                resultList.add(objectList);
//                index++;
//            }
//        } catch (Exception e) {
//            throw new BusinessException("", index.toString());
//        }

        List<CustomerDto> customerList = new ArrayList<>();
        for (String[]  strings: list) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setName(strings[0]);
            customerDto.setGender((strings[1].equals('男')? Gender.FEMA:Gender.FEMA));
            customerDto.setIdcard(String.valueOf(strings[2]));
            customerDto.setPhone(String.valueOf(strings[3]));
            customerDto.setAge(strings[4].equals("") ? null : Integer.parseInt(String.valueOf(strings[4])));
            customerDto.setBirthday(strings[5].equals("") ? null : DateUtilHelper.fomatDate(strings[5]));
            customerDto.setTel(strings[6]);
            customerDto.setWechat(strings[7]);
            customerDto.setEmail(strings[8]);
            customerDto.setQq(strings[9]);
            customerDto.setBuyTimes(strings[10].equals("") ? null : Integer.valueOf(strings[10]));
            customerDto.setCrashName(strings[11]);
            customerDto.setCrashPhone(strings[12]);
            customerDto.setAddress(strings[13]);
            customerDto.setType(strings[14].equals("") ? null : (strings[14].equals("内部员工") ? CustomType.OUT_CUS : CustomType.IN_CUS));
            customerList.add(customerDto);
        }
        return customerList;
    }

}
