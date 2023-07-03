package Controller;

import Model.Database;
import Model.LittleCircles;
import Model.MiddleCircle;
import View.*;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import static View.GameMenu.gamePane;
import static View.GameMenu.playersNumber;
import static View.LoginMenu.stage;
import static View.Messages.GAME_OVER;
import static View.Messages.SUCCESS;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static java.lang.System.currentTimeMillis;

public class GameController {
    public static int indexPause;
    private static int count = 0;
    private static int count2 = 0;
    private static int phase = 1;
    public static int prevScore;
    private static MediaPlayer mediaPlayer1;
    private static MediaPlayer mediaPlayer2;
    public static boolean stopFreeze = false;
    private static int indexChoose;
    public ChoiceBox chooseSong;
    private static Text timerText;
    private static TimerAnimation timerAnimation;
    private static Long pauseStarted;
    public static boolean half = false;

    public static Messages moveCircle(ArrayList<LittleCircles> littleCircles1, Pane gamePane,
                                      ProgressBar progressBar, Text ballsRemaining, Text score)
            throws NonInvertibleTransformException, IOException {
        LittleCircles littleCircles;
        if(count + count2 < littleCircles1.size()) {
            if (playersNumber == 1 || (playersNumber == 2 && !half)) littleCircles = littleCircles1.get(count);
            else littleCircles = littleCircles1.get(Database.getCurrentGame().getBallsCount()/2  + count2);;

            double endY = sqrt(110*110 - (300-littleCircles.getCenterX())*(300-littleCircles.getCenterX()));
            double startY = endY * 70 / 110;
            double startX = 0;
            if (littleCircles.getCenterX() <= 300) startX = 70 * (300 - littleCircles.getCenterX()) / 110;
            else startX = -70 * (littleCircles.getCenterX() - 300) / 110;

            Line line = null;
            if (playersNumber == 1) {
                line = new Line(
                        300 - startX, startY + 140, littleCircles.getCenterX(), endY + 140);
                gamePane.getChildren().add(line);
                littleCircles.setCenterX(littleCircles.getCenterX());
                littleCircles.setCenterY(endY + 140);
            }
            else {
                if (!half) {
                    line = new Line(
                            300 - startX, startY + 270, littleCircles.getCenterX(), endY + 270);
                    gamePane.getChildren().add(line);
                    littleCircles.setCenterX(littleCircles.getCenterX());
                    littleCircles.setCenterY(endY + 270);
                }
                else {
                    line = new Line(
                            300 - startX, 270 - startY, littleCircles.getCenterX(), 270 - endY);
                    gamePane.getChildren().add(line);
                    littleCircles.setCenterX(littleCircles.getCenterX());
                    littleCircles.setCenterY(270 - endY);
                }
            }


            for (int i = 1; i < gamePane.getChildren().size(); i++) {
                if (littleCircles.getBoundsInParent().intersects(gamePane.getChildren().get(i).getBoundsInParent())) {
                    if (gamePane.getChildren().get(i).equals(littleCircles)) continue;
                    if (gamePane.getChildren().get(i).equals(line)) continue;
                    Text text = new Text("Game Over!");
                    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                    text.setFill(Color.WHITE);
                    text.setX(205);
                    if (playersNumber == 1) text.setY(280);
                    if (playersNumber == 2) text.setY(410);
                    gamePane.getChildren().add(text);

                    String time = timerText.getText();
                    Long time2 = (currentTimeMillis() - Database.getCurrentGame().getTimeStarted())/1000;
                    Text text3 = new Text("in " + time);
                    text3.setFill(Color.WHITE);
                    text3.setX(270);
                    if (playersNumber == 1) text3.setY(300);
                    if (playersNumber == 2) text3.setY(430);
                    gamePane.getChildren().add(text3);

                    if (Database.getLoggedInUser() != null) {
                        Text text2 = new Text("your score: " + Database.getLoggedInUser().getScore());
                        text2.setFill(Color.WHITE);
                        text2.setX(265);
                        if (playersNumber == 1) text2.setY(315);
                        if (playersNumber == 2) text2.setY(445);
                        gamePane.getChildren().add(text2);
                        Database.getLoggedInUser().changeAllTimePassed(time2, Database.getCurrentGame().getHardshipLevel());
                    }

                    Text back = new Text("press escape to go back");
                    back.setFill(Color.WHITE);
                    back.setX(240);
                    if (playersNumber == 1) back.setY(330);
                    if (playersNumber == 2) back.setY(460);
                    gamePane.getChildren().add(back);
                    phase = 1;
                    count = 0;
                    count2 = 0;
                    BallsAnimation.setPhase(1);
                    Background background = new Background(new BackgroundFill(Color.DARKRED,  null, null));
                    gamePane.setBackground(background);
                    Database.saveUsers();
                    MainMenuViewController.menu = "game";
                    MainMenuViewController.scoreBoardShow();
                    stopEverything();
                    return GAME_OVER;
                }
            }
            handleShooting(gamePane, littleCircles1);

            BallsAnimation ballsAnimation = new BallsAnimation(gamePane, line,
                    littleCircles, Database.getCurrentGame().getHardshipLevel(), Database.getCurrentGame().getFrozenModeTimer());
            progressBar.setProgress(progressBar.getProgress() + 0.2);
            if ((double) (count + count2) / Database.getCurrentGame().getBallsCount() >= 0.75) {
                Background background = new Background(new BackgroundImage(new Image("khoob.png"), null, null, null, null));
                gamePane.setBackground(background);
                phase = 4;
                BallsAnimation.setPhase(4);
                BallsAnimation.count+=1;
            }
            else if ((double) (count + count2) / Database.getCurrentGame().getBallsCount() >= 0.5) {
                Background background = new Background(new BackgroundImage(new Image("nesf.png"), null, null, null, null));
                gamePane.setBackground(background);
                phase = 3;
                BallsAnimation.setPhase(3);
                BallsAnimation.count+=1;
            }
            else if ((double) (count + count2) / Database.getCurrentGame().getBallsCount() >= 0.25) {
                phase = 2;
                BallsAnimation.setPhase(2);
                BallsAnimation.count+=1;
            }
            else {
                phase = 1;
                BallsAnimation.setPhase(1);
            }
            if (Database.getLoggedInUser() != null) {
//                int prevScore = Database.getLoggedInUser().getScore();
                Database.getLoggedInUser().changeScore(phase, Database.getCurrentGame().getHardshipLevel());
                score.setText("your score : " + (Database.getLoggedInUser().getScore() - prevScore));
            }

            ballsAnimation.play();
            playAudio();
            if (playersNumber == 1 || (playersNumber == 2 && !half)) count++;
            else count2++;

            ballsRemaining.setText("number of balls remaining: " + (Database.getCurrentGame().getBallsCount() - count - count2));
            if ((count + count2) == Database.getCurrentGame().getBallsCount()) {
                Text text = new Text("You won!");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                text.setFill(Color.WHITE);
                text.setX(230);
                if (playersNumber == 1) text.setY(280);
                if (playersNumber == 2) text.setY(410);

                String time = timerText.getText();
                Long time2 = (currentTimeMillis() - Database.getCurrentGame().getTimeStarted())/1000;
                if (Database.getLoggedInUser() != null) {
                    Database.getLoggedInUser().changeAllTimePassed(time2, Database.getCurrentGame().getHardshipLevel());
                }
                Text text3 = new Text("in " + time);
                text3.setFill(Color.WHITE);
                text3.setX(270);
                if (playersNumber == 1) text3.setY(300);
                if (playersNumber == 2) text3.setY(430);
                gamePane.getChildren().add(text3);

                if (Database.getLoggedInUser() != null) {
                    Text text2 = new Text("your score: " + Database.getLoggedInUser().getScore());
                    text2.setFill(Color.WHITE);
                    text2.setX(265);
                    if (playersNumber == 1) text2.setY(315);
                    if (playersNumber == 2) text2.setY(445);
                    gamePane.getChildren().add(text2);
                }
                gamePane.getChildren().add(text);

                Text back = new Text("press escape to go back");
                back.setFill(Color.WHITE);
                back.setX(240);
                if (playersNumber == 1) back.setY(330);
                if (playersNumber == 2) back.setY(460);
                gamePane.getChildren().add(back);
                Background background = new Background(new BackgroundFill(Color.GREEN, null, null));
                gamePane.setBackground(background);
                phase = 1;
                count = 0;
                count2 = 0;
                BallsAnimation.setPhase(1);
                Database.saveUsers();
                MainMenuViewController.menu = "game";
                MainMenuViewController.scoreBoardShow();
                stopEverything();
                return SUCCESS;
            }
        }
        return null;
    }

