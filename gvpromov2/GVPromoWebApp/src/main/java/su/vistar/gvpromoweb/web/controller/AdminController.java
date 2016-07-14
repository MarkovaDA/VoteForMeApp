package su.vistar.gvpromoweb.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;
import su.vistar.gvpromoweb.security.AppUserDetails;
import su.vistar.gvpromoweb.service.UserService;
import su.vistar.gvpromoweb.web.error.UserNotAuthorizedException;
import su.vistar.gvpromoweb.web.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ControllerUtils controllerUtils;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView adminHome() {
        
        UserEntity loginedUser;
        
        /*try 
        {
            loginedUser = controllerUtils.getCurrentUser();
        } 
        catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } */
        ModelAndView mv = new ModelAndView("main");
        //mv.addObject("temp", "temp text");
        //mv.addObject("user", user);
        //controllerUtils.addHeaderInfo(mv);
        return mv;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView usersList() {
        ModelAndView mv = new ModelAndView("admin/users.list");
        ControllerUtils.addMenuActiveInfo(mv, "administration", "users", "");
        controllerUtils.addHeaderInfo(mv);
        return mv;
    }

    @RequestMapping(value = { "/403" })
    public ModelAndView _403() {
        ModelAndView mv = new ModelAndView("main");
        mv.addObject("temp", "temp text");
        return mv;
    }

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
