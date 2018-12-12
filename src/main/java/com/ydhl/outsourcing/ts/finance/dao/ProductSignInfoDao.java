package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ProductSignInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 凭证Dao
 * @create 2017-12-18 上午 11:30
 * @description
 */
public interface ProductSignInfoDao extends BaseMapper<ProductSignInfo> {

    @Select("select * from product_sign_info where product_id = #{productId}")
    public List<ProductSignInfo> getProductSignInfoListByProductId(@Param("productId") Long productId);

    @Delete("delete from product_sign_info where product_id = #{productId}")
    public void deleteProductSignInfoByProductId(@Param("productId") Long productId);
}
