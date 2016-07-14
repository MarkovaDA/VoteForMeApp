package su.vistar.gvpromo.services;

import com.google.gson.Gson;
import su.vistar.gvpromo.commons.dto.VkUserDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import su.vistar.gvpromo.ClientAPI;


public class AppVkService {
    
    private static final String searchUsersUrl = "https://api.vk.com/method/users.search?";
    private static final String sendMessageUrl = "https://api.vk.com/method/messages.send?";
    private static Gson gsonMapper = new Gson();
    
    private static  HttpURLConnection getHttpConnection(String query) throws MalformedURLException, IOException{
        URL obj = new URL(query);
	return (HttpURLConnection)obj.openConnection();
    }
    
    //построение запроса на получения юзеров
    private static String getUsersQuery(int numberQuery, Map<String, String> params){
        //странные вещи происходят
        String searchUsersUrlWithParams = searchUsersUrl;
        //int offset = numberQuery * Integer.parseInt(params.get("count")); 
        int offset = numberQuery;
        for(Map.Entry<String, String> entry: params.entrySet()){
            searchUsersUrlWithParams += entry.getKey() + "=" + entry.getValue() + "&";
        }       
        return searchUsersUrlWithParams +  "offset=" + offset;
    }
    //построение запроса на отправку сообщения      
    private static String sendMessageQuery(String userId, String message, String accessToken){
       
        return sendMessageUrl + 
                "user_id=" + userId +
                "&message=" + message +
                "&access_token="+accessToken;
    }
    
    private static VkUserDTO[] users;
    
    private static int counter = -1;

    
    public static VkUserDTO getNextUsers(String accessToken, int count, int cityId, int candidateId){               
    
    if ( (++counter) == users.length){
            counter = 0;           
            try {
                getUniqueUsers(accessToken, count, cityId, candidateId);
            } 
            catch (IOException ex) {
                Logger.getLogger(AppVkService.class.getName()).log(Level.SEVERE, null, ex);
            } 
       }
       return users[counter];
    }
    
    public static VkUserDTO getCurrentUser(){
        if (users==null) return null;
        return users[counter];
    }    
    public static void getUniqueUsers(String accessToken, int count, int city, int candidateId) throws IOException{
        counter = 0;
        Map<String, String> urlParams = new HashMap<>();       
        urlParams.put("count", Integer.toString(count));       
        urlParams.put("city",  Integer.toString(city));
        urlParams.put("age_from", "18");
        urlParams.put("access_token", accessToken);
        urlParams.put("fields", "id,first_name,last_name,photo_400_orig");       
        int numberQuery = ClientAPI.getCountQuery(count, candidateId);        
        String query = getUsersQuery(numberQuery, urlParams);
        HttpURLConnection connection = getHttpConnection(query);       
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();       
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
                                 
            String usersJson =  readResponseFromHTTP(connection);
            usersJson = "[" + usersJson.substring(usersJson.indexOf(",") + 1, usersJson.lastIndexOf("]}")) + "]";    
            users = gsonMapper.fromJson(usersJson, VkUserDTO[].class);
        } 
        else 
        {   
            users = null;
            System.out.println("getUsers failed");
        }
    }

    public static String sendMessage(String userId, String message, String accessToken) throws IOException
    {   
        String query = sendMessageQuery(userId, message, accessToken);
        
        HttpURLConnection connection = getHttpConnection(query);       
        connection.setRequestMethod("GET");
        
        int responseCode = connection.getResponseCode();
        
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String responseJson =  readResponseFromHTTP(connection);
            return responseJson;
        }
        return "error";
    }
   
    private static String readResponseFromHTTP(HttpURLConnection connection) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {                   
                response.append(inputLine);
        }            
        in.close();                       
        return response.toString();         
    }
}
