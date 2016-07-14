package su.vistar.gvpromoweb.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import su.vistar.gvpromoweb.web.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException() {
        super("User is not authorized");
    }

    public UserNotAuthorizedException(HttpServletRequest request) {
        super("User is not authorized.\n" +
                "Request details:\n" +
                "Remote ip: " +
                ControllerUtils.getIp(request)
        );
    }
}
