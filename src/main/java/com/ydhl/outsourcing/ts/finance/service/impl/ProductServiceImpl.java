package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.BeanUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dao.ProductDao;
import com.ydhl.outsourcing.ts.finance.dto.EarningsDto;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.ProductDto;
import com.ydhl.outsourcing.ts.finance.dto.VoucherDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ProductQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.EarningsType;
import com.ydhl.outsourcing.ts.finance.example.ProductExample;
import com.ydhl.outsourcing.ts.finance.model.Product;
import com.ydhl.outsourcing.ts.finance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2018/1/16.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private EarningsService earningsService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private ProductSignInfoService productSignInfoService;

    @Autowired
    private OperationService operationService;

    @Override
    public PageInfo<ProductDto> queryProductListPage(PageModel pageModel, ProductQueryDto productQueryDto) {
        Example example = ProductExample.getProductExample(productQueryDto, pageModel);
        Page<Product> products = (Page<Product>)productDao.selectByExample(example);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product:products) {
            ProductDto productDto = productToProductDto(product);
            productDto = productToProductDto(productDto,product);
            productDtos.add(productDto);
        }
        return new PageInfo<>(productDtos,products.getPages());
    }

    @Override
    public ProductDto getProductDtoById(Long id) {
        if(id!=null){
            Product product = new Product();
            product.setId(id);
            product = productDao.selectByPrimaryKey(product);
            ProductDto productDto = null;
            if(product!=null){
                productDto = productToProductDto(product);
                productDto.setVoucherDto(voucherService.getVoucherById(product.getVoucherId()));
                Product product2 = new Product();
                if(product.getConversionProductId()!=null) {
                    product2.setId(product.getConversionProductId());
                    product2 = productDao.selectByPrimaryKey(product2);
                    if (product2 != null) {
                        ProductDto productDto2 = productToProductDto(product2);
                        productDto.setConversionProductDto(productDto2);
                    }
                }
            }
            return productDto;
        }
        return null;
    }

    @Override
    public List<ProductDto> getProductList(Boolean struts, Date endTime) {
        List<Product> products = productDao.getProductList(struts,endTime);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product:products) {
            ProductDto productDto = productToProductDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    @Transactional
    public Map<Object,Object> insertProduct(ProductDto productDto) {
        Map<Object,Object> map = new HashMap<>();
        try{
            //判断数据合法性
            //1.判断产品编号是否重复
            Product p = productDao.getProductsByNumber(productDto.getNumber());
            if(p!=null){
                map.put("msg","产品编号已存在");
                return map;
            }
            //2.设置阶梯收益比
            if(productDto.getEarningsType().equals(EarningsType.FIXED)){
                productDto.setEarningsDtos(new ArrayList<>());
                EarningsDto earningsDto = new EarningsDto();
                earningsDto.setOutEarningsRatio(productDto.getOutRateValue());
                productDto.getEarningsDtos().add(earningsDto);
            }else{
                //检查金额是否符合规则
                if(productDto.getSalesAmount().multiply(productDto.getOutStartAmount()).multiply((productDto.getSupportInter()?productDto.getInterQuota():new BigDecimal(1))).compareTo(new BigDecimal(0))<=0){
                    map.put("msg","金额不能小于等于0");
                    return map;
                }
                if(productDto.getSalesAmount().compareTo(productDto.getOutStartAmount())<0){
                    map.put("msg","产品总金额不能小于起购金额");
                    return map;
                }/*else if(productDto.getSupportInter() && productDto.getSalesAmount().compareTo(productDto.getInterQuota())<0){
                    map.put("msg","产品总金额不能小于内部购买额度");
                    return map;
                }*/
                List<EarningsDto> earningsDtos = productDto.getEarningsDtos();
                for(int i=0;i<earningsDtos.size()-1;i++){
                    BigDecimal minRange = earningsDtos.get(i).getMinRange();
                    BigDecimal maxRange = earningsDtos.get(i).getMaxRange();
                    BigDecimal minRange1 = earningsDtos.get(i+1).getMinRange();
                    BigDecimal maxRange1 = earningsDtos.get(i+1).getMaxRange();
                    if(maxRange.compareTo(minRange)<=0 || maxRange.compareTo(minRange1)!=0  || maxRange1.compareTo(minRange1)<=0){
                        map.put("msg","阶梯收益不正确，请仔细检查");
                        return map;
                    }
                }
            }
            Product product = productDtoToProduct(productDto);
            product.setRemainingAmount(productDto.getSalesAmount());
            product.setInterRemainingAmount(productDto.getInterQuota());
            productDao.insert(product);
            Long productId = product.getId();
            //插入阶梯收益列表
            earningsService.insertEarningsList(productDto.getEarningsDtos(),productId);
            //插入产品签约信息列表
            if(productDto.getProductSignInfoDtos()!=null){
                productSignInfoService.insertProductSignInfoList(productDto.getProductSignInfoDtos(),productId);
            }
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("新增产品("+ productDto.getNumber() + ")信息");
            operationDto.setRemark("产品销售总金额：" + productDto.getSalesAmount());
            operationService.insertOperation(operationDto);
            map.put("msg","Success");
        }catch(Exception e){
            System.out.println(e.getMessage());
            map.put("msg","新增出错了,请仔细检查参数");
            return map;
        }
        return map;
    }

    @Override
    public void editProductStruts(Long id, Boolean struts) {
       /*  ProductDto productDto = getProductDtoById(id);
         Long closeTme = productDto.getCloseTime().getTime();
        if(closeTime<)*/
        productDao.editProductStruts(id,struts);
    }

    @Override
    @Transactional
    public Map<Object,Object> editProduct(ProductDto productDto) {
        Map<Object,Object> map = new HashMap<>();
        Product dto = productDao.selectByPrimaryKey(productDto.getId());
        try{
            //判断产品编辑后的产品编号是否重复
            Product p = productDao.getProductsByNumber(productDto.getNumber());
            if(p.getId()!=productDto.getId()){
                map.put("msg","产品编号已存在");
                return map;
            }
            //设置阶梯收益比
            if(productDto.getEarningsType().equals(EarningsType.FIXED)){
                productDto.setEarningsDtos(new ArrayList<>());
                EarningsDto earningsDto = new EarningsDto();
                earningsDto.setOutEarningsRatio(productDto.getOutRateValue());
                productDto.getEarningsDtos().add(earningsDto);
            }else{
                //检查金额是否符合规则
                if(productDto.getSalesAmount().multiply(productDto.getOutStartAmount()).multiply((productDto.getSupportInter()?productDto.getInterQuota():new BigDecimal(1))).compareTo(new BigDecimal(0))<=0){
                    map.put("msg","金额不能小于等于0");
                    return map;
                }
                if(productDto.getSalesAmount().compareTo(productDto.getOutStartAmount())<=0){
                    map.put("msg","产品总金额不能小于起购金额");
                    return map;
                }/*else if(productDto.getSupportInter() && productDto.getSalesAmount().compareTo(productDto.getInterQuota())<=0){
                    map.put("msg","产品总金额不能小于内部购买额度");
                    return map;
                }*/
                List<EarningsDto> earningsDtos = productDto.getEarningsDtos();
                for(int i=0;i<earningsDtos.size()-1;i++){
                    BigDecimal minRange = earningsDtos.get(i).getMinRange();
                    BigDecimal maxRange = earningsDtos.get(i).getMaxRange();
                    BigDecimal minRange1 = earningsDtos.get(i+1).getMinRange();
                    BigDecimal maxRange1 = earningsDtos.get(i+1).getMaxRange();
                    if(maxRange.compareTo(minRange)<=0 || maxRange.compareTo(minRange1)!=0  || maxRange1.compareTo(minRange1)<=0){
                        map.put("msg","阶梯收益不正确，请仔细检查");
                        return map;
                    }
                }
            }
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("修改产品("+ productDto.getNumber() + ")基本信息");
            if (!dto.getSalesAmount().equals(productDto.getSalesAmount())) {
                operationDto.setRemark("产品销售总金额：" + dto.getSalesAmount() + "到"+ productDto.getSalesAmount());
            }
            operationService.insertOperation(operationDto);
            Product product = productDtoToProduct(productDto);
            //更新产品
            productDao.updateByPrimaryKey(product);
            //更新产品阶梯收益
            earningsService.updateEarningsList(productDto.getEarningsDtos(),productDto.getId());

            //删除产品签约信息列表
            productSignInfoService.deleteProductSignInfoByProductId(productDto.getId());
            if(productDto.getProductSignInfoDtos()!=null && productDto.getProductSignInfoDtos().size()!=0){
                //插入产品签约列表
                productSignInfoService.insertProductSignInfoList(productDto.getProductSignInfoDtos(),productDto.getId());
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"--------------");
            map.put("msg","编辑出错了,请仔细检查参数");
            return map;
        }
        map.put("msg","Success");
        return map;
    }

    @Override
    public List<ProductDto> getAllShelveProduct() {
        List<Product> productList = productDao.getAllShelveProduct();
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCycle(product.getCycle());
            dtoList.add(productDto);
        }
        return dtoList;
    }

    @Override
    public void updateRemainAmount(BigDecimal remaingAmount, Integer buyCount, Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        product.setRemainingAmount(remaingAmount);
        product.setBuyCount(buyCount);
        productDao.updateByPrimaryKey(product);
    }

    @Override
    public void updateInterRamainAmount(BigDecimal interRamainAmount,Integer buyTime, Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        product.setInterRemainingAmount(interRamainAmount);
        product.setBuyCount(buyTime);
        productDao.updateByPrimaryKey(product);
    }

    @Override
    public List<ProductDto> getProductDtosByIds(List<Long> ids) {
        List<Product> products = productDao.getProductDtosByIds(ids);
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product:products){
            ProductDto productDto = productToProductDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product product = productDao.selectByPrimaryKey(productDto.getId());
        product.setRemainingAmount(productDto.getRemainingAmount());
        product.setInterRemainingAmount(productDto.getInterRemainingAmount());
        product.setBuyCount(productDto.getBuyCount());
        productDao.updateByPrimaryKey(product);
    }

    @Override
    public List<String> getProductListByVoucherId(Long id) {
        List<Product> productList = productDao.getProductListByVoucherId(id);
        List<String> list = new ArrayList<>();
        for (Product product : productList) {
            list.add(product.getName());
        }
        return list;
    }

    public ProductDto productToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNumber(product.getNumber());
        productDto.setName(product.getName());
        productDto.setSalesAmount(product.getSalesAmount());
        productDto.setRemainingAmount(product.getRemainingAmount());
        productDto.setOutStartAmount(product.getOutStartAmount());
        productDto.setContent(product.getContent());
        productDto.setSupportVoucher(product.getSupportVoucher());
        productDto.setSupportManagerAudit(product.getSupportManagerAudit());
        productDto.setSupportInter(product.getSupportInter());
        productDto.setSupportConversion(product.getSupportConversion());
        productDto.setSupportQuit(product.getSupportQuit());
        productDto.setCycle(product.getCycle());
        productDto.setBillingCycle(product.getBillingCycle());
        productDto.setQuiteFeeType(product.getQuiteFeeType());
        productDto.setQuiteFeeValue(product.getQuiteFeeValue());
        productDto.setOpenTime(product.getOpenTime());
        productDto.setCloseTime(product.getCloseTime());
        productDto.setInterQuota(product.getInterQuota());
        productDto.setInterQuit(product.getInterQuit());
        productDto.setInterEarningsRatio(product.getInterEarningsRatio());
        productDto.setInterRemainingAmount(product.getInterRemainingAmount());
        productDto.setBuyCount(product.getBuyCount());
        productDto.setInterCycle(product.getInterCycle());
        productDto.setStruts(product.getStruts());
        productDto.setRemark(product.getRemark());
        productDto.setIntOrB(product.getIntOrB());
        productDto.setInterRemainingAmount(product.getInterRemainingAmount());
        productDto.setInterQuota(product.getInterQuota());
        productDto.setEarningsDtos(earningsService.getEarningsList(product.getId()));
        productDto.setProductSignInfoDtos(productSignInfoService.getProductSignInfoList(product.getId()));
        productDto.setEarningsType(product.getEarningsType());
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setId(product.getVoucherId());
        productDto.setVoucherDto(voucherDto);
        return productDto;
    }

    public ProductDto productToProductDto(ProductDto productDto,Product product){
        if(product!=null){
            productDto.setVoucherDto(voucherService.getVoucherById(product.getVoucherId()));
            if(product.getConversionProductId()!=null){
                productDto.setConversionProductDto(getProductDtoById(product.getConversionProductId()));
            }else{
                productDto.setConversionProductDto(null);
            }
        }
        return productDto;
    }

    public Product productDtoToProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setNumber(productDto.getNumber());
        product.setName(productDto.getName());
        product.setSalesAmount(productDto.getSalesAmount());
        product.setRemainingAmount(productDto.getRemainingAmount());
        product.setOutStartAmount(productDto.getOutStartAmount());
        product.setContent(productDto.getContent());
        product.setSupportVoucher(productDto.getSupportVoucher());
        if(productDto.getVoucherDto()!=null){
            product.setVoucherId(productDto.getVoucherDto().getId());
        }
        product.setSupportManagerAudit(productDto.getSupportManagerAudit());
        product.setSupportInter(productDto.getSupportInter());
        product.setSupportConversion(productDto.getSupportConversion());
        if(productDto.getConversionProductDto()!=null){
            product.setConversionProductId(productDto.getConversionProductDto().getId());
        }
        product.setSupportQuit(productDto.getSupportQuit());
        product.setBillingCycle(productDto.getBillingCycle());
        product.setCycle(productDto.getCycle());
        product.setQuiteFeeType(productDto.getQuiteFeeType());
        product.setQuiteFeeValue(productDto.getQuiteFeeValue());
        product.setOpenTime(productDto.getOpenTime());
        product.setCloseTime(productDto.getCloseTime());
        product.setInterQuota(productDto.getInterQuota());
        product.setInterQuit(productDto.getInterQuit());
        product.setInterEarningsRatio(productDto.getInterEarningsRatio());
        product.setInterRemainingAmount(productDto.getInterRemainingAmount());
        product.setInterCycle(productDto.getInterCycle());
        product.setStruts(productDto.getStruts());
        product.setRemark(productDto.getRemark());
        product.setIntOrB(productDto.getIntOrB());
        product.setEarningsType(productDto.getEarningsType());
        return product;
    }

}
