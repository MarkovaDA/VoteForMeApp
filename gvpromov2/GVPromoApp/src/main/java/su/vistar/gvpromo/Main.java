package su.vistar.gvpromo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Scene main;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GVPromoScene.fxml"));
        main = new Scene(root, 400, 500);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Поддержите своего кандидата");
        primaryStage.setScene(main);
        primaryStage.show();       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
