package View;

import Controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;

import java.net.URL;

public class LoginMenu extends Application {
    public static Stage stage;
    public static void main(String[] args) {
        Application.launch(LoginMenu.class, args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        URL url = getClass().getClassLoader().getResource("LoginMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        var appIcon = new Image("icon2.png");
        stage.getIcons().add(appIcon);

        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getClassLoader().getResource("icon2.png"));
                taskbar.setIconImage(dockIcon);
            }
        }
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}
