package su.vistar.gvpromoweb.web.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;
import su.vistar.gvpromoweb.security.AppUserDetails;
import su.vistar.gvpromoweb.web.error.UserNotAuthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class ControllerUtils {

    @Autowired(required = true)
    private HttpServletRequest request;


    private static final Locale locale = LocaleContextHolder.getLocale();


    public UserEntity getCurrentUser() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AppUserDetails) {
            AppUserDetails ud = (AppUserDetails) principal;
            return ud.getUser();
        } else {
            throw new UserNotAuthorizedException(request);
        }
    }

    public static void addMenuActiveInfo(ModelAndView mv, String first, String second, String third){
        mv.addObject("menuItem",first);
        mv.addObject("menuSubItem",second);
        mv.addObject("menuThirdItem",third);
    }

    public void addHeaderInfo(ModelAndView mv){

    }

    public static long getCurrentUserId() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails) {
            AppUserDetails ud = (AppUserDetails) principal;
            return ud.getUser().getId();
        } else {
            throw new UserNotAuthorizedException();
        }
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            userAgent = "Unknown";
        }
        return userAgent;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null) {
            ip = request.getHeader("X-Forwarded-For");
            if (ip == null) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }
}
