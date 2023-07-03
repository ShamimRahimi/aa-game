package Controller;

import Model.Database;
import Model.User;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainMenuController {

    public static ArrayList<String> scoreBoard0() {
        List<User> users = new ArrayList<>();
        users = (ArrayList) Database.getUsers().clone();

        Comparator<User> comp = Comparator.comparingInt(User::getScore).reversed()
                .thenComparing(User::getAllTimePassed);
        Collections.sort(users , comp);

        String first3 = "";
        int rank1 = 1;
        while (rank1 - 1 < users.size() && rank1 <= 3) {
            first3 += rank1 + "- " + users.get(rank1 - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank1 - 1).getScore() + "\n"
                    + "     time: " + users.get(rank1 - 1).getAllTimePassed() + "\n";
            rank1++;
        }

        String res = "";
        int rank = 4;
        while (rank - 1 < users.size() && rank <= 7) {
            res += rank + "- " + users.get(rank - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank - 1).getScore() + "\n"
                    + "     time: " + users.get(rank - 1).getAllTimePassed() + "\n";
            rank++;
        }

        ArrayList<String> khh = new ArrayList<>();
        khh.add(first3);
        khh.add(res);
        return khh;
    }

    public static ArrayList<String> scoreBoard1() {
        List<User> users = new ArrayList<>();
        users = (ArrayList) Database.getUsers().clone();

        Comparator<User> comp = Comparator.comparingInt(User::getLevelScores1).reversed()
                .thenComparing(User::getLevelTimes1);
        Collections.sort(users , comp);

        String res = "";
        String first3 = "";
        first3 += """
                level 1 scores:

                """;

        int rank1 = 1;
        while (rank1 - 1 < users.size() && rank1 <= 3) {
            first3 += rank1 + "- " + users.get(rank1 - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank1 - 1).getLevelScores1() + "\n"
                    + "     time: " + users.get(rank1 - 1).getLevelTimes1() + "\n";
            rank1++;
        }

        int rank = 4;
        while (rank - 1 < users.size() && rank <= 7) {
            res += rank + "- " + users.get(rank - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank - 1).getLevelScores1() + "\n"
                    + "     time: " + users.get(rank - 1).getLevelTimes1() + "\n";
            rank++;
        }

        ArrayList<String> khh = new ArrayList<>();
        khh.add(first3);
        khh.add(res);
        return khh;
    }

    public static ArrayList<String> scoreBoard2() {
        List<User> users = new ArrayList<>();
        users = (ArrayList) Database.getUsers().clone();

        Comparator<User> comp = Comparator.comparingInt(User::getLevelScores2).reversed()
                .thenComparing(User::getLevelTimes2);
        Collections.sort(users , comp);

        String res = "";
        String first3 = "";
        first3 += """
                level 2 scores:

                """;

        int rank1 = 1;
        while (rank1 - 1 < users.size() && rank1 <= 3) {
            first3 += rank1 + "- " + users.get(rank1 - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank1 - 1).getLevelScores2() + "\n"
                    + "     time: " + users.get(rank1 - 1).getLevelTimes2() + "\n";
            rank1++;
        }

        int rank = 4;
        while (rank - 1 < users.size() && rank <= 7) {
            res += rank + "- " + users.get(rank - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank - 1).getLevelScores2() + "\n"
                    + "     time: " + users.get(rank - 1).getLevelTimes2() + "\n";
            rank++;
        }

        ArrayList<String> khh = new ArrayList<>();
        khh.add(first3);
        khh.add(res);
        return khh;
    }

    public static ArrayList<String> scoreBoard3() {
        List<User> users = new ArrayList<>();
        users = (ArrayList) Database.getUsers().clone();

        Comparator<User> comp = Comparator.comparingInt(User::getLevelScores3).reversed()
                .thenComparing(User::getLevelTimes3);
        Collections.sort(users , comp);

        String res = "";
        String first3 = "";
        first3 += """
                level 3 scores:

                """;
        int rank1 = 1;
        while (rank1 - 1 < users.size() && rank1 <= 3) {
            first3 += rank1 + "- " + users.get(rank1 - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank1 - 1).getLevelScores3() + "\n"
                    + "     time: " + users.get(rank1 - 1).getLevelTimes3() + "\n";
            rank1++;
        }

        int rank = 4;
        while (rank - 1 < users.size() && rank <= 7) {
            res += rank + "- " + users.get(rank - 1).getUsername() + "\n" +
                    "     score: " + users.get(rank - 1).getLevelScores3() + "\n"
                    + "     time: " + users.get(rank - 1).getLevelTimes3() + "\n";
            rank++;
        }

        ArrayList<String> khh = new ArrayList<>();
        khh.add(first3);
        khh.add(res);
        return khh;
    }
}
