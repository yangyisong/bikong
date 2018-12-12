package com.ydhl.outsourcing.ts.finance.service;

import com.ydhl.outsourcing.ts.finance.dto.VoucherDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */
public interface VoucherService {
    /**
     * 获取凭证列表
     */
    List<VoucherDto> getVoucherList() throws BusinessException, UnknownHostException;

    /**
     * 修改凭证联数
     */
    void editUnited(Long id,Integer united) throws UnknownHostException, BusinessException;

    /**
     * 通过凭证Id获取凭证
     */
    VoucherDto getVoucherById(Long id);
}
