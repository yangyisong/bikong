package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.ApprovedRecording;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by dell on 2018/1/28.
 */
public interface ApprovedRecordingDao extends BaseMapper<ApprovedRecording> {
    @Select("select  distinct max(num) from approved_recording where approved_id=#{approvedId} and effective=false order by create_time desc")
    public Integer getNumByApprovedId(Long approvedId);

    @Select("select distinct approved_id from approved_recording where user_id=#{userId} and effective=true")
    public List<Long> getEffectiveApprovedIdListByUserId(Long userId);

    @Select("select * from approved_recording where approved_id=#{approvedId} and effective=#{effective} order by create_time asc")
    public List<ApprovedRecording> getApprovedRecordingDtoListByApprovedIdAndEffective(@Param("approvedId") Long approvedId,@Param("effective")  Boolean effective);

    @Update("update approved_recording set effective = false where approved_id=#{approvedId} ")
    void updateApprovedRecordEffectiveByApprovedId(Long approvedId);

    @Delete("delete from approved_recording where approved_id=#{approvedId} ")
    void deleteApprovedRecordingByApprovedId(Long approvedId);
}
