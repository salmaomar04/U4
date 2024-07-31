package Entity;

public class Player {
    private final String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public void loseScore(int points) {
        score -= points;
    }

    public void resetScore() {
        score = 0;
    }
}
