package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RPanel extends JPanel {
    private final JList<String> highScoreList;

    public RPanel() {
        setLayout(new BorderLayout());
        highScoreList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(highScoreList);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateHighScores(List<String> highScores) {
        highScoreList.setListData(highScores.toArray(new String[0]));
    }
}
