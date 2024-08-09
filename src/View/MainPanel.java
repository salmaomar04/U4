package View;

import Entity.BuriedObject;
import Entity.EmptySquare;
import Entity.Trap;
import Entity.Treasure;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final LPanel lPanel;
    private final RPanel rPanel;
    private final MainFrame mainFrame;

    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        lPanel = new LPanel(this);
        rPanel = new RPanel();

        add(lPanel, BorderLayout.CENTER);
        add(rPanel, BorderLayout.EAST);
    }

    public void buttonClicked(int row, int col) {
        mainFrame.getController().selectSquare(row, col);
        mainFrame.getController().handleDig();
    }

    public void updateGameBoard() {

        for (int row = 0; row < mainFrame.getController().getGameBoard().getSize(); row++) {
            for (int col = 0; col < mainFrame.getController().getGameBoard().getSize(); col++) {
                BuriedObject object = mainFrame.getController().getGameBoard().getObjectAt(row, col);
                if (object != null && object.isDug(row, col)) {
                    if (object instanceof Treasure) {
                        lPanel.updateButton(row, col, Color.GREEN); // Treasure found
                    } else if (object instanceof Trap) {
                        lPanel.updateButton(row, col, Color.RED); // Trap found
                    } else if (object instanceof EmptySquare) {
                        lPanel.updateButton(row, col, Color.white); // Empty square
                    }
                } else if (object == null) {
                    lPanel.updateButton(row, col, Color.GRAY);
                }
            }
        }
        rPanel.updateHighScores(mainFrame.getController().getHighScores());
    }
}