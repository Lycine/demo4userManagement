Read me
=============

Environment Support
-------------
* jdk 1.8.0_144
* tomcat 8.5.20

Features
-------------
Demo project designed for login authentication with cookie, session via Spring Boot

Usage
----------- 
After start project
1. send post request to "localhost:8080/pass/signIn" with payload username=123 and password=abc123, you will see "sign in success".
2. send request to "localhost:8080/afterSignInCanVisit", you will see "hello 123". Means sign in success.
3. send request to "localhost:8080/pass/logout", you will see "logout success!".

