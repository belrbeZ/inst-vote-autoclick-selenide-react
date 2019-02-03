#### VoteClick
## Autotest App

This is Spring Boot app to provide smoke tests for another services. 
There is no communication and database. 
It provide core functions for initializing and implementing auto-tests by selenium framework.

##Technologies
Although Java is the main technology used for the software, a couple of libraries and frameworks are used to improve the development process:
- [Spring Boot](https://projects.spring.io/spring-boot/): Spring Boot Framework;
- [JUnit](https://junit.org/): Test framework;
- [Selenide](http://selenide.org/): Selenide is a framework for test automation powered by Selenium WebDriver.


##Setup a local development environment
It is a Maven project, you can simply open it via Eclipse or IDEA, configure start of spring boot application. Change default configuration parameters, in /resources/application.properties

## Component flow
![Component diagram]

## Configuration
  ###To build component run:
      mvn clean test
## Current limitations:

## Examples


