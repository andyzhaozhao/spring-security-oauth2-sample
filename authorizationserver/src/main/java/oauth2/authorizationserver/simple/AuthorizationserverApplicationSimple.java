package oauth2.authorizationserver.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("simple")
@SpringBootApplication
public class AuthorizationserverApplicationSimple {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationserverApplicationSimple.class, args);
    }

}
