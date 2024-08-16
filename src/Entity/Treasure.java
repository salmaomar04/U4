package Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Treasure extends BuriedObject {
    private final int points;
    private final List<int[]> coordinates;
    private final Set<String> dugCoordinates; 

    public Treasure(int points, List<int[]> coordinates) {
        super(-1, -1);
        this.points = points;
        this.coordinates = coordinates;
        this.dugCoordinates = new HashSet<>();
    }

    @Override
    public void digObject(Player player, int row, int col) {
        String dugPosition = row + "," + col;
        if (isPartOfTreasure(row, col) && !isDug(row, col)) {
            dugCoordinates.add(dugPosition);
        }
    }

    private boolean isPartOfTreasure(int row, int col) {
        for (int[] coordinate : coordinates) {
            if (coordinate[0] == row && coordinate[1] == col) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDug(int row, int col) {
        String position = row + "," + col;
        return dugCoordinates.contains(position);
    }

    public boolean isCompletelyDug() {

        for (int[] coordinate : coordinates) {
            String position = coordinate[0] + "," + coordinate[1];
            if (!dugCoordinates.contains(position)) {
                return false;
            }
        }
        return true;
    }

    public int getPoints() {
        return points;
    }
}