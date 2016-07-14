package su.vistar.gvpromoweb.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;
import su.vistar.gvpromoweb.security.AppUserDetails;
import su.vistar.gvpromoweb.web.error.UserNotAuthorizedException;
import su.vistar.gvpromoweb.web.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sweetvrn on 05.07.16.
 */
@Controller
@RequestMapping("/admin/reg")
public class RegisterController {

    @RequestMapping(value = "/user/show/registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage() throws Exception {
        ModelAndView mv = new ModelAndView("registration/registration.page");
        ControllerUtils.addMenuActiveInfo(mv, "administration", "users", "");
        return mv;
    }


    //Этот метод должени быть во всех контроллерах, где доступ только зарегестрированным пользователям
    @ModelAttribute("currentUser")
    public UserEntity currentUser(HttpServletRequest request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AppUserDetails) {
            AppUserDetails ud = (AppUserDetails) principal;
            return ud.getUser();
        } else {
            throw new UserNotAuthorizedException(request);
        }
    }

}
