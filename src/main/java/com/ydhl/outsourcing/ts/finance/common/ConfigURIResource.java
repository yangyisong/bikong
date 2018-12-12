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
package com.ydhl.outsourcing.ts.finance.common;


public class ConfigURIResource {
    /**
     * 静态资源路径
     */
    public static final String PATH_STATIC_RESOURCE_REGX = "/css/**";

    /**
     * 错误页面路径
     */
    public static final String PATH_ERROR_REGX = "/**";

    /**
     * 跳转到登录页面
     */
    public static final String PATH_LOGIN = "/login";

    /**
     * 登录成功后跳转到的页面
     */
    public static final String PATH_LOGIN_SUCCESS = "/index";

    /**
     * 登出
     */
    public static final String PATH_LOGOUT = "/login/loginOut.do";

    /**
     * 登出
     */
    public static final String PATH_LOGOUT_SUCCESS = "/logout/success";

    /**
     * 修改密码
     */
    public static final String PATH_CHANGE_MY_PASSWORD = "/changeMyPassword";

    /**
     * 登出页面
     */
    public static final String PAGE_LOGIN = "login";

    /**
     * 首页
     */
    public static final String PAGE_INDEX = "index";

    /**
     * 修改密码页面
     */
    public static final String PAGE_CHANGE_MY_PASSWORD = "changeMyPassword";

    /**
     * 登出成功页面
     */
    public static final String PAGE_LOGOUT_SUCCESS = "logout_success";

    /**
     * 菜单页面
     */
    public static final String PAGE_MENU = "menu";

    /**
     * 主菜单
     */
    public static final String PATH_MAIN_MENU = "/mmenu";

    /**
     * 子菜单
     */
    public static final String PATH_CHILD_MENU = "/{menuId}/cmenu";


}
