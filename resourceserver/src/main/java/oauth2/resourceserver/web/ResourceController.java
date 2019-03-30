package oauth2.resourceserver.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获得认证信息，认证通过后，第三方应用可以请求的资源
 */
@RestController
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);


    @GetMapping("/resource")
    public String phone() {
        logger.info("in resource");
        return "resource";
    }
}
