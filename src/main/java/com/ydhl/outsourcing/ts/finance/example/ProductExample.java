package com.ydhl.outsourcing.ts.finance.example;

import com.github.pagehelper.PageHelper;
import com.ydhl.outsourcing.ts.finance.common.utils.PageModel;
import com.ydhl.outsourcing.ts.finance.dto.query.ProductQueryDto;
import com.ydhl.outsourcing.ts.finance.model.Product;
import tk.mybatis.mapper.entity.Example;


/**
 * 产品查询对象
 * Created by dell on 2018/1/12.
 */
public class ProductExample {

    public static Example getProductExample(ProductQueryDto productQueryDto, PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        if (productQueryDto.getStruts()!=null) {
            criteria.andEqualTo("struts", productQueryDto.getStruts());
        }
        return example;
    }
}
