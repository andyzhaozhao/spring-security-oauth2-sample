package oauth2.client.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SimpleController {

    @GetMapping("/hello")
    public String email(Principal principal) {
        return "hello， " + principal.getName();
    }

}