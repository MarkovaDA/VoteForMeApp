package su.vistar.gvpromoweb.service;

import com.google.gson.Gson;
import su.vistar.gvpromo.commons.dto.VkUserDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


public class AppVkService {
    
    private static final String searchUsersUrl = "https://api.vk.com/method/users.search?";
    
    private static Gson gsonMapper = new Gson();
    
    private static  HttpURLConnection getHttpConnection(String query) throws MalformedURLException, IOException{
        URL obj = new URL(query);
	    return (HttpURLConnection)obj.openConnection();
    }
    
    private static String getUsersQuery(int numberQuery, Map<String, String> params){
        
        String searchUsersUrlWithParams = searchUsersUrl;
        int offset = numberQuery * Integer.parseInt(params.get("count"));  
        
        for(Map.Entry<String, String> entry: params.entrySet()){
            searchUsersUrlWithParams += entry.getKey() + "=" + entry.getValue() + "&";
        }       
        return searchUsersUrlWithParams +  "offset=" + offset;
    }
          
    private static String getMessageQuery(){
        return "";
    }
    
    private static VkUserDTO[] users;
    
    private static int counter=0;
    
    public static VkUserDTO getNextUser(){
       int _counter = counter; counter++;
       return  _counter < users.length ? users[_counter] : null;       
    }
    
    public static VkUserDTO getCurrentUser(){
        return (counter - 1) >= 0 ? users[counter-1]: null;
    }
    
    public static void getUniqueUsers(int numberQuery, Map<String,String> params) throws IOException{
        
        String query = getUsersQuery(numberQuery, params);
        HttpURLConnection connection = getHttpConnection(getUsersQuery(numberQuery, params));
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode(); 
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {                   
                    response.append(inputLine);
            }            
            in.close();                       
            String usersJson = response.toString();           
            usersJson = "[" + usersJson.substring(usersJson.indexOf(",") + 1, usersJson.lastIndexOf("]}")) + "]";    
            System.out.println(usersJson);
            /*users = gsonMapper.fromJson(usersJson, VkUserDTO[].class);
            for(VkUserDTO user: users){
                System.out.println(user);
                //LocalDBService.insertUser(user);
            }*/
        } 
        else 
        {
            System.out.println("getUsers failed");
        }
    }
}
