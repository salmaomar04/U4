package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(750,750);
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);
        frame.setVisible(true);

        /*JFrame frame = new JFrame();
        frame.setTitle("Skattjakt 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);

        int rows = 10;
        int cols = 10;
        frame.setLayout(new GridLayout(rows,cols));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frame.add(new JButton());
            }
        }
        frame.setVisible(true);

        JFrame frame1 = new JFrame();
        frame1.setTitle("Skattjakt 2");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(600,400);
        frame1.setLocation(0,600);

        int rows1 = 10;
        int cols1 = 10;
        frame1.setLayout(new GridLayout(rows1, cols1));

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                frame1.add(new JButton());
            }
        }
        frame1.setVisible(true);

        JFrame frame2 = new JFrame();
        frame2.setTitle("High-score lista");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(200, 800);
        frame2.setLocation(600,0);
        frame2.setVisible(true);*/
    }
}
