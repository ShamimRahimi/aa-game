package View;

import Model.LittleCircles;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static Controller.GameController.half;
import static View.GameMenu.playersNumber;

public class ShootingAnimation extends Transition {
    private Pane pane;
    private LittleCircles littleCircle;

    public ShootingAnimation(Pane pane, LittleCircles littleCircle) {
        this.pane = pane;
        this.littleCircle = littleCircle;
        this.setCycleDuration(Duration.millis(75));
        this.setCycleCount(1);
    }

    @Override
    protected void interpolate(double frac) {
        double finalY;
        double y;
        if (playersNumber == 1 || (playersNumber == 2 && !half)) {
            finalY = littleCircle.getCenterY() - 30;
            y = littleCircle.getCenterY() - 5;
        }
        else {
            finalY = littleCircle.getCenterY() + 30;
            y = littleCircle.getCenterY() + 5;
        }

        if(y == finalY) {
            this.stop();
        }
        littleCircle.setCenterY(y);
    }
}
