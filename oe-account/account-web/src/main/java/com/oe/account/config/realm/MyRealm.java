package com.oe.account.config.realm;

import com.oe.account.exception.OeException;
import com.oe.account.facade.UserFacade;
import com.oe.account.vo.UserVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangwj
 * @data 2019/3/29
 */
@Component
public class MyRealm extends AuthorizingRealm {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private UserFacade userFacade;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("授权！");
        String username = (String) principalCollection.getPrimaryPrincipal();
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        try {
            UserVo user = userFacade.getUserByUsername(username);
            info.addRole(user.getRole().getRoleName());
            user.getpList().forEach((e) -> info.addStringPermission(e.getPermissionName()));
        } catch (Exception e) {
            LOGGER.error("授权错误" + e.getMessage());
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("认证");
        String username = (String) authenticationToken.getPrincipal();
        UserVo user = null;

        try {
            user = userFacade.getUserByUsername(username);
        } catch (OeException e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                getName()
        );

    }
}
