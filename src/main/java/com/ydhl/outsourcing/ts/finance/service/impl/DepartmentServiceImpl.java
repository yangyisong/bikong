package com.ydhl.outsourcing.ts.finance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ydhl.outsourcing.ts.finance.common.utils.BeanUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.OperaionUtil;
import com.ydhl.outsourcing.ts.finance.custantModule.ConstantModule;
import com.ydhl.outsourcing.ts.finance.dao.DepartmentDao;
import com.ydhl.outsourcing.ts.finance.dao.UserDao;
import com.ydhl.outsourcing.ts.finance.dto.DepartmentDto;
import com.ydhl.outsourcing.ts.finance.dto.OperationDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import com.ydhl.outsourcing.ts.finance.model.Department;
import com.ydhl.outsourcing.ts.finance.model.User;
import com.ydhl.outsourcing.ts.finance.service.DepartmentService;
import com.ydhl.outsourcing.ts.finance.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Martins
 * @create 2018/1/2 11:51.
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OperationService operationService;

    @Override
    public PageInfo<DepartmentDto> queryDepartmentPage(Integer pageNo, Integer pageSize, String name) {
        pageNo = 0 ;
        PageHelper.startPage(pageNo, pageSize);
        List<DepartmentDto> departmentDtoList = null ;
        if(name != null && !Objects.equals(name, "")){
            departmentDtoList = departmentDao.queryDepartmentPage(name);
        }else{
            departmentDtoList = BeanUtilPlus.copyAs(departmentDao.selectAll(), DepartmentDto.class);
        }
        return new PageInfo<>(departmentDtoList);
    }

    @Override
    public DepartmentDto getDepartmentByDepartmentId(Long departmentId) {
        Department department = departmentDao.selectByPrimaryKey(departmentId);
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setEmployeeNum(department.getEmployeeNum());
        departmentDto.setParentId(department.getParentId());
        departmentDto.setHead(department.getHead());
        departmentDto.setHeadId(department.getHeadId());
        return departmentDto;
    }

    @Override
    public List<DepartmentDto> getAllDepartmentDtoList() {
        List<Department> departments = departmentDao.selectAll();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        for (Department department : departments) {
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setId(department.getId());
            departmentDto.setName(department.getName());
            departmentDto.setParentId(department.getParentId());
            departmentDto.setHead(department.getHead());
            departmentDtoList.add(departmentDto);
        }
        return departmentDtoList;
    }

    @Override
    public List<DepartmentDto> getDepartmentsList(List<Department> departList) {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        if(departList!=null && departList.size()!=0){
            for(int i=0;i<departList.size();i++){
                Department department =departList.get(i);
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setId(department.getId());
                departmentDto.setName(department.getName());
                departmentDto.setHead(department.getHead());
                departmentDto.setParentId(department.getParentId());
                departmentDto.setStruts(department.getStruts());
                department.setHeadId(department.getHeadId());
                //departmentDto.setDepartmentDtos(getDepartmentsList(getDepartmentListByParentId(departList.get(i).getId())));
                departmentDto.setChildren(getDepartmentsList(getDepartmentListByParentId(departList.get(i).getId())));
                departmentDtos.add(departmentDto);
            }
        }
        return departmentDtos;
    }

    @Override
    public List<Long> getDepartmentIdList(List<Department> departList) {
        List<Long> departmentIds = new ArrayList<>();
        if(departList!=null && departList.size()!=0){
            for(int i=0;i<departList.size();i++){
                List<Long> ids = getDepartmentIdList(getDepartmentListByParentId(departList.get(i).getId()));
                for(int j=0;j<ids.size();j++){
                    departmentIds.add(ids.get(j));
                }
                departmentIds.add(departList.get(i).getId());
            }
        }
        return departmentIds;
    }

    @Override
    public List<Department> getDepartmentListByParentId(Long parentId) {
        List<Department> departments = departmentDao.getDepartmentByParentId(parentId);
        return departments;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void insertDepartment(String name, Long parentId) throws BusinessException {
        Department oldDepartment = departmentDao.getDepartmentByNameAndParentId(name, parentId);
        if (oldDepartment != null) {
            throw new BusinessException(ConstantModule.ERROR_DEPARTMENT_HAS_EXITS.getCode(), ConstantModule.ERROR_DEPARTMENT_HAS_EXITS.getMessage());
        }
        Department department = new Department();
        department.setName(name);
        department.setParentId(parentId);
        department.setHead(false);
        departmentDao.insert(department);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateDepartment(Long id, String name, Long parentId) throws BusinessException {
        Department department = departmentDao.selectByPrimaryKey(id);
        if (department.getName().equals(name) && department.getParentId().equals(parentId)) {
            throw new BusinessException(ConstantModule.ERROR_DEPARTMENT_HAS_EXITS.getCode(), ConstantModule.ERROR_DEPARTMENT_HAS_EXITS.getMessage());
        }
        department.setName(name);
        department.setParentId(parentId);
        departmentDao.updateByPrimaryKey(department);

    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void deleteDepartmentById(Long departmentId) throws BusinessException {
        Department department = departmentDao.selectByPrimaryKey(departmentId);
        if (department.getHead()) {
            throw new BusinessException(ConstantModule.ERROR_HEAD_NOT_ALLOW_DELETE.getCode(), ConstantModule.ERROR_HEAD_NOT_ALLOW_DELETE.getMessage());
        }
        List<User> userList = userDao.getUserByDepartmentId(departmentId);
        if (userList.size() != 0) {
            throw new BusinessException(ConstantModule.ERROR_DEPARTMENT_HAS_USER_NOT_ALLOW_DELETE.getCode(), ConstantModule.ERROR_DEPARTMENT_HAS_USER_NOT_ALLOW_DELETE.getMessage());
        }
        departmentDao.deleteByPrimaryKey(departmentId);
    }

    @Override
    @Transactional
    public Map<String,String> addDepartment(DepartmentDto departmentDto) {
        Map<String,String> map = new HashMap<>();
        try {
            //判断同级部门是否存在
            Department dept = departmentDao.getDepartmentByNameAndParentId(departmentDto.getName(),departmentDto.getParentId());
            if(dept!=null){
                map.put("msg","部门已经存在");
                return map;
            }
            Department department = new Department();
            department.setName(departmentDto.getName());
            department.setHead(false);
            department.setStruts(true);
            department.setParentId(departmentDto.getParentId());
            department.setEmployeeNum(0);
            departmentDao.insert(department);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("新增部门");
            operationService.insertOperation(operationDto);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","新增部门失败");
        }
        return map;
    }

    @Override
    public Map<String,String> editDepartment(Long id, String name) {
        Map<String,String> map = new HashMap<>();
        try {
            //判断同级部门是否存在
            Long dId = departmentDao.getDepartmentByNameAndId(id,name);
            if(dId>0){
                map.put("msg","部门已经存在");
                return map;
            }
            departmentDao.editDepartment(id,name);
            OperationDto operationDto = OperaionUtil.getOperationDto();
            operationDto.setContent("编辑部门信息");
            operationService.insertOperation(operationDto);
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","编辑部门失败");
        }
        return map;
    }

    @Override
    public void deleteDepartmentsByIds(List<Long> ids) {
        departmentDao.deleteDepartmentsByIds(ids);
    }
}
