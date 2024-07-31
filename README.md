# uts-apigw

---

## 1 项目简介

本项目为业务网关项目，主要功能为路由转发、流量控制。

## 2 主要功能

主要功能为路由转发、流量控制（暂未实现）。

## 3 实现细节

采用gateway实现。配置了路由转发规则，到达网关的请求通过转发规则，转发到不同的服务。

## 4 SpringBoot集成Gateway

（1）导入Gateway依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
    <version>2.2.4.RELEASE</version>
</dependency>
```

（2）yml配置文件新增网关配置

```yml
# project info config
server:
  port: 8001
  servlet:
    context-path: /uts-apigw
spring:
  cloud:
    # nacos配置
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: 3064957d-b949-4a3b-a014-46b850c3bf0c
        group: uts-apigw
      config:
        server-addr: 127.0.0.1:8848
        namespace: 3064957d-b949-4a3b-a014-46b850c3bf0c
        group: uts-apigw
        file-extension: yaml
    # gateway配置
    gateway:
      routes:
        - id: uts-discover
          uri: lb://uts-discover
          predicates:
            - Path=/uts-apigw/uts-discover/**
          filters:
            - StripPrefix=1
        - id: uts-apigw
          uri: lb://uts-apigw
          predicates:
            - Path=/uts-apigw/**
          filters:
            - StripPrefix=1
```

其中，namespace是在nacos创建的命名空间ID。

启动uts-discover服务、uts-apigw服务，通过http://127.0.0.1:8001/uts-apigw/uts-discover/instance访问，请求从uts-apigw服务转发到uts-discover服务

![image-20240714112651223](images\1.png)