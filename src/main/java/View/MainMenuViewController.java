package View;

import Controller.SettingsController;
import Model.Database;
import Model.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

import static View.LoginMenu.main;
import static View.LoginMenu.stage;

public class MainMenuViewController {
    public static int scoreBoardIndex1;
    public static int scoreBoardIndex2;
    static Pane settingsPane;
    public static String menu;
    public ImageView image;

    public void startNewGame(MouseEvent mouseEvent) throws Exception {
        new GameMenu().start(stage);
    }

    public void resume(MouseEvent mouseEvent) {
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(stage);
    }

    public static void scoreBoardShow() throws IOException {
        URL url = MainMenuViewController.class.getClassLoader().getResource("ScoreBoard.fxml");
        Pane pane = FXMLLoader.load(url);
        Background background = new Background(new BackgroundFill(Color.WHITE,  null, null));
        pane.setBackground(background);
        if (menu.equals("game")) {
            GameMenu.gamePane.getChildren().add(pane);
            int index2 = GameMenu.gamePane.getChildren().indexOf(pane);
            scoreBoardIndex2 = index2;
            GameMenu.gamePane.getChildren().get(index2).setLayoutX(125);
            GameMenu.gamePane.getChildren().get(index2).setLayoutY(50);
        }
        else {
            MainMenu.pane.getChildren().add(pane);
            int index = MainMenu.pane.getChildren().indexOf(pane);
            scoreBoardIndex1 = index;
            MainMenu.pane.getChildren().get(index).setLayoutX(60);
            MainMenu.pane.getChildren().get(index).setLayoutY(15);
        }
        Text text00 = new Text(Controller.MainMenuController.scoreBoard0().get(0));
        text00.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text00.setFill(Color.DARKSEAGREEN);
        text00.setX(10);
        text00.setY(40);
        Text text01 = new Text(Controller.MainMenuController.scoreBoard0().get(1));
        text01.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text01.setX(10);
        text01.setY(160);
        Text text10 = new Text(Controller.MainMenuController.scoreBoard1().get(0));
        text10.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text10.setFill(Color.DARKSEAGREEN);
        text10.setX(100);
        text10.setY(40);
        Text text11 = new Text(Controller.MainMenuController.scoreBoard1().get(1));
        text11.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text11.setX(100);
        text11.setY(180);
        Text text20 = new Text(Controller.MainMenuController.scoreBoard2().get(0));
        text20.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text20.setFill(Color.DARKSEAGREEN);
        text20.setX(190);
        text20.setY(40);
        Text text21 = new Text(Controller.MainMenuController.scoreBoard2().get(1));
        text21.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text21.setX(190);
        text21.setY(180);
        Text text30 = new Text(Controller.MainMenuController.scoreBoard3().get(0));
        text30.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text30.setFill(Color.DARKSEAGREEN);
        text30.setX(280);
        text30.setY(40);
        Text text31 = new Text(Controller.MainMenuController.scoreBoard3().get(1));
        text31.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 10));
        text31.setX(280);
        text31.setY(180);
        pane.getChildren().addAll(text00, text01, text10, text11, text20, text21, text30, text31);
    }

    public void settings(MouseEvent mouseEvent) throws IOException {
        Database.setCurrentGame(new Game());
        URL url = getClass().getClassLoader().getResource("Settings.fxml");
        Pane pane = FXMLLoader.load(url);
        MainMenuViewController.settingsPane = pane;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void exitGame(MouseEvent mouseEvent) {
        stage.close();
    }

    public void back(MouseEvent mouseEvent) {
        if (menu.equals("main")) {
            MainMenu.pane.getChildren().remove(scoreBoardIndex1);
        }
        else {
            GameMenu.gamePane.getChildren().remove(scoreBoardIndex2);
            for (int i = 0; i < Database.getCurrentGame().getBallsCount(); i++) {
                GameMenu.gamePane.getChildren().get(i+11).requestFocus();
            }
        }
    }

    public void scoreBoard(MouseEvent mouseEvent) throws IOException {
        MainMenuViewController.menu = "main";
        scoreBoardShow();
    }
}
