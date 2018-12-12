package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.base.CurrentUserUtils;
import com.ydhl.outsourcing.ts.finance.common.utils.BeanUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.DateHelperPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.DateUtilHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.dao.VoucherDao;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.dto.VoucherDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Voucher;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import com.ydhl.outsourcing.ts.finance.service.ProductService;
import com.ydhl.outsourcing.ts.finance.service.UserService;
import com.ydhl.outsourcing.ts.finance.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */
@Service
public class VoucherServiceImpl implements VoucherService{
    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private OperationService operationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public List<VoucherDto> getVoucherList() throws BusinessException, UnknownHostException {
        List<Voucher> vouchers = voucherDao.selectAll();
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for (Voucher voucher : vouchers) {
            VoucherDto voucherDto = new VoucherDto();
            voucherDto.setId(voucher.getId());
            voucherDto.setContent(voucher.getContent());
            voucherDto.setName(voucher.getName());
            voucherDto.setNumber(voucher.getNumber());
            voucherDto.setUnited(voucher.getUnited());
            voucherDto.setProductNameList(productService.getProductListByVoucherId(voucher.getId()));
            voucherDtoList.add(voucherDto);
        }
        return voucherDtoList;
    }

    @Override
    public void editUnited(Long id, Integer united) throws UnknownHostException, BusinessException {
        voucherDao.editUnited(id,united);
        OperationDto operationDto = OperaionUtil.getOperationDto();
        operationDto.setContent("修改凭证("+id+")联数为:"+ united);
        operationService.insertOperation(operationDto);
    }

    @Override
    public VoucherDto getVoucherById(Long id) {
        if(id!=null){
            Voucher voucher = new Voucher();
            voucher.setId(id);
            voucher = voucherDao.selectOne(voucher);
            VoucherDto voucherDto = new VoucherDto();
            BeanUtilPlus.copy(voucher,voucherDto);
            return voucherDto;
        }
        return null;
    }
}
