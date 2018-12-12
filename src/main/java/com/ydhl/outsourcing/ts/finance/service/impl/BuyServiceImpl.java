package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.BuyDao;
import com.ydhl.outsourcing.ts.finance.dao.ProductDao;
import com.ydhl.outsourcing.ts.finance.dto.BuyDto;
import com.ydhl.outsourcing.ts.finance.model.Buy;
import com.ydhl.outsourcing.ts.finance.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 17:51.
 * @description
 */
@Service
public class BuyServiceImpl implements BuyService {

    @Autowired
    private BuyDao buyDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<BuyDto> getAllCustomBuy(Long customerId) {
        List<Buy> buyList = buyDao.getAllCustomBuyByCustomId(customerId);
        List<BuyDto> buyDtoList = new ArrayList<>();
        for (Buy buy : buyList) {
            BuyDto buyDto = new BuyDto();
            buyDto.setProductName(productDao.selectByPrimaryKey(buy.getProductId()).getName());
            buyDto.setTimeValue(buy.getTimePeriod().getDesc());
            buyDtoList.add(buyDto);
        }
        return buyDtoList;
    }

    @Override
    public void inserBuyList(List<BuyDto> buyDtoList, Long customerId) {
        if (buyDtoList != null) {
            for (BuyDto buyDto : buyDtoList) {
                Buy buy = new Buy();
                buy.setProductId(buyDto.getProductId());
                buy.setCustomerId(customerId);
                buy.setTimePeriod(buyDto.getTimePeriod());
                buyDao.insert(buy);
            }
        }
    }
}
