## 简介
TDF-oauth-client是TDF-oauth-server项目的客户端演示代码，作为Oauth2的client。
TDF-oauth-server是Oauth2的认证服务器(Authentication Server)，同时也是资源服务器

### 源码分支说明
1. master 最新稳定版本,基于spring boot 2.x
2. master_1.5.x 基于spring boot 1.5.x的版本
3. dev 开发主分支
4. 其他为临时开发分支,随时删除

## 环境
 JDK1.8

## 运行
#### github登录
1. 运行，选择Github登录。
2. 如需要自定义github的client，可以登录自己的github账号，注册Oauth Apps：https://github.com/settings/developers

#### 历史版本
1. v2.1.1文档: http://gitlab.taiji.com.cn/IRI/tdf/TDF-oauth-client/blob/v2.1.1/readme.md

### 演示普通调用第三方认证服务器登录（认证和授权）
* 在IDE中直接运行TdfOauth2ClientApplication
* 或控制台项目根路径下运行：mvn spring-boot:run -e

### 演示单点登录
* 启动项目
 1. 控制台项目根路径下运行：mvn spring-boot:run -e -Dspring.profiles.active=sso1 启动8081端口 运行 tdfOauth2SSO1
 1.  控制台项目根路径下运行：mvn spring-boot:run -e -Dspring.profiles.active=sso2 启动8082端口 运行 tdfOauth2SSO2
* 操作：
 1. 浏览器输入localhost:8081登录后展示内容
 2. 浏览器输入localhost:8082此时不用再登录可以展示内容
* 单点退出：（正在开发）
 1. localhost:8081点击注销
 2. localhost:8080和localhost:8082都无法展示内容

### 自定义代码完成Oauth2的授权码流程，演示不使用@EnableOAuth2Sso注解
参考AuthorizationCodeLiveTest（正在开发）（可参考[spring-security-oauth-sample](http://gitlab.taiji.com.cn/zhaozhao/spring-security-oauth-sample)项目中的实现）

###  演示第三方应用统一用户认证的常规操作流程
login页面（正在开发)-》第三方oauth server登录-》选择第三方登录-》跳转的TDF-oauth-server登录页

### 演示JWT方式
1. 前提条件：TDF-oauth-server打开AuthorizationServerJWTTokenStoreConfig类注释
2. 控制台项目根路径下运行：mvn spring-boot:run -e -Dspring.profiles.active=jwt 启动8082端口
3. 浏览器登录成功


## 关键配置说明
1. pom引入spring-security-oauth2相关包
2. yml配置oauth2 client信息:

```yml
security:
  oauth2:
    client:
      client-id: tdfOauth2SSO2v2
      client-secret: 123456
      access-token-uri: http://localhost:8080/oauth/token
      user-authorization-uri: http://localhost:8080/oauth/authorize
    resource:
      user-info-uri: http://localhost:8080/user/me
       #自定义属性：客户端logout时调用oauth2Server
      logout-url: http://localhost:8080/revoke-token
```

3. WebSecurityConfigurerAdapter增加@EnableOAuth2Sso，支持单点登录

```java
@EnableOAuth2Sso
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Component
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {
    //...
}
```

4. 支持单点退出相关配置
（正在修改）

## 与master_1.5.x配置区别
1. 此版本pom文件引入spring boot2.x相关包
2. 此版本不需要HomeController
3. cookie配置不同

```yml
server:
  port: 8081
  #v1.5.x
  #session:
  #  cookie:
  #   name: UISESSIONCOUPON

  #v2.x
  servlet:
    session:
      cookie:
        name: UISESSIONCOUPON
```