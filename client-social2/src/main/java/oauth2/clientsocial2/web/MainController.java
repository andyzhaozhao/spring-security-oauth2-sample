package oauth2.clientsocial2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        return "index";
    }

    @GetMapping("/login/oauth2")
    public String login() {
        return "login";
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

//
//    @GetMapping("/")
//    public String index(Model model,  @AuthenticationPrincipal OAuth2User oauth2User,
//                        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
//        model.addAttribute("userName", oauth2User.getName());
//        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
//        return "index";
//    }
//
//    @GetMapping("/login/oauth2")
//    public String login() {
//        return "login";
//    }
}