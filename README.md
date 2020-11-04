#### VoteClick
## App for Voting in Social Voter resource

The goal of this app is to imitate user actions in social media voter site to reach poits for voting.
Inside this is a Spring Boot app with React Frontend Application that usese smoke-tests approach of another services. 
There is no communication and database. 
It provide core functions for initializing and implementing auto-tests by selenium framework.

##Technologies
Although Java is the main technology used for the software, a couple of libraries and frameworks are used to improve the development process:
- [Spring Boot](https://projects.spring.io/spring-boot/): Spring Boot Framework;
- [JUnit](https://junit.org/): Test framework;
- [Selenide](http://selenide.org/): Selenide is a framework for test automation powered by Selenium WebDriver.
- React Application packaged with custom maven plugin 

##Setup a local development environment
It is a Maven project, you can simply open it via Eclipse or IDEA, configure start of spring boot application. Change default configuration parameters, in /resources/application.properties

## Build
To build component run:\
      `mvn clean test` 
## Current limitations:
Work with one Voter Resource
