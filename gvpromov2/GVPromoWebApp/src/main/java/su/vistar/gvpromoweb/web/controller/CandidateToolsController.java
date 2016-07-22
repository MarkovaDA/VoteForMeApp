package su.vistar.gvpromoweb.web.controller;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.gvpromoweb.persistence.entity.CandidateEntity;
import su.vistar.gvpromoweb.persistence.entity.CandidateUsers;
import su.vistar.gvpromoweb.persistence.entity.MessageEntity;
import su.vistar.gvpromoweb.persistence.entity.ReceiverEntity;
import su.vistar.gvpromoweb.service.CandidateService;
import su.vistar.gvpromoweb.service.CandidateUsersService;

@Controller
@RequestMapping("/candidate_tools")
public class CandidateToolsController {

    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private CandidateUsersService candidateUserService;

    
    private VkApiRequest vkapiRequest = new VkApiRequest();
    
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
    
    //url для аутентификации
    private String oauthVkUrl = "https://oauth.vk.com/" +
            "authorize?" +
            "client_id=5546142" +
            "&redirect_uri=http://localhost:8084/GVPromoWebApp/candidate_tools/login"+
            "&response_type=code&scope=messages,offline";
    
    private String accessTokenUrl = "https://oauth.vk.com/access_token" +
            "?client_id=5546142" +
            "&redirect_uri=http://localhost:8084/GVPromoWebApp/candidate_tools/login" +
            "&client_secret="+"6P3jkHUml1zYKjmMK919"+
            "&code=";
    
    private  Gson mapper = new Gson();
    private Map<Integer, CandidateEntity> candidates = new HashMap<>();    
    private Map<Integer, String> tokens = new HashMap<>();
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginPage(@RequestParam("code")String code) throws Exception {      
        
        VkApiRequest.Response loginData = null;
        CandidateEntity currentCandidate = null;        
        ModelAndView model = new ModelAndView("candidate_page");        
        String response = vkapiRequest.doRequest(accessTokenUrl + code);     
        loginData = mapper.fromJson(response, VkApiRequest.Response.class);
        if (loginData == null && code.length() > 0){
            return new ModelAndView("redirect:");
        }
        currentCandidate = candidateService.getCandidateByVkId(loginData.getUser_id());
        if (currentCandidate == null){
            ModelAndView accessDeniedModel = new ModelAndView("access_denied");
            accessDeniedModel.addObject("error_header_message", "Вы отсутсвутете в списке возможных кандидатов");
            return accessDeniedModel;
        } 
        else 
        {   
            candidates.put(currentCandidate.getId(), currentCandidate); 
            tokens.put(currentCandidate.getId(), loginData.getAccess_token());
            model.addObject("candidateUser", currentCandidate); //информация о юзере
            model.addObject("messages", currentCandidate.getMessages());
        }
        
        return model;
    }
    
    
    @RequestMapping(value = "/update_message", method = RequestMethod.POST)
    public void updateMessage(@RequestParam("message")String newMessage,@RequestParam("candidate_id")Integer candidateId) 
    {   
        CandidateEntity currentCandidate = candidates.get(candidateId); 
        MessageEntity message = mapper.fromJson(newMessage, MessageEntity.class); //обновленное сообщение
        int indexMessage = findMessageById(message.getMessageId(), currentCandidate);
        currentCandidate.getMessages().get(indexMessage).setMessageText(message.getMessageText());
        
        candidateService.saveOrUpdate(currentCandidate);       
    }
    @RequestMapping(value = "/add_sendors", method = RequestMethod.GET)
    public ModelAndView addSendors(@RequestParam("candidate_id")Integer candidateId) throws IOException
    {
        if (candidates.get(candidateId) == null) {
            return new ModelAndView("redirect:/candidate_tools");
        }
           
        ModelAndView model = new ModelAndView("add_sendors");      
        String access_token = tokens.get(candidateId);
        //проверить на существование access_token
        CandidateEntity currentCandidate = candidates.get(candidateId);
        ReceiverEntity[] friends = vkapiRequest.getFriends(currentCandidate.getVkId(), tokens.get(candidateId));  
        //добавленные пользователи не учитываются
        for(ReceiverEntity item: friends)
        {   
            if (candidateUserService.getCandidateForVkUser(item.getUid())!= null){
                item.setStatus(false);
            }
            else item.setStatus(true);
        }
        model.addObject("friends",  friends);
        model.addObject("candidateUser", currentCandidate);
        return model;
    }
    //прислать сюда данные
    @RequestMapping(value = "/store_sendors", method = RequestMethod.POST)   
    public void storeSendors(@RequestBody ReceiverEntity[] sendors) throws IOException
    {   
        Integer cityId = sendors[sendors.length -1].getCity();
        if (cityId != -2) return;
        Integer candidateId = Integer.parseInt(sendors[sendors.length -1].getUid());
        CandidateUsers sendor;
        for(int i = 0; i < sendors.length - 1; i++)
        {
            sendor = new CandidateUsers();
            sendor.setCandidateId(candidateId);
            sendor.setVkUserId(sendors[i].getUid());
            candidateUserService.saveOrUpdate(sendor);
        }
    }
    
    private int findMessageById(Integer id, CandidateEntity cand){
        Iterator<MessageEntity> iterator =  cand.getMessages().iterator();
        int i = 0;
        while(iterator.hasNext()){
            MessageEntity item = iterator.next();
            if (Objects.equals(item.getMessageId(), id))
                return i;
            i++;
        }
        return -1;
    }
    
    @RequestMapping(value = "/delete_sendor", method = RequestMethod.POST)
    public void deleteSendors(@RequestBody CandidateEntity candidate)
    {
        //candidateService.delete(candidate);
        CandidateUsers sendor = new CandidateUsers();
        sendor.setId(candidate.getId());
        candidateUserService.delete(sendor);
    }
    
    @RequestMapping(value = "/sendors", method = RequestMethod.GET)
    public ModelAndView getSendors(@RequestParam("candidate_id")Integer candidateId) throws IOException
    {   
        ModelAndView model = new ModelAndView("sendors");
        CandidateEntity currentCandidate = candidates.get(candidateId);
        List<CandidateUsers> sendors = candidateUserService.allSendors(candidateId);
        CandidateUsers current;
        CandidateEntity sendor;
        List<CandidateEntity> _sendors = new ArrayList<>();
        Iterator<CandidateUsers> iterator = sendors.iterator();
        while(iterator.hasNext())
        {
            current = iterator.next();
            sendor = new CandidateEntity();
            sendor.setId(current.getId());
            ReceiverEntity info = vkapiRequest.getInfo(current.getVkUserId(), tokens.get(candidateId));
            sendor.setName(info.getFirst_name() + " " + info.getLast_name());
            _sendors.add(sendor);
            
        }
        model.addObject("candidateUser", currentCandidate);
        model.addObject("employees", _sendors);
        return model;
    }
    
    
}

