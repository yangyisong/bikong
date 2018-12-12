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
package com.ydhl.outsourcing.ts.finance.base;

/**
 * <p>类简述：基础用户实体</p>
 * <p>
 * <p>描述：系统中所所有用户的抽象，拥有必须的属性以供spring security使用，其他用户需要继承该类来扩展更多的自定义属性</p>
 * <p>
 * <p>补充：
 * 其他用户扩展规则是使用@Entity和@DiscriminatorValue注解即可，其中DiscriminatorValue的值应避免重复，例如：</p>
 * <pre>
 * &#064;Entity
 * &#064;DiscriminatorValue(value = "B")
 * public class BackendUser extends BaseUser{
 *    private String nickname;
 *    ...
 *    ...
 * }
 * </pre>
 *
 * @author Jupeng.Su
 */
public class BaseUser extends BaseDomain<Long> {

    private static final long serialVersionUID = 8391175457158683468L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private Boolean accountNonLocked;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean credentialsNonExpired;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}
