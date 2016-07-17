package su.vistar.gvpromoweb.web.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import su.vistar.gvpromo.commons.dto.ResponseDTO;
import su.vistar.gvpromoweb.persistence.entity.CandidateEntity;
import su.vistar.gvpromoweb.persistence.entity.FilterEntity;
import su.vistar.gvpromoweb.persistence.entity.HistoryEntity;
import su.vistar.gvpromoweb.persistence.entity.ReceiverEntity;
import su.vistar.gvpromoweb.persistence.entity.CandidateUsers;
import su.vistar.gvpromoweb.persistence.entity.MessageEntity;

import su.vistar.gvpromoweb.service.CandidateService;
import su.vistar.gvpromoweb.service.FilterService;
import su.vistar.gvpromoweb.service.HistoryService;
import su.vistar.gvpromoweb.service.MessageService;
import su.vistar.gvpromoweb.service.ReceiverService;
import su.vistar.gvpromoweb.service.UserService;
import su.vistar.gvpromoweb.service.CandidateUsersService;
import su.vistar.gvpromoweb.web.utils.ControllerUtils;

/**
 * Контроллер,принимающий запросы от клиентского приложения
 * 
 */

@Controller
@RequestMapping("/api")
public class ClientApiController {
   

    
    @Autowired
    private MessageService messageService;    
    
    @Autowired
    private ReceiverService receiverService;  
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private CandidateUsersService candidateUsersService;
    
    @Autowired
    private FilterService filterService;
    
    private  Gson mapper = new Gson();
      

    @RequestMapping(value="/get/messages", method=RequestMethod.GET)
    @ResponseBody
    public ResponseDTO getMessages(@RequestParam("candidate_id")Integer candidateId){
       
        ResponseDTO result = new ResponseDTO();
        CandidateEntity candidate = candidateService.getCandidateById(candidateId);
        List<MessageEntity> messages = candidate.getMessages();
        for(MessageEntity message: messages){
            message.setCandidate(null);           
        }
        String messagesJson = mapper.toJson(messages);         
        result.setObject(mapper.toJson(candidate.getMessages()));     
        return result; 
    }
    
    @RequestMapping(value="/save_message_info", method=RequestMethod.GET)
    @ResponseBody
    public ResponseDTO saveMessageInfo(@RequestParam("json_user")String jsonUser){
       
        ResponseDTO result = new ResponseDTO();
        ReceiverEntity receiver = mapper.fromJson(jsonUser, ReceiverEntity.class);
        receiverService.saveOrUpdate(receiver);
        result.setObject(true);
        return result;
    }
    
    
    @RequestMapping(value="/get_history/{vkuser_id}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseDTO getHistoryById(@PathVariable("vkuser_id")String vkuser_id)
    {   
        HistoryEntity entity = historyService.selectByIdAndDate(vkuser_id);
        ResponseDTO result = new ResponseDTO();
        
        HistoryEntity previous = historyService.selectByVkId(vkuser_id);
       
        //первый сеанс у пользователя - не отправлял cегодня сообщений
        if (previous != null && entity == null)
        {   
            previous.setCountQuery(0);
            historyService.simpleSave(previous);           
            result.setObject(0);
            return result;
        }
        result.setObject(entity.getCountQuery());       
        return result;
    }
   
    @RequestMapping(value="/get_count_query/{count}/{candidate_id}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseDTO getCountQuery(@PathVariable("count")Integer count, @PathVariable("candidate_id")Integer candidateId){
        
        FilterEntity entity = filterService.get(candidateId);       
        
        if (entity == null) //записи нет в бд
        {   
            entity = new FilterEntity();
            entity.setCandidateId(candidateId);
            entity.setOffset(0);           
        }
        ResponseDTO result = new ResponseDTO();
        result.setObject(entity.getOffset());
        entity.setOffset(entity.getOffset() + count);
        filterService.saveOrUpdate(entity);       
        return result;
    }   
  
    @RequestMapping(value="/update_history/{vkuser_id}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseDTO updateHistory(@PathVariable("vkuser_id")String vkuser_id)
    {
        HistoryEntity entity = historyService.selectByVkId(vkuser_id);
        //вставка новой entity
        if (entity == null)
        {           
            entity = new HistoryEntity();
            entity.setCountQuery(0);
            entity.setVkUserId(vkuser_id);
        }
        historyService.saveOrUpdate(entity);
        ResponseDTO result = new ResponseDTO();
        result.setObject(true);
        return result;
    }
   
    @RequestMapping(value="/get_candidate", method=RequestMethod.GET)
    @ResponseBody
    public ResponseDTO getCandidateForVkUser(@RequestParam("vkuser_id")String vkuserId){
        
        //получаем кандидата, к которому приписан юзер с вк id, равным vkuser_id
        ResponseDTO response = new ResponseDTO(); 
        
        CandidateUsers entity = candidateUsersService.getCandidateForVkUser(vkuserId);        
        if (entity == null){
            response.setObject(null);
            return response;
        }        
        CandidateEntity candidate = candidateService.getCandidateById(entity.getCandidateId()); 
        candidate.setMessages(null);       
        response.setObject(mapper.toJson(candidate));  
        return response;
    }   
}
