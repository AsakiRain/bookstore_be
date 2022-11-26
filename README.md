# bookstore4_be

## 介绍

因为前后端分离，后端只用servlet没有jsp。使用了fastjson和auth0-jwt

## 项目结构

- controller 中是响应请求的处理逻辑
- model 中是用到的数据结构和对象相关的方法
- utils 中是一些工具类，封装了一套返回数据结构

## 项目接口

**接口文档地址：https://www.apifox.cn/web/project/1958328**
**建议下载apifox进行调试**

- 所有的接口返回的都是json格式的数据
- GET方法接收的数据采用query参数，POST，PUT，DELETE方法接收的数据采用json格式。
- 推荐前端使用fetch函数进行ajax请求。
- 如果出现调用上的错误会返回400，如发送的数据不完整，或者数据格式错误等
- 如果jwt token验证失败会返回401，此时需要重新登录获取新的token
- 如果有500，我是没有遇到过，建议debug完叫我一下

## 数据库配置
1. 打开一个MySQL Command Line客户端，输入用户名密码
![客户端](./md/client.png)
2. 运行```CREATE DATABASE `bookstore` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';```创建数据库
3. 运行```use bookstore;```选择数据库
4. 找到项目根目录下面的bookstore.sql文件夹，运行```source D:\code\bookstore4_be\bookstore.sql```导入数据库
    > 注意，这里的路径要是你的项目实际路径
5. （可跳过）运行```CREATE USER `crud`@`%` IDENTIFIED WITH mysql_native_password BY 'ssr129631';```创建用户
6. （可跳过）运行```GRANT Alter, Alter Routine, Create, Create Routine, Create Temporary Tables, Create View, Delete, Drop, Event, Execute, Grant Option, Index, Insert, Lock Tables, References, Select, Show View, Trigger, Update ON `bookstore`.* TO `crud`@`%`;```为他授予数据库权限
7. 去src/Config.java修改一下你的数据库配置，比如多数人的端口是3306，和我的默认配置不一样。如果你跳过了上面两步，可以把账号密码设置成root用户直接连接。
![配置](./md/config.png)
8. 已经结束了，大的还在后头

## 项目运行

1. git clone下来一个干净的文件夹
![clone下来的文件夹](./md/folder.png)
2. 删除.idea文件夹，日他仙人
![世界，遗忘我](./md/del.png)
3. 打开项目，观察到maven已经正确下载了依赖
![依赖列表](./md/deps.png)
4. 在web/WEB-INF下创建文件夹classes和lib
5. 在工具栏打开文件→项目结构→模块→路径
6. 选择使用模块编译输出路径，并且设置目录为刚刚创建的classes
![编译输出文件夹](./md/path.png)
7. 切换到依赖选项卡，把所有maven依赖打勾，并且把范围都设置成编译
![设置编译选项](./md/dep.png)
8. 点击左上角加号，选择添加JAR或目录
![添加目录](/md/add.png)
9. 选择本机tomcat目录下的lib文件夹，不是刚刚创建的那个lib
![添加tomcat的lib](./md/lib.png)
10. 切换到Facet选项卡，点击加号，选择web，点击确认
![添加web](./md/web.png)
11. 右下角出现警告，点击创建工件，跳转到工件选项卡
![创建工件](./md/create.png)
12. 选中右侧所有的可选依赖，右键选择置于/WEB-INF/lib
![放置依赖](./md/place.png)
13. 大功告成，点击应用并退出
14. 添加一个运行配置，大概像我这样
![设置路径](./md/run.png)
![设置上下文](./md/context.png)
15. 运行，如果弹出浏览器访问显示404是正常的，因为/并没有对应的servlet
![404](./md/404.png)
16. 相关请求api都在apifox里面，请求路径以http://127.0.0.1:8080/bookstore4_be/api/开头,如http://127.0.0.1:8080/bookstore4/api/login，http://127.0.0.1:8080/bookstore4/api/booklist/page?page=1