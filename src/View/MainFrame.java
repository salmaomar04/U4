package View;

import Control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JButton digButton;
    private JButton newGameButton;
    private JLabel playerLabel;
    private MainPanel mainPanel;
    private Controller controller;

    public MainFrame(Controller controller) {
        this.controller = controller;

        setTitle("Game Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mainPanel = new MainPanel(this);
        add(mainPanel, BorderLayout.CENTER);

        playerLabel = new JLabel("Current Player: ");
        add(playerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        digButton = new JButton("Dig");
        buttonPanel.add(digButton);

        newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton);

        add(buttonPanel, BorderLayout.SOUTH);

        digButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleDig();
                mainPanel.updateGameBoard();
                updatePlayerLabel();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
                mainPanel.updateGameBoard();
                updatePlayerLabel();
            }
        });

        setVisible(true);
    }

    public void updatePlayerLabel() {
        playerLabel.setText("Current Player: " + controller.getCurrentPlayerName());
    }

    public Controller getController() {
        return controller;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}