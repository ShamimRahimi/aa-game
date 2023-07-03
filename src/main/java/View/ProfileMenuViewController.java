package View;

import Controller.ProfileMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static View.LoginMenu.stage;
import static View.ProfileMenu.profScene;

public class ProfileMenuViewController {
    public ImageView pic1;

    public void changeUsername(MouseEvent mouseEvent) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("enter new username");
        TilePane r = new TilePane();
        Label l = new Label("no text input");
        td.showAndWait();
        l.setText(td.getEditor().getText());
        switch (ProfileMenuController.changeUsername(l.getText())) {
            case SUCCESS:
                td.close();
                break;
            case USERNAME_EXIST:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("username exists");
                alert.showAndWait();
                break;
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                break;
        }
    }

    public void changePassword(MouseEvent mouseEvent) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("enter new password");
        TilePane r = new TilePane();
        Label l = new Label("no text input");
        td.showAndWait();
        l.setText(td.getEditor().getText());
        switch (ProfileMenuController.changePassword(l.getText())) {
            case SUCCESS:
                td.close();
                break;
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                break;
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        ProfileMenuController.deleteAccount();
        new LoginMenu().start(stage);
    }

    public void chooseAvatar(MouseEvent mouseEvent) throws IOException {
        URL url = getClass().getClassLoader().getResource("AvatarsMenu.fxml");
        TilePane tilePane = FXMLLoader.load(url);
        tilePane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        Scene scene = new Scene(tilePane);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        ProfileMenuController.logout();
        new LoginMenu().start(stage);
    }

    public void backToProfile() throws Exception {
        new ProfileMenu().start(stage);
    }

    public void choose1(MouseEvent mouseEvent) throws Exception {
        switch (ProfileMenuController.chooseAvatar(1)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }
    public void choose2(MouseEvent mouseEvent) throws Exception {
        switch (ProfileMenuController.chooseAvatar(2)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }
    public void choose3(MouseEvent mouseEvent) throws Exception {
        switch (ProfileMenuController.chooseAvatar(3)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }
    public void choose4(MouseEvent mouseEvent) throws Exception {
        switch (ProfileMenuController.chooseAvatar(4)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }
    public void choose5(MouseEvent mouseEvent) throws Exception {
        switch (ProfileMenuController.chooseAvatar(5)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }
    public void choose6(MouseEvent mouseEvent) throws Exception {
        switch (ProfileMenuController.chooseAvatar(6)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }

    public void chooseAvatarFromFiles(MouseEvent mouseEvent) throws Exception {
        //TODO: not available for guests
        Window window = ((Node) (mouseEvent.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);
        mouseEvent.consume();
        switch (ProfileMenuController.chooseAvatarFromFiles(file)) {
            case NOT_AVAILABLE_FOR_GUESTS:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("not available for guests");
                alert2.showAndWait();
                backToProfile();
                break;
            case SUCCESS:
                backToProfile();
                break;
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(stage);
    }
}