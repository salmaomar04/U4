package Entity;

import javax.swing.*;
import java.util.*;

public class GameBoard {
    private final int size;
    private final BuriedObject[][] board;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public GameBoard(int size) {
        this.size = size;
        board = new BuriedObject[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        Random random = new Random();
        int treasures = 5;
        int traps = 3;

        clearBoard();

        // Place treasures
        for (int i = 0; i < treasures; i++) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                List<int[]> shapeCoords = getTreasureShape(row, col, i);
                if (canPlaceShape(shapeCoords)) {
                    placeTreasure(shapeCoords);
                    placed = true;
                }
            }
        }

        // Place traps
        for (int i = 0; i < traps; i++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            while (board[row][col] != null) {
                row = random.nextInt(size);
                col = random.nextInt(size);
            }
            board[row][col] = new Trap(50, row, col);
        }

        fillEmptySquares();
    }

    public void clearBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = null;
            }
        }
    }

    private void fillEmptySquares() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == null) {
                    board[row][col] = new EmptySquare(row, col);
                }
            }
        }
    }

    private List<int[]> getTreasureShape(int startRow, int startCol, int index) {
        List<int[]> coordinates = new ArrayList<>();
        switch (index) {
            case 0:
                addShape(startRow, startCol, coordinates, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});
                break;
            case 1:
                addShape(startRow, startCol, coordinates, new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}});
                break;
            case 2:
                addShape(startRow, startCol, coordinates, new int[][]{{0, 0}, {0, 1}, {1, 0}, {2, 0}, {2, 1}});
                break;
            case 3:
                addShape(startRow, startCol, coordinates, new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 1}, {2, 1}});
                break;
            case 4:
                addShape(startRow, startCol, coordinates, new int[][]{{0, 0}, {0, 1}, {1, 1}, {1, 2}, {2, 1}});
                break;
        }
        return coordinates;
    }

    private void addShape(int startRow, int startCol, List<int[]> coordinates, int[][] shape) {
        for (int[] offset : shape) {
            int r = startRow + offset[0];
            int c = startCol + offset[1];
            if (r >= 0 && r < size && c >= 0 && c < size) {
                coordinates.add(new int[]{r, c});
            }
        }
    }

    private boolean canPlaceShape(List<int[]> coordinates) {
        for (int[] coord : coordinates) {
            int r = coord[0];
            int c = coord[1];
            if (r < 0 || r >= size || c < 0 || c >= size || board[r][c] != null) {
                return false;
            }
        }
        return true;
    }

    private void placeTreasure(List<int[]> coordinates) {
        Treasure treasure = new Treasure(100, coordinates);
        for (int[] coord : coordinates) {
            int r = coord[0];
            int c = coord[1];
            board[r][c] = treasure;
        }
    }

    public BuriedObject getObjectAt(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return board[row][col];
        }
        return null;
    }

    public void resetBoard() {
        initializeBoard();
    }

    public void selectSquare(int row, int col) {
        selectedRow = row;
        selectedCol = col;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedCol() {
        return selectedCol;
    }

    public void digObject(Player player, int row, int col) {
        BuriedObject object = getObjectAt(row, col);
        if (object != null && !object.isDug(row, col)) {
            object.digObject(player, row, col);

            if (object instanceof Treasure) {
                Treasure treasure = (Treasure) object;
                if (treasure.isCompletelyDug()) {
                    player.addScore(treasure.getPoints());
                    JOptionPane.showMessageDialog(null, "Wohoo! " + player.getName() + " has dug the last part of a treasure and gained " + treasure.getPoints() + " points!");
                }
            } else if (object instanceof Trap) {
                JOptionPane.showMessageDialog(null, "Oh no! " + player.getName() + ", unfortunately, you have dug a trap.");
            }
        }
    }

    public int getSize() {
        return size;
    }
}
