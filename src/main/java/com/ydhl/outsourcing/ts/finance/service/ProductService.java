package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.ProductDto;
import com.ydhl.outsourcing.ts.finance.dto.query.ProductQueryDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/1/16.
 */
public interface ProductService {
    /**
     * 查询产品分页
     * @param pageModel
     * @param productQueryDto
     * @return
     */
    PageInfo<ProductDto> queryProductListPage(PageModel pageModel, ProductQueryDto productQueryDto);

    /**
     * 通过Id获取产品
     * @param id
     * @return
     */
    ProductDto getProductDtoById(Long id);

    /**
     * 获取有效的产品列表
     * @return
     */
    List<ProductDto> getProductList(Boolean struts, Date endTime);

    /**
     * 插入产品
     * @param productDto
     * @return
     */
    Map<Object,Object> insertProduct(ProductDto productDto);

    /**
     * 编辑产品状态
     * @param id
     * @param struts
     */
    void editProductStruts(Long id,Boolean struts);

    /**
     * 编辑产品
     */
    Map<Object,Object> editProduct(ProductDto productDto);

    /**
     * 获取所有已上架产品信息
     *
     * @return
     */
    List<ProductDto> getAllShelveProduct();

    /**
     * 根据id修改产品外部剩余额度
     * @param remaingAmount
     * @param id
     */
    void updateRemainAmount(BigDecimal remaingAmount, Integer buyCount, Long id);

    /**
     * 修改内部额度
     *
     * @param interRamainAmount
     * @param id
     */
    void updateInterRamainAmount(BigDecimal interRamainAmount, Integer buyCount, Long id);

    /**
     * 通过产品Id获取产品
     */
    List<ProductDto> getProductDtosByIds(List<Long> ids);

    void updateProduct(ProductDto productDto);

    List<String> getProductListByVoucherId(Long id);
}
