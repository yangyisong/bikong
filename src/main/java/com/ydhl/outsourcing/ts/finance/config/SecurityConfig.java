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
package com.ydhl.outsourcing.ts.finance.config;


import com.ydhl.outsourcing.ts.finance.common.ConfigURIResource;
import com.ydhl.outsourcing.ts.finance.controller.BackendExceptionController;
import com.ydhl.outsourcing.ts.finance.service.ResourceService;
import com.ydhl.outsourcing.ts.finance.service.UserLoginService;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>类简述：spring-security 配置</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：需要放在root config里面启动</p>
 *
 * @author wiiyaya
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private ResourceService resourceService;

    @Resource
    private PasswordEncoder passwordEncoder;

    //====以下需要成对出现，否则出错，具体原因不明，authenticationManager 可以在Oauth2里面使用，暂时无用=====
//	@Resource
//    protected AuthenticationManager authenticationManager;
//	
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
    //====以上需要成对出现，否则出错=====

    /**
     * 配置web
     *
     * @param web WebSecurity
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/datas/**", "/images/**", "/jquery/**", "/js/**", "/plugins/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userLoginService);
//        provider.setUserCache(userCache());
        provider.setPasswordEncoder(passwordEncoder);

        auth.authenticationProvider(provider);
    }

    /**
     * 安全配置
     *
     * @param http HttpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configHeaders(http);

        configCsrf(http);

        configBackendFormLogin(http);

        configResourceAuthority(http);

//        configSessionManager(http);
    }


    /**
     * 资源权限配置
     *
     * @param http HttpSecurity
     * @throws Exception
     */
    private void configResourceAuthority(HttpSecurity http) throws Exception {
        String[] noNeedAuths = resourceService.getNoNeedAuthResources();
        http.authorizeRequests().antMatchers(noNeedAuths).permitAll();
//        http.antMatcher("/user/**");

        Map<String, String[]> needAuths = resourceService.getNeedAuthResources();
        for (Map.Entry<String, String[]> entity : needAuths.entrySet()) {
            http.authorizeRequests().antMatchers(entity.getKey()).hasAnyAuthority(entity.getValue());
        }
        http.authorizeRequests().anyRequest().authenticated();
    }

    /**
     * Csrf攻击配置
     *
     * @param http HttpSecurity
     * @throws Exception
     */
    private void configCsrf(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    private void configHeaders(HttpSecurity http) throws Exception {
        http.headers()
                .httpStrictTransportSecurity()
                .requestMatcher(AnyRequestMatcher.INSTANCE)
                .maxAgeInSeconds(31536000)
                .includeSubDomains(false)
                .and()
                .frameOptions()
                .sameOrigin();
    }

    /**
     * 登陆/登出配置
     *
     * @param http HttpSecurity
     * @throws Exception
     */
    private void configBackendFormLogin(HttpSecurity http) throws Exception {
/*
        http.authorizeRequests()
                .and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/index.html", true)
                .and().logout().logoutUrl("/logout")
                .and().sessionManagement().maximumSessions(1).expiredUrl("/expired")
                .and()
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
*/
        UserCacheLogoutHandler logoutHandler = new UserCacheLogoutHandler();
        logoutHandler.setUserCache(userCache());
/*
        DefaultAuthenticationSuccessHandler successHandler = new DefaultAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl(ConfigURIResource.PATH_LOGIN_SUCCESS);
        successHandler.setAlwaysUseDefaultTargetUrl(true);*/
//        successHandler.setUserLoginHisService(userLoginHisService);
/*        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/index.html").failureUrl("/login?error").permitAll()
                .and()
                .logout()
                //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("/login")
                .permitAll();*/

        http.formLogin()
                .loginPage(ConfigURIResource.PATH_LOGIN)
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/index.html")
//                .successHandler(successHandler)
				.failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl(ConfigURIResource.PATH_LOGOUT)
                .addLogoutHandler(logoutHandler)
//				.logoutSuccessUrl(FwResource.PATH_LOGOUT_SUCCESS)
                .permitAll();
    }

    /**
     * session配置
     *
     * @param http HttpSecurity
     * @throws Exception
     */
    private void configSessionManager(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl(BackendExceptionController.PATH_ERROR_MAX_SESSION)//多出现后登陆，超过指定值后，先登陆的跳转到URL
                .and()
                .invalidSessionUrl(BackendExceptionController.PATH_TIME_OUT);//session失效后，跳转的URL
    }

    /**
     * 用户缓存配置
     *
     * @return UserCache
     * @throws Exception
     */
    @Bean
    public UserCache userCache() throws Exception {
        SpringCacheBasedUserCache userCache = new SpringCacheBasedUserCache(new ConcurrentMapCache("userCache"));
        return userCache;
    }

    /**
     * spring代理的事件监听
     *
     * @return
     * @throws Exception
     */
/*
    @Bean
    public SpringEventListener springEventListener() throws Exception {
        return new SpringEventListener();
    }
*/

    private class UserCacheLogoutHandler implements LogoutHandler {

        private UserCache userCache;

        public void setUserCache(UserCache userCache) {
            this.userCache = userCache;
        }

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            userCache.removeUserFromCache(((UserDetails) authentication.getPrincipal()).getUsername());
        }
    }

//    private class DefaultAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

/*
        private UserLoginHisService userLoginHisService;

        public void setUserLoginHisService(UserLoginHisService userLoginHisService) {
            this.userLoginHisService = userLoginHisService;
        }
*//*

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
            LoginSecurityDto currentUser = (LoginSecurityDto) authentication.getPrincipal();

            String sessionId = request.getSession(false).getId();//获取真实的sessionId
            String ipAddress = NetworkUtils.getIpAddress(request);//获取真实的ip地址

//            userLoginHisService.recordLogin(currentUser.getUsername(), sessionId, ipAddress);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }*/
}
