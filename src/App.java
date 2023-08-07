import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
    
        Parent root;

try {
    
    /* Loads the initial first page that's built using the FXML scenebuilder. */

    root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
    Scene scene = new Scene(root);
    scene.getStylesheets().add("FXML/login.css");
    primaryStage.setTitle("Spartan Fitness.");
    primaryStage.setScene(scene);
    primaryStage.show();} catch (IOException e) {

    e.printStackTrace();
}
}
 
     public static void main(String[] args) {
        launch(args);
    }
}