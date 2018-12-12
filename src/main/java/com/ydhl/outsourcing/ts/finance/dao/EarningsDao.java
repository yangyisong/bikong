package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Earnings;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dell on 2018/1/16.
 */
public interface EarningsDao extends BaseMapper<Earnings> {
    /**
     * 通过产品id 获取对应收益
     *
     * @param productId
     * @return
     */
    @Select("select * from earnings where product_id = #{productId} order by max_range asc")
    List<Earnings> getEarningsList(Long productId);

    @Delete("delete from earnings where product_id=#{productId}")
    void deleteEarningsByProductId(@Param("productId") Long productId);

    @Select("select out_earnings_ratio from earnings where min_range<=#{amount} and #{amount}<max_range product_id = #{productId}")
    BigDecimal getRateByProductIdAndAmount(Long productId, BigDecimal amount);
}
