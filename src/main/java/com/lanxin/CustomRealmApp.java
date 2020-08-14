package com.lanxin;

import com.lanxin.custom.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

public class CustomRealmApp {
    public static void main(String[] args) {
        CustomRealm customRealm=new CustomRealm();

        //构建SecurityManager环境
        DefaultSecurityManager securityManager=new DefaultSecurityManager();

        securityManager.setRealm(customRealm);

        //加密回复原始数值
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//加密的方式
        hashedCredentialsMatcher.setHashIterations(10);//加密的次数
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("admin","123");

        subject.login(usernamePasswordToken);

        //验证权限
        try {
            subject.checkPermission("user:select");//没有该权限会抛出错误：UnauthorizedException
        } catch (AuthorizationException e) {
            System.out.println("没有该权限");
        }

        //验证角色
        try {
            subject.checkRole("system");//没有该角色会抛出错误：UnauthorizedException
        } catch (AuthorizationException e) {
            System.out.println("没有该角色");
        }
    }
}
