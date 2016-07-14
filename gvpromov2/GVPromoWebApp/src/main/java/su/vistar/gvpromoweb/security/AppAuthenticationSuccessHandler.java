package su.vistar.gvpromoweb.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    //@Autowired
    //private ControllerUtils controllerUtils;

    private String userHomeUrl;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + userHomeUrl);
        try {
            //userService.recordLogin(controllerUtils.getCurrentUser(), request);
        } catch (Exception e) {
            log.error("Error when recording user login", e);
        }
    }

    public void setUserHomeUrl(String userHomeUrl) {
        this.userHomeUrl = userHomeUrl;
    }
}
