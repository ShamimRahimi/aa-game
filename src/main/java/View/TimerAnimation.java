package View;

import Controller.GameController;
import javafx.animation.Transition;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

import static java.lang.System.currentTimeMillis;

public class TimerAnimation extends Transition {
    private Long timeStarted;
    private int seconds;
    private int minutes;
    private Text timer;
    private static boolean stop = false;
    private static long delaySeconds;

    public TimerAnimation(Long timeStarted, Text timer) {
        this.timeStarted = timeStarted;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
        this.timer = timer;
        delaySeconds = 0;
    }

    public static void stopTrue(boolean b) {
        stop = b;
    }

    public void setDelaySeconds(long l) {
        delaySeconds += l;
    }

    @Override
    protected void interpolate(double frac) {
        if (!stop) {
            seconds = (int) ((currentTimeMillis() - timeStarted - delaySeconds) / 1000) % 60;
            minutes = (int) ((currentTimeMillis() - timeStarted) / 1000) / 60;
            timer.setText(String.format("%02d:%02d", minutes, seconds));
            if (timer.getText().equals("01:30")) {
                System.out.println("yes");
                try {
                    GameController.gameOver();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.stop();
            }
        }
    }

}
