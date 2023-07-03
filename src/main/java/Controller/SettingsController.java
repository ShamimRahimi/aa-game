package Controller;

import Model.Database;

public class SettingsController {

    public static void setHardship(int i) {
        Database.getCurrentGame().setHardshipLevel(i);
    }

    public static void setLanguage(String language) {
        Database.getCurrentGame().setLanguage(language);
    }

    public static void chooseBallsCount(int i) {
        Database.getCurrentGame().setBallsCount(i);
    }

    public static void chooseMapGame(String text) {
        Database.getCurrentGame().setMapId(text);
    }

    public static void choosePlayersNumber(int i) {
        Database.getCurrentGame().setPlayersNumber(i+1);
    }
}
