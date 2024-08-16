package View;

import Control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*public class MainFrame extends JFrame {
    private JButton startButton;
    private JButton newGameButton;
    private JButton showHighScoreButton;
    private JLabel playerLabel;
    private MainPanel mainPanel;
    private Controller controller;
    private boolean gameStarted = false;

    public MainFrame(Controller controller) {
        this.controller = controller;

        setTitle("Treasure Hunt Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mainPanel = new MainPanel(this);
        add(mainPanel, BorderLayout.CENTER);

        playerLabel = new JLabel("Current Player: " + controller.getCurrentPlayerName());
        add(playerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        startButton = new JButton("Start");
        newGameButton = new JButton("New Game");
        showHighScoreButton = new JButton("Show High-Score List");

        buttonPanel.add(startButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(showHighScoreButton);

        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameStarted) {
                    gameStarted = true;
                    controller.startGame();
                    updatePlayerLabel();
                    JOptionPane.showMessageDialog(MainFrame.this, "Game started! It's " + controller.getCurrentPlayerName() + "'s turn.");
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "The game has already started.");
                }
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStarted = false;
                controller.resetGame();
                mainPanel.updateGameBoard();
                updatePlayerLabel();
                JOptionPane.showMessageDialog(MainFrame.this, "New game started! Click 'Start' to begin.");
            }
        });

        showHighScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showHighScores(true);
            }
        });

        setVisible(true);
    }

    public void updatePlayerLabel() {
        playerLabel.setText("Current Player: " + controller.getCurrentPlayerName());
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
}*/

public class MainFrame extends JFrame {
    private JLabel playerLabel;
    private MainPanel mainPanel;
    private Controller controller;
    private boolean gameStarted = false;

    public MainFrame(Controller controller) {
        this.controller = controller;

        setTitle("Treasure Hunt Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mainPanel = new MainPanel(this);
        add(mainPanel, BorderLayout.CENTER);

        playerLabel = new JLabel("Current Player: " + controller.getCurrentPlayerName());
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
        playerLabel.setText("Current Player: " + controller.getCurrentPlayerName());
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
}