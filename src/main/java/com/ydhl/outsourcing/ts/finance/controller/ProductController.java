package com.ydhl.outsourcing.ts.finance.controller;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.base.BaseController;
import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dao.RoleDao;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.ProductDto;
import com.ydhl.outsourcing.ts.finance.dto.ProductSignInfoDto;
import com.ydhl.outsourcing.ts.finance.dto.VoucherDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ProductQueryDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Role;
import com.ydhl.outsourcing.ts.finance.service.ContractService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.ProductService;
import com.ydhl.outsourcing.ts.finance.service.ProductSignInfoService;
import com.ydhl.outsourcing.ts.finance.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martins
 * @create 2018/1/10 16:50.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private ProductSignInfoService productSignInfoService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private RoleDao roleDao;

    /**
     * 获取产品列表
     *
     * @return 产品列表
     */
    @GetMapping(value = "/productList")
    public ModelAndView getProductList(PageModel pageModel, ProductQueryDto productQueryDto) throws UnknownHostException, BusinessException {
        ModelAndView mav = getModelAndView();
        PageInfo<ProductDto> pageInfo = productService.queryProductListPage(pageModel,productQueryDto);
        pageInfo.setPageNum(pageModel.getPage());
        Role role = roleDao.selectByPrimaryKey(roleDao.findRoleIdListByUserId(CurrentUserUtils.currentUserId()).get(0));
        mav.addObject("role", role);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("productQueryDto", productQueryDto);
        mav.setViewName("product/productList");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看产品列表");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 跳转产品新增页面
     */
    @GetMapping(value = "/toAddProduct.html")
    public ModelAndView toAddProduct() throws UnknownHostException, BusinessException {
        ModelAndView mav = getModelAndView();
        List<VoucherDto> voucherDtos = voucherService.getVoucherList();
        List<ProductDto> productDtos = productService.getProductList(true,new Date());
        mav.addObject("voucherDtos",voucherDtos);
        mav.addObject("productDtos",productDtos);
        mav.setViewName("product/addProduct");
        return mav;
    }

    /**
     * 判断能否编辑
     */
    @PostMapping(value = "/canEdit")
    @ResponseBody
    public Map<String,String> canEdit(Long productId){
        Map<String,String> map = new HashMap<>();
        Long  count = contractService.getContractNumberByProductId(productId);
        if(count<=0){
            map.put("msg","success");
        }else{
            map.put("msg","faild");
        }
        return map;
    }

    @PostMapping(value = "/addProduct")
    @ResponseBody
    public Map<Object,Object> addProduct(ProductDto productDto){
        //return productService.insertProduct(productDto);
        Map<Object,Object> map = productService.insertProduct(productDto);
        return map;
    }

    /**
     * 修改产品状态
     */
    @PostMapping(value = "/editProductStruts")
    @ResponseBody
    public Object editProductStruts(Long id,Boolean struts){
        Map<String,Object> result  = new HashMap<String,Object>();
        try {
            productService.editProductStruts(id, struts);
            result.put("status", "success");
        }catch (Exception e){
            result.put("status", "faild");
        }
        return result;
    }

    /**
     * 跳转产品编辑页面
     */
    @GetMapping(value = "/toEditProduct.html")
    public ModelAndView toEditProduct(Long id) throws UnknownHostException, BusinessException {
        ModelAndView mav = getModelAndView();
        ProductDto productDto = productService.getProductDtoById(id);
        List<VoucherDto> voucherDtos = voucherService.getVoucherList();
        List<ProductDto> productDtos = productService.getProductList(true,new Date());
        for (int i=0;i<productDtos.size();i++){
            if(productDtos.get(i).getId()==productDto.getId()){
                productDtos.remove(i);
            }
        }
        mav.addObject("voucherDtos",voucherDtos);
        mav.addObject("productDtos",productDtos);
        mav.addObject("productDto", productDto);
        mav.setViewName("product/editProduct");
        return mav;
    }

    @PostMapping(value="/editProduct")
    @ResponseBody
    public Object editProduct(ProductDto productDto){
        Map<Object,Object> map = productService.editProduct(productDto);
        return map;
    }

    /**
     * 跳转产品详情页面
     */
    @GetMapping(value = "/toProductDetail.html")
    public ModelAndView toProductDetail(Long id) throws UnknownHostException, BusinessException {
        ModelAndView mav = getModelAndView();
        ProductDto productDto = productService.getProductDtoById(id);
        mav.addObject("productDto", productDto);
        mav.setViewName("product/productDetail");
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看产品("+ productDto.getNumber()+")详情");
        operationService.insertOperation(operationDto);
        return mav;
    }

    /**
     * 跳转产品新增页面
     */
    @GetMapping(value = "/addParam")
    public ModelAndView addParam(){
        ModelAndView mav = getModelAndView();
        mav.setViewName("product/addParam");
        return mav;
    }

    /**
     * 通过产品Id获取产品签约信息列表
     */
    @GetMapping(value = "/getProductSignInfoList")
    @ResponseBody
    public List<ProductSignInfoDto> getProductSignInfoList(Long productId){
        List<ProductSignInfoDto> productSignInfoDtos = productSignInfoService.getProductSignInfoList(productId);
        return productSignInfoDtos;
    }
}
