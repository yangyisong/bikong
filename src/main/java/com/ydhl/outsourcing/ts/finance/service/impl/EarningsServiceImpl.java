package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.EarningsDao;
import com.ydhl.outsourcing.ts.finance.dto.EarningsDto;
import com.ydhl.outsourcing.ts.finance.model.Earnings;
import com.ydhl.outsourcing.ts.finance.service.EarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/16.
 */
@Service
public class EarningsServiceImpl implements EarningsService {
    @Autowired
    private EarningsDao earningsDao;

    @Override
    public List<EarningsDto> getEarningsList(Long productId) {
        List<Earnings> earningses = earningsDao.getEarningsList(productId);
        List<EarningsDto> earningsDtos = new ArrayList<>();
        for (Earnings earnings:earningses) {
            EarningsDto earningsDto = new EarningsDto();
            earningsDto.setId(earnings.getId());
            earningsDto.setProductId(earnings.getProductId());
            earningsDto.setMinRange(earnings.getMinRange());
            earningsDto.setMaxRange(earnings.getMaxRange());
            earningsDto.setEarningsType(earnings.getEarningsType());
            earningsDto.setOutEarningsRatio(earnings.getOutEarningsRatio());
            earningsDtos.add(earningsDto);
        }
        return earningsDtos;
    }

    @Override
    @Transactional
    public void insertEarningsList(List<EarningsDto> earningsDtos,Long productId) {
        for (EarningsDto earningsDto:earningsDtos) {
            Earnings earnings = earningsDtoToEarnings(earningsDto,productId);
            earningsDao.insert(earnings);
        }
    }

    @Override
    @Transactional
    public void updateEarningsList(List<EarningsDto> earningsDtos,Long productId) {
        earningsDao.deleteEarningsByProductId(productId);
        for (EarningsDto earningsDto: earningsDtos) {
            Earnings earnings = earningsDtoToEarnings(earningsDto,productId);
            earningsDao.insert(earnings);
        }
    }

    @Override
    public BigDecimal getRateByProductIdAndAmount(Long productId, BigDecimal amount) {
        return earningsDao.getRateByProductIdAndAmount(productId,amount);
    }

    public Earnings earningsDtoToEarnings(EarningsDto earningsDto, Long productId){
        Earnings earnings = new Earnings();
        earnings.setId(earningsDto.getId());
        earnings.setProductId(productId);
        earnings.setMinRange(earningsDto.getMinRange());
        earnings.setMaxRange(earningsDto.getMaxRange());
        earnings.setEarningsType(earningsDto.getEarningsType());
        earnings.setOutEarningsRatio(earningsDto.getOutEarningsRatio());
        return earnings;
    }
}
