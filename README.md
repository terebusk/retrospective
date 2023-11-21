# Retrospective
Example Sprint Retrospective application

### Requirements
Java 17 JDK, or newer

### Run
From the command line execute `./gradlew bootRun`

### Usage
The [postman](./postman) directory contains Postman collection and environment with API and example requests, 
import both into Postman to start using the API

#### Swagger
After running the application, to access the API documentation [click here](http://localhost:8080/swagger-ui/index.html)

### Libraries
I have chosen the Spring Boot framework due to familiarity and to support rapidly developing features like Paging. 
`spring-boot-starter-data-jpa` to enable Entity support and H2 in memory database (due to requirements specifying no need for persistence of data).
`springdoc-openapi-starter-webmvc-ui` to enable swagger support and self documenting the API 
(TODO: some documentation needs corrections, as it doesn't completely match actual API behaviour).
Minor `jackson` libraries to enable JSON parsing and XML MediaType in response body.
