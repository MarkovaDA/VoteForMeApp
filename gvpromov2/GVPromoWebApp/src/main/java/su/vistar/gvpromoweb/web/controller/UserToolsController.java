package su.vistar.gvpromoweb.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author darya
 */
@Controller
@RequestMapping("/user_tools")
public class UserToolsController {
    
    @RequestMapping(value = "", method = RequestMethod.GET)    
    public String home() 
    {       
        return "user_tools";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String alsohome() 
    {       
        return "user_tools";
    }
}
