package Controller;

import Model.Database;
import Model.User;
import View.Messages;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

import static Model.Database.getPaths;
import static View.LoginMenu.stage;

public class LoginMenuController {
    public static Messages login(String username, String password) {
        Database.loadUsers();
        if(Database.getUserByUsername(username) == null) return Messages.USERNAME_DOES_NOT_EXIST;
        User user = Database.getUserByUsername(username);
        if(!user.getPassword().equals(password)) return Messages.WRONG_PASSWORD;
        Database.setLoggedInUser(user);
        return Messages.SUCCESS;
    }

    public static Messages signup(String username, String password) {
        Database.loadUsers();
        if(Database.getUserByUsername(username) != null) return Messages.USERNAME_EXIST;
        User user = new User(username, password, getRandomAvatar());
        Database.addUser(user);
        Database.setLoggedInUser(user);
        Database.saveUsers();
        return Messages.SUCCESS;
    }

    private static String getRandomAvatar() {
        int index = (int) (Math.random() * getPaths().length);
        return getPaths()[index];
//        String path = Database.getLoggedInUser().getAvatarPath();
//        File file = new File(path);
//        String localUrl = file.toURI().toURL().toString();
//        Image image = new Image(localUrl, false);
//        imageView.setImage(image);
//        URL url = LoginMenuController.class.getClassLoader().getResource(paths[index]);
//        Image image = new Image(String.valueOf(url));
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        imageView.setX(10);
//        imageView.setY(10);
//        imageView.setFitWidth(575);
//        imageView.setPreserveRatio(true);
//        //Setting the Scene object
//        Group root = new Group(imageView);
//        Scene scene = new Scene(root, 595, 370);
//        stage.setTitle("Displaying Image");
//        stage.setScene(scene);
//        stage.show();
    }
}
