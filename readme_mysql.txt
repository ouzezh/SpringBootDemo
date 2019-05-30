一、添加依赖
  implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:{version}'
  implementation 'mysql:mysql-connector-java:{version}'
  implementation 'com.alibaba:druid-spring-boot-starter:{version}'

二、添加注解：SampleApplication.java
@MapperScan("com.ozz.springboot.dao")

三、添加配置：application.yml
spring:
    datasource:
        name: mysql_test
        type: com.alibaba.druid.pool.DruidDataSource
        #druid相关配置
        druid:
          #监控统计拦截的filters
          filters: stat
          driver-class-name: com.mysql.jdbc.Driver
          #基本属性
          url: jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
          username: x
          password: x
          #配置初始化大小/最小/最大
          initial-size: 1
          min-idle: 1
          max-active: 20
          #获取连接等待超时时间
          max-wait: 60000
          #间隔多久进行一次检测，检测需要关闭的空闲连接
          time-between-eviction-runs-millis: 60000
          #一个连接在池中最小生存的时间
          min-evictable-idle-time-millis: 300000
          validation-query: SELECT 1 FROM DUAL
          test-while-idle: true
          test-on-borrow: false
          test-on-return: false
          #打开PSCache，并指定每个连接上PSCache的大小。oracle设为true，mysql设为false。分库分表较多推荐设置为false
          pool-prepared-statements: false
          max-pool-prepared-statement-per-connection-size: 20
mybatis:
  mapper-locations: classpath*:mapping/*.xml
  type-aliases-package: com.ozz.springboot.model
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    mapUnderscoreToCamelCase: true

四、定义接口，直接调用即可