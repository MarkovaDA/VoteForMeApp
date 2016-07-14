package su.vistar.gvpromoweb.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import su.vistar.gvpromoweb.web.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("Access denied");
    }

    public AccessDeniedException(HttpServletRequest request) {
        super("Access denied.\n" +
                "Request details:\n" +
                "Remote ip: " +
                ControllerUtils.getIp(request)
        );
    }
}
