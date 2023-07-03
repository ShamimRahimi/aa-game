package Model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;

public class LittleCircles extends Circle {
    public LittleCircles(int x, int y, int number) {
        super(x, y, 8);
        String url;
        if (Database.getCurrentGame().getPlayersNumber() == 2) {
            if (number > Database.getCurrentGame().getBallsCount() / 2) url = "image" + number + ".jpg";
            else url = "image" + number + "2.png";
        }
        else {
            url = "image" + number + ".jpg";
        }
        this.setFill(new ImagePattern(new Image(url)));
    }
}
