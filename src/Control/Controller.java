package Control;

import Entity.*;
import View.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Controller {
    private final GameBoard gameBoard;
    private final GameManager gameManager;
    private final MainFrame mainFrame;
    private boolean gameStarted;

    public Controller() {
        gameBoard = new GameBoard(10);
        gameManager = new GameManager();
        mainFrame = new MainFrame(this);
        gameStarted = false;
        mainFrame.updatePlayerLabel();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public String getCurrentPlayerName() {
        try {
            return gameManager.getCurrentPlayer().getName();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return "No player";
        }
    }

    public void resetGame() {
        gameBoard.resetBoard();
        mainFrame.updatePlayerLabel();
        mainFrame.getMainPanel().resetBoardUI();
        gameStarted = false;
    }

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

    public void handleDig() {
        if (gameStarted) {
            int row = gameBoard.getSelectedRow();
            int col = gameBoard.getSelectedCol();

            BuriedObject object = gameBoard.getObjectAt(row, col);
            if (object != null) {
                if (!object.isDug(row, col)) {
                    gameBoard.digObject(gameManager.getCurrentPlayer(), row, col);

                    if (gameBoard.allSquaresDug()) {
                        Player winner = determineWinner();
                        JOptionPane.showMessageDialog(mainFrame,
                                "Game Over! The winner is " + winner.getName() + " with " + winner.getScore() + " points.");
                        gameManager.updateHighScores();
                    } else {
                        gameManager.switchToNextPlayer();
                        mainFrame.updatePlayerLabel();
                    }
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

    private Player determineWinner() {
        List<Player> players = new ArrayList<>((Collection) gameManager.getPlayers());
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return players.get(0);
    }

    public void selectSquare(int row, int col) {
        gameBoard.selectSquare(row, col);
    }

    public List<Player> getHighScores() {
        return gameManager.getHighScores();
    }
}