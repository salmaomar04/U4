package View;

import Entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RPanel extends JPanel {
    private final JTextArea highScoreTextArea;

    public RPanel() {
        highScoreTextArea = new JTextArea();
        highScoreTextArea.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(highScoreTextArea), BorderLayout.CENTER);
    }

    public void updateHighScores(List<Player> highScores) {
        StringBuilder sb = new StringBuilder();
        for (Player player : highScores) {
            sb.append(player.getName()).append(": ").append(player.getScore()).append("\n");
        }
        highScoreTextArea.setText(sb.toString());
    }
}
