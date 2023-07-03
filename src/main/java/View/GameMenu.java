package View;

import Controller.GameController;
import Model.Database;
import Model.Game;
import Model.LittleCircles;
import Model.MiddleCircle;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static View.LoginMenu.stage;

public class GameMenu extends Application {
    public static Timeline timeline;
    public static Pane gamePane;
    static int i = 0;
    public static boolean mute;
    public static int playersNumber;
    @Override
    public void start(Stage primaryStage) throws Exception {
        BallsAnimation.count = 0;
        if(Database.getCurrentGame() == null) Database.setCurrentGame(new Game());
        else Database.getCurrentGame().start();
        playersNumber = Database.getCurrentGame().getPlayersNumber();
        URL url = getClass().getClassLoader().getResource("Game.fxml");
        Pane gamePane = FXMLLoader.load(url);
        this.gamePane = gamePane;
        MiddleCircle middleCircle = null;
        if (playersNumber == 1) middleCircle = new MiddleCircle(140);
        if (playersNumber == 2) middleCircle = new MiddleCircle(270);
        gamePane.getChildren().add(middleCircle);
        HBox hBox = new HBox();
        Text text = new Text(360, 50, "frozen mode");
        text.setFill(Color.BLACK);
        ProgressBar progressBar = new ProgressBar(0);
        Text ballsRemaining = new Text("number of balls remaining: " + Database.getCurrentGame().getBallsCount());
        ballsRemaining.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        ballsRemaining.setX(4);
        ballsRemaining.setY(35);
        Text score = new Text(" ");
        if (Database.getLoggedInUser() != null) {
            GameController.prevScore =  Database.getLoggedInUser().getScore();
             score = new Text("your score : 0");
            score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
            score.setX(4);
            score.setY(55);
        }
        if (!mute) playAudio();
        setBallsAndLines(gamePane, middleCircle);
        createHBox(hBox, text, progressBar);
        if (playersNumber == 1) createLittleCircles1(gamePane, progressBar, ballsRemaining, score);
        if (playersNumber == 2) createLittleCircles2(gamePane, progressBar, ballsRemaining, score);

        ImageView pause = new ImageView();
        addPauseButton(gamePane, pause);
        TimerAnimation.stopTrue(false);
        GameController.setTimer(gamePane);
        gamePane.getChildren().addAll(pause);
        gamePane.getChildren().add(ballsRemaining);
        gamePane.getChildren().add(score);
        gamePane.getChildren().add(hBox);
        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        for (int i = 0; i < Database.getCurrentGame().getBallsCount(); i++) {
            gamePane.getChildren().get(i+11).requestFocus();
        }
        Background background = new Background(new BackgroundImage(new Image("hichi.png"),
                null, null, null, null));
        gamePane.setBackground(background);
        stage.show();
    }

