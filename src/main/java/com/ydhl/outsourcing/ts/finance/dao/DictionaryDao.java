package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Dictionary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Martins
 * @create 2018/1/17 18:38.
 */
public interface DictionaryDao extends BaseMapper<Long> {

    @Select("select * from dictionary")
    List<Dictionary> getAllDictionary();

    /**
     * 根据code 获取value
     * @param code
     * @return
     */
    @Select("select value from dictionary where code = #{code}")
    String getOccupationValueByCode(@Param("code") String code);
}
