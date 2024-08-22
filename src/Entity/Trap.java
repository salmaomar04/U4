package Entity;

/**
 * Represents a trap in the game that penalizes a player when dug.
 * The trap reduces the player's score by a specified number of penalty points.
 *
 * Authors: Salma Omar and Roa Jamhour
 */
public class Trap extends BuriedObject {
    private final int penaltyPoints;

    /**
     * Constructs a new Trap with the given penalty points and location.
     *
     * @param penaltyPoints the number of points to subtract from the player's score when the trap is dug.
     * @param row           the row position of the trap on the game board.
     * @param col           the column position of the trap on the game board.
     * @author Salma Omar
     */
    public Trap(int penaltyPoints, int row, int col) {
        super(row, col);
        this.penaltyPoints = penaltyPoints;
    }

    /**
     * Processes the action of digging the trap.
     * If the trap has not been dug yet, it marks the trap as dug and subtracts penalty points from the player's score.
     *
     * @param player the player who is digging the trap.
     * @param row    the row position where the digging occurs.
     * @param col    the column position where the digging occurs.
     * @author Roa Jamhour
     */
    @Override
    public void digObject(Player player, int row, int col) {
        if (!isDug(row, col)) {
            super.digObject(player, row, col);
            player.addScore(-penaltyPoints);
        }
    }
}
