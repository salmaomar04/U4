package View;

import javax.swing.*;
import java.awt.*;

public class LPanel extends JPanel {
    private final MainPanel mainPanel;
    private final JButton[][] buttons;

    public LPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new GridLayout(10, 10));
        buttons = new JButton[10][10];
        initializeButtons();
    }

    private void initializeButtons() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));

                button.setOpaque(true);
                button.setBorderPainted(true);

                int finalCol = col;
                int finalRow = row;
                button.addActionListener(e -> mainPanel.buttonClicked(finalRow, finalCol));
                buttons[row][col] = button;
                add(button);
            }
        }
    }

    public void updateButton(int row, int col, Color color) {
        JButton button = buttons[row][col];
        button.setBackground(color);

        button.setOpaque(true);
        button.setBorderPainted(true);
        button.repaint();
    }
}
