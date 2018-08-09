spring cloud demo

参考自http://www.ityouknow.com/spring-cloud.html

spring cloud 的版本为Finchley.SR1

依赖的spring boot版本为2.0.4.RELEASE

jdk为1.8

eureka-server为注册中心
，测试地址为http://localhost:8000/

eureka-provider为服务生产者
，测试地址为http://localhost:9000/hello/helloWorld?name=liuxiany

eureka-consumer为服务消费者
，测试地址为http://localhost:9001/hello/helloWorld/jack

springcloud-zuul为网关
，测试地址为http://localhost:9002/eureka-consumer/hello/helloWorld/tom?token=abc

，将token传空可测试zuul的filter

zipkin-server为链路追踪服务
,测试地址为http://localhost:8001/zipkin/

目前包含consumer调provider的熔断，和zuul网关路由的熔断。

微服务的监控：http://localhost:9001/hystrix/