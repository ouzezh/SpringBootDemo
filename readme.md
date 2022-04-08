## 热部署jar包
testCompile("org.springframework.boot:spring-boot-devtools")

## 拦截器Interceptor
	MyHandlerInterceptor,MyConfig

## StringToDateConverter

## 窗口启动初始化 ApplicationRunner
	MyApplicationRunner.run

## 定时任务
	@EnableScheduling
	MySchedule

## 跨域CROS
  MyConfig.corsFilter

## thymeleaf
```
build.gradle add spring-boot-starter-thymeleaf
MyMvcController add /greeting
src/main/resources/templates/greeting.html
```

## aspect
  AnnotationAspect
  PointcutAspect

## Spring应用上下文环境
  SpringUtils

## 停止应用
  ShutdownController

## 添加jasypt加密
- 添加依赖

com.github.ulisesbocchio:jasypt-spring-boot-starter:1.12

- properties中修改配置
```
myPwd=ENC(encoded_password)	#密码密文
jasypt.encryptor.password=	#主密码(加密密码的密码)
jasypt.encryptor.algorithm=PBEWithMD5AndDES	#加密算法
jasypt.encryptor.providerName=SunJCE	#算法供应商(default Sun Java Cryptography Extension)
```
- 手动加密、解密文件

@Autowired StringEncryptor

## 集成 Kafka
https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.messaging.kafka

## Swagger
SwaggerConfig

knife4j:
https://gitee.com/xiaoym/knife4j

原版:
https://github.com/ityouknow/spring-boot-examples/tree/master/spring-boot-swagger
http://localhost:8080/swagger-ui/index.html
