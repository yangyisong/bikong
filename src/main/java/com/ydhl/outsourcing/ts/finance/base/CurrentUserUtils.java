package com.ydhl.outsourcing.ts.finance.base;

import com.ydhl.outsourcing.ts.finance.dto.LoginSecurityDto;
import com.ydhl.outsourcing.ts.finance.exception.BusinessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * <p>类简述：获取当前用户帮助类</p>
 * <p>
 * <p>描述：用来获取当前登陆的用户，如果是消费端调用，则可以返回完整的信息，如果是服务端调用，则只能返回当前登陆用户的id</p>
 * <p>
 * <p>补充：如果不加入dubbo框架，则在任何地方，都可以访问到完整信息</p>
 *
 * @author wiiyaya
 */
public class CurrentUserUtils {

    /**
     * 获取当前登录的用户名，如果未登录，抛出异常
     *
     * @return 用户ID
     * @throws BusinessException
     */
    public static String getUsername() throws BusinessException {
        LoginSecurityDto loginUserDto = getCurrUser();
        if (loginUserDto == null) {
            throw new BusinessException("error.not.found","找不到指定用户");
        }
        return loginUserDto.getUsername();
    }

    /**
     * 获取当前登录的用户ID，如果未登录，抛出异常
     *
     * @return 用户ID
     * @throws BusinessException
     */
    public static Long currentUserId() throws BusinessException {
        LoginSecurityDto loginUserDto = getCurrUser();
        if (loginUserDto == null) {
            throw new BusinessException("找不到指定用户");
        }
        return loginUserDto.getId();
    }

    /**
     * 获取当前登录的角色ID，如果未登录，抛出异常
     *
     * @return 角色ID
     * @throws BusinessException
     */
    public static Long currentRoleId() throws BusinessException {
        LoginSecurityDto loginUserDto = getCurrUser();
        if (loginUserDto == null) {
            throw new BusinessException("找不到指定用户");
        }
        return loginUserDto.getRoleId();
    }

    public static String currentUserRealname() throws BusinessException {
        LoginSecurityDto loginUserDto = getCurrUser();
        if (loginUserDto == null) {
            throw new BusinessException("找不到指定用户");
        }
        return loginUserDto.getRealname();
    }
    /**
     * 获取当前登录的用户ID，如果未登录，返回null
     *
     * @return 用户ID
     */
    public static Long currentUserIdWithNull() {
        LoginSecurityDto loginUserDto = getCurrUser();
        if (loginUserDto == null) {
            return null;
        }
        return loginUserDto.getId();
    }

    /**
     * 获取当前登录的用户ID，如果未登录，返回null
     *
     * @return 用户ID
     */
    public static List<Long> getPrivilegeIdList() {
        LoginSecurityDto loginUserDto = getCurrUser();
        if (loginUserDto == null) {
            return null;
        }
        return loginUserDto.getAuthoritieIds();
    }

    /**
     * 获取当前用户
     *
     * @param <T> User
     * @return 当前用户
     */
    @SuppressWarnings("unchecked")
    private static <T extends UserDetails> T getCurrUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            return (T) authentication.getPrincipal();
        }
    }

    /**
     * 获取本机ip地址
     *
     * @return 本机ip地址
     * @throws UnknownHostException
     */
    public static String getLocalhostIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
