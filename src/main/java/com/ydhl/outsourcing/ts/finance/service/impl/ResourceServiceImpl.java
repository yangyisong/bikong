package com.ydhl.outsourcing.ts.finance.service.impl;

import com.ydhl.outsourcing.ts.finance.dao.PrivilegeDao;
import com.ydhl.outsourcing.ts.finance.dao.ResourceDao;
import com.ydhl.outsourcing.ts.finance.model.Resource;
import com.ydhl.outsourcing.ts.finance.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Junpeng.Su
 * @create 2017-12-18 下午 3:36
 * @description
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private PrivilegeDao privilegeDao;


    @Override
    public String[] getNoNeedAuthResources() {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getNeedAuthResources() {
        List<Resource> needAuthResources = resourceDao.findNeedAuthResources();
        List<Long> existIdList = new ArrayList<>();
        Map<Long, List<Long>> parentPrivilegeIdMap = new HashMap<>();
        Map<String, String[]> resourcePrivilegeMap = new HashMap<>();
        for (Resource resource : needAuthResources) {
            List<Long> privilegeIdDbs = privilegeDao.findPrivilegeIdListByResourceId(resource.getId());//查找资源对应的权限id
            if (CollectionUtils.isEmpty(privilegeIdDbs)) {
                LOGGER.warn("Resource [" + resource.getId() + "] no privilege has been found!");
                continue;
            }
/*            if(resource.getParentId() != 0){
                if(!existIdList.contains(resource.getParentId())){
                    List<Long> parentPrivilegeIdList = privilegeDao.findPrivilegeIdListByResourceId(resource.getParentId());//查找资源对应的权限id
                    privilegeIdDbs.addAll(parentPrivilegeIdList);
                    parentPrivilegeIdMap.put(resource.getParentId(), parentPrivilegeIdList);
                }else{
                    privilegeIdDbs.addAll(parentPrivilegeIdMap.get(resource.getParentId()));
                }
            }*/
            List<String> privilegeCodes = privilegeDao.findCodeListByIdList(privilegeIdDbs);//查找权限代码
            resourcePrivilegeMap.put(resource.getUrl(), privilegeCodes.toArray(new String[privilegeCodes.size()]));
        }
        return resourcePrivilegeMap;
    }
}
