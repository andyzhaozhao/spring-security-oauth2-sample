package oauth2.authorizationserver.otherresourceserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Profile("otherResourceServer")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigMultiRes extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置授权服务器的安全，意味着实际上是/oauth/token端点,/oauth/authorize端点也应该是安全的
     * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 此处通过配置ClientDetailsService，来配置注册到此授权服务器的客户端Clients信息。
     * 注意，除非在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了
     * 一个AuthenticationManager，否则密码授权方式不可用。
     * 至少要配置一个client，否则服务器将不会启动。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // client_id
                .withClient("client-for-server")
                // client_secret
                .secret(passwordEncoder.encode("client-for-server"))
                // 此Client支持的授权类型。OAuth2的Client请求code时会传递授权类型参数，此处包含的授权类型才可以访问
                .authorizedGrantTypes("authorization_code", "implicit")
                // 此Client分配的access_token的有效时间，要小于刷新时间
                .accessTokenValiditySeconds(7200)
                // 此Client分配的access_token的可刷新时间，要大于有效时间。超过有效时间，但是在可刷新时间范围的access_token可以刷新
                .refreshTokenValiditySeconds(72000)
                // 重定向URL
                .redirectUris("http://localhost:8080/login/oauth2/code/authorizationserver")
                .additionalInformation()
                // 此Client可以访问的资源服务器ID，每个资源服务器有一个ID。
                .resourceIds(ResourceServerConfig.RESOURCE_ID, "resourceserver")
                // 此Client拥有的权限，资源服务器可以依据此处定义的权限对Client进行鉴权。
                .authorities("ROLE_CLIENT")
                // 此Client可以访问的资源的范围，资源服务器可以依据此处定义的范围对Client进行鉴权。
                .scopes("profile", "email", "phone")
                // 自动批准的范围（scope），自动批准的scope在批准页不需要显示，即不需要用户确认批准，如果所有scope都自动批准，
                // 则不显示批准页
                .autoApprove("profile");
    }

    /**
     * 该方法是用来配置授权服务器端点特性（Authorization Server endpoints），主要是一些非安全的特性。
     * 比如token存储、token自定义、授权类型等等的
     * 默认不需要任何配置如果是需要密码授权则需要提供一个AuthenticationManager
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
    }

}
