package com.lanxin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;

/**
 * Hello world!
 *
 */
public class SimpleRealmApp
{
    public static void main( String[] args )
    {

        SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
        //设置用户和密码,角色
        simpleAccountRealm.addAccount("admin","123","管理员");
        simpleAccountRealm.addAccount("ymt","666","普通用户");

        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();


        defaultSecurityManager.setRealm(simpleAccountRealm);


        SecurityUtils.setSecurityManager(defaultSecurityManager);


        Subject subject=SecurityUtils.getSubject();


        //认证，登录,用户在界面上填写用户和密码

        UsernamePasswordToken token=new UsernamePasswordToken("admin","123");


        //发起认证请求
        try {

            subject.login(token);

        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        }catch(UnknownAccountException un){
            System.out.println("账户不正确");
        }

        System.out.println("是否登录成功:"+subject.isAuthenticated());


        //发起授权请求
        System.out.println("是否是普通用户"+subject.hasRole("普通用户"));

        System.out.println("是否是管理员"+subject.hasRole("管理员"));

        try {
            subject.checkRole("普通用户");//如果不是，会抛出异常
        } catch (UnauthorizedException e) {
            System.out.println("不是普通用户");
        }


    }
}
