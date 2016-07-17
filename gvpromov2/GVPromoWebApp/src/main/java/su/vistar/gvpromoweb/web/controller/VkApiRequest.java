
package su.vistar.gvpromoweb.web.controller;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import su.vistar.gvpromoweb.persistence.entity.ReceiverEntity;



public class VkApiRequest {
    
    private static Gson mapper = new Gson();
    
    
    public  String doRequest(String url) throws MalformedURLException, ProtocolException, IOException
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
    public  ReceiverEntity getInfo(String vkId, String accessToken) throws ProtocolException, IOException 
    {   
        String url = "https://api.vk.com/method/users.get?fields=first_name,last_name,city&user_ids=" + vkId + "&access_token=" + accessToken;
        String res = doRequest(url);       
        res = res.substring(res.indexOf(":") + 1, res.lastIndexOf("]}")) + "]"; 
        ReceiverEntity[] receivers = mapper.fromJson(res, ReceiverEntity[].class);
        return receivers[0];
    }
    
    public  ReceiverEntity[] getFriends(String vkId, String accessToken) throws ProtocolException, IOException
    {   
        String url = "https://api.vk.com/method/friends.get?fields=first_name,last_name,city&user_id=" + vkId + "&access_token=" + accessToken;
        String res = doRequest(url);       
        res = res.substring(res.indexOf(":") + 1, res.lastIndexOf("]}")) + "]"; 
        ReceiverEntity[] receivers = mapper.fromJson(res, ReceiverEntity[].class);

        /*for(ReceiverEntity item: receivers){
            if (candidateService.getCandidateByVkId(item.getUid())!= null){
                item.setStatus(false);
            }
            else item.setStatus(true);
            
        }*/
        return receivers;
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
}
