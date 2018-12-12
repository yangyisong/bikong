package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.CollectionUtilPlus;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.common.utils.StringUtilPlus;
import com.ydhl.outsourcing.ts.finance.dto.query.UserQueryDto;
import com.ydhl.outsourcing.ts.finance.model.User;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Martins
 * @create 2018/1/12 14:15.
 */
public class UserExample {

    public static Example getUserExample(UserQueryDto userQueryDto, PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (userQueryDto.getDepartmentId() != null) {
            criteria.andEqualTo("departmentId", userQueryDto.getDepartmentId());
        }
        if (StringUtilPlus.isNotEmpty(userQueryDto.getRealname())) {
            criteria.andLike("realname", "%" + userQueryDto.getRealname() + "%");
        }
        if (StringUtilPlus.isNotEmpty(userQueryDto.getPhone())) {
            criteria.andLike("phone", "%" + userQueryDto.getPhone() + "%");
        }
        if (StringUtilPlus.isNotEmpty(userQueryDto.getJobNumber())) {
            criteria.andLike("jobNumber", "%" + userQueryDto.getJobNumber() + "%");
        }
        if (CollectionUtilPlus.isNotNullOrEmpty(userQueryDto.getUserIdList())) {
            criteria.andIn("id", userQueryDto.getUserIdList());
        }
        example.orderBy("id");
        return example;
    }

    /**
     * 导出列表查询
     *
     * @param userQueryDto
     * @return
     */
    public static Example getUserExample(UserQueryDto userQueryDto) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (userQueryDto.getDepartmentId() != null) {
            criteria.andEqualTo("departmentId", userQueryDto.getDepartmentId());
        }
        if (StringUtilPlus.isNotEmpty(userQueryDto.getRealname())) {
            criteria.andLike("realname", "%" + userQueryDto.getRealname() + "%");
        }
        if (StringUtilPlus.isNotEmpty(userQueryDto.getPhone())) {
            criteria.andLike("phone", "%" + userQueryDto.getPhone() + "%");
        }
        if (StringUtilPlus.isNotEmpty(userQueryDto.getJobNumber())) {
            criteria.andLike("jobNumber", "%" + userQueryDto.getJobNumber() + "%");
        }
        if (CollectionUtilPlus.isNotNullOrEmpty(userQueryDto.getUserIdList())) {
            criteria.andIn("id", userQueryDto.getUserIdList());
        }
        example.orderBy("id");
        return example;
    }
}
