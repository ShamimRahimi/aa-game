package View;

import Controller.SettingsController;
import Model.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

import static View.LoginMenu.stage;

public class SettingsViewController {
    public ChoiceBox chooseHardship;
    public ChoiceBox chooseLanguage;
    public ChoiceBox chooseBallsCount;
    public TextField mapId;
    public Button muteButton;
    public ChoiceBox playersNumber;
    public TextField shootBallsKey;
    public TextField moveRightKey;
    public TextField moveLeftKey;

    public void mute(MouseEvent mouseEvent) {
        if (muteButton.getText().equals("mute")) {
            muteButton.setText("unmute");
            GameMenu.mute = true;
        }
        else {
            muteButton.setText("mute");
            GameMenu.mute = false;
        }
    }

    public void BWTheme(MouseEvent mouseEvent) {

    }

    public void chooseHardship(MouseEvent mouseEvent) {
        chooseHardship.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                SettingsController.setHardship(new_value.intValue());
            }
        });
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws Exception {
        setKeys();
        new MainMenu().start(stage);
    }

    public void chooseLanguage(MouseEvent mouseEvent) {
        chooseLanguage.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                SettingsController.setLanguage(new_value.toString());
            }
        });
    }

    public void chooseBallsCount(MouseEvent mouseEvent) {
        chooseBallsCount.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                SettingsController.chooseBallsCount(new_value.intValue() * 4);
            }
        });
    }

    public void chooseGameMap(MouseEvent mouseEvent) throws IOException {
        URL url = getClass().getClassLoader().getResource("GameMaps.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        SettingsController.chooseMapGame(mapId.getText());
        URL url = getClass().getClassLoader().getResource("Settings.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void choosePlayersNumber(MouseEvent mouseEvent) {
        playersNumber.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                SettingsController.choosePlayersNumber(new_value.intValue());
            }
        });
    }

    public void setKeys() {
        if (!shootBallsKey.getText().equals("")) Database.getCurrentGame().setShootBallsKey(shootBallsKey.getText());
        if (!moveLeftKey.getText().equals("")) Database.getCurrentGame().setMoveLeftKey(moveLeftKey.getText());
        if (!moveRightKey.getText().equals("")) Database.getCurrentGame().setMoveRightKey(moveRightKey.getText());
    }
}