    private static void handleShooting(Pane gamePane, ArrayList<LittleCircles> littleCircles1) {
        if (playersNumber == 1) {
            for (int i = count + 1; i < littleCircles1.size(); i++) {
                ShootingAnimation shootingAnimation = new ShootingAnimation(gamePane, littleCircles1.get(i));
                shootingAnimation.play();
            }
        }
        else {
            if (!half) {
                for (int i = count + 1; i < littleCircles1.size() / 2; i++) {
                    ShootingAnimation shootingAnimation = new ShootingAnimation(gamePane, littleCircles1.get(i));
                    shootingAnimation.play();
                }
            }
            else {
                for (int i = count2 + 1; i < littleCircles1.size() / 2; i++) {
                    ShootingAnimation shootingAnimation = new ShootingAnimation(gamePane,
                            littleCircles1.get(i + Database.getCurrentGame().getBallsCount()/2));
                    shootingAnimation.play();
                }
            }
        }
    }

    private static void stopEverything() {
        count = 0;
        count2 = 0;
        phase = 1;
        BallsAnimation.setPhase(1);
        if (mediaPlayer1 != null) mediaPlayer1.stop();
        if (mediaPlayer2 != null) mediaPlayer2.stop();
        BallsAnimation.stopSho(true);
        GameMenu.mute = false;
        TimerAnimation.stopTrue(true);
    }

