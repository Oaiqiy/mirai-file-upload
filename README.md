# mirai-file-upload

一个基于Spring Boot框架和[Mirai](https://github.com/mamoe/mirai) QQ bot框架开发的文件传输应用

在QQ群中存储文件,利用Bot读取，上传和删除群文件，用Spring作为web框架，在后端调用bot，实现在网页中，上传，下载，删除群文件。

开发目的为了解决手机电脑之间，电脑与电脑之间文件同步麻烦的问题，使用这个程序只需要把想要同步的文件发送或转发到qq群中，即可在网页中下载，同时在网页中也可以上传文件到qq群中。

为安全考虑使用spring security对网站进行保护，可在[这里](/src/main/java/org/oaiqiy/miraifileupload/web/WebSecurity.java)关闭spring security。

### 使用方法
1. 创建一个qq群把想要使用的bot拉进去。
2. 在[配置文件](/src/main/resources/application.yml)中填入bot的qq号，密码和qq群号。
3. 用gradle build项目。
4. 在`/build/libs`里找到jar包。
5. 在服务器中运行。
6. 放开[这里](/src/main/resources/application.yml)配置的端口。
7. 用[这里](/src/main/resources/application.yml)配置用户账号密码登录网页，即可开始使用。

### 问题
1. 不能使用https，因为用到官方的url不是https的，所以更推荐使用edge而不是chrome来打开此页面。
2. 下载文件不能显示进度，同样是官方的api下载的文件不带有文件名，又因为是跨域下载不能直接改名，所用只能用javascript下载。

