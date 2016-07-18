package su.vistar.gvpromo;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import su.vistar.gvpromo.commons.dto.VkUserDTO;
import su.vistar.gvpromo.services.AppVkService;
import su.vistar.gvpromo.commons.utils.StringCrypter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import su.vistar.gvpromo.commons.dto.CandidateDTO;
import su.vistar.gvpromo.commons.dto.MessageDTO;

public class MainController implements Initializable {

    @FXML WebView viewOAuth;
    @FXML Button sendBtn;
    @FXML Button messageUpdateBtn;
    @FXML Button nextUserIdBtn;
    @FXML TextField userNameText;
    @FXML Hyperlink pageLink;
    @FXML TextArea messageText;
    @FXML Label info;
    @FXML Label messageStatus;
    @FXML Label activeCandidateLabel;  
    
    private Map<String, String> loginParams;
    private StringCrypter crypter;
    private MessageDTO [] messages; //сообщения текущего кандидата
    private MessageDTO [] defaultMessages;
    private int messageCounter = 1; //счетчик-переключатель по текущим сообщениям
    private int sentMessageCounter = 0; //счетчик отправленных сообщений в текущем сеансе
    private String currentMessage = "";
    private VkUserDTO currentUser;   
    private static int countAllowedSendings = 0; //кол-во допустимых для отправки сообщений
    private static final int portionSize = 20;  //в день можно отправить не более portionSize сообщений
    
    private boolean okConditions = true;
    private String[] notifyMessages = 
    {
        "Ваш лимит сообщений на сегодня исчерпан",
        "Сегодня Вы можете отправить ",
        "У Вас нет прав для рассылки сообщений"
    };
    String loginDataFileName = "login.data";

    String oauthVkUrl = "https://oauth.vk.com/" +
            "authorize?" +
            "client_id=5544397" +
            "&display=mobile"+
            "&revoke=1"+
            "&scope=messages,offline" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&response_type=token";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crypter = new StringCrypter(new byte[]{2, 4, 3, 5, 6, 2, 7, 1});

        WebEngine webEngine = viewOAuth.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {                       
                        if (webEngine.getLocation().contains("#access_token")){
                            String webLocation = webEngine.getLocation();
                            String[] params = webLocation.split("&");
                            loginParams = new HashMap<>();
                            for (String param : params){
                                loginParams.put(param.split("=")[0], param.split("=")[1]);
                            }
                            loginParams.put("access_token", webLocation.substring(webLocation.indexOf("=")+1, webLocation.indexOf("&")));  
                            saveLoginData();
                            prepareData();
                            viewOAuth.setVisible(false);
                        }
                        //отправка сообщения
                        if (webEngine.getLocation().contains("messages.send")){
                            try {
                                String response = viewOAuth.getEngine().getDocument().getDocumentElement().getTextContent();
                                
                                if (response.contains("response")) {
                                    currentUser.setStatus(true);
                                    ++sentMessageCounter; //учет отправленного сообщения
                                    showMessageSuccessStatus();
                                }
                                else if (response.contains("error")) {
                                    currentUser.setStatus(false);
                                    showMessageFailStatus();
                                }                               
                                //сохранение статуса сообщений
                                ClientAPI.saveMessageInfo(currentUser);
                                } 
                                catch (Exception ex) {
                                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                    }
                    
                }
        );

