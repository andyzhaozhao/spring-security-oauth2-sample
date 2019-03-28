#### 新建项目
新建Spring Boot工程,引入spring-boot-starter-web
和spring-boot-starter-security包,作为一个被Spring Security安全保护的初始web应用。 

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```  

#### 引入spring-security-oauth2-client
在Spring Security5.0之前版本中，Oauth2的功能是在独立的项目[spring-security-oauth](https://github.com/spring-projects/spring-security-oauth)中实现的。 从5.0版本开始，Spring Security逐渐把Oauth2功能内嵌进来，而[spring-security-oauth](https://github.com/spring-projects/spring-security-oauth)进入了代码维护模式.

Spring Security项目的spring-security-oauth2-client模块具体实现了Oauth2的Client逻辑。
Spring Security5.0版本的Oauth2有三个模块
* spring-security-oauth2-core 核心库，定义基础数据结构等
* spring-security-oauth2-jose对JOSE协议组的支持
* spring-security-oauth2-client Oauth2协议的Client角色的实现

其中 spring-security-oauth2-client
模块具体实现了Oauth2的Client角色的逻辑。
> * Spring Security 5.1版本实现了Oauth2的Resource Server角色功能
> * Spring Security 5.3版本实现了Oauth2的Authorization Server角色功能

```xml
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-config</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-oauth2-client</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-oauth2-jose</artifactId>
</dependency>
```

Spring Security帮助我们封装了OAuth2的标准认证流程，而对于QQ登录等不够标准的Oauth2流程来说，我们需要进行扩展开发。
Spring Security的Oauth2功能的扩展性非常好，我们可以方便地进行代码扩展，从而支持QQ登录。

#### 针对Spring Security的Oauth2功能扩展流程
针对Spring Security的Oauth2功能进行扩展开发，从而可以支持QQ，微信等等不够标准的Oauth2服务商。
一般情况下都是使用授权码模式（authorization code）



Spring Security5.0开始oauth2客户端只需要配置
```
http.oauth2Login()
```

tokenType不是Bear 先设置为Bear



因为在CommonOAuth2Provider中预置的
Github，Google，Facebook，Okta的baseUri是
**OAuth2LoginAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI**
为了同时支持多种Provder我们在QQ互联平台注册的应用的回掉地址为:
```xml

```
保持一致


#### 原理
当@EnableWebSecurity(debug = true)debug属性为true时可以从控制台日志查看针对所有url的过滤器链:
```
Security filter chain: [
  WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CsrfFilter
  LogoutFilter
  OAuth2AuthorizationRequestRedirectFilter
  OAuth2LoginAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  FilterSecurityInterceptor
]

```
Oauth2客户端认证流程特有的三个过滤器
* OAuth2AuthorizationRequestRedirectFilter

* OAuth2LoginAuthenticationFilter
* DefaultLoginPageGeneratingFilter
* 

#### OAuth2LoginAuthenticationFilter
```java
this.getAuthenticationManager().authenticate(...)
```
使用OAuth2LoginAuthenticationProvider处理Oauth2认证逻辑

