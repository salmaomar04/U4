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

    private void placeTreasure(List<int[]> coordinates) {
        Treasure treasure = new Treasure(100, coordinates);
        for (int[] coord : coordinates) {
            int r = coord[0];
            int c = coord[1];
            board[r][c] = treasure;
        }
    }

    private static final int[][][] FIXED_TREASURE_SHAPES = {
            {{0, 0}, {0, 1}},
            {{0, 0}, {0, 1}, {0, 2}},
            {{0, 0}, {0, 1}, {1, 0}, {1, 1}},
            {{0, 0}, {0, 1}, {0, 2}, {1, 1}, {2, 1}},
            {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {2, 1}}
    };

    private List<int[][]> getFixedTreasureShapes() {
        List<int[][]> shapes = new ArrayList<>();
        for (int[][] shape : FIXED_TREASURE_SHAPES) {
            shapes.add(shape);
        }
        return shapes;
    }

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

    private List<int[]> convertShapeCoordinates(int[][] shape, int startRow, int startCol) {
        List<int[]> shapeCoords = new ArrayList<>();
        for (int[] coord : shape) {
            int row = startRow + coord[0];
            int col = startCol + coord[1];
            shapeCoords.add(new int[]{row, col});
        }
        return shapeCoords;
    }

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
