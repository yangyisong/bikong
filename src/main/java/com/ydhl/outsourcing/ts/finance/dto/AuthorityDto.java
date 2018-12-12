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
package com.ydhl.outsourcing.ts.finance.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * <p>类简述：简单的授权对象，和spring自带唯一不同的是有一个默认的构造函数，以方便分布式使用</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public class AuthorityDto implements GrantedAuthority {

    private static final long serialVersionUID = -5488853194362504456L;

    private String privilege;

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public String getAuthority() {
        return privilege;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof AuthorityDto) {
            return privilege.equals(((AuthorityDto) obj).privilege);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.privilege.hashCode();
    }

    @Override
    public String toString() {
        return this.privilege;
    }

}
