Read me
=============

Environment Support
-------------
* jdk 1.8.0_144
* tomcat 8.5.20

Features
-------------
Demo project designed for user management via Spring Boot

Usage
----------- 
1. Create a new user table in the database, build table sql in the deploy folder.
2. Call the helperGeneratePasswordAndSalt method in Demo4userManagementTests to generate the salt and hash of the password you want.
3. in the user table to create a few new test data.
4. In the applicatoin.yml modify MySQL connection information.
5. Start the project.
6. Send the request to "localhost: 8080 / pass / signIn" with the following parameters: email = xxx and password = xxx. The login "success in sign" will be displayed.
7. Send the request to "localhost: 8080 / afterSignInCanVisit" and "hello xxx" will be displayed, which means the login is successful.
8. Send the request to "localhost: 8080 / pass / logout" and "logout success!" Will be displayed.

