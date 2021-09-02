
# Alinta Energy code test - Customer CRUD

Simple Spring Boot-based Customer CRUD application written as a coding test for an Alinta Energy job application.

See `<host>:8080/swagger-ui.html` for API documentation.

## Requirements
### Build a simple API that allows
* Adding customers with the following fields
  * First name
  * Last name
  * Date of birth
* Editing customers
* Deleting customers
* Searching for a customer by partial name match (first or last name).
### Tech
* Java Version 1.8+
* Any testing framework (such as JUnit)
* Any ORM tool (Ebean, Hibernate)
* Documentation (Swagger)
### Please note
* Submit as an online Git repo (e.g. GitHub or Bitbucket)
* Code should be of a high standard in terms of
  * Readability
  * Maintainability
  * Performance

## Build
    mvn clean verify
### Requirements
* Java JDK (8+)
* Maven

## Run
    java -jar target/alintaenergy-codetest-customercrud-0.0.1-SNAPSHOT.jar
### Requirements
* Java JRE (8+)

## Configuration
Configuration can be changed by either updating the properties file packaged into the app at `src/main/resources/application.properties`, or by providing a new one at runtime using `--spring.config.location` argument.
