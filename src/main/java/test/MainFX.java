package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFX extends Application {
    private Stage stage;
    private Parent parent;
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            this.stage = primaryStage ;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficheJob.fxml"));
            parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setScene(scene);
          //  stage.getIcons().add(new Image("/logoboutaba.png"));
            scene.getStylesheets().add("/bleutaba.css");
            stage.setTitle("Recruter");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
