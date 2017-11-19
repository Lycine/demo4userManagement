Read me
=============

环境
-------------
* jdk 1.8.0_144
* tomcat 8.5.20

简介
-------------
通过cookie和session实现简单的登录验证，基于Spring Boot的项目.

使用说明
----------- 
After start project
1. 发送post请求至 "localhost:8080/pass/signIn" 并附上参数:username=123 和 password=abc123, 将会显示登录成功 "sign in success".
2. 发送请求至 "localhost:8080/afterSignInCanVisit", 将会显示 "hello 123". 意味着登录成功.
3. 发送请求至 "localhost:8080/pass/logout", 将会显示 "logout success!".

