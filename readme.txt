更新日志：

1.添加对jsonp支持
	JsonpResponseBodyAdvice

2.添加异常处理：防止SQL信息输出到页面
	RestResponseEntityExceptionHandler

3.添加jasypt加密
--添加jar
com.github.ulisesbocchio:jasypt-spring-boot-starter:1.12
--properties中修改配置
security.password=ENC(gGPAnX2MZmxKsqLkz4Ns8RiJjGJie1FTlWWi2JqCtGg=)	#密码密文
jasypt.encryptor.password=ncsoft	#主密码(加密密码的密码)
jasypt.encryptor.algorithm=PBEWithMD5AndDES	#加密算法
jasypt.encryptor.providerName=SunJCE	#算法供应商(default Sun Java Cryptography Extension)

4.热部署jar包
testCompile("org.springframework.boot:spring-boot-devtools")

5.拦截器Interceptor
	SampleHandlerInterceptor,SampleConfiguration

6.StringToDateConverter

7.ApplicationRunner
	SampleApplicationRunner.run