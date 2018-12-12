package com.ydhl.outsourcing.ts.finance.common.utils;

import java.math.BigDecimal;

/**
 * @author Martins
 * @create 2018/1/26 10:41.
 * @description
 */
public class EaringsUtil {

    //保留两位小数
    private static final Integer SCALE = 2;

    /**
     * 计算每日收益
     *
     * @param amount 合同金额
     * @param earingRatio 年收益比
     * @return 每日收益
     */
    public static BigDecimal getEverydayEarning(BigDecimal amount, BigDecimal earingRatio) {
        /*BigDecimal dayEarning = amount.multiply(earingRatio).divide(new BigDecimal(360), SCALE, BigDecimal.ROUND_HALF_UP);*/
        BigDecimal dayEarning = amount.multiply(earingRatio.divide(new BigDecimal(100), 10 , BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(360), 10, BigDecimal.ROUND_HALF_UP);
        return dayEarning;
    }
}
