package Control;

import Entity.BuriedObject;
import Entity.GameBoard;
import Entity.GameManager;
import View.MainFrame;

import java.util.List;

public class Controller {
    private final GameBoard gameBoard;
    private final GameManager gameManager;
    private final MainFrame mainFrame;

    public Controller() {
        gameBoard = new GameBoard(10);
        gameManager = new GameManager();
        mainFrame = new MainFrame(this);
        gameManager.setupPlayers();
        mainFrame.updatePlayerLabel();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public String getCurrentPlayerName() {
        return gameManager.getCurrentPlayer().getName();
    }

    public void resetGame() {
        gameManager.saveHighScores();
        gameBoard.resetBoard();
        gameManager.resetScores();
        gameManager.setupPlayers();
        mainFrame.updatePlayerLabel();
        mainFrame.getMainPanel().updateGameBoard();
    }

    //jag tror jag behöver ha en startgame method

    public void handleDig() {
        System.out.println("Reached handle dig method");
        int row = gameBoard.getSelectedRow();
        int col = gameBoard.getSelectedCol();
        System.out.println("Selected position: (" + row + ", " + col + ")");

        BuriedObject object = gameBoard.getObjectAt(row, col);
        if (object != null) {
            System.out.println("Object found: " + object.getClass().getSimpleName());
            if (!object.isDug(row, col)) {
                System.out.println("Object has not been dug yet");
                gameBoard.digObject(gameManager.getCurrentPlayer(), row, col);
                gameManager.updateHighScores();
                gameManager.switchToNextPlayer();
                mainFrame.updatePlayerLabel();
                mainFrame.getMainPanel().updateGameBoard(); // Refresh the game board
            } else {
                System.out.println("Object already dug");
            }
        } else {
            System.out.println("No object found at position");
        }
    }

    public void selectSquare(int row, int col) {
        gameBoard.selectSquare(row, col);
    }

    public List<String> getHighScores() {
        return gameManager.getHighScores();
    }
}
