# album
基于ssm的相册网站

- 采用ssm框架
- 文件存储使用腾讯云cos
- 数据库为mysql


**注意事项**

1. WEB-INF/view/module/head.jsp 第14行登录路径为了兼容nginx，去掉了contextPath 
2. 所有静态资源路径都使用了http://static.inkroom.cn 域名，这是我的域名，可以直接使用，但是最好改成contextPath根路径
3. cos为个人资源，不提供秘钥
4. 密码为md5加盐加密，可以调用 [EncryptUtil](https://github.com/inkroom/album/tree/master/src/main/java/cn/inkroom/web/quartz/util/EncryptUtil.java) 类生成，
5. 数据库脚本为 [sql/base.sql](https://github.com/inkroom/album/tree/master/src/main/resources/sql/base.sql)


### 项目现已成功运行，地址请访问

# [墨盒相册](http://image.inkroom.cn/album/1/)