    private static void playAudio() {
        Media media = new Media(GameController.class.getClassLoader().getResource("balls.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer1 = mediaPlayer;
        mediaPlayer.setAutoPlay(true);
    }

    public static void startFrozenMode(Pane gamePane, ProgressBar progressBar, Timeline timeline) {
        if (progressBar.getProgress() >= 1) {
            BallFreeze ballFreeze = new BallFreeze((MiddleCircle) gamePane.getChildren().get(0), Database.getCurrentGame().getFrozenModeTimer());
            ballFreeze.play();
            BallsAnimation.setFreeze(true);
        }
        progressBar.setProgress(0);
    }

    public static void moveFirstCircles(LittleCircles littleCircles, Line line,  Pane gamePane, int i) {
            littleCircles.setCenterX(littleCircles.getCenterX());
            if (GameMenu.playersNumber == 1) littleCircles.setCenterY(250);
            if (GameMenu.playersNumber == 2) littleCircles.setCenterY(380);
            BallsAnimation ballsAnimation = new BallsAnimation(gamePane, line,
                    littleCircles, Database.getCurrentGame().getHardshipLevel(),
                    Database.getCurrentGame().getFrozenModeTimer());
            switch (Database.getCurrentGame().getMapId()) {
                case "1":
                    ballsAnimation.setDelay(Duration.seconds(i * 0.1));
                    break;
                case "3":
                    ballsAnimation.setDelay(Duration.seconds(i * 0.6));
                    break;
                default:
                    ballsAnimation.setDelay(Duration.seconds(i * 0.3));
                    break;
            }

            ballsAnimation.play();
    }

    public static void moveBalls(Pane gamePane, ArrayList<LittleCircles> littleCircles, String direction) {

        if (phase != 4) return;

        if (direction.equals("right")) {
            for (int i = count; i < littleCircles.size(); i++) {
                if (littleCircles.get(i).getCenterX() + 10 != 600) {
                    littleCircles.get(i).setCenterX(littleCircles.get(i).getCenterX() + 10);
                }
            }
        }

        if (direction.equals("left")) {
            for (int i = count; i < littleCircles.size(); i++) {
                if (littleCircles.get(i).getCenterX() - 10 != 0) {
                    littleCircles.get(i).setCenterX(littleCircles.get(i).getCenterX() - 10);
                }
            }
        }
    }

    public static void playFirstAudion() {
        Media media = new Media(GameController.class.getClassLoader().getResource("2.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer2 = mediaPlayer;
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setAutoPlay(true);
    }

    public static void pause() {
        BallsAnimation.stopSho(true);
        timerAnimation.pause();
        pauseStarted = currentTimeMillis();
    }

    public static void gameOver() throws IOException {
        Text text = new Text("Game Over!");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        text.setFill(Color.WHITE);
        text.setX(205);
        if (playersNumber == 1) text.setY(280);
        if (playersNumber == 2) text.setY(410);
        gamePane.getChildren().add(text);

        String time = timerText.getText();
        Long time2 = (currentTimeMillis() - Database.getCurrentGame().getTimeStarted())/1000;
        Text text3 = new Text("in " + time);
        text3.setFill(Color.WHITE);
        text3.setX(270);
        if (playersNumber == 1) text3.setY(300);
        if (playersNumber == 2) text3.setY(430);
        gamePane.getChildren().add(text3);

        if (Database.getLoggedInUser() != null) {
            Text text2 = new Text("your score: " + Database.getLoggedInUser().getScore());
            text2.setFill(Color.WHITE);
            text2.setX(265);
            if (playersNumber == 1) text2.setY(315);
            if (playersNumber == 2) text2.setY(445);
            gamePane.getChildren().add(text2);
            Database.getLoggedInUser().changeAllTimePassed(time2, Database.getCurrentGame().getHardshipLevel());
        }

        Text back = new Text("press escape to go back");
        back.setFill(Color.WHITE);
        back.setX(240);
        if (playersNumber == 1) back.setY(330);
        if (playersNumber == 2) back.setY(460);
        gamePane.getChildren().add(back);
        phase = 1;
        count = 0;
        count2 = 0;
        BallsAnimation.setPhase(1);
        Background background = new Background(new BackgroundFill(Color.DARKRED,  null, null));
        gamePane.setBackground(background);
        Database.saveUsers();
        MainMenuViewController.menu = "game";
        MainMenuViewController.scoreBoardShow();
        stopEverything();
    }

    public void back(MouseEvent mouseEvent) {
        BallsAnimation.stopSho(false);
        gamePane.getChildren().remove(indexPause);
        timerAnimation.setDelaySeconds(currentTimeMillis() - pauseStarted);
        timerAnimation.play();
    }

    public void changeSong(MouseEvent mouseEvent) {
        URL url = getClass().getClassLoader().getResource("chooseSong.fxml");
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
        GameController.indexChoose = gamePane.getChildren().indexOf(pane);
    }

    public void mute(MouseEvent mouseEvent) {
       mediaPlayer2.stop();
    }

    public void exit(MouseEvent mouseEvent) throws Exception {
        stopEverything();
        Database.setCurrentGame(null);
        new MainMenu().start(stage);
    }

    public void restart(MouseEvent mouseEvent) throws Exception {
        stopEverything();
        Database.getCurrentGame().setTimeStarted(currentTimeMillis());
        timerAnimation.setDelaySeconds(0);
        new GameMenu().start(stage);
    }

    public void saveGame(MouseEvent mouseEvent) {
    }

    public void pause1(MouseEvent mouseEvent) {
        mediaPlayer2.stop();
    }

    public void play1(MouseEvent mouseEvent) {
        mediaPlayer2.stop();
        mediaPlayer2 = new MediaPlayer(new Media(GameController.class.getClassLoader().getResource("1.wav").toString()));
        mediaPlayer2.setVolume(0.1);
        mediaPlayer2.play();
    }

    public void chooseSong(MouseEvent mouseEvent) {
        chooseSong.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                Database.getCurrentGame().setMusicNumber(Integer.toString(new_value.intValue() + 1));
            }
        });
    }

    public void pause3(MouseEvent mouseEvent) {
        mediaPlayer2.stop();
    }

    public void pause2(MouseEvent mouseEvent) {
        mediaPlayer2.stop();

    }

    public void play2(MouseEvent mouseEvent) {
        mediaPlayer2.stop();
        mediaPlayer2 = new MediaPlayer(new Media(GameController.class.getClassLoader().getResource("2.wav").toString()));
        mediaPlayer2.setVolume(0.1);
        mediaPlayer2.play();
    }

    public void play3(MouseEvent mouseEvent) {
        mediaPlayer2.stop();
        mediaPlayer2 = new MediaPlayer(new Media(GameController.class.getClassLoader().getResource("3.wav").toString()));
        mediaPlayer2.setVolume(0.1);
        mediaPlayer2.play();
    }

    public void chooseSongDone(MouseEvent mouseEvent) {
        mediaPlayer2.stop();
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
        gamePane.getChildren().remove(indexChoose);
        mediaPlayer2 = new MediaPlayer(new Media(
                GameController.class.getClassLoader().getResource(
                        Database.getCurrentGame().getMusicNumber() + ".wav").toString()));
        mediaPlayer2.setVolume(0.1);
        mediaPlayer2.play();
    }

    public static void setTimer(Pane gamePane) {
        Text text = new Text("00:00");
        timerText = text;
        gamePane.getChildren().add(text);
        text.setX(560);
        text.setY(540);
        TimerAnimation timer = new TimerAnimation(Database.getCurrentGame().getTimeStarted(), text);
        timerAnimation = timer;
        timer.play();
    }
}
