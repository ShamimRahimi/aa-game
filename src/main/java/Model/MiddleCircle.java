package Model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Formatter;

public class MiddleCircle extends Circle {

    public MiddleCircle(int centerY){
        super(300, centerY, 70);
        this.setFill(new ImagePattern(new Image("aa.jpg")));
    }
}