package oauth2.client.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String email(Principal principal) {
        return "Hello 1 " + principal.getName();
    }

}