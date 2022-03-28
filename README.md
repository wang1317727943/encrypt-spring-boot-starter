# encrypt-spring-boot-starter
springboot请求响应加解密(加密方式为AES)
直接引入该项目pom依赖，会自动加密get请求返回数据，解密post请求数据。
可以配置是否启动加解密功能，及修改密钥
spring
  encrypt:
    enabled: false
    key: hello11特教云
