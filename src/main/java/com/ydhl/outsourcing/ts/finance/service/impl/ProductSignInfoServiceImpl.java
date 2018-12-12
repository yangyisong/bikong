package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.ProductSignInfoDao;
import com.ydhl.outsourcing.ts.finance.dto.ProductSignInfoDto;
import com.ydhl.outsourcing.ts.finance.model.ProductSignInfo;
import com.ydhl.outsourcing.ts.finance.service.ProductSignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */
@Service
public class ProductSignInfoServiceImpl implements ProductSignInfoService{
    @Autowired
    private ProductSignInfoDao productSignInfoDao;

    @Override
    public void insertProductSignInfoList(List<ProductSignInfoDto> productSignInfoDtos,Long productId) {
        for (ProductSignInfoDto productSignInfoDto:productSignInfoDtos) {
            productSignInfoDao.insert(productSignInfoDtoToProductSignInfo(productSignInfoDto,productId));
        }
    }

    @Override
    public List<ProductSignInfoDto> getProductSignInfoList(Long productId) {
        List<ProductSignInfo> productSignInfos = productSignInfoDao.getProductSignInfoListByProductId(productId);
        List<ProductSignInfoDto> productSignInfoDtos = new ArrayList<>();
        for (ProductSignInfo productSignInfo2:productSignInfos) {
            productSignInfoDtos.add(productSignInfoToProductSignInfoDto(productSignInfo2));
        }
        return productSignInfoDtos;
    }

    @Override
    public void deleteProductSignInfoByProductId(Long productId) {
        productSignInfoDao.deleteProductSignInfoByProductId(productId);
    }

    public ProductSignInfo productSignInfoDtoToProductSignInfo(ProductSignInfoDto productSignInfoDto, Long productId){
        ProductSignInfo productSignInfo = new ProductSignInfo();
        productSignInfo.setId(productSignInfoDto.getId());
        productSignInfo.setCode(productSignInfoDto.getCode());
        productSignInfo.setProductId(productId);
        productSignInfo.setType(productSignInfoDto.getType());
        productSignInfo.setDefaultValue(productSignInfoDto.getDefaultValue());
        productSignInfo.setValue(productSignInfoDto.getValue());
        return productSignInfo;
    }

    public ProductSignInfoDto productSignInfoToProductSignInfoDto(ProductSignInfo productSignInfo){
        ProductSignInfoDto productSignInfoDto = new ProductSignInfoDto();
        productSignInfoDto.setId(productSignInfo.getId());
        productSignInfoDto.setCode(productSignInfo.getCode());
        productSignInfoDto.setProductId(productSignInfo.getProductId());
        productSignInfoDto.setType(productSignInfo.getType());
        productSignInfoDto.setDefaultValue(productSignInfo.getDefaultValue());
        productSignInfoDto.setValue(productSignInfo.getValue());
        return productSignInfoDto;
    }
}
