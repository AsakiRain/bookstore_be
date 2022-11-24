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

## 项目运行

有人说我项目结构奇怪，那怎么办呢，自己研究下怎么启动？