package View;

import Model.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.DatagramPacket;
import java.net.URL;

public class MainMenu extends Application {
    public static Pane pane;
    @Override
    public void start(Stage stage) throws Exception {
//        URL url = MainMenu.class.getResource("MainMenu.fxml");
        URL url = getClass().getClassLoader().getResource("MainMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        this.pane = pane;
        ImageView imageView = (ImageView) pane.getChildren().get(0);
        if(Database.getLoggedInUser() != null) {
            Image image;
            try {
                image = new Image(Database.getLoggedInUser().getAvatarPath());
            } catch (IllegalArgumentException ex) {
                String path = Database.getLoggedInUser().getAvatarPath();
                File file = new File(path);
                String localUrl = file.toURI().toURL().toString();
                image = new Image(localUrl, false);
            }
            imageView.setImage(image);
        }
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
