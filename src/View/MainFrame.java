package View;

import Control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final JLabel playerLabel;
    private final MainPanel mainPanel;
    private final Controller controller;
    private boolean gameStarted = false;

    public MainFrame(Controller controller) {
        this.controller = controller;

        setTitle("Treasure Hunt Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mainPanel = new MainPanel(this);
        add(mainPanel, BorderLayout.CENTER);

        playerLabel = new JLabel(controller.getCurrentPlayerName());
        add(playerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        for (ButtonType type : ButtonType.values()) {
            JButton button = new JButton(type.getLabel());
            button.addActionListener(e -> handleButtonAction(type));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void handleButtonAction(ButtonType type) {
        switch (type) {
            case START:
                gameStarted = true;
                controller.startGame();
                JOptionPane.showMessageDialog(null, "Game started! It's " + controller.getCurrentPlayerName() + "'s turn.");
                break;

            case NEW_GAME:
                gameStarted = false;
                controller.resetGame();
                mainPanel.updateGameBoard();
                updatePlayerLabel();
                JOptionPane.showMessageDialog(null, "New game started! Click 'Start' to begin.");
                break;

            case SHOW_HIGH_SCORE_LIST:
                mainPanel.showHighScores(true);
                break;
        }
    }

    public void updatePlayerLabel() {
        playerLabel.setText(controller.getCurrentPlayerName() + " : " + controller.getCurrentPlayerScore());
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public Controller getController() {
        return controller;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void showGameOverMessage(String playerName, int score) {
        JOptionPane.showMessageDialog(this,
                "Game Over! The winner is " + playerName + " with " + score + " points.");
    }

    public void showTreasureMessage() {
        JOptionPane.showMessageDialog(this, "Yay! " + controller.getCurrentPlayerName() + " got the treasure and earned 100 points!");
    }

    public void showTrapMessage() {
        JOptionPane.showMessageDialog(this, "O noo! " + controller.getCurrentPlayerName() + " has dug a trap and lost 50 points!");
    }
}