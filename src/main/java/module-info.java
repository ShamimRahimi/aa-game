module aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;
    requires java.desktop;

    exports View;
    opens View to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Model to com.google.gson;
}