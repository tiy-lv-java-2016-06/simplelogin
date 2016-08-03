[![Build Status](https://travis-ci.org/tiy-lv-java-2016-06/simplelogin.svg?branch=master)](https://travis-ci.org/tiy-lv-java-2016-06/simplelogin)

# Simple Login

## Description
Simple login using Spring MVC rest controllers

## Description
Using spring boot to make a fast login system for front end

## Running
* Locate the `jar` file in the `jar/` directory
* Run `java -jar <jar file>`
* Open browser to localhost:8080

## End Points

### POST `/register`
Allows a new user to be registered

Expected payload
```
{"username":"bob", "password":"mypassword"}
```

Return:
User object

Exceptions:
409 User exists already

### POST `/token`
Retrieves a token based on the user

Expected payload
```
{"username":"bob", "password":"mypassword"}
```

Return:
String Token

Exceptions:
401 Login Failed
404 User not found

### GET `/user`
Retrieves the information about the given user or nothing if user not found

Required Header:
`Authorization: Token <usertoken>`

Return:
User Object

