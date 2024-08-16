package Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a treasure in the game that players can dig to gain points.
 * The treasure consists of multiple parts, and a player earns points when all parts are dug.
 *
 * Authors: Salma Omar and Roa Jamhour
 */
public class Treasure extends BuriedObject {
    private final int points;
    private final List<int[]> coordinates;
    private final Set<String> dugCoordinates;

    /**
     * Constructs a new Treasure with the given points and coordinates.
     *
     * @param points      the number of points awarded when the entire treasure is dug.
     * @param coordinates a list of coordinates representing the parts of the treasure on the game board.
     */
    public Treasure(int points, List<int[]> coordinates) {
        super(-1, -1);
        this.points = points;
        this.coordinates = coordinates;
        this.dugCoordinates = new HashSet<>();
    }

    /**
     * Marks the specified position as dug if it is part of this treasure.
     *
     * @param player the player who is digging.
     * @param row    the row position where the digging occurs.
     * @param col    the column position where the digging occurs.
     */
    @Override
    public void digObject(Player player, int row, int col) {
        String dugPosition = row + "," + col;
        if (isPartOfTreasure(row, col) && !isDug(row, col)) {
            dugCoordinates.add(dugPosition);
        }
    }

    /**
     * Checks if the specified position is part of this treasure.
     *
     * @param row the row position to check.
     * @param col the column position to check.
     * @return true if the position is part of the treasure, false otherwise.
     */
    private boolean isPartOfTreasure(int row, int col) {
        for (int[] coordinate : coordinates) {
            if (coordinate[0] == row && coordinate[1] == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the specified position has been dug.
     *
     * @param row the row position to check.
     * @param col the column position to check.
     * @return true if the position has been dug, false otherwise.
     */
    @Override
    public boolean isDug(int row, int col) {
        String position = row + "," + col;
        return dugCoordinates.contains(position);
    }

    /**
     * Checks if all parts of this treasure have been dug.
     *
     * @return true if all parts are dug, false otherwise.
     */
    public boolean isCompletelyDug() {

        for (int[] coordinate : coordinates) {
            String position = coordinate[0] + "," + coordinate[1];
            if (!dugCoordinates.contains(position)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the number of points awarded for digging this treasure.
     *
     * @return the number of points.
     */
    public int getPoints() {
        return points;
    }
}