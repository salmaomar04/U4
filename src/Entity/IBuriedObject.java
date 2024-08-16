package Entity;

/**
 * The IBuriedObject interface represents objects that can be buried in the game.
 * Implementing classes should define how the object is dug up and whether it has been dug.
 *
 * Authors: Salma Omar and Roa Jamhour
 */
public interface IBuriedObject {

    /**
     * Handles the action of digging the object at a specific location.
     *
     * @param player the player who is digging the object.
     * @param row    the row position of the object.
     * @param col    the column position of the object.
     * @author Roa Jamhour
     */
    void digObject(Player player, int row, int col);

    /**
     * Checks if the object at the given location has already been dug.
     *
     * @param row the row position of the object.
     * @param col the column position of the object.
     * @return true if the object has been dug at the specified location, false otherwise.
     *
     * @author Salma Omar
     */

    boolean isDug(int row, int col);
}
