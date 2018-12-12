package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.DateHelperPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.EnumConvertUtilplus;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dao.PersonalIncomeDao;
import com.ydhl.outsourcing.ts.finance.dao.ProductDao;
import com.ydhl.outsourcing.ts.finance.dto.PersonalIncomeDto;
import com.ydhl.outsourcing.ts.finance.dto.ProductDto;
import com.ydhl.outsourcing.ts.finance.dto.query.PersonalIncomeQueryDto;
import com.ydhl.outsourcing.ts.finance.enums.ApprovalType;
import com.ydhl.outsourcing.ts.finance.enums.IncomeType;
import com.ydhl.outsourcing.ts.finance.example.PersonalIncomeExample;
import com.ydhl.outsourcing.ts.finance.model.Contract;
import com.ydhl.outsourcing.ts.finance.model.ContractQuitInfo;
import com.ydhl.outsourcing.ts.finance.model.PersonalIncome;
import com.ydhl.outsourcing.ts.finance.service.ContractQuitInfoService;
import com.ydhl.outsourcing.ts.finance.service.IncomeService;
import com.ydhl.outsourcing.ts.finance.service.ProductService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Martins
 * @create 2018/1/16 15:36.
 * @description
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private PersonalIncomeDao personalIncomeDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ContractQuitInfoService quitInfoService;

    @Override
    public Map<String,Object> getPersonalIncomePage(PersonalIncomeQueryDto personalIncomeQueryDto, PageModel pageModel) {
        Map<String,Object> map = new HashedMap<>();
        Example example = PersonalIncomeExample.getPersonalIncomeExample(personalIncomeQueryDto, pageModel);
        Page<PersonalIncome> incomePage = (Page<PersonalIncome>)personalIncomeDao.selectByExample(example);
        BigDecimal allIncomeTatal = new BigDecimal(0);
        BigDecimal sxf = new BigDecimal(0);
        List<PersonalIncomeDto> personalIncomeDtoList = new ArrayList<>();
        for (PersonalIncome personalIncome : incomePage) {
            PersonalIncomeDto personalIncomeDto = new PersonalIncomeDto();
            personalIncomeDto.setId(personalIncome.getId());
            personalIncomeDto.setIncomeNumber(personalIncome.getIncomeNumber());
            personalIncomeDto.setFreeze(personalIncome.getFreeze());
            personalIncomeDto.setContractNumber(personalIncome.getContractNumber());
            personalIncomeDto.setCustomerName(personalIncome.getCustomerName());
            personalIncomeDto.setProductName(productService.getProductDtoById(personalIncome.getProductId()).getName());
            personalIncomeDto.setUserRealname(userService.getUserDtoById(personalIncome.getUserId()).getRealname());
            if (personalIncome.getSettleStartTime() != null && personalIncome.getSettleEndTime() != null ) {
                personalIncomeDto.setSettleTime(DateFormatUtils.format(personalIncome.getSettleStartTime(), "yyyy年MM月dd日")+"-"+DateFormatUtils.format(personalIncome.getSettleEndTime(), "yyyy年MM月dd日"));
            }
            personalIncomeDto.setIncome(personalIncome.getIncome());
            personalIncomeDto.setDayNums(personalIncome.getDayNums());
            personalIncomeDto.setIncomeTypeValue(personalIncome.getIncomeType().getDesc());
            personalIncomeDto.setStrutsValue(personalIncome.getStruts().getDesc());
            personalIncomeDto.setPrincipal(personalIncome.getPrincipal());
            personalIncomeDto.setApprovedId(personalIncome.getApprovedId());
            if (personalIncome.getIncomeType()==IncomeType.R_P) {
                ContractQuitInfo quitInfo = quitInfoService.getInfoByContractId(personalIncome.getApplyId());
                if (quitInfo == null) {
                    sxf = new BigDecimal(0);
                }else {
                    sxf = quitInfo.getQuitPoundage();
                }
            }
            personalIncomeDto.setSxf(sxf);
            allIncomeTatal = allIncomeTatal.add(personalIncome.getIncome());
            personalIncomeDtoList.add(personalIncomeDto);
        }
        map.put("allIncomeTatal", allIncomeTatal);
        map.put("totalCount",incomePage.getTotal());
        map.put("pageInfo",new PageInfo<>(personalIncomeDtoList, incomePage.getPages()));
        return map;
    }

    @Override
    public PageInfo<PersonalIncomeDto> getIncomePage(PersonalIncomeQueryDto personalIncomeQueryDto) {
        Example example = PersonalIncomeExample.getIncomeExampleForExport(personalIncomeQueryDto);
        List<PersonalIncome> incomePage = personalIncomeDao.selectByExample(example);
        List<PersonalIncomeDto> personalIncomeDtoList = new ArrayList<>();
        for (PersonalIncome personalIncome : incomePage) {
            PersonalIncomeDto personalIncomeDto = new PersonalIncomeDto();
            personalIncomeDto.setId(personalIncome.getId());
            personalIncomeDto.setIncomeNumber(personalIncome.getIncomeNumber());
            personalIncomeDto.setContractNumber(personalIncome.getContractNumber());
            personalIncomeDto.setCustomerName(personalIncome.getCustomerName());
            personalIncomeDto.setProductName(personalIncome.getCustomerName());
            personalIncomeDto.setUserRealname(userService.getUserDtoById(personalIncome.getUserId()).getRealname());
            if (personalIncome.getSettleStartTime() != null && personalIncome.getSettleEndTime() != null ) {
                personalIncomeDto.setSettleTime(DateFormatUtils.format(personalIncome.getSettleStartTime(), "yyyy年MM月dd日")+"-"+DateFormatUtils.format(personalIncome.getSettleEndTime(), "yyyy年MM月dd日"));
            }
            personalIncomeDto.setIncome(personalIncome.getIncome());
            personalIncomeDto.setDayNums(personalIncome.getDayNums());
            personalIncomeDto.setIncomeTypeValue(personalIncome.getIncomeType().getDesc());
            personalIncomeDto.setStrutsValue(personalIncome.getStruts().getDesc());
            personalIncomeDto.setPrincipal(personalIncome.getPrincipal());
            personalIncomeDto.setApprovedId(personalIncome.getApprovedId());
            personalIncomeDtoList.add(personalIncomeDto);
        }
        return new PageInfo<>(personalIncomeDtoList);
    }

    @Override
    public BigDecimal getAllIncomeTotal(Long userId) {
        BigDecimal allIncomeTatal = personalIncomeDao.getPrepaidTotal(userId);
        return allIncomeTatal;
    }

    @Override
    public Map<Object, IncomeType> getAllIncomeType() {
        return EnumConvertUtilplus.toMap(IncomeType.class, "getDesc");
    }

    @Override
    public Map<Object, ApprovalType> getAllApprovalType() {
        return EnumConvertUtilplus.toMap(ApprovalType.class, "getDesc");
    }

    @Override
    public void insertIncomeDtoList(List<PersonalIncomeDto> incomeDto, Long applyId, String userName) {
        for (PersonalIncomeDto personalIncomeDto : incomeDto) {
            PersonalIncome income = new PersonalIncome();
            income.setStruts(ApprovalType.N_A);
            income.setUserRealname(userName);
            income.setCustomerName(personalIncomeDto.getCustomerName());
            income.setContractNumber(personalIncomeDto.getContractNumber());
            income.setCustomerId(personalIncomeDto.getCustomerId());
            income.setSeq(personalIncomeDto.getSeq());
            income.setIncomeNumber(personalIncomeDto.getIncomeNumber());
            income.setIncome(personalIncomeDto.getIncome());
            income.setSettleStartTime(personalIncomeDto.getSettleStartTime());
            income.setSettleEndTime(personalIncomeDto.getSettleEndTime());
            income.setUserId(personalIncomeDto.getUserId());
            income.setQuit(personalIncomeDto.getQuit());
            income.setPrincipal(personalIncomeDto.getPrincipal());
            income.setProductId(personalIncomeDto.getProductId());
            income.setDayNums(personalIncomeDto.getDayNums());
            income.setIncomeType(personalIncomeDto.getIncomeType());
            income.setStruts(personalIncomeDto.getStruts());
            income.setCreateTime(DateHelperPlus.getNow().getTime());
            income.setContractId(personalIncomeDto.getContractId());
            income.setApplyId(applyId);
            income.setFreeze(false);
            personalIncomeDao.insert(income);
        }
    }

    @Override
    public List<PersonalIncome> getQuitListByContractId(Long id) {
        return personalIncomeDao.getQuitListByContractId(id);
    }

    @Override
    public void insertIncome(List<PersonalIncome> incomeList, Contract contract, Long applyId) {
        for (PersonalIncome personalIncome : incomeList) {
            personalIncome.setStruts(ApprovalType.N_A);
            personalIncome.setFreeze(false);
            personalIncome.setContractId(contract.getId());
            personalIncome.setUserRealname(userService.getUserDtoById(contract.getUserId()).getRealname());
            personalIncome.setUserId(contract.getUserId());
            personalIncome.setContractNumber(contract.getNumber());
            personalIncome.setCustomerId(contract.getCustomerId());
            if (personalIncome.getSeq() == -1) {
                personalIncome.setPrincipal(true);
                personalIncome.setIncomeType(IncomeType.R_P);
            }else {
                personalIncome.setPrincipal(false);
                personalIncome.setIncomeType(IncomeType.N_S);
            }
            personalIncome.setApplyId(applyId);
            personalIncome.setQuit(false);
            personalIncome.setProductId(contract.getProductId());
            personalIncome.setCreateTime(DateHelperPlus.getNow().getTime());
            personalIncomeDao.insert(personalIncome);
        }
    }

    @Override
    public List<PersonalIncome> getAfterQuitDateIncomeList(Long contractId, Date quitDate) {
        return personalIncomeDao.getAfterQuitDateIncomeList(contractId, quitDate);
    }

    @Override
    public void updateIncomeStruts(PersonalIncome income) {
        personalIncomeDao.updateByPrimaryKey(income);
    }

    @Override
    public void updateIncomeStruts(Long id, ApprovalType struts) {
        personalIncomeDao.updateIncomeStruts(id,struts);
    }

    @Override
    public Map<String, Object> getPersonalIncomeListByProductId(Long productId,Long userId) {
        Map<String,Object> map = new HashedMap<>();
        BigDecimal total = new BigDecimal(0);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,1);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        List<PersonalIncome> personalIncomes = personalIncomeDao.getPersonalIncomeListByProductId(productId,userId,c.getTime());
        List<PersonalIncomeDto> personalIncomeDtoList = new ArrayList<>();
        for (PersonalIncome personalIncome : personalIncomes) {
            PersonalIncomeDto personalIncomeDto = new PersonalIncomeDto();
            personalIncomeDto.setId(personalIncome.getId());
            personalIncomeDto.setIncomeNumber(personalIncome.getIncomeNumber());
            personalIncomeDto.setContractNumber(personalIncome.getContractNumber());
            personalIncomeDto.setCustomerName(personalIncome.getCustomerName());
            ProductDto productDto = productService.getProductDtoById(personalIncome.getProductId());
            personalIncomeDto.setProductName(productDto.getName());
            personalIncomeDto.setUserRealname(userService.getUserDtoById(personalIncome.getUserId()).getRealname());
            if (personalIncome.getSettleStartTime() != null && personalIncome.getSettleEndTime() != null ) {
                personalIncomeDto.setSettleTime(DateFormatUtils.format(personalIncome.getSettleStartTime(), "yyyy年MM月dd日")+"-"+DateFormatUtils.format(personalIncome.getSettleEndTime(), "yyyy年MM月dd日"));
            }
            personalIncomeDto.setIncome(personalIncome.getIncome());
            personalIncomeDto.setDayNums(personalIncome.getDayNums());
            personalIncomeDto.setIncomeTypeValue(personalIncome.getIncomeType().getDesc());
            personalIncomeDto.setStrutsValue(personalIncome.getStruts().getDesc());
            personalIncomeDto.setPrincipal(personalIncome.getPrincipal());
            personalIncomeDto.setApprovedId(personalIncome.getApprovedId());
            personalIncomeDtoList.add(personalIncomeDto);
            total = total.add(personalIncome.getIncome());
        }
        map.put("personalIncomeDtoList",personalIncomeDtoList);
        map.put("total",total);
        return map;
    }

    @Override
    public List<Long> getIncomeProductIdList(Long userId) {
        if(userId==null){
            return personalIncomeDao.getIncomeProductIdList();
        }else{
            return personalIncomeDao.getIncomeProductIdListByUserId(userId);
        }
    }

    @Override
    public List<PersonalIncome> getIncomeListByContractId(Long contractId) {
        return personalIncomeDao.getIncomeListByContractId(contractId);
    }

    @Override
    public void updateApprovedIdbyApprovedId(Long approvedId) {
        personalIncomeDao.updateApprovedIdbyApprovedId(approvedId);
    }

    @Override
    public void updateApprovedIdByApprovedIdAndIdList(Long approvedId, List<Long> ids) {
        personalIncomeDao.updateApprovedIdByApprovedIdAndIdList(approvedId,ids);
    }

    @Override
    public Map<String,Object> getIncomeListByIdList(List<Long> ids) {
        Map<String,Object> map = new HashMap<>();
        List<PersonalIncome> personalIncomes =  personalIncomeDao.getIncomeListByIdList(ids);
        List<PersonalIncomeDto> personalIncomeDtos = new ArrayList<>();
        BigDecimal total = new BigDecimal(0);
        Integer count = personalIncomes.size();
        for(PersonalIncome personalIncome:personalIncomes){
            PersonalIncomeDto personalIncomeDto = new PersonalIncomeDto();
            personalIncomeDto.setId(personalIncome.getId());
            personalIncomeDto.setIncomeNumber(personalIncome.getIncomeNumber());
            personalIncomeDto.setContractNumber(personalIncome.getContractNumber());
            personalIncomeDto.setCustomerName(personalIncome.getCustomerName());
            ProductDto productDto = productService.getProductDtoById(personalIncome.getProductId());
            personalIncomeDto.setProductName(productDto.getName());
            personalIncomeDto.setUserRealname(userService.getUserDtoById(personalIncome.getUserId()).getRealname());
            personalIncomeDto.setIncome(personalIncome.getIncome());
            personalIncomeDto.setDayNums(personalIncome.getDayNums());
            personalIncomeDto.setIncomeTypeValue(personalIncome.getIncomeType().getDesc());
            personalIncomeDto.setStrutsValue(personalIncome.getStruts().getDesc());
            personalIncomeDto.setPrincipal(personalIncome.getPrincipal());
            personalIncomeDto.setApprovedId(personalIncome.getApprovedId());
            personalIncomeDto.setSettleStartTime(personalIncome.getSettleStartTime());
            personalIncomeDto.setSettleEndTime(personalIncome.getSettleEndTime());
            personalIncomeDtos.add(personalIncomeDto);
            total = total.add(personalIncome.getIncome());
        }
        map.put("total",total);
        map.put("count",count);
        map.put("list",personalIncomeDtos);
        return map;
    }

    @Override
    public void updateIncomeStrutsByApprovedIdAndStruts(Long approvedId,ApprovalType struts) {
        personalIncomeDao.updateIncomeStrutsByApprovedIdAndStruts(approvedId,struts);
    }

    @Override
    public List<PersonalIncome> getPersonIncomeListByContractIdAndApprovedId(Long id) {
        return personalIncomeDao.getPersonIncomeListByContractIdAndApprovedId(id);
    }

    @Override
    public List<PersonalIncome> getIncomeListByApplyIdAndQuitDate(Long contractId, Date quitDate) {
        return personalIncomeDao.getIncomeListByApplyIdAndQuitDate(contractId, quitDate);
    }

    @Override
    public List<PersonalIncome> getAfterQuitDateNAIncomeList(Long contractId, Date quitDate) {
        return personalIncomeDao.getAfterQuitDateNAIncomeList(contractId, quitDate);
    }

    @Override
    public List<PersonalIncome> getIncomeListByFreezeByContractId(Long contractId) {
        return personalIncomeDao.getIncomeListByFreezeByContractId(contractId);
    }


    @Override
    public void updateIncomeList(List<PersonalIncome> incomes, Boolean boo) {
        for (PersonalIncome personalIncome : incomes) {
            personalIncome.setFreeze(boo);
            personalIncomeDao.updateByPrimaryKey(personalIncome);
        }
    }

    @Override
    public Long getApplyIdByApprovedId(Long approvedId) {
        return personalIncomeDao.getApplyIdByApprovedId(approvedId);
    }

    @Override
    public Map<String, Object> getPersonalIncomePageByProductId(Long productId, Long userId, PageModel pageModel) {
        Map<String,Object> map = new HashedMap<>();
        BigDecimal total = new BigDecimal(0);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,1);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);

        PersonalIncomeQueryDto personalIncomeQueryDto = new PersonalIncomeQueryDto();
        personalIncomeQueryDto.setProductId(productId);
        personalIncomeQueryDto.setUserId(userId);
        //personalIncomeQueryDto.setSettleEndTime(c.getTime());
        personalIncomeQueryDto.setFreeze(false);
        Example example = PersonalIncomeExample.getPersonalIncomeExample(personalIncomeQueryDto, pageModel);
        Page<PersonalIncome> incomePage = (Page<PersonalIncome>)personalIncomeDao.selectByExample(example);

        List<PersonalIncomeDto> personalIncomeDtoList = new ArrayList<>();
        for (PersonalIncome personalIncome : incomePage) {
            PersonalIncomeDto personalIncomeDto = new PersonalIncomeDto();
            personalIncomeDto.setId(personalIncome.getId());
            personalIncomeDto.setIncomeNumber(personalIncome.getIncomeNumber());
            personalIncomeDto.setContractNumber(personalIncome.getContractNumber());
            personalIncomeDto.setCustomerName(personalIncome.getCustomerName());
            ProductDto productDto = productService.getProductDtoById(personalIncome.getProductId());
            personalIncomeDto.setProductName(productDto.getName());
            personalIncomeDto.setUserRealname(userService.getUserDtoById(personalIncome.getUserId()).getRealname());
            if (personalIncome.getSettleStartTime() != null && personalIncome.getSettleEndTime() != null ) {
                personalIncomeDto.setSettleTime(DateFormatUtils.format(personalIncome.getSettleStartTime(), "yyyy年MM月dd日")+"-"+DateFormatUtils.format(personalIncome.getSettleEndTime(), "yyyy年MM月dd日"));
            }
            personalIncomeDto.setIncome(personalIncome.getIncome());
            personalIncomeDto.setDayNums(personalIncome.getDayNums());
            personalIncomeDto.setIncomeTypeValue(personalIncome.getIncomeType().getDesc());
            personalIncomeDto.setStrutsValue(personalIncome.getStruts().getDesc());
            personalIncomeDto.setPrincipal(personalIncome.getPrincipal());
            personalIncomeDto.setApprovedId(personalIncome.getApprovedId());
            personalIncomeDtoList.add(personalIncomeDto);
            total = total.add(personalIncome.getIncome());
        }
        map.put("pageInfo",new PageInfo<>(personalIncomeDtoList, incomePage.getPages()));
        map.put("total",total);
        return map;
    }
}
