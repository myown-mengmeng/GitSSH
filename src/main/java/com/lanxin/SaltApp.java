package com.lanxin;

import com.lanxin.dao.RoleDao;
import com.lanxin.util.SqlSessionUtil;
import org.apache.shiro.crypto.hash.Md5Hash;

public class SaltApp {
    public static void main(String[] args) {

        //这应该写在注册逻辑中

        //对密码进行加密（原始密码，密码盐值，加密次数）
        String salt="asd!@#$%";
        String username="zhangsan";

        Md5Hash md5Hash=new Md5Hash("zs123",salt+username,10);

        System.out.println(md5Hash.toString());
    }
}
