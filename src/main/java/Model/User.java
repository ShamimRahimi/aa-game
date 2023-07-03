package Model;
import javafx.scene.image.Image;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;

public class User {
    private String username;
    private String password;
    private String avatarPath;
    private int score;
    private ArrayList<Integer> levelScores;
    private ArrayList<Integer> levelTimes;
    private Long allTimePassed;

    public User(String username, String password, String avatarPath) {
        this.username = username;
        this.password = password;
        this.avatarPath = avatarPath;
        this.score = 0;
        this.levelScores = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.levelScores.add(0);
        }
        this.allTimePassed = 0L;
        this.levelTimes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.levelTimes.add(0);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        Database.saveUsers();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        Database.saveUsers();
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
        Database.saveUsers();
    }

    public int getScore() {
        return score;
    }

    public void changeScore(int score, int level) {
        this.score += score;
        this.levelScores.set(level-1, this.levelScores.get(level-1) + score);
        Database.saveUsers();
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getLevelScores1() {
        return levelScores.get(0);
    }
    public int getLevelScores2() {
        return levelScores.get(1);
    }
    public int getLevelScores3() {
        return levelScores.get(2);
    }

    public int getLevelTimes1() {
        return levelTimes.get(0);
    }
    public int getLevelTimes2() {
        return levelTimes.get(1);
    }
    public int getLevelTimes3() {
        return levelTimes.get(2);
    }

    public void setLevelScores(ArrayList<Integer> levelScores) {
        this.levelScores = levelScores;
    }

    public Long getAllTimePassed() {
        return allTimePassed;
    }

    public void changeAllTimePassed(Long allTimePassed, int level) {
        this.allTimePassed += allTimePassed;
        this.levelTimes.set(level-1, (int) (this.levelTimes.get(level-1) + allTimePassed));
        Database.saveUsers();
    }
}
