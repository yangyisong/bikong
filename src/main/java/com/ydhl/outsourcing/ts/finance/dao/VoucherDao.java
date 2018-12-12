package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Voucher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 凭证Dao
 * @create 2017-12-18 上午 11:30
 * @description
 */
public interface VoucherDao extends BaseMapper<Voucher> {

    @Update("update voucher set united = #{united} where id = #{id}")
    void editUnited(@Param("id") Long id, @Param("united") Integer united);
}
