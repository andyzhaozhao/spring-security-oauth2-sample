package oauth2.authorizationserver.simple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 * 资源服务器的指责是对来自于OAuth2 Client的access_token进行鉴权。一个资源服务器包含多个端点（接口），
 * 一部分端点是作为资源服务器的资源提供给OAuth2的Client访问,另一部分端点不由资源服务器管理
 * 有资源服务器管理的端点安全性配置在此类中，其余端点的安全性配置在SecurityConfiguration类中。
 * 当请求中包含OAuth2 access_token时Spring Security根据资源服务器配置进行过滤。
 * EnableResourceServer将会创建一个WebSecurityConfigurerAdapter执行顺序（Order）是3。在SecurityConfiguration类之前
 * 运行，有更高的优先级。
 */
@Profile("simple")
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);

    public static final String RESOURCE_ID = "authorizationserver";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.info("ResourceServerConfig中配置HttpSecurity对象执行");
        // 只有/me
        // 端口作为资源服务器的资源
        http.requestMatchers().antMatchers("/me")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

    }

}
