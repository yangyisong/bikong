package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.dto.ProductDto;
import com.ydhl.outsourcing.ts.finance.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author Martins
 * @create 2018/1/15 17:53.
 */
public interface ProductDao extends BaseMapper<Product> {

    /**
     * 通过产品名查询产品id
     *
     * @param productName 产品名
     * @return 产品id
     */
    @Select("select id from product where name = #{name}")
    Long getProductIdByName(@Param("name") String productName);

    @Update("update product set struts = #{struts} where id = #{id}")
    public void editProductStruts(@Param("id") Long id, @Param("struts") Boolean struts);

    @Select("select * from product where number = #{number}")
    public Product getProductsByNumber(@Param("number") String number);

    @Select("select * from product where struts = #{struts} and close_time >=#{endTime}")
    public List<Product> getProductList(@Param("struts") Boolean struts, @Param("endTime") Date endTime);

    /**
     * 获取所有已上架的产品
     *
     * @return
     */
    @Select("select * from product where struts = true")
    List<Product> getAllShelveProduct();

    /**
     * 查询所有允许转换的上架产品
     *
     * @return
     */
    @Select("select * from product where support_conversion = true and struts = true")
    List<Product> getAllConversionProduct();

    @Select("<script> "
            + "SELECT * from product where id in "
            + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' > "
            +"#{item}"
            +"</foreach>"
            +"</script>")
    List<Product> getProductDtosByIds(@Param("ids") List<Long> ids);

    @Select("select * from product where voucher_id = #{id}")
    List<Product> getProductListByVoucherId(Long id);
}
