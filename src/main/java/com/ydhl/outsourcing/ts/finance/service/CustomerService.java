package com.ydhl.outsourcing.ts.finance.service;

import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.CustomerDto;
import com.ydhl.outsourcing.ts.finance.dto.query.CustomerQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.CustomSource;
import com.ydhl.outsourcing.ts.finance.enums.CustomType;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Customer;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * 客户
 * Created by dell on 2018/1/12.
 */
public interface CustomerService {

    PageInfo<CustomerDto> queryCustomerListPage(PageModel pageModel, CustomerQueryDto customerQueryDto) throws UnknownHostException, BusinessException;

    /**
     * 导出列表
     *
     * @param customerQueryDto
     * @return
     */
    PageInfo<CustomerDto> queryCustomerPage(CustomerQueryDto customerQueryDto) throws UnknownHostException, BusinessException;

    /**
     * 通过客户id 查询客户详情
     *
     * @param customerId 客户id
     * @return 客户详情
     */
    CustomerDto getCustomerInfoById(Long customerId);

    /**
     * 编辑客户资料
     *
     * @param customerDto
     */
    void updateCustomer(CustomerDto customerDto) throws UnknownHostException, BusinessException;

    /**
     * 新增客户资料
     *
     * @param customerDto 客户资料
     */
    Map<String, Object> insertCustomer(CustomerDto customerDto) throws UnknownHostException, BusinessException;

    /**
     * 客户转换
     *
     * @param customerId 客户id
     * @param userId 业务员
     */
    Map<String, Object> converCustomer(Long customerId, Long userId) throws BusinessException, UnknownHostException;

    /**
     * 批量插入客户数据
     *
     * @param customerList 批量插入客户数据
     */
    Map<String, Object> insertCustomerList(List<CustomerDto> customerList, Long userId) throws BusinessException, UnknownHostException;

    /**
     * 查询员工所属客户列表
     *
     * @param userId
     * @return
     */
    List<CustomerDto> getAllCustomreByUserId(Long userId);

    /**
     * 根据客户姓名获取客户Id列表
     */
    List<Long> getCustomIdsByName(String name);

    /**
     * 获取所有来源
     * @return
     */
    Map<Object, CustomSource> getAllSoruce();

    /**
     * 获取所有类型
     * @return
     */
    Map<Object, CustomType> getAllType();

    Customer getCustomByPhone(String phone);
}
