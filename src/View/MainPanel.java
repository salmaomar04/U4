package View;

import Entity.*;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class MainPanel extends JPanel {
    private final LPanel lPanel;
    private final RPanel rPanel;
    private final MainFrame mainFrame;
    private boolean showingHighScores = false;

    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        lPanel = new LPanel(this);
        rPanel = new RPanel();

        add(lPanel, BorderLayout.CENTER);
        add(rPanel, BorderLayout.EAST);
    }

    public void buttonClicked(int row, int col) {
        if (mainFrame.isGameStarted()) {
            mainFrame.getController().selectSquare(row, col);
            mainFrame.getController().handleDig();
            updateGameBoard();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Click 'Start' to begin the game.");
        }
    }

    public void updateGameBoard() {

        for (int row = 0; row < mainFrame.getController().getGameBoard().getSize(); row++) {
            for (int col = 0; col < mainFrame.getController().getGameBoard().getSize(); col++) {
                BuriedObject object = mainFrame.getController().getGameBoard().getObjectAt(row, col);
                if (object != null && object.isDug(row, col)) {
                    if (object instanceof Treasure) {
                        lPanel.updateButton(row, col, Color.GREEN);
                    } else if (object instanceof Trap) {
                        lPanel.updateButton(row, col, Color.RED);
                    } else if (object instanceof EmptySquare) {
                        lPanel.updateButton(row, col, Color.WHITE);
                    }
                } else if (object == null) {
                    lPanel.updateButton(row, col, Color.GRAY);
                }
            }
        }

        if (showingHighScores) {
            List<Player> highScores = mainFrame.getController().getHighScores();
            rPanel.updateHighScores(highScores);
        }
    }

    public void showHighScores(boolean show) {
        showingHighScores = show;
        if (show) {
            rPanel.updateHighScores(mainFrame.getController().getHighScores());
        } else {
            rPanel.updateHighScores(Collections.emptyList());
        }
        revalidate();
        repaint();
    }

    public void resetBoardUI() {
        for (int row = 0; row < mainFrame.getController().getGameBoard().getSize(); row++) {
            for (int col = 0; col < mainFrame.getController().getGameBoard().getSize(); col++) {
                lPanel.updateButton(row, col, Color.GRAY);
            }
        }
    }
}