package Entity;

import javax.swing.*;
import java.util.*;

/**
 * Represents the game board which holds all buried objects including treasures and traps.
 * The GameBoard is responsible for managing the placement and digging of objects.
 *
 * @author  Salma Omar, Roa Jamhour
 */
public class GameBoard {
    private final int size;
    private final BuriedObject[][] board;
    private int selectedRow = -1;
    private int selectedCol = -1;

    /**
     * Constructs a GameBoard of the specified size.
     *
     * @param size The size of the game board (number of rows and columns).
     */
    public GameBoard(int size) {
        this.size = size;
        board = new BuriedObject[size][size];
        initializeBoard();
    }

    /**
     * Clears the board by setting all squares to null.
     */
    public void clearBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = null;
            }
        }
    }

    /**
     * Fills all empty squares on the board with EmptySquare objects.
     */
    private void fillEmptySquares() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == null) {
                    board[row][col] = new EmptySquare(row, col);
                }
            }
        }
    }

    /**
     * Places a treasure at the specified coordinates on the board.
     *
     * @param coordinates A list of coordinates where the treasure will be placed.
     */
    private void placeTreasure(List<int[]> coordinates) {
        Treasure treasure = new Treasure(100, coordinates);
        for (int[] coord : coordinates) {
            int r = coord[0];
            int c = coord[1];
            board[r][c] = treasure;
        }
    }

    /**
     * Predefined shapes for the treasures on the game board.
     * Each shape is represented as a set of coordinates.
     */
    private static final int[][][] FIXED_TREASURE_SHAPES = {
            {{0, 0}, {0, 1}},
            {{0, 0}, {0, 1}, {0, 2}},
            {{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            {{0, 0}, {0, 1}, {0, 2}, {1, 1}, {2, 1}},
            {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {2, 1}}
    };

    /**
     * Returns a list of predefined shapes for treasures.
     *
     * @return A list of predefined treasure shapes.
     */
    private List<int[][]> getFixedTreasureShapes() {
        List<int[][]> shapes = new ArrayList<>();
        for (int[][] shape : FIXED_TREASURE_SHAPES) {
            shapes.add(shape);
        }
        return shapes;
    }

    /**
     * Initializes the game board by placing treasures, traps, and filling empty squares.
     */
    private void initializeBoard() {
        Random random = new Random();
        List<int[][]> shapes = getFixedTreasureShapes();
        Collections.shuffle(shapes, random);

        clearBoard();

        for (int[][] shape : shapes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                List<int[]> shapeCoords = convertShapeCoordinates(shape, row, col);

                if (canPlaceShapeWithSpacing(shapeCoords)) {  //this is for spacing
                    placeTreasure(shapeCoords);
                    placed = true;
                }
            }
        }

        // Place traps
        for (int i = 0; i < 3; i++) {
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

    /**
     * Converts a shape's coordinates relative to a starting position on the board.
     *
     * @param shape The shape's local coordinates.
     * @param startRow The starting row for the shape.
     * @param startCol The starting column for the shape.
     * @return A list of global coordinates for the shape.
     */
    private List<int[]> convertShapeCoordinates(int[][] shape, int startRow, int startCol) {
        List<int[]> shapeCoords = new ArrayList<>();
        for (int[] coord : shape) {
            int row = startRow + coord[0];
            int col = startCol + coord[1];
            shapeCoords.add(new int[]{row, col});
        }
        return shapeCoords;
    }

    /**
     * Checks if a shape can be placed on the board with proper spacing between other shapes.
     *
     * @param shapeCoords The coordinates of the shape to be placed.
     * @return True if the shape can be placed, otherwise false.
     */
    private boolean canPlaceShapeWithSpacing(List<int[]> shapeCoords) {
        for (int[] coord : shapeCoords) {
            int row = coord[0];
            int col = coord[1];
            if (row < 0 || row >= size || col < 0 || col >= size || board[row][col] != null) {
                return false;
            }

            for (int r = Math.max(0, row - 1); r <= Math.min(size - 1, row + 1); r++) {
                for (int c = Math.max(0, col - 1); c <= Math.min(size - 1, col + 1); c++) {
                    if (board[r][c] != null && !shapeCoords.contains(new int[]{r, c})) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retrieves the object at the specified row and column on the board.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return The object at the specified location, or null if there is no object.
     */
    public BuriedObject getObjectAt(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return board[row][col];
        }
        return null;
    }

    /**
     * Resets the game board by reinitializing it.
     */
    public void resetBoard() {
        initializeBoard();
    }

    /**
     * Selects a square on the board, storing the selected row and column.
     *
     * @param row The row to select.
     * @param col The column to select.
     */
    public void selectSquare(int row, int col) {
        selectedRow = row;
        selectedCol = col;
    }

    /**
     * Gets the row that is currently selected on the board.
     *
     * @return the selected row, or -1 if no row is selected.
     */
    public int getSelectedRow() {
        return selectedRow;
    }

    /**
     * Gets the column that is currently selected on the board.
     *
     * @return the selected column, or -1 if no column is selected.
     */
    public int getSelectedCol() {
        return selectedCol;
    }

    /**
     * Lets a player dig at a specific spot on the board.
     * Depending on what's buried there, the player might find treasure or trigger a trap.
     *
     * @param player the player who is digging.
     * @param row the row to dig in.
     * @param col the column to dig in.
     */
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

    /**
     * Gets the size of the game board (number of rows or columns).
     *
     * @return the size of the board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if all squares on the board have been dug.
     *
     * @return true if all squares are dug; false if any are still buried.
     */
    public boolean allSquaresDug() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                BuriedObject object = board[row][col];
                if (object != null && !object.isDug(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }
}
