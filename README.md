# LegoCMS
Base On PublicCMS

## 源码路径
https://gitee.com/zqliang_01/LegoCMS.git

## 环境要求
* jdk或jre 1.8 及以上
* mysql 5.5 及以上

## 运行步骤
### 创建数据库
* 创建数据库lego-cms
* 创建用户：legocms，密码：123456
### 项目打包 + 初始化数据库
* cd到legocms-parent目录
* 执行mvn package
### 项目启动
* cd到legocms-parent/legocms/target
* 拷贝文件legocms.jar、目录lib到任意路径
* cmd到该路经，执行java -jar legocms.jar
### 访问路径
* 项目默认端口8080
* 管理台路径http://localhost:8080/legocms/admin
* 默认账号密码admin/admin
### 配置修改
* 默认配置
```psp
spring.profiles.include=hibernate,freemarker,redis 
## tomcat配置
server.port=8080 
server.tomcat.maxThreads=500 
server.tomcat.uri-encoding=UTF-8 
server.tomcat.maxConnections=2000 
server.servlet.session.timeout=1800 
spring.servlet.multipart.enabled=true 
server.servlet.context-path=/legocms 
spring.session.store-type=none 
## 数据库配置
spring.datasource.driver-class-name=com.mysql.jdbc.Driver 
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/lego-cms?useunicode=true&characterEncoding=utf8&serverTimezone=GMT 
spring.datasource.username=legocms 
spring.datasource.password=123456 
```
* 修改默认配置，在启动路径下新建文件application.properties，按上述配置内容修改即可
