package Entity;

/**
 * Represents an empty square on the game board.
 * An empty square can be dug but does not provide any points or penalties.
 *
 * @author Salma Omar, Roa Jamhour
 */
public class EmptySquare extends BuriedObject {

    /**
     * Constructs an EmptySquare at the specified row and column.
     *
     * @param row The row where the empty square is located.
     * @param col The column where the empty square is located.
     */
    public EmptySquare(int row, int col) {
        super(row, col);
    }

    /**
     * Digs the empty square if it has not been dug yet.
     *
     * @param player The player who is digging the square.
     * @param row The row where the player is digging.
     * @param col The column where the player is digging.
     */
    @Override
    public void digObject(Player player, int row, int col) {
        if (!isDug(row, col)) {
            super.digObject(player, row, col);
        }
    }
}
