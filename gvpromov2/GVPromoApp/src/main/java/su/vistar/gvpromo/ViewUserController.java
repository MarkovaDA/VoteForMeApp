
package su.vistar.gvpromo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import su.vistar.gvpromo.commons.dto.VkUserDTO;
import su.vistar.gvpromo.services.AppVkService;

import java.net.URL;
import java.util.ResourceBundle;


public class ViewUserController implements Initializable{
    
    @FXML
    WebView userPage;

    private Stage currentStage;


    public void closeForm(){
        currentStage.close();
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VkUserDTO current;
        if ( (current = AppVkService.getCurrentUser())!=null ) {
            WebEngine engine = userPage.getEngine();       
            engine.load("https://m.vk.com/id" + current.getUid());
        }
    }
}
