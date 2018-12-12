package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Buy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 17:48.
 * @description
 */
public interface BuyDao extends BaseMapper<Buy> {

    /**
     * 查询客户所有购买需求
     *
     * @param customerId 客户id
     * @return
     */
    @Select("select * from buy where customer_id = #{customerId}")
    List<Buy> getAllCustomBuyByCustomId(@Param("customerId") Long customerId);

    /**
     * 清空客户数据
     *
     * @param customerId
     */
    @Delete("delete from buy where customer_id = #{customerId}")
    void deleteByCustomId(@Param("customerId") Long customerId);
}
