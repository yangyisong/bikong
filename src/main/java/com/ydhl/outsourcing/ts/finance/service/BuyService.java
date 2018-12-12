package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.BuyDto;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 17:49.
 */
public interface BuyService {

    /**
     * 查询客户所有购买需求
     *
     * @param customerId 客户id
     * @return 购买需求
     */
    List<BuyDto> getAllCustomBuy(Long customerId);

    /**
     * 新增客户购买数据
     *
     * @param buyDtoList
     */
    void inserBuyList(List<BuyDto> buyDtoList, Long customerId);
}
