package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ApprovedApply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by dell on 2018/2/5.
 */
public interface ApprovedApplyDao extends BaseMapper<ApprovedApply> {
    /**
     * 通过申请Id修改申请状态
     */
    @Update("update approved_apply set struts=#{struts},reason=#{reason} where id = #{id}")
    public void updateApplyStruts(@Param("id") Long id,@Param("struts") Boolean struts,@Param("reason") String reason);
}
