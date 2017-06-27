更新日志：

1.添加对jsonp支持
	JsonpResponseBodyAdvice

2.添加异常处理：防止SQL信息输出到页面
	RestResponseEntityExceptionHandler

3.添加jasypt加密
--添加jar
com.github.ulisesbocchio:jasypt-spring-boot-starter:1.12
--properties中添加密码
jasypt.encryptor.password=master_key	#主密码(加密密码的密码)
password:ENC(encrypt_string)	#密码密文
--可选:修改算法