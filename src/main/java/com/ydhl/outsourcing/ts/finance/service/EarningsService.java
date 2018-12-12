package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.EarningsDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dell on 2018/1/16.
 */
public interface EarningsService {
    /**
     * 获取阶梯收益列表
     * @param productId
     * @return
     */
    List<EarningsDto> getEarningsList(Long productId);

    /**
     * 插入阶梯收益列表
     * @param earningsDtos
     * @param productId
     */
    void insertEarningsList(List<EarningsDto> earningsDtos,Long productId);

    /**
     * 批量修改收益列表
     */
    void updateEarningsList(List<EarningsDto> earningsDtos,Long productId);

    /**
     * 获取某个产品金额对应的阶梯收益比
     */
    BigDecimal getRateByProductIdAndAmount(Long productId,BigDecimal amount);
}
