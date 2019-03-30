package oauth2.authorizationserver.otherresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("otherResourceServer")
@SpringBootApplication
public class AuthorizationserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationserverApplication.class, args);
    }

}
