
package su.vistar.gvpromo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Form {

    private  Stage form;
    private  String xmlResorces = "/GVPromoViewUser.fxml";

    public Form(){
        form = new Stage();
    }

    public void setXmlResorces(String xmlResorces) {
        this.xmlResorces = xmlResorces;
    }
    
    public  void showForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(xmlResorces));
            Parent root = loader.load();
            ViewUserController viewUserController = loader.getController();
            viewUserController.setCurrentStage(form);
            Scene scene = new Scene(root);
            form.setScene(scene);
            form.setResizable(false);
            form.show();
        }catch (IOException ex) {
            System.out.println("неверно указан ресурс для формы");
            ex.printStackTrace();
        }

    }

}
