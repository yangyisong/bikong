package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.ProductSignInfoDto;

import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */
public interface ProductSignInfoService {
    /**
     * 插入产品签约信息列表
     * @param productSignInfoDtos
     */
    public void insertProductSignInfoList(List<ProductSignInfoDto> productSignInfoDtos,Long productId);

    /**
     * 获取产品签约信息列表
     */
    public List<ProductSignInfoDto> getProductSignInfoList(Long productId);

    /**
     * 删除产品签约信息
     */
    public void deleteProductSignInfoByProductId(Long productId);
}
