package Controller;

import Model.Database;
import View.Messages;

import java.io.File;
import java.io.IOException;

public class ProfileMenuController {
    public static Messages changeUsername(String contentText) {
        if(Database.getUserByUsername(contentText) != null) return Messages.USERNAME_EXIST;
        if(Database.getLoggedInUser() == null) return Messages.NOT_AVAILABLE_FOR_GUESTS;
        Database.getLoggedInUser().setUsername(contentText);
        return Messages.SUCCESS;
    }

    public static Messages changePassword(String text) {
        if(Database.getLoggedInUser() == null) return Messages.NOT_AVAILABLE_FOR_GUESTS;
        Database.getLoggedInUser().setPassword(text);
        return Messages.SUCCESS;
    }

    public static void logout() {
        Database.setLoggedInUser(null);
    }

    public static void deleteAccount() {
        Database.getUsers().remove(Database.getLoggedInUser());
        Database.saveUsers();
    }

    public static Messages chooseAvatar(int i) {
        if(Database.getLoggedInUser() == null) return Messages.NOT_AVAILABLE_FOR_GUESTS;
        String path = i + ".jpg";
        Database.getLoggedInUser().setAvatarPath(path);
        return Messages.SUCCESS;
    }

    public static Messages chooseAvatarFromFiles(File file) throws IOException {
        if(Database.getLoggedInUser() == null) return Messages.NOT_AVAILABLE_FOR_GUESTS;
        Database.getLoggedInUser().setAvatarPath(file.getAbsolutePath());
        return Messages.SUCCESS;
    }
}