        //авторизация
        if (!loadLoginData()) {
            oauth();           
        }
        //пользователь уже авторизован
        else  
            prepareData();
        sendBtn.setOnAction(event -> sendMessage());
        messageUpdateBtn.setOnAction(event -> updateMessage());
        nextUserIdBtn.setOnAction(event -> nextUser());
    }
    
    private CandidateDTO activeCandidate;
   
    private void prepareData()
    {
        
        try {
            activeCandidate = ClientAPI.getCandidate(loginParams.get("user_id"));
            //подгружаем еще сообщения ГВ (неявно)
            if (activeCandidate == null) {               
                allDisabled();
                info.setText(notifyMessages[2]);
                return;
            }
            activeCandidateLabel.setText(activeCandidateLabel.getText() + activeCandidate.getName());
            countAllowedSendings = portionSize - ClientAPI.getPassedCount(loginParams.get("user_id"));           
            String notifyMessage = notifyMessages[1] + Integer.toString(countAllowedSendings) + " сообщений";
            
            if (countAllowedSendings <= 0)
            {
                notifyMessage = notifyMessages[0];
                okConditions = false;
            }
            info.setText(notifyMessage);
            
            
            if (!okConditions) {
                allDisabled();
                return;
            }
            loadUsers(); //подгружаются первые countAllowedSendings пользователей
            getMessages();
            currentMessage = messages[0].getMessageText();
            messageText.setText(currentMessage);
        } 
        catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadUsers(){
        
        try {
            AppVkService.getUniqueUsers(loginParams.get("access_token"), countAllowedSendings, activeCandidate.getCityId(), activeCandidate.getId());
            currentUser = AppVkService.getCurrentUser();
            showCurrentUser();
        } 
        catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    private void oauth(){
        viewOAuth.getEngine().load(oauthVkUrl);
        viewOAuth.setVisible(true);
    }

    private int indexDefault = -1; 
    /*отправить сообщение*/
    private void sendMessage() {        
        if (sentMessageCounter > countAllowedSendings) {
            info.setText(notifyMessages[0]);
            allDisabled();
        }
        viewOAuth.getEngine().load(buildMessageSendUrl(currentMessage, loginParams.get("user_id"))); //отправка сообщения пока себе 
        if (defaultMessages != null && defaultMessages.length > 0)
        {
            indexDefault++;
            indexDefault = (0 > indexDefault || indexDefault > defaultMessages.length - 1) ? 0 : indexDefault;
            //теперь можно отправлять сообщение          
        }
        ClientAPI.updateHistory(loginParams.get("user_id"));
        sendBtn.setDisable(true);
    }
    private final String mainVkUId = "17500492";

    /*получаем сообщения текущего кандидата*/
    private void getMessages()
    {
        try {
            messages = ClientAPI.getMessages(activeCandidate.getId()); 
            
            if (!activeCandidate.getVkId().equals(mainVkUId)) 
            {
                defaultMessages = ClientAPI.getDefaultMessages();
            };
        } 
        catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    
    private void updateMessage() {       
        messageCounter = (messageCounter > messages.length-1) ? 0 : messageCounter; 
        currentMessage = messages[messageCounter].getMessageText();
        messageText.setText(currentMessage);
        ++messageCounter;
    }

    
    private void nextUser() {
        int countNewUser = countAllowedSendings - sentMessageCounter;
        if  (countNewUser == 0) 
        {
            info.setText(notifyMessages[1]);
            allDisabled();
            return;
        }
        currentUser = AppVkService.getNextUsers(loginParams.get("access_token"), countNewUser, activeCandidate.getCityId(), activeCandidate.getId());                      
        showCurrentUser();
        showMessageUsualStatus();
        updateMessage();
        sendBtn.setDisable(false);
    }

    private String buildMessageSendUrl(String message, String userId){
        return "https://api.vk.com/method/messages.send?" +
                "user_id=" + userId +
                "&message=" + message +
                "&access_token="+loginParams.get("access_token");
    }
            
    public void showPage() throws IOException{
        Form viewUserForm = new Form();
        viewUserForm.showForm();
    }

    private void saveLoginData(){

        StringBuilder loginData = new StringBuilder();

        for(Map.Entry<String, String> entry : loginParams.entrySet()) {
            loginData
                    .append(entry.getKey())
                    .append("::")
                    .append(entry.getValue())
                    .append(";");
        }

        try{
            PrintWriter out = new PrintWriter(loginDataFileName);
            out.write(crypter.encrypt(loginData.toString()));
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean loadLoginData(){
        if (Files.exists(Paths.get(loginDataFileName))){
            try {
                String[] params = crypter.decrypt(new String(Files.readAllBytes(Paths.get(loginDataFileName)))).split(";");
                System.out.println(Arrays.toString(params));
                loginParams = new HashMap<>();
                for (String param : params){
                    loginParams.put(param.split("::")[0], param.split("::")[1]);
                }
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                try {
                    Files.delete(Paths.get(loginDataFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }
    
    private void showCurrentUser(){
        pageLink.setText(currentUser.getFirst_name() + " " + currentUser.getLast_name());
    }
    /*отключение функциональности кнопок*/
    private void allDisabled(){
        sendBtn.setDisable(true);
        messageUpdateBtn.setDisable(true);  
        nextUserIdBtn.setDisable(true);
    }
    private void allActive(){
        sendBtn.setDisable(false);
        messageUpdateBtn.setDisable(false);  
        nextUserIdBtn.setDisable(false);
    }

    private void showMessageSuccessStatus()
    {
        messageStatus.setText("сообщение отправлено успешно");
        updateInfoText(countAllowedSendings - sentMessageCounter);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), messageStatus);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.play();
    }
    private void showMessageFailStatus() 
    {
        messageStatus.setText("отправка сообщения невозможна");
    }
    private void showMessageUsualStatus()
    {
        messageStatus.setText("Статус отправки сообщения");
    }
    private void updateInfoText(int number)
    {          
        info.setText(notifyMessages[1] + number + " сообщений");        
    }  
}
