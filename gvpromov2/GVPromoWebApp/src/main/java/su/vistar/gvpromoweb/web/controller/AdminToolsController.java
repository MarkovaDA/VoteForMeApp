package su.vistar.gvpromoweb.web.controller;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
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
    //url для аутентификации
    private String oauthVkUrl = "https://oauth.vk.com/" +
            "authorize?" +
            "client_id=5546142" +
            "&revoke=1"+
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
        Response loginData = null;
        UserEntity currentUser = null;
        ModelAndView model = new ModelAndView("main");        
        String response = doRequest(accessTokenUrl + code);     
        loginData = mapper.fromJson(response, Response.class);
        currentUser = userService.get(loginData.getUser_id());
        String roleName = (currentUser != null) ? currentUser.getRole().getName() : "";       
        if (currentUser == null  ||  !"ADMIN".equals(currentUser.getRole().getName())){
           return new ModelAndView("access_denied");
        } 
        else 
        {   
            accessToken = loginData.getAccess_token();
            model.addObject("adminUser", getInfo(loginData.getUser_id())); //информация о юзере
            model.addObject("friends",   getFriends(loginData.getUser_id())); //информация о друзьях
        }
        
        return model;
    }
    @RequestMapping(value = "/store_candidates", method = RequestMethod.POST)
    public void addCandidates(@RequestBody ReceiverEntity[] candidates) throws IOException
    {   
        CandidateEntity candidate; 

         
        for(ReceiverEntity item: candidates){
            if (item.getCity() == -1){
                item = getInfo(item.getUid());
            }
            candidate = new CandidateEntity();
            candidate.setAppUserId(1);
            candidate.setCityId(item.getCity()); //getByunction
            candidate.setVkId(item.getUid());
            candidate.setName(item.getFirst_name() + " " + item.getLast_name());
            candidateService.saveOrUpdate(candidate);
        }
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginfailed(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception {
        return "redirect:/dologout";
    }
    
    //формат ответа от вк-сервера при получении access-token
    class Response 
    {
        private String access_token;
        private String user_id;
        private String expires_in;

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }
        public Response()
        {
            
        }
        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
        
    }

    private String doRequest(String url) throws MalformedURLException, ProtocolException, IOException
    {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {          
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return "";
    }
    //получаем имя пользователя по вк_id
    private ReceiverEntity getInfo(String vkId) throws ProtocolException, IOException 
    {   
        String url = "https://api.vk.com/method/users.get?fields=first_name,last_name,city&user_ids=" + vkId + "&access_token=" + accessToken;
        String res = doRequest(url);       
        res = res.substring(res.indexOf(":") + 1, res.lastIndexOf("]}")) + "]"; 
        ReceiverEntity[] receivers = mapper.fromJson(res, ReceiverEntity[].class);
        return receivers[0];
    }
    
    private ReceiverEntity[] getFriends(String vkId) throws ProtocolException, IOException
    {   
        String url = "https://api.vk.com/method/friends.get?fields=first_name,last_name,city&user_id=" + vkId + "&access_token=" + accessToken;
        String res = doRequest(url);       
        res = res.substring(res.indexOf(":") + 1, res.lastIndexOf("]}")) + "]"; 
        ReceiverEntity[] receivers = mapper.fromJson(res, ReceiverEntity[].class);

        for(ReceiverEntity item: receivers){
            if (candidateService.getCandidateByVkId(item.getUid())!= null){
                item.setStatus(false);
            }
            else item.setStatus(true);
            
        }
        return receivers;
    }
    
}
