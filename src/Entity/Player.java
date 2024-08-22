package Entity;

/**
 * Represents a player in the game with a name and a score.
 * Players can have their scores updated as they play the game.
 *
 * Authors: Salma Omar and Roa Jamhour
 */
public class Player {
    private final String name;
    private int score;

    /**
     * Constructs a new Player with the specified name and an initial score of 0.
     *
     * @param name  the name of the player.
     * @param score the initial score of the player.
     *
     * @author Salma Omar
     */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name.
     * @author Roa Jamhour
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current score of the player.
     *
     * @return the player's score.
     * @author Salma Omar
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds the specified number of points to the player's score.
     *
     * @param points the number of points to add to the player's score.
     *
     * @author Salma Omar
     */
    public void addScore(int points) {
        score += points;
    }
}

