package Entity;

/**
 * Represents an object that can be buried on the game board.
 * This class provides basic functionality for an object that can be dug up by a player.
 *
 * @author  Salma Omar, Roa Jamhour
 */
public class BuriedObject implements IBuriedObject {
    private final int row;
    private final int col;
    private boolean isDug;

    /**
     * Constructs a BuriedObject at the specified row and column.
     *
     * @param row The row where the object is located.
     * @param col The column where the object is located.
     */
    public BuriedObject(int row, int col) {
        this.row = row;
        this.col = col;
        this.isDug = false;
    }

    /**
     * Digs the object if it is located at the specified row and column and has not been dug yet.
     *
     * @param player The player who is digging the object.
     * @param row The row where the player is digging.
     * @param col The column where the player is digging.
     */
    @Override
    public void digObject(Player player, int row, int col) {
        if (this.row == row && this.col == col && !isDug) {
            isDug = true;
        }
    }

    /**
     * Checks if the object at the specified row and column has been dug.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if the object has been dug at the specified position, otherwise false.
     */
    @Override
    public boolean isDug(int row, int col) {
        return isDug && this.row == row && this.col == col;
    }
}