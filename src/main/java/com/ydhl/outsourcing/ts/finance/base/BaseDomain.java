package com.ydhl.outsourcing.ts.finance.base;

/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import com.ydhl.outsourcing.ts.finance.base.annotation.CreatedDate;
import com.ydhl.outsourcing.ts.finance.base.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>类简述：系统中所有实体的父类</p>
 * <p>
 * <p>描述：该类定义了主键，版本和审计需要的必须字段</p>
 * <p>
 * <p>补充：</p>
 *
 * @param <PK> 当前实体的主键类型
 * @author Jupeng.Su
 */
@MappedSuperclass
public class BaseDomain<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = -5554308939380869754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @CreatedDate
    public Date createTime;

    @LastModifiedDate
    public Date updateTime;


    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
