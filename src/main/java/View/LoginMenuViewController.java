package View;

import Controller.LoginMenuController;
import Model.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginMenuViewController {
    public PasswordField password;
    @FXML
    private TextField username;
    public void login(MouseEvent mouseEvent) throws Exception {
        switch (LoginMenuController.login(username.getText(), password.getText())) {
            case SUCCESS:
                new MainMenu().start(LoginMenu.stage);
                break;
            case USERNAME_DOES_NOT_EXIST:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("login error");
                alert.setContentText("username does not exist");
                alert.showAndWait();
                break;
            case WRONG_PASSWORD:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("login error");
                alert2.setContentText("password does not match");
                alert2.showAndWait();
                break;
        }
    }

    public void signup(MouseEvent mouseEvent) throws Exception {
        switch (LoginMenuController.signup(username.getText(), password.getText())) {
            case SUCCESS:
                new MainMenu().start(LoginMenu.stage);
                break;
            case USERNAME_EXIST:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("signup error");
                alert.setContentText("username exists");
                alert.showAndWait();
                break;
        }
    }

    public void reset(MouseEvent mouseEvent) {
        username.setText("");
        password.setText("");
    }

    public void guest(MouseEvent mouseEvent) throws Exception {
        //TODO bere to controller
        new MainMenu().start(LoginMenu.stage);
    }
}
