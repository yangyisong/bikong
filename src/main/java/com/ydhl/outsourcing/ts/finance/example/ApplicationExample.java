package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.*;
import com.ydhl.outsourcing.ts.finance.dto.query.ApprovedApplicationQueryDto;
import com.ydhl.outsourcing.ts.finance.model.ApprovedApplication;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Martins
 * @create 2018/1/16 20:48.
 * @description
 */
public class ApplicationExample {

    public static Example getApplicationExample(ApprovedApplicationQueryDto approvedApplicationQueryDto, PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        Example example = new Example(ApprovedApplication.class);
        Example.Criteria criteria = example.createCriteria();
        /*if (approvedApplicationQueryDto.getStartTime() != null) {
            criteria.andGreaterThanOrEqualTo("applyTime", DateUtilPlus.setDayStart(approvedApplicationQueryDto.getStartTime()));
        }
        if (approvedApplicationQueryDto.getEndTime() != null ) {
            criteria.andLessThanOrEqualTo("applyTime", DateUtilPlus.setDayEnd(approvedApplicationQueryDto.getEndTime()));
        }
        if (approvedApplicationQueryDto.getStruts() != null) {
            criteria.andEqualTo("struts", approvedApplicationQueryDto.getStruts());
        }*/
        if (approvedApplicationQueryDto.getApprovedStruts() != null) {
            criteria.orEqualTo("approvedStruts", approvedApplicationQueryDto.getApprovedStruts());
        }
        /*if (StringUtilPlus.isNotEmpty(approvedApplicationQueryDto.getSettleNumber())) {
            criteria.andLike("settleNumber", "%" + approvedApplicationQueryDto.getSettleNumber() + "%");
        }*/
        if (CollectionUtilPlus.isNotNullOrEmpty(approvedApplicationQueryDto.getUserIds()) && approvedApplicationQueryDto.getFlag()==0) {
            criteria.andIn("userId", approvedApplicationQueryDto.getUserIds());
        }
        if (CollectionUtilPlus.isNotNullOrEmpty(approvedApplicationQueryDto.getUserIds()) && approvedApplicationQueryDto.getFlag()==1) {
            criteria.orIn("userId", approvedApplicationQueryDto.getUserIds());
        }
        if (CollectionUtilPlus.isNotNullOrEmpty(approvedApplicationQueryDto.getApprovedIds())) {
            criteria.orIn("id", approvedApplicationQueryDto.getApprovedIds());
        }
        if (approvedApplicationQueryDto.getSupportManagerAudit() != null) {
            criteria.andEqualTo("supportManagerAudit", approvedApplicationQueryDto.getSupportManagerAudit());
        }
        example.orderBy("struts").asc();
        example.orderBy("applyTime").desc();
        return example;
    }
}
