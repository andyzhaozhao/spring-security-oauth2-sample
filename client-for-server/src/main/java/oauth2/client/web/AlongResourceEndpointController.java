package oauth2.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 使用RestTemplate携带access token请求独立资源服务器获取信息
 */
@RestController
public class AlongResourceEndpointController {

    // 独立的资源服务器resourceserver的API
    private static final String URL_GET_RES= "http://localhost:9090/resource";

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private RestTemplate restTemplate;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        return restTemplate;
    }

    @GetMapping("/resource")
    public String userinfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, header);

        ResponseEntity<String> response = getRestTemplate().exchange(URL_GET_RES, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }

}