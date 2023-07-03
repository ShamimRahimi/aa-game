package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;

public class Database {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser;
    private static Game currentGame;
    private static String[] paths = {("2.jpg"),
            ("3.jpg"),
            ("6.jpg"),
            ("4.jpg"),
            ("5.jpg"),
            ("1.jpg")};
    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/UserDatabase.json");

            String gson = new Gson().toJson(getUsers());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/UserDatabase.json")));

            ArrayList<User> savedUsers;
            savedUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());
            if (savedUsers != null) setUsers(savedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUsers(ArrayList<User> users) {
        Database.users = users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        Database.loggedInUser = loggedInUser;
    }

    public static String[] getPaths() {
        return paths;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Database.currentGame = currentGame;
    }
}
