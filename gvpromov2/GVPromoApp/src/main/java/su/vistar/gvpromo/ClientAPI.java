package su.vistar.gvpromo;


import com.google.gson.Gson;
import su.vistar.gvpromo.commons.dto.ResponseDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import su.vistar.gvpromo.commons.dto.CandidateDTO;
import su.vistar.gvpromo.commons.dto.MessageDTO;
import su.vistar.gvpromo.commons.dto.VkUserDTO;


public class ClientAPI {

    private static final Logger log = Logger.getLogger(ClientAPI.class.getName());

    private static final String BASE_URL = "http://localhost:8084/GVPromoWebApp/api";

    private static Gson mapper = new Gson();
   
    public static MessageDTO[] getMessages(Integer candidateId) throws Exception{
        ResponseDTO response = request("/get/messages?candidate_id=", candidateId.toString());           
        String responseToString = response.getObject().toString();
        return mapper.fromJson(responseToString, MessageDTO[].class);      
    }
    public static MessageDTO[] getDefaultMessages() throws Exception{
        ResponseDTO response = request("/get_default_messages");           
        String responseToString = response.getObject().toString();
        return mapper.fromJson(responseToString, MessageDTO[].class);
    }
    
    /*получаем кол-во позволенных запросов для текущего пользователя
    * на сегодняшний день(максимум 20 запросов)
    */   
    public static int getPassedCount(String vkUserId) throws Exception{
        ResponseDTO response = request("/get_history/", vkUserId);        
        return ((Double)response.getObject()).intValue();
    }
    /*count - сколько нужно получить юзеров*/
    public static int getCountQuery(int count, int candidateId){
        Double countQuery;
        countQuery = 0.0;
        try {
            ResponseDTO response = request("/get_count_query/", Integer.toString(count) + "/" + Integer.toString(candidateId));          
            countQuery = (Double)response.getObject();
        } 
        catch (Exception ex) {
            Logger.getLogger(ClientAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return countQuery.intValue();
    }
    /*обновление истории отправки сообщений*/
    public static void updateHistory(String appVkUser){
        try {
            request("/update_history/", appVkUser);
        } 
        catch (Exception ex) {
            Logger.getLogger(ClientAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*сохранение пользователя, которому отправляли сообщение
    * и статус отправки сообщения
    */
    public static void saveMessageInfo(VkUserDTO user) throws Exception{
       String jsonUser = mapper.toJson(user);
       ResponseDTO response = request("/save_message_info", "?json_user=" + jsonUser);      
    }
    
    public static CandidateDTO getCandidate(String vkUserId) throws Exception
    {
        ResponseDTO response = request("/get_candidate", "?vkuser_id=" + vkUserId);
        if (response.getObject() == null) return null;     
        return (CandidateDTO)mapper.fromJson((String)response.getObject(), CandidateDTO.class);
    }
    
    /*запрос с параметрами*/
    private static ResponseDTO request(String apiUrl, String params) throws Exception {
        String newUrl = apiUrl+params;
        return request(newUrl);
    }
    //http://localhost:8084/GVPromoWebApp/api/get_candidate?vkuser_id=262591631
    private static ResponseDTO request(String apiUrl) throws Exception {
        
        URL obj = new URL(BASE_URL+apiUrl);
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
            String usersJson = response.toString();
            return mapper.fromJson(usersJson, ResponseDTO.class);
        }
        else{
            log.log(Level.WARNING, "Connection refused");
            return null;
        }
    }           
}
