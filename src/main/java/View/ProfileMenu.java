package View;

import Model.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class ProfileMenu extends Application {
    public static Scene profScene;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getClassLoader().getResource("ProfileMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        ImageView imageView = (ImageView) pane.getChildren().get(3);
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
        profScene = new Scene(pane);
        stage.setScene(profScene);
        stage.show();
    }
}
