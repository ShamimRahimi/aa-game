package Model;

import static java.lang.System.currentTimeMillis;

public class Game {
    private int frozenModeTimer;
    private double rotationSpeed;
    private double windSpeed;
    private int hardshipLevel;
    private int ballsCount;
    private String language;
    private Long timeStarted;
    private String mapId;
    private String musicNumber;
    private int playersNumber;
    private String shootBallsKey;
    private String moveRightKey;
    private String moveLeftKey;

    public Game() {
        this.hardshipLevel = 2;
        this.frozenModeTimer = 5;
        this.windSpeed = 1.5;
        this.rotationSpeed = 2;
        this.ballsCount = 12;
        this.language = "English";
        this.mapId = "2";
        this.musicNumber = "2";
        this.playersNumber = 1;
        this.shootBallsKey = "Space";
        this.moveLeftKey = "Left";
        this.moveRightKey = "Right";
        this.timeStarted = currentTimeMillis();
    }
    public void start() {

    }
    public int getFrozenModeTimer() {
        return frozenModeTimer;
    }

    public void setFrozenModeTimer(int frozenModeTimer) {
        this.frozenModeTimer = frozenModeTimer;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHardshipLevel() {
        return hardshipLevel;
    }

    public void setHardshipLevel(int hardshipLevel) {
        this.hardshipLevel = hardshipLevel;
        this.rotationSpeed = hardshipLevel * 0.5 + 0.5;
        this.windSpeed = (hardshipLevel + 3) * 0.3;
        this.frozenModeTimer = (-2 * hardshipLevel) + 9;
    }

    public int getBallsCount() {
        return ballsCount;
    }

    public void setBallsCount(int ballsCount) {
        this.ballsCount = ballsCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getTimeStarted() {
        return timeStarted;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMusicNumber() {
        return musicNumber;
    }

    public void setMusicNumber(String musicNumber) {
        this.musicNumber = musicNumber;
    }

    public void setTimeStarted(long l) {
        this.timeStarted = l;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public String getShootBallsKey() {
        return shootBallsKey;
    }

    public void setShootBallsKey(String shootBallsKey) {
        this.shootBallsKey = shootBallsKey;
    }

    public String getMoveRightKey() {
        return moveRightKey;
    }

    public void setMoveRightKey(String moveRightKey) {
        this.moveRightKey = moveRightKey;
    }

    public String getMoveLeftKey() {
        return moveLeftKey;
    }

    public void setMoveLeftKey(String moveLeftKey) {
        this.moveLeftKey = moveLeftKey;
    }
}
