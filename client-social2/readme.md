## 2.1.x版本的OAuth2-client

### 与2.0.x区别
1. thymeleaf的包
```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity4</artifactId>
</dependency>
```
需要使用
```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```
2。 OAuth2相关包
```xml
<!--OAuth2 登录-->
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
可以使用：
```xml
<!--OAuth2 登录-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```

3. 获取OAuth2AuthorizedClient的方式
```java
@Autowired
private OAuth2AuthorizedClientService authorizedClientService;

private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
    return this.authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(), authentication.getName());
}
```
可以直接在参数中注入：

```java
@GetMapping("/")
public String index(Model model,  @AuthenticationPrincipal OAuth2User oauth2User,
                    @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
    model.addAttribute("userName", oauth2User.getName());
    model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
    return "index";
}
```