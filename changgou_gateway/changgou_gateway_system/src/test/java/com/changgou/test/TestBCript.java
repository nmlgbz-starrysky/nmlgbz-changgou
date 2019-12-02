package com.changgou.test;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestBCript {
    public static void main(String[] args) {
        //获取盐
        String gensalt = BCrypt.gensalt();
        System.out.println("盐：" + gensalt);
        //基于盐进行加密
        String hashpw = BCrypt.hashpw("123456", gensalt);
        System.out.println("加密后的密文：" + hashpw);
        //解析比较密文
        boolean flag = BCrypt.checkpw("123456", hashpw);
        System.out.println(flag);
        //
    }
}
