package com.lanxin;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;

public class JdbcRealmApp {
    public static void main(String[] args) {
        //连接数据库
        JdbcRealm jdbcRealm=new JdbcRealm();
        DruidDataSource druidDataSource=new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("lanxin");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test");
        jdbcRealm.setDataSource(druidDataSource);

        //设置权限的查找是可以的
        jdbcRealm.setPermissionsLookupEnabled(true);


        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject=SecurityUtils.getSubject();


        UsernamePasswordToken token=new UsernamePasswordToken("admin","123");

        subject.login(token);


        //验证角色
        subject.checkRole("system");//没有这个角色会抛出错误：UnauthorizedException

        //验证权限
        subject.checkPermission("user:update");//没有这个权限会抛出错误：UnauthorizedException
    }
}
