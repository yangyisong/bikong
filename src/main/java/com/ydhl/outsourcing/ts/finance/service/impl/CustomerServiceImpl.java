package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.*;
import com.ydhl.outsourcing.ts.finance.custantModule.ConstantModule;
import com.ydhl.outsourcing.ts.finance.dao.*;
import com.ydhl.outsourcing.ts.finance.dto.CustomerDto;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.query.CustomerQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.CustomSource;
import com.ydhl.outsourcing.ts.finance.enums.CustomType;
import com.ydhl.outsourcing.ts.finance.example.CustomerExample;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.ApprovedApplication;
import com.ydhl.outsourcing.ts.finance.model.Contract;
import com.ydhl.outsourcing.ts.finance.model.Customer;
import com.ydhl.outsourcing.ts.finance.service.BuyService;
import com.ydhl.outsourcing.ts.finance.service.CustomerService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/5/22 下午5:49.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ApprovedApplicationDao applicationDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private OperationService operationService;

    @Override
    public PageInfo<CustomerDto> queryCustomerListPage(PageModel pageModel, CustomerQueryDto customerQueryDto) throws UnknownHostException, BusinessException {
        Long roleId = roleDao.findRoleIdListByUserId(customerQueryDto.getUserId()).get(0);
        List<Long> userIdList = new ArrayList<>();
        Page<Customer> customers = null;
        if (roleId != 2) {
            if (roleId == 1 || roleId == 6 ) {

                if(StringUtilPlus.isNotEmpty(customerQueryDto.getBusinessUser())){
                    List<Long> userIds = userService.getUserIdByName(customerQueryDto.getBusinessUser());
                    if(userIds.size()==0){
                        return new PageInfo<>(new ArrayList<>(),0);
                    }
                    customerQueryDto.setBusinessIds(userIds);
                }
            }else {
                List<Long> userIds = userService.getAllUserId();
                customerQueryDto.setBusinessIds(userIds);
            }
            Example example = CustomerExample.getCustomerExample(customerQueryDto, pageModel);
            customers = (Page<Customer>)customerDao.selectByExample(example);
        }else {
            Example example = CustomerExample.getCustomerExample(customerQueryDto, pageModel);
            customers = (Page<Customer>)customerDao.selectByExample(example);
        }
        List<CustomerDto> customerDtos = getCustomerDtos(customers);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("查看客户列表");
        operationService.insertOperation(operationDto);
        return new PageInfo<>(customerDtos,customers.getPages());
    }

    private List<CustomerDto> getCustomerDtos(List<Customer> customers) {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer: customers) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setContractCount(contractDao.getContractCountByCustomerId(customer.getId()));
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setGenderValue(customer.getGender().getDesc());
            customerDto.setIdcard(customer.getIdcard());
            customerDto.setAge(customer.getAge());
            customerDto.setBirthday(customer.getBirthday());
            if (customer.getOccupation() != null) {
                customerDto.setOccupationValue(dictionaryDao.getOccupationValueByCode(customer.getOccupation()));
            }
            customerDto.setTel(customer.getTel());
            customerDto.setPhone(customer.getPhone());
            customerDto.setWechat(customer.getWechat());
            customerDto.setEmail(customer.getEmail());
            customerDto.setQq(customer.getQq());
            customerDto.setIdcard(customer.getIdcard());
            customerDto.setSource(customer.getSource());
            if (customer.getSource()!= null) {
                customerDto.setSourceValue(customer.getSource().getDesc());
            }
            customerDto.setBuyTimes(customer.getBuyTimes());
            if (customer.getUserId() != null) {
                customerDto.setSalesmanName(userService.queryUser(customer.getUserId()).getRealname());
            }
            customerDto.setEntryUser(customer.getEntryUser());
            customerDto.setCrashName(customer.getCrashName());
            customerDto.setCrashPhone(customer.getCrashPhone());
            customerDto.setAddress(customer.getAddress());
            if (customer.getType() != null) {
                customerDto.setTypeValue(customer.getType().getDesc());
            }
            customerDto.setCreateTime(customer.getCreateTime());
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    @Override
    public PageInfo<CustomerDto> queryCustomerPage(CustomerQueryDto customerQueryDto) throws UnknownHostException, BusinessException {
        Long roleId = roleDao.findRoleIdListByUserId(customerQueryDto.getUserId()).get(0);
        List<Long> userIdList = new ArrayList<>();
        List<Customer> customers = null;
        if (roleId != 2) {
            if (roleId == 1 || roleId == 6 ) {

                if(StringUtilPlus.isNotEmpty(customerQueryDto.getBusinessUser())){
                    List<Long> userIds = userService.getUserIdByName(customerQueryDto.getBusinessUser());
                    if(userIds.size()==0){
                        return new PageInfo<>(new ArrayList<>(),0);
                    }
                    customerQueryDto.setBusinessIds(userIds);
                }
            }else {
                List<Long> userIds = userService.getAllUserId();
                customerQueryDto.setBusinessIds(userIds);
            }
            Example example = CustomerExample.getCustomerExample(customerQueryDto);
            customers = customerDao.selectByExample(example);
        }else {
            customers = customerDao.getAllCustomreByUserId(customerQueryDto.getUserId());
        }
        List<CustomerDto> customerDtos = getCustomerDtos(customers);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("导出客户列表");
        operationService.insertOperation(operationDto);
        return new PageInfo<>(customerDtos);
    }

    @Override
    public CustomerDto getCustomerInfoById(Long customerId) {
        Customer customer = customerDao.selectByPrimaryKey(customerId);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setContractCount(contractDao.getContractCountByCustomerId(customerId));
        customerDto.setName(customer.getName());
        customerDto.setGender(customer.getGender());
        customerDto.setIdcard(customer.getIdcard());
        customerDto.setPhone(customer.getPhone());
        customerDto.setAge(customer.getAge());
        customerDto.setBirthday(customer.getBirthday());
        customerDto.setOccupation(customer.getOccupation());
        customerDto.setOccupationValue(dictionaryDao.getOccupationValueByCode(customer.getOccupation()));
        customerDto.setTel(customer.getTel());
        customerDto.setWechat(customer.getWechat());
        customerDto.setEmail(customer.getEmail());
        customerDto.setQq(customer.getQq());
        customerDto.setSource(customer.getSource());
        customerDto.setType(customer.getType());
        customerDto.setSourceValue(customer.getSource().getDesc());
        customerDto.setEntryUser(customer.getEntryUser());
        customerDto.setCreateTime(customer.getCreateTime());
        customerDto.setBuyTimes(customer.getBuyTimes());
        customerDto.setCrashName(customer.getCrashName());
        customerDto.setCrashPhone(customer.getCrashPhone());
        customerDto.setAddress(customer.getAddress());
        customerDto.setType(customer.getType());
        if (customer.getType() != null) {
            customerDto.setTypeValue(customer.getType().getDesc());
        }
        if (customer.getUserId() != null) {
            customerDto.setSalesmanName(userService.queryUser(customer.getUserId()).getRealname());
        }
        return customerDto;
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) throws UnknownHostException, BusinessException {
        Customer customer = customerDao.selectByPrimaryKey(customerDto.getId());
        customer = getCustomer(customerDto);
        customer.setIdcard(customerDto.getIdcard());
        customer.setPhone(customerDto.getPhone());
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("修改客户("+ customer.getPhone() +")基本资料");
        operationService.insertOperation(operationDto);
        customerDao.updateByPrimaryKeySelective(customer);
    }

    @Override
    public Map<String, Object> insertCustomer(CustomerDto customerDto) throws UnknownHostException, BusinessException {
        Map<String, Object> map = new HashMap<>();
        Customer customer = getCustomer(customerDto);
        customer.setCreateTime(DateHelperPlus.getNow().getTime());
        customer.setUserId(customerDto.getUserId());
        List<Customer> custIdcard = contractDao.getCustomerByIdcardOrPhone(customerDto.getIdcard(), customerDto.getPhone());
        if (custIdcard.size() != 0) {
            map.put("msg", "身份证或手机号已注册");
            return map;
        }
        customer.setSource(CustomSource.ADD);
        customer.setIdcard(customerDto.getIdcard());
        customer.setPhone(customerDto.getPhone());
        customerDao.insert(customer);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("添加了新客户("+ customer.getPhone() +")");
        operationService.insertOperation(operationDto);
        map.put("msg", "success");
        return map;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Map<String, Object> converCustomer(Long customerId, Long userId) throws BusinessException, UnknownHostException {
        Map<String, Object> map= new HashMap<>();
        Customer customer = customerDao.selectByPrimaryKey(customerId);
        customer.setUserId(userId);
        customer.setSource(CustomSource.TRAN);

        //TODO 待确认修改
        Long contractId = contractDao.getContractIdByCustomerIdAndUserId(customerId, userId);
        List<ApprovedApplication> applicationList = applicationDao.getApprovedApplicationByContractIdAndUserId( userId);
        if(applicationList.size() != 0) {
            map.put("msg","目前有申请正在流程中，不允许客户转换");
            return map;
        }else {
            customerDao.updateByPrimaryKey(customer);
            map.put("msg", "success");
        }
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("转换客户("+ customer.getPhone() +")到" + userService.getUserDtoById(userId).getRealname());
        operationService.insertOperation(operationDto);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insertCustomerList(List<CustomerDto> customerList, Long userId) throws UnknownHostException, BusinessException {
        Map<String, Object> map= new HashMap<>();
        for (CustomerDto customerDto : customerList) {
            Customer customer = new Customer();
            customer.setSource(CustomSource.IMPO);
            customer.setName(customerDto.getName());
            List<Customer> cust = contractDao.getCustomerByIdcardOrPhone(customerDto.getIdcard(), customerDto.getPhone());
            if (cust.size() != 0) {
                map.put("msg", customerDto.getName()+"：身份证或手机号已存在");
                continue;
            }
            customer.setUserId(userId);
            customer.setPhone(customerDto.getPhone());
            customer.setGender(customerDto.getGender());
            customer.setIdcard(customerDto.getIdcard());
            customer.setAge(customerDto.getAge());
            customer.setBirthday(customerDto.getBirthday());
            customer.setTel(customerDto.getTel());
            customer.setWechat(customerDto.getWechat());
            customer.setEmail(customerDto.getEmail());
            customer.setQq(customerDto.getQq());
            customer.setBuyTimes(customerDto.getBuyTimes());
            customer.setCrashName(customerDto.getCrashName());
            customer.setCrashPhone(customerDto.getCrashPhone());
            customer.setAddress(customerDto.getAddress());
            customer.setType(customerDto.getType());
            customer.setCreateTime(DateHelperPlus.getNow().getTime());
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("添加新客户("+ customer.getPhone()+")" );
            operationService.insertOperation(operationDto);
            customerDao.insert(customer);
        }
        return map;
    }

    @Override
    public List<Long> getCustomIdsByName(String name) {
        return customerDao.getCustomIdsByName(name);
    }

    @Override
    public Map<Object, CustomSource> getAllSoruce() {

        return EnumConvertUtilplus.toMap(CustomSource.class, "getDesc");
    }

    @Override
    public Map<Object, CustomType> getAllType() {

        return EnumConvertUtilplus.toMap(CustomType.class, "getDesc");
    }

    @Override
    public Customer getCustomByPhone(String phone) {
        return customerDao.getCustomByPhone(phone);
    }

    @Override
    public List<CustomerDto> getAllCustomreByUserId(Long userId) {
        Page<Customer> customers = customerDao.getAllCustomreByUserId(userId);
        List<CustomerDto> dtoList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            dtoList.add(customerDto);
        }
        return dtoList;
    }

    /**
     * 客户信息私有封装
     *
     * @param customerDto
     * @return
     */
    private Customer getCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setGender(customerDto.getGender());
        customer.setType(customerDto.getType());
        customer.setAge(customerDto.getAge());
        customer.setBirthday(customerDto.getBirthday());
        customer.setOccupation(customerDto.getOccupation());
        customer.setTel(customerDto.getTel());
        customer.setWechat(customerDto.getWechat());
        customer.setEmail(customerDto.getEmail());
        customer.setQq(customerDto.getQq());
        customer.setSource(customerDto.getSource());
        customer.setEntryUser(customerDto.getEntryUser());
        customer.setBuyTimes(customerDto.getBuyTimes());
        customer.setCrashName(customerDto.getCrashName());
        customer.setCrashPhone(customerDto.getCrashPhone());
        customer.setAddress(customerDto.getAddress());
        return customer;
    }


}
