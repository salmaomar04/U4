package Control;

import Entity.*;
import View.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Controller class manages the flow of the game, coordinating actions between
 * the game board, the players, and the user interface.
 *
 * @author Salma Omar & Roa Jamhour
 */
public class Controller {
    private final GameBoard gameBoard;
    private final GameManager gameManager;
    private final MainFrame mainFrame;
    private boolean gameStarted;

    /**
     * Constructor to initialize the game controller. It sets up the game board, player manager,
     * and the main user interface, and starts with the game in a not-started state.
     */
    public Controller() {
        gameBoard = new GameBoard(10);
        gameManager = new GameManager();
        mainFrame = new MainFrame(this);
        gameStarted = false;
        mainFrame.updatePlayerLabel();
    }

    /**
     * Retrieves the game board.
     *
     * @return The GameBoard object representing the game board.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Retrieves the name of the current player.
     *
     * @return The name of the current player, or "No player" if there is an error.
     */
    public String getCurrentPlayerName() {
        try {
            return gameManager.getCurrentPlayer().getName();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return "No player ";
        }
    }

    /**
     * Retrieves the score of the current player.
     *
     * @return The score of the current player, or "0" if there is an error.
     */
    public int getCurrentPlayerScore() {
        try {
            return gameManager.getCurrentPlayer().getScore();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Resets the game to its initial state, clearing the board and resetting the UI.
     */
    public void resetGame() {
        gameBoard.resetBoard();
        mainFrame.updatePlayerLabel();
        mainFrame.getMainPanel().resetBoardUI();
        gameStarted = false;
    }

    /**
     * Starts a new game by resetting the board, setting up players, and updating the UI.
     * If the game is already started, it does nothing.
     */
    public void startGame() {
        if (!gameStarted) {
            gameBoard.resetBoard();
            gameManager.setupPlayers();
            gameStarted = true;
            mainFrame.updatePlayerLabel();
        } else {
            System.out.println("The game has already started.");
        }
    }

    /**
     * Handles the action of digging at a selected square on the game board.
     * It checks if the object is a trap to switch the player's turn, updates the game state,
     * and checks if the game is over.
     */
    public void handleDig() {
        if (gameStarted) {
            int row = gameBoard.getSelectedRow();
            int col = gameBoard.getSelectedCol();

            BuriedObject object = gameBoard.getObjectAt(row, col);
            if (object != null) {
                if (!object.isDug(row, col)) {
                    gameBoard.digObject(gameManager.getCurrentPlayer(), row, col);
                    if (object instanceof Trap) {
                        gameManager.switchToNextPlayer();
                        mainFrame.updatePlayerLabel();
                    }
                    if (gameBoard.allSquaresDug()) {
                        Player winner = determineWinner();
                        JOptionPane.showMessageDialog(mainFrame,
                                "Game Over! The winner is " + winner.getName() + " with " + winner.getScore() + " points.");
                        gameManager.updateHighScores();
                    }
                    mainFrame.updatePlayerLabel();
                    mainFrame.getMainPanel().updateGameBoard();

                } else {
                    System.out.println("Object already dug");
                }
            } else {
                System.out.println("No object found at position: (" + row + ", " + col + ")");
            }
        } else {
            System.out.println("Start the game before digging.");
        }
    }

    /**
     * Determines the winner of the game by sorting the players based on their scores
     * and returning the player with the highest score.
     *
     * @return The Player object representing the winner.
     */
    private Player determineWinner() {
        List<Player> players = new ArrayList<>((Collection) gameManager.getPlayers());
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return players.get(0);
    }

    /**
     * Selects a square on the game board based on the given row and column.
     *
     * @param row The row of the square to select.
     * @param col The column of the square to select.
     */
    public void selectSquare(int row, int col) {
        gameBoard.selectSquare(row, col);
    }

    /**
     * Retrieves the list of high scores.
     *
     * @return A list of Player objects representing the high scores.
     */
    public List<Player> getHighScores() {
        return gameManager.getHighScores();
    }
}