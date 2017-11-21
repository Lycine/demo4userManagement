changelog
=============
* 2017-11-21 13:57

    1. Add unit test
    2. The user information required for login verification is read from the database
    3. Added MySQL user table build script
    4. In order to facilitate the test logon operation request method is allowed GET request
    
* 2017-11-20 18:09

    1. Added verifyPassword and generatePasswordAndSalt methods, not tested;
    2. Added connection to the MySQL database
    
* 2017-11-19 21:28

    1. add more log;
    2. fix logout twice will throw null pointer exception;
    3. change POST request to all request except signIn option;
    4. reduce the length of ticketName and ticketValue;
    5. log output to file
    
* 2017-11-18 18:49
    
    1. init;