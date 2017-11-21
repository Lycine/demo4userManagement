Read me
=============

环境
-------------
* jdk 1.8.0_144
* tomcat 8.5.20

简介
-------------
实现简单的用户管理，基于Spring Boot的项目.

使用说明
----------- 
1. 在数据库中新建用户表，建表sql在deploy文件夹下。
2. 在Demo4userManagementTests中调用helperGeneratePasswordAndSalt方法生成你想要的密码的盐值和哈希值。
3. 在用户表中新建几条测试数据。
4. 在applicatoin.yml中修改MySQL连接信息。
5. 启动项目。
6. 发送请求至 "localhost:8080/pass/signIn" 并附上参数:email=xxx 和 password=xxx, 将会显示登录成功 "sign in success"。
7. 发送请求至 "localhost:8080/afterSignInCanVisit", 将会显示 "hello xxx". 意味着登录成功。
8. 发送请求至 "localhost:8080/pass/logout", 将会显示 "logout success!"。

