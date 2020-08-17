## 添加对jsonp支持
	JsonpResponseBodyAdvice

## 添加异常处理：防止SQL信息输出到页面
	RestResponseEntityExceptionHandler

## 添加jasypt加密
- 添加依赖

com.github.ulisesbocchio:jasypt-spring-boot-starter:1.12

- properties中修改配置
```
security.password=ENC(encoded_password)	#密码密文
jasypt.encryptor.password=	#主密码(加密密码的密码)
jasypt.encryptor.algorithm=PBEWithMD5AndDES	#加密算法
jasypt.encryptor.providerName=SunJCE	#算法供应商(default Sun Java Cryptography Extension)
```
- 手动加密、解密文件

@Autowired StringEncryptor

## 热部署jar包
testCompile("org.springframework.boot:spring-boot-devtools")

## 拦截器Interceptor
	MyHandlerInterceptor,MyConfig

## StringToDateConverter

## ApplicationRunner
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