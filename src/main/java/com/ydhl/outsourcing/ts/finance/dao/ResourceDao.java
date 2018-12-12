package com.ydhl.outsourcing.ts.finance.dao;

import com.ydhl.outsourcing.ts.finance.base.BaseMapper;
import com.ydhl.outsourcing.ts.finance.model.Resource;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-12-19 上午 10:14
 * @description
 */
public interface ResourceDao extends BaseMapper<Resource> {

    /**
     * 查询所有需要权限的资源
     *
     * @return 资源列表
     */
    @Select("select * from resource where auth_type = 'A' ")
    List<Resource> findNeedAuthResources();
}
