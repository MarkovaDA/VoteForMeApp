package su.vistar.gvpromoweb.web.controller;


import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.gvpromoweb.persistence.entity.CandidateEntity;
import su.vistar.gvpromoweb.persistence.entity.ReceiverEntity;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;
import su.vistar.gvpromoweb.service.CandidateService;
import su.vistar.gvpromoweb.service.UserService;

@Controller
@RequestMapping("/admin_tools")
public class AdminToolsController {

    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() 
    {       
        return "redirect:" + oauthVkUrl;  
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String alsohome() 
    {       
        return "redirect:" + oauthVkUrl;  
    }
    private VkApiRequest vkapiRequest = new VkApiRequest();
    //url для аутентификации
    private String oauthVkUrl = "https://oauth.vk.com/" +
            "authorize?" +
            "client_id=5546142" +
            "&redirect_uri=http://localhost:8084/GVPromoWebApp/admin_tools/login"+
            "&response_type=code";
    
    private String accessTokenUrl = "https://oauth.vk.com/access_token" +
            "?client_id=5546142" +
            "&redirect_uri=http://localhost:8084/GVPromoWebApp/admin_tools/login" +
            "&client_secret="+"6P3jkHUml1zYKjmMK919"+
            "&code=";
    private  Gson mapper = new Gson();
    
    private String accessToken;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginPage(@RequestParam("code")String code) throws Exception {      
        VkApiRequest.Response loginData = null;
        
        UserEntity currentUser = null;        
        ModelAndView model = new ModelAndView("main");        
        String response = vkapiRequest.doRequest(accessTokenUrl + code);     
        loginData = mapper.fromJson(response, VkApiRequest.Response.class);
        if (loginData == null && code.length() > 0){
            return new ModelAndView("redirect:");
        }
        currentUser = userService.get(loginData.getUser_id());                     
        String roleName = (currentUser != null) ? currentUser.getRole().getName() : "";       
        if (currentUser == null  ||  !"ADMIN".equals(currentUser.getRole().getName())){
            ModelAndView accessDeniedModel = new ModelAndView("access_denied");
            accessDeniedModel.addObject("error_header_message", "Вы не являетесь администратором данного сервиса");
            return accessDeniedModel;
        } 
        else 
        {   
            accessToken = loginData.getAccess_token();
            model.addObject("adminUser", vkapiRequest.getInfo(loginData.getUser_id(), accessToken)); //информация о юзере            
            ReceiverEntity[] friends = vkapiRequest.getFriends(loginData.getUser_id(), accessToken);
            
            for(ReceiverEntity item: friends){
                if (candidateService.getCandidateByVkId(item.getUid())!= null){
                    item.setStatus(false);
                }
                else item.setStatus(true);
            }
            model.addObject("friends",  friends); //информация о друзьях           
        }
        
        return model;
    }
    @RequestMapping(value = "/store_candidates", method = RequestMethod.POST)
    
    public void addCandidates(@RequestBody ReceiverEntity[] candidates) throws IOException
    {   
        CandidateEntity candidate; 
         
        for(ReceiverEntity item: candidates){
            if (item.getCity() == -1){
                item = vkapiRequest.getInfo(item.getUid(), accessToken);
            }
            candidate = new CandidateEntity();
            candidate.setAppUserId(1);
            candidate.setCityId(item.getCity()); //getByunction
            candidate.setVkId(item.getUid());
            candidate.setName(item.getFirst_name() + " " + item.getLast_name());
            candidateService.saveOrUpdate(candidate);
        }
    }
    @RequestMapping(value = "/delete_candidate", method = RequestMethod.POST)
    public void deleteCandidate(@RequestBody CandidateEntity candidate)
    {
        candidateService.delete(candidate);
    }

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public ModelAndView getCandidates()
    {
        if (accessToken == null)
            //return new ModelAndView("access_denied");
          return new ModelAndView("redirect:/admin_tools");
        ModelAndView model = new ModelAndView("candidates");
        List<CandidateEntity> candidates = candidateService.getAllCandidates();
        
        model.addObject("employees", candidateService.getAllCandidates());        
        return model;
    }    
}
