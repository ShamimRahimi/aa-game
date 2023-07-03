package View;

import Model.MiddleCircle;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class BallFreeze extends Transition {
    private MiddleCircle middleCircle;
    public BallFreeze(MiddleCircle middleCircle, int time) {
        this.middleCircle = middleCircle;
        this.setCycleCount(time * 2);
        this.setCycleDuration(Duration.seconds(0.5));
        this.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                middleCircle.setFill(new ImagePattern(new Image("aa.jpg")));
            }
        });
    }
    @Override
    protected void interpolate(double v) {
        int number = 1;
        if (0 <= v && v <= 0.33) number = 1;
        else if (0.33 < v && v <= 0.66) number = 2;
        else if (0.66 < v && v <= 1) number = 3;

        middleCircle.setFill(new ImagePattern(new Image("freez" + number + ".png")));
    }
}
