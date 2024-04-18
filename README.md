# About
This is an API service that contains standard libraries, including but not limited to OpenFeign, Spring Cloud AWS, Micrometer.

# Getting Started
run:
```shell
$ mvn spring-boot:run -Dspring-boot.run.profiles=default
```

# API
```shell
$ curl http://localhost:8080/api/foos?id=1

<== 200 Ok
{
    "value": "a, b"
}
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.10/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.10/maven-plugin/reference/html/#build-image)

