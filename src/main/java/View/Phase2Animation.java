package View;

import Model.LittleCircles;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.security.spec.RSAOtherPrimeInfo;

import static java.lang.System.currentTimeMillis;

public class Phase2Animation extends Transition {
    private LittleCircles littleCircle;
    private Timeline timeline;
    private Long time;

    public Phase2Animation(LittleCircles littleCircle) {
        this.littleCircle = littleCircle;
        this.setCycleDuration(Duration.seconds(1));
        this.setCycleCount(-1);
        this.time = currentTimeMillis();
        this.setAutoReverse(true);
    }
    protected void interpolate(double v) {
        int number = 1;
        if (0 <= v && v <= 0.5) number = 1;
        else if (0.5 < v && v <= 1) number = 2;

        if (number == 1) {
            double r = this.littleCircle.getRadius() + 0.1;
            double finalRadius = 10;

            while (r < finalRadius) {
                this.littleCircle.setRadius(r);
                r = this.littleCircle.getRadius() + 0.1;
            }
        }
        else {
            double r = this.littleCircle.getRadius() - 0.1;
            double finalRadius = 8;

            while (r > finalRadius) {
                this.littleCircle.setRadius(r);
                r = this.littleCircle.getRadius() - 0.1;
            }
        }

    }
}
