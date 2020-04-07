# LegoCMS
Base On PublicCMS

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
