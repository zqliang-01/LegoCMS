# LegoCMS
Base On PublicCMS

## 源码路径
https://gitee.com/zqliang_01/LegoCMS.git

## 环境要求
* jdk或jre 1.8 及以上
* mysql 5.5 及以上
* maven 3.0.0及以上

## 运行步骤
### 创建数据库
* 创建数据库lego-cms
* 创建用户：legocms，密码：123456
### 系统环境配置
* 安装JDK
* 配置Maven环境变量（自行百度配置）
### 项目打包 + 初始化数据库
* 根目录下双击执行dbInit.bat，初始化数据库
* 根目录下双击执行devRun.bat，会自动打包并运行项目
### 访问路径
* 项目默认端口8080
* 管理台路径http://localhost:8080/legocms/admin
* 默认账号密码admin/admin
### 项目版本路径
* legocms-parent/legocms/target
* 文件legocms.jar、目录lib为项目依赖文件
* 按实际需要把上述文件拷贝到指定路径，执行java -jar legocms.jar即可运行版本
### 配置修改
* 默认配置
* application.properties
```psp
spring.profiles.include=hibernate,freemarker,redis

### web
server.port=8080
server.tomcat.maxThreads=500
server.tomcat.uri-encoding=UTF-8
server.tomcat.maxConnections=2000
server.servlet.session.timeout=PT30M
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
server.servlet.context-path=/legocms
### 默认值none单机会话，redis共享会话，共享会话依赖application-redis配置，默认本机单节点redis
### 设置为redis后，会启用ftp托管站点文件，需配置ftp后开启
spring.session.store-type=none
spring.messages.basename=language/messages,language/page

### ftp
ftp.ip=127.0.0.1
ftp.username=test
ftp.password=test

### database
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/lego-cms?useunicode=true&characterEncoding=utf8&serverTimezone=GMT
spring.datasource.username=legocms
spring.datasource.password=123456
```
* 修改默认配置，在启动路径下新建文件application.properties，按上述配置内容修改即可
