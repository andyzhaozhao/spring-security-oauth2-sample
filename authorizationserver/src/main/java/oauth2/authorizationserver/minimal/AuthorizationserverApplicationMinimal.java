package oauth2.authorizationserver.minimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Profile("minimal")
@SpringBootApplication
@EnableAuthorizationServer
public class AuthorizationserverApplicationMinimal {

    // 最简配置
    // 命令行输入 curl client:client@localhost:9999/oauth/token -dgrant_type=client_credentials -dscope=any 获取access_token
    // curl -H  'Authorization: Bearer 33f7e4da-5df8-4ef7-9188-b753af878326' http://localhost:9090/resource
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationserverApplicationMinimal.class, args);
    }

}
