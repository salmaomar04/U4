package Entity;

public class Player {
    private final String name;
    private int score;

    public Player(String name, int score) {
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
}

