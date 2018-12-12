package com.ydhl.outsourcing.ts.finance.service;

import java.util.Map;

/**
 * @author Junpeng.Su
 * @create 2017-12-18 下午 3:36
 * @description
 */
public interface ResourceService {

    /**
     * 获取不需要授权的资源数据(系统使用)
     *
     * @return 资源数组
     */
    String[] getNoNeedAuthResources();

    /**
     * 获取需要授权的资源数据(系统使用)
     *
     * @return 资源-权限关系映射
     */
    Map<String, String[]> getNeedAuthResources();

}