    private void createLittleCircles2(Pane gamePane, ProgressBar progressBar, Text ballsRemaining, Text score) {
        ArrayList<LittleCircles> littleCircles = new ArrayList<>();
        for(int i = 0; i < Database.getCurrentGame().getBallsCount(); i++) {
            if (i < Database.getCurrentGame().getBallsCount() / 2) littleCircles.add(
                    new LittleCircles(300, 404 + 30*i, Database.getCurrentGame().getBallsCount() - i));
            else littleCircles.add(new LittleCircles(
                    300, 136 - 30*(i - Database.getCurrentGame().getBallsCount()/2), Database.getCurrentGame().getBallsCount() - i));
            int finalI = i;
            littleCircles.get(i).setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String keyName = event.getCode().getName();
                    if (keyName.equals(Database.getCurrentGame().getShootBallsKey())) {
                        GameController.half = false;
                        try {
                            GameController.moveCircle(littleCircles, gamePane, progressBar, ballsRemaining, score);
                        } catch (NonInvertibleTransformException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (keyName.equals("Enter")) {
                        try {
                            GameController.half = true;
                            GameController.moveCircle(littleCircles, gamePane, progressBar, ballsRemaining, score);
                        } catch (NonInvertibleTransformException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (keyName.equals("Tab")) GameController.startFrozenMode(gamePane, progressBar, timeline);
                    if (keyName.equals(Database.getCurrentGame().getMoveRightKey())) {
                        GameController.moveBalls(gamePane, littleCircles, "right");
                    }
                    if (keyName.equals(Database.getCurrentGame().getMoveLeftKey())) {
                        GameController.moveBalls(gamePane, littleCircles, "left");
                    }
                    if (keyName.equals("Esc")) {
                        try {
                            new MainMenu().start(stage);
                            Database.setCurrentGame(null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            gamePane.getChildren().add(littleCircles.get(i));
        }
    }

    private void addPauseButton(Pane gamePane, ImageView pause) {
        Image image = new Image("pause2.png");
        pause.setFocusTraversable(false);
        pause.setImage(image);
        pause.setX(554);
        pause.setY(2);
        pause.setFitHeight(40);
        pause.setFitWidth(43);
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                URL url = getClass().getClassLoader().getResource("PauseMenu.fxml");
                Pane pane;
                try {
                    pane = FXMLLoader.load(url);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pane.setLayoutX(170);
                pane.setLayoutY(100);
                pane.setBackground(new Background(new BackgroundImage(
                        new Image("background.png"), null, null, null, null)));
                gamePane.getChildren().add(pane);
                GameController.indexPause = gamePane.getChildren().indexOf(pane);
                GameController.pause();
            }
        });
    }

    private void playAudio() {
        GameController.playFirstAudion();
    }

    private void setBallsAndLines(Pane gamePane, MiddleCircle middleCircle) throws InterruptedException {
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<LittleCircles> littleCircles = new ArrayList<>();

        Line line;
        LittleCircles littleCircles1;

        for (int j = 0; j < 5; j++) {
            lines.add(new Line(300, middleCircle.getCenterY() + 70, 300, middleCircle.getCenterY()+110));
            if (playersNumber == 1) littleCircles.add(new LittleCircles(
                    300, 274 + 30, Database.getCurrentGame().getBallsCount() + 5 - j));
            else littleCircles.add(new LittleCircles(
                    300, 404 + 30, Database.getCurrentGame().getBallsCount() + 5 - j));
            gamePane.getChildren().add(lines.get(j));
            gamePane.getChildren().add(littleCircles.get(j));
            GameController.moveFirstCircles(littleCircles.get(j), lines.get(j), gamePane, j);
        }
    }

    private void createHBox(HBox hBox, Text score, ProgressBar progressBar) {
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(score);
        hBox.setSpacing(50);
        hBox.getChildren().add(progressBar);
    }

    private void createLittleCircles1(Pane gamePane, ProgressBar progressBar, Text ballsRemaining, Text score) {
        ArrayList<LittleCircles> littleCircles = new ArrayList<>();
        for(int i = 0; i < Database.getCurrentGame().getBallsCount(); i++) {
            littleCircles.add(new LittleCircles(300, 274 + 30*i, Database.getCurrentGame().getBallsCount() - i));
            littleCircles.get(i).setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String keyName = event.getCode().getName();
                    if (keyName.equals(Database.getCurrentGame().getShootBallsKey())) {
                        try {
                            GameController.moveCircle(littleCircles, gamePane, progressBar, ballsRemaining, score);
                        } catch (NonInvertibleTransformException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (keyName.equals("Tab")) GameController.startFrozenMode(gamePane, progressBar, timeline);
                    if (keyName.equals(Database.getCurrentGame().getMoveRightKey())) {
                        GameController.moveBalls(gamePane, littleCircles, "right");
                    }
                    if (keyName.equals(Database.getCurrentGame().getMoveLeftKey())) {
                        GameController.moveBalls(gamePane, littleCircles, "left");
                    }
                    if (keyName.equals("Esc")) {
                        try {
                            new MainMenu().start(stage);
                            Database.setCurrentGame(null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            gamePane.getChildren().add(littleCircles.get(i));
        }
    }
}