# Spring Boot Security JWT

This project demonstrates a simple microservice that uses Spring Boot, Spring Security and JWT authentication token.

### Run project

``` docker build . -t spring-boot-security-jwt ```

``` docker run -p 8080:8080 -e JWT_ACCESS_TOKEN_EXPIRATION_MINUTES=15 -e JWT_ACCESS_TOKEN_SECRET=FlzRvmaWsCzUy5jESg2vIShRBsN0k9wIoskjNEeoIJFs1E1xSoblkszRLK8f -e JWT_REFRESH_TOKEN_EXPIRATION_MINUTES=1500 -e JWT_REFRESH_TOKEN_SECRET=3yYAMO04ZC76Hok3kiRE51aIg9XQg5PcEV3QlW477kiijShToEjkFxLypKe spring-boot-security-jwt ```

And access: ``` http://localhost:8080/ ```

### Swagger

Access: ``` http://localhost:8080/swagger-ui/index.html ```

![Alt text](docs/swagger.png?raw=true "Swagger")


### Example

Authenticate and get tokens (user: user|admin, password: 123456)

![Alt text](docs/auth.png?raw=true "Authentication")

Access protected resources passing access token in Authorization header (Bearer)

![Alt text](docs/resource.png?raw=true "Resource")