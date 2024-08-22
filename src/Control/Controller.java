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
     *
     * @author Roa Jamhour
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
     * @author Roa Jamhour
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Retrieves the name of the current player.
     *
     * @return The name of the current player, or "No player" if there is an error.
     * @author Roa Jamhour
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
     * @author Roa Jamhour
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
     *
     * @author Salma Omar
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
     *
     * @author Salma Omar
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
     * It checks if the object is a trap then it invokes on a method from mainframe
     * that informs the player that he/she has dug a trap and lost points. It also checks if
     * it's the last part of a treasure shape, then it will invoke on a method from mainframe
     * that informs the player of it. It switches player's turn, updates the game state,
     * and checks if the game is over.
     *
     * @author Salma Omar
     */
    public void handleDig() {
        if (gameStarted) {
            int row = gameBoard.getSelectedRow();
            int col = gameBoard.getSelectedCol();

            BuriedObject object = gameBoard.getObjectAt(row, col);
            if (object != null) {
                if (!object.isDug(row, col)) {
                    gameBoard.digObject(gameManager.getCurrentPlayer(), row, col);

                    if (object instanceof Treasure) {
                        if (((Treasure) object).isCompletelyDug()) {
                            mainFrame.showTreasureMessage();
                        }
                    } else if (object instanceof Trap) {
                        mainFrame.showTrapMessage();
                    }

                    gameManager.switchToNextPlayer();
                    mainFrame.updatePlayerLabel();

                    if (allTreasuresDug()) {
                        Player winner = determineWinner();
                        mainFrame.showGameOverMessage(winner.getName(), winner.getScore());
                        gameStarted = false;
                        gameManager.updateHighScores();
                    }
                    mainFrame.getMainPanel().updateGameBoard();
                } else {
                    System.out.println("Object already dug");
                }
            } else {
                gameManager.switchToNextPlayer();
                mainFrame.updatePlayerLabel();
            }
        } else {
            System.out.println("Start new game before digging!");
        }
    }

    /**
     * This method checks if all treasures are dug.
     * @return boolean, false if there are treasures left or true if all the treasures are dug.
     *
     * @author Salma Omar
     */
    private boolean allTreasuresDug() {
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                BuriedObject object = gameBoard.getObjectAt(row, col);
                if (object instanceof Treasure && !object.isDug(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines the winner of the game by sorting the players based on their scores
     * and returning the player with the highest score.
     *
     * @return The Player object representing the winner.
     *
     * @author Salma Omar
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
     *
     * @author Roa Jamhour
     */
    public void selectSquare(int row, int col) {
        gameBoard.selectSquare(row, col);
    }

    /**
     * Retrieves the list of high scores.
     *
     * @return A list of Player objects representing the high scores.
     *
     * @author Roa Jamhour
     */
    public List<Player> getHighScores() {
        return gameManager.getHighScores();
    }
}