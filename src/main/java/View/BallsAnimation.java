package View;

import Controller.GameController;
import Model.LittleCircles;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import static View.GameMenu.playersNumber;
import static java.lang.System.currentTimeMillis;

public class BallsAnimation extends Transition {
    private Pane pane;
    private Line line;
    private LittleCircles littleCircles;
    private Rotate rotate;
    private int hardshipLevel;
    private Timeline timeline;
    private int freezeTime;
    private static int phase = 1;
    private static boolean freeze = false;
    static long time;
    private FadeTransition circleFadeTransition;
    private FadeTransition lineFadeTransition;
    private Phase2Animation phase2Animation;
    private ScaleTransition scaleTransition;
    private static boolean stop;
    public static int count = 0;
    public static long phase2Start;
    public BallsAnimation(Pane pane, Line line, LittleCircles littleCircles, int hardshipLevel, int freezeTime) {
        this.pane = pane;

        this.line = line;
        this.littleCircles = littleCircles;
        this.hardshipLevel = hardshipLevel;
        if (playersNumber == 1) this.rotate = new Rotate(0, 300, 140);
        else this.rotate = new Rotate(0, 300, 270);
        this.freezeTime = freezeTime;

        this.circleFadeTransition = new FadeTransition(Duration.seconds(1), this.littleCircles);
        this.circleFadeTransition.setFromValue(10);
        this.circleFadeTransition.setToValue(0);
        this.circleFadeTransition.setCycleCount(-1);
        circleFadeTransition.setAutoReverse(true);

        this.lineFadeTransition = new FadeTransition(Duration.seconds(1), this.line);
        this.lineFadeTransition.setFromValue(10);
        this.lineFadeTransition.setToValue(0);
        this.lineFadeTransition.setCycleCount(-1);
        lineFadeTransition.setAutoReverse(true);

        this.phase2Animation = new Phase2Animation(this.littleCircles);
        this.phase2Animation.setAutoReverse(true);
        this.phase2Animation.setCycleCount(-1);

        littleCircles.getTransforms().add(rotate);
        line.getTransforms().add(rotate);
        Timeline rotationAnimation = new Timeline();
        this.timeline = rotationAnimation;
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        rotationAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
                new KeyValue(this.rotate.angleProperty(), 360)));
        this.setCycleDuration(Duration.INDEFINITE);

        this.stop = false;
    }

    public static void setPhase(int phase) {
        BallsAnimation.phase = phase;
    }

    public static void stopSho(boolean bool) {
        stop = bool;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public static void setFreeze(boolean freeze) {
        BallsAnimation.freeze = freeze;
        time = currentTimeMillis();
    }

    @Override
    protected void interpolate(double frac) {
        timeline.setRate((double) hardshipLevel / 3);
        timeline.play();

        if (phase >=2 ) {
            if (count % 2 == 0) timeline.setRate((double) -hardshipLevel / 3);
            if (count % 2 == 1) timeline.setRate((double) hardshipLevel / 3);

            this.phase2Animation.play();
        }

        if (phase >= 3) {
            this.circleFadeTransition.play();
            this.lineFadeTransition.play();
        }

        if (phase == 4) {

        }

        if (stop) {
            this.timeline.pause();
            this.circleFadeTransition.stop();
            this.lineFadeTransition.stop();
        }

        if (freeze) {
            double prevRate = timeline.getRate();
            timeline.setRate(timeline.getRate() / 2);
            if (currentTimeMillis() - time >= (this.freezeTime * 1000L)) {
                timeline.setRate(prevRate);
                GameController.stopFreeze = true;
            }
        }
    }
}
