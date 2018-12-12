package com.ydhl.outsourcing.ts.finance.dao;

import com.github.pagehelper.Page;
import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户Dao
 * Created by dell on 2018/1/12.
 */
public interface CustomerDao  extends BaseMapper<Customer> {

    @Select("select * from customer where user_id = #{userId}")
    Page<Customer> getAllCustomreByUserId(@Param("userId") Long userId);

    /**
     * 查询员工所属客户数量
     *
     * @param userId
     * @return
     */
    @Select("select count(*) from customer where user_id = #{userId}")
    Integer selectTotalCustomByUserId(@Param("userId") Long userId);

    @Select("select id from customer where name like CONCAT('%', #{name}, '%')")
    List<Long> getCustomIdsByName(@Param("name") String name);

    @Select("select * from customer where phone = #{phone}")
    Customer getCustomByPhone(String phone);
}
